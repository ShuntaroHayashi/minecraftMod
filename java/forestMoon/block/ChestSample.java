package forestMoon.block;

import java.util.Random;

import cpw.mods.fml.common.registry.GameRegistry;
import forestMoon.ForestMoon;
import forestMoon.client.entity.TileEntityChestSample;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ChestSample extends Block implements ITileEntityProvider{
	private Random random = new Random();

	public ChestSample(){
		super(Material.rock);
		String name = "ChestSample";
		this.setBlockName(name);
		this.setBlockTextureName("forestmoon:"+name);
		this.setCreativeTab(ForestMoon.forestmoontab);
		this.setHardness(5.0F);
		this.setResistance(1.0F);
		this.setStepSound(soundTypeMetal);
		isBlockContainer = true;
		GameRegistry.registerBlock(this, name);
		GameRegistry.registerTileEntity(TileEntityChestSample.class, "TileEntityChestSample");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityChestSample();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		// TileEntityを取得し、プレイヤーが手にアイテムを持っていないなら取り出し、持っているなら入れる。
//		TileEntity tileEntity = world.getTileEntity(x, y, z);
//		if (tileEntity == null || !(tileEntity instanceof TileEntityChestSample))
//			return false;
//		TileEntityChestSample chest = (TileEntityChestSample) tileEntity;
//		if (player.getHeldItem() == null) {
//			player.inventory.mainInventory[player.inventory.currentItem] = chest.tryExportItemStack();
//		} else {
//			if (chest.tryImportItemStack(player.getHeldItem())) {
//				player.inventory.mainInventory[player.inventory.currentItem] = null;
//			}
//		}
//		return true;


		// GUIを開く。
		player.openGui(ForestMoon.instance, 1, world, x, y, z);
		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		// TileEntityの内部にあるアイテムをドロップさせる。
		TileEntityChestSample tileentity = (TileEntityChestSample) world.getTileEntity(x, y, z);
		if (tileentity != null) {
			for (int i = 0; i < tileentity.getSizeInventory(); i++) {
				ItemStack itemStack = tileentity.getStackInSlot(i);

				if (itemStack != null) {
					float f = random.nextFloat() * 0.6F + 0.1F;
					float f1 = random.nextFloat() * 0.6F + 0.1F;
					float f2 = random.nextFloat() * 0.6F + 0.1F;

					while (itemStack.stackSize > 0) {
						int j = random.nextInt(21) + 10;

						if (j > itemStack.stackSize) {
							j = itemStack.stackSize;
						}

						itemStack.stackSize -= j;
						EntityItem entityItem = new EntityItem(world, x + f, y + f1, z + f2,
								new ItemStack(itemStack.getItem(), j, itemStack.getItemDamage()));

						if (itemStack.hasTagCompound()) {
							entityItem.getEntityItem()
									.setTagCompound(((NBTTagCompound) itemStack.getTagCompound().copy()));
						}

						float f3 = 0.025F;
						entityItem.motionX = (float) random.nextGaussian() * f3;
						entityItem.motionY = (float) random.nextGaussian() * f3 + 0.1F;
						entityItem.motionZ = (float) random.nextGaussian() * f3;
						world.spawnEntityInWorld(entityItem);
					}
				}
			}
			world.func_147453_f(x, y, z, block);
		}
		super.breakBlock(world, x, y, z, block, meta);
	}
}
