package forestMoon;

import java.util.Random;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import forestMoon.Items.ItemRegister;
import forestMoon.packet.MessagePlayerJoinInAnnouncement;
import forestMoon.packet.MessagePlayerProperties;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BlockEvent;

public class SampleEntityPropertiesEventHandler {

	private Random random = new Random();

    /*IExtendedEntityPropertiesを登録する処理を呼び出す*/
    @SubscribeEvent
    public void onEntityConstructing(EntityEvent.EntityConstructing event) {
        if (event.entity instanceof EntityPlayer) {
            ExtendedPlayerProperties.register((EntityPlayer)event.entity);
        }
    }

    @SubscribeEvent
    /*ワールドに入った時に呼ばれるイベント。ここでIExtendedEntityPropertiesを読み込む処理を呼び出す*/
    public void onEntityJoinWorld(EntityJoinWorldEvent event)  {
        if (event.world.isRemote && event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)event.entity;
            PacketHandler.INSTANCE.sendToServer(new MessagePlayerJoinInAnnouncement(player));
        }
    }

   @SubscribeEvent
   //Dimension移動時や、リスポーン時に呼ばれるイベント。古いインスタンスと新しいインスタンスの両方を参照できる。
   public void onClonePlayer(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
        //死亡時に呼ばれてるかどうか
        if (event.wasDeath) {
            //古いカスタムデータ
            IExtendedEntityProperties oldEntityProperties = event.original.getExtendedProperties(ExtendedPlayerProperties.EXT_PROP_NAME);
            //新しいカスタムデータ
            IExtendedEntityProperties newEntityProperties = event.entityPlayer.getExtendedProperties(ExtendedPlayerProperties.EXT_PROP_NAME);
            NBTTagCompound playerData = new NBTTagCompound();
            //データの吸い出し
            oldEntityProperties.saveNBTData(playerData);
            Integer money = ExtendedPlayerProperties.get(event.original).getMoney();
            //データの書き込み
            newEntityProperties.loadNBTData(playerData);
            ExtendedPlayerProperties.get(event.entityPlayer).setMoney(money / 2,event.entityPlayer);
//            System.out.println("money:" + money);
            money /= 2;
            EntityPlayer entityPlayer = event.original;

        	for (int i = 0; i < 3; i++) {

            	ItemStack itemStack = new ItemStack(ItemRegister.ItemCoin,0,2-i);
				itemStack.setItemDamage(2-i);
				int stackSize = 0;
				while(money>metaToCoin(2-i)){
					money -= metaToCoin(2-i);
					System.out.println(money);
					stackSize++;
				}
				itemStack.stackSize = stackSize;
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

						EntityItem entityItem = new EntityItem(entityPlayer.worldObj, entityPlayer.lastTickPosX + f, entityPlayer.lastTickPosY + f1, entityPlayer.lastTickPosZ + f2,
								new ItemStack(itemStack.getItem(), j, itemStack.getItemDamage()));
						System.out.println(itemStack.getItem().getUnlocalizedName() + "/" + j +"/" + itemStack.getItemDamage());


						float f3 = 0.025F;
						entityItem.motionX = (float) random.nextGaussian() * f3;
						entityItem.motionY = (float) random.nextGaussian() * f3 + 0.1F;
						entityItem.motionZ = (float) random.nextGaussian() * f3;
						event.entityPlayer.worldObj.spawnEntityInWorld(entityItem);
					}

				}
			}

        }
    }


   @SubscribeEvent
   /*ブロックの破壊時*/
   public void breakBlock(BlockEvent.BreakEvent event){
	   ExtendedPlayerProperties properties = ExtendedPlayerProperties.get(event.getPlayer());
	   properties.setMoney(properties.getMoney() + 10,event.getPlayer());
	   properties.syncPlayerData(event.getPlayer());
	   System.out.println("money:"+ properties.getMoney());
   }
	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event) {

		// 別のMOD等でキャンセル済みの場合はなにもしない
		if (event.isCancelable() && event.isCanceled()) {
			return;
		}

		// 別のMOD等で不可とされた場合はなにもしない
		if (event.getResult() == Result.DENY) {
			return;
		}

		// なぜかダメージソースがnullだった場合はなにもしない
		if (event.source.getSourceOfDamage() == null || event.source.getEntity() == null) {
			return;
		}

		// ダメージソースがプレイヤーの場合はMP加算する
		if (event.source.getSourceOfDamage() instanceof EntityPlayerMP) {
			EntityPlayerMP entityPlayer = (EntityPlayerMP) event.source.getSourceOfDamage();
			   ExtendedPlayerProperties properties = ExtendedPlayerProperties.get(entityPlayer);
			   properties.setMoney(properties.getMoney() + 10,entityPlayer);
			   properties.syncPlayerData(entityPlayer);
			   System.out.println("money:"+ properties.getMoney());


		} else if (event.source.getEntity() instanceof EntityPlayerMP) {
			EntityPlayerMP entityPlayer = (EntityPlayerMP) event.source.getSourceOfDamage();
			   ExtendedPlayerProperties properties = ExtendedPlayerProperties.get(entityPlayer);
			   properties.setMoney(properties.getMoney() + 10,entityPlayer);
			   properties.syncPlayerData(entityPlayer);
			   System.out.println("money:"+ properties.getMoney());
		}
	}

	private int metaToCoin(int meta) {
		int work = 0;

		switch (meta) {
			case 0:work = 100;
			break;

			case 1:work=1000;
			break;

			case 2: work = 10000;
			break;

		default:
			break;
		}

		return work;
	}

    @SubscribeEvent
    /*リスポーン時に呼ばれるイベント。Serverとの同期を取る*/
    public void respawnEvent(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.player.worldObj.isRemote) {
            PacketHandler.INSTANCE.sendTo(new MessagePlayerProperties(event.player), (EntityPlayerMP)event.player);
        }
    }
}