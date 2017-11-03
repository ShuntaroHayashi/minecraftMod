package forestMoon.event;

import java.util.Random;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import forestMoon.ExtendedPlayerProperties;
import forestMoon.item.ItemRegister;
import forestMoon.item.Items.ItemCoin;
import forestMoon.packet.MessagePlayerJoinInAnnouncement;
import forestMoon.packet.MessagePlayerProperties;
import forestMoon.packet.PacketHandler;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BlockEvent;

public class EntityPropertiesEventHandler {

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

        	System.out.println("死亡時/" + event.entityPlayer.getClass());

            //古いカスタムデータ
            IExtendedEntityProperties oldEntityProperties = event.original.getExtendedProperties(ExtendedPlayerProperties.EXT_PROP_NAME);
            //新しいカスタムデータ
            IExtendedEntityProperties newEntityProperties = event.entityPlayer.getExtendedProperties(ExtendedPlayerProperties.EXT_PROP_NAME);
            NBTTagCompound playerData = new NBTTagCompound();

            //データの吸い出し
            oldEntityProperties.saveNBTData(playerData);
            long money = ExtendedPlayerProperties.get(event.original).getMoney();
            //データの書き込み
            newEntityProperties.loadNBTData(playerData);
            ExtendedPlayerProperties.get(event.entityPlayer).setMoney(money / 2,event.entityPlayer);


            //コインドロップ処理
            money /= 2;
            float work = random.nextFloat();
            if(work < 0.5){
            	work = 0.5F;
            }
            money *= work;

            EntityPlayer deathPlayer = event.original;
        	for (int i = 0; i < 7; i++) {

        		//Itemの作成
            	ItemStack itemStack = new ItemStack(ItemRegister.ItemCoin,0,6-i);
				itemStack.setItemDamage(6-i);
				//ドロップ数計算
				int stackSize = 0;
				while(money>ItemCoin.metaToCoin(6-i)){
					money -= ItemCoin.metaToCoin(6-i);
					stackSize++;
				}
				itemStack.stackSize = stackSize;
				//ドロップ処理
				if (itemStack != null) {
					//ドロップアイテムを散らばす為の変数
					float f = random.nextFloat() * 0.6F + 0.1F;
					float f1 = random.nextFloat() * 0.6F + 0.1F;
					float f2 = random.nextFloat() * 0.6F + 0.1F;
					//アイテムの一つ当たりのドロップ数
					while (itemStack.stackSize > 0) {
						int j = random.nextInt(21) + 10;

						if (j > itemStack.stackSize) {
							j = itemStack.stackSize;
						}

						itemStack.stackSize -= j;

						//ドロップさせるための各種変数
						World world = deathPlayer.worldObj;
						double x = deathPlayer.lastTickPosX;
						double y = deathPlayer.lastTickPosY;
						double z = deathPlayer.lastTickPosZ;

						//ドロップ処理
						EntityItem entityItem = new EntityItem(world, x + f, y + f1, z + f2,
								new ItemStack(itemStack.getItem(), j, itemStack.getItemDamage()));

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
//	   ExtendedPlayerProperties properties = ExtendedPlayerProperties.get(event.getPlayer());
//	   properties.setMoney(properties.getMoney() + 10,event.getPlayer());
//	   properties.syncPlayerData(event.getPlayer());
//	   System.out.println("money:"+ properties.getMoney());
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
//			EntityPlayerMP entityPlayer = (EntityPlayerMP) event.source.getSourceOfDamage();
		} else if (event.source.getEntity() instanceof EntityPlayerMP) {
//			EntityPlayerMP entityPlayer = (EntityPlayerMP) event.source.getSourceOfDamage();
		}
	}

    @SubscribeEvent
    /*リスポーン時に呼ばれるイベント。Serverとの同期を取る*/
    public void respawnEvent(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.player.worldObj.isRemote) {
            PacketHandler.INSTANCE.sendTo(new MessagePlayerProperties(event.player), (EntityPlayerMP)event.player);
        }
    }
}