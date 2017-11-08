package forestMoon.item.items;

import cpw.mods.fml.common.registry.GameRegistry;
import forestMoon.ForestMoon;
import forestMoon.client.entity.EntityECVillager;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemECVillagerEgg extends Item{

	public ItemECVillagerEgg() {
		String name = "VillagerEgg";
		//クリエイティブタブの登録
		this.setCreativeTab(ForestMoon.forestmoontab);
		//システム名の登録
		this.setUnlocalizedName(name);
		//テクスチャ名の登録
		this.setTextureName("forestmoon:"+name);
		//GameRegistryへの登録
		GameRegistry.registerItem(this, name);
	}


	/**アイテムでブロックを右クリックしたのメソッド。ItemMonsterPlacer参照。*/
    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float posX, float posY, float posZ)
    {
    	//サーバー側の場合は処理をスキップする
        if (world.isRemote)
        {
            return true;
        }
        else
        {
            net.minecraft.block.Block block = world.getBlock(x, y, z);
            x += Facing.offsetsXForSide[side];
            y += Facing.offsetsYForSide[side];
            z += Facing.offsetsZForSide[side];
            double height = 0.0D;

            if (side == 1 && block.getRenderType() == 11)
            {
                height = 0.5D;
            }

            Entity entity = spawnEntity(world, (double)x + 0.5D, (double)y + height, (double)z + 0.5D);

            if (entity != null)
            {

                if (!player.capabilities.isCreativeMode)
                {
                    --itemStack.stackSize;
                }
            }

            return true;
        }
    }

    /**アイテムを使ったときのメソッド。ItemMonsterPlacer参照。*/
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
    	//サーバー側の場合は処理をスキップする
        if (world.isRemote)
        {
            return itemStack;
        }
        else
        {
            MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

            if (movingobjectposition == null)
            {
                return itemStack;
            }
            else
            {
                if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
                {
                    int x = movingobjectposition.blockX;
                    int y = movingobjectposition.blockY;
                    int z = movingobjectposition.blockZ;

                    if (!world.canMineBlock(player, x, y, z))
                    {
                        return itemStack;
                    }

                    if (!player.canPlayerEdit(x, y, z, movingobjectposition.sideHit, itemStack))
                    {
                        return itemStack;
                    }

                    if (world.getBlock(x, y, z) instanceof BlockLiquid)
                    {
                        Entity entity = spawnEntity(world, (double)x, (double)y, (double)z);

                        if (entity != null)
                        {
                            if (!player.capabilities.isCreativeMode)
                            {
                                --itemStack.stackSize;
                            }
                        }
                    }
                }

                return itemStack;
            }
        }
    }

    /**Mobをスポーンさせるメソッド*/
    public Entity spawnEntity(World world, double x, double y, double z)
    {
    	EntityECVillager entityliving = new EntityECVillager(world);
        entityliving.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
        entityliving.rotationYawHead = entityliving.rotationYaw;
        entityliving.renderYawOffset = entityliving.rotationYaw;
        entityliving.onSpawnWithEgg((IEntityLivingData)null);
        world.spawnEntityInWorld(entityliving);
        entityliving.playLivingSound();
        return entityliving;
    }
}
