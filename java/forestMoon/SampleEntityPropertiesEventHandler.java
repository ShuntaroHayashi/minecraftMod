package forestMoon;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import forestMoon.packet.MessagePlayerJoinInAnnouncement;
import forestMoon.packet.MessagePlayerProperties;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BlockEvent;

public class SampleEntityPropertiesEventHandler {

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
            System.out.println("money:" + money);

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


    @SubscribeEvent
    /*リスポーン時に呼ばれるイベント。Serverとの同期を取る*/
    public void respawnEvent(PlayerEvent.PlayerRespawnEvent event) {
        if (!event.player.worldObj.isRemote) {
            PacketHandler.INSTANCE.sendTo(new MessagePlayerProperties(event.player), (EntityPlayerMP)event.player);
        }
    }
}