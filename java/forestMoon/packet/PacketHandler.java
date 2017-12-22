package forestMoon.packet;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import forestMoon.ForestMoon;
import forestMoon.packet.player.MessageInventory;
import forestMoon.packet.player.MessageInventoryHandler;
import forestMoon.packet.player.MessagePlayerJoinInAnnouncement;
import forestMoon.packet.player.MessagePlayerJoinInAnoucementHandler;
import forestMoon.packet.player.MessagePlayerPropertieToServer;
import forestMoon.packet.player.MessagePlayerProperties;
import forestMoon.packet.player.MessagePlayerPropertiesHandler;
import forestMoon.packet.player.MessagePlayerPropertiesToServerHandler;
import forestMoon.packet.shoping.MessageAdminFlagToClient;
import forestMoon.packet.shoping.MessageAdminFlagToClientHandler;
import forestMoon.packet.shoping.MessageAdminFlagToServer;
import forestMoon.packet.shoping.MessageAdminFlagToServerHandler;
import forestMoon.packet.shoping.MessageEarningsSyncToClient;
import forestMoon.packet.shoping.MessageEarningsSyncToClientHandler;
import forestMoon.packet.shoping.MessageEarningsSyncToServer;
import forestMoon.packet.shoping.MessageEarningsSyncToServerHandler;
import forestMoon.packet.shoping.MessageFlagSync;
import forestMoon.packet.shoping.MessageFlagSyncHandler;
import forestMoon.packet.shoping.MessageShopChangeFlag;
import forestMoon.packet.shoping.MessageShopChangeFlagHandler;
import forestMoon.packet.shoping.MessageShopingSyncToClient;
import forestMoon.packet.shoping.MessageShopingSyncToClientHandler;
import forestMoon.packet.shoping.MessageShopingSyncToServer;
import forestMoon.packet.shoping.MessageShopingSyncToServerHandler;
import forestMoon.packet.shoping.MessageSpawnItemStackHandler;
import forestMoon.packet.shoping.MessageSpawnItemstack;
import forestMoon.packet.shoping.MessageTileEntitySync;
import forestMoon.packet.shoping.MessageTileEntitySyncHandler;
import forestMoon.packet.villager.MessageVillager;
import forestMoon.packet.villager.MessageVillagerHandler;
import forestMoon.packet.villager.MessageVillagerSync;
import forestMoon.packet.villager.MessageVillagerSyncHandler;
import forestMoon.packet.villager.MessageVillagerSyncToServer;
import forestMoon.packet.villager.MessageVillagerSyncToServerHandler;

public class PacketHandler {
	/*
	 * MOD固有のSimpleNetworkWrapperを取得。 文字列は他のMODと被らないようにMOD_IDを指定しておくと良い
	 */
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ForestMoon.MODID);

	public static void init() {
		/*
		 * Messageクラスの登録。 第一引数：IMessageHandlerクラス 第二引数：送るMessageクラス
		 * 第三引数：登録番号。255個まで 第四引数：ClientとServerのどちらに送るか。送り先
		 */
		INSTANCE.registerMessage(MessagePlayerPropertiesHandler.class, MessagePlayerProperties.class, 0, Side.CLIENT);
		INSTANCE.registerMessage(MessagePlayerJoinInAnoucementHandler.class, MessagePlayerJoinInAnnouncement.class, 1,Side.SERVER);
		INSTANCE.registerMessage(MessageInventoryHandler.class, MessageInventory.class, 2, Side.SERVER);
		INSTANCE.registerMessage(MessagePlayerPropertiesToServerHandler.class,MessagePlayerPropertieToServer.class , 3, Side.SERVER);
		INSTANCE.registerMessage(MessageVillagerHandler.class, MessageVillager.class, 4, Side.SERVER);
		INSTANCE.registerMessage(MessageSpawnItemStackHandler.class, MessageSpawnItemstack.class, 5, Side.SERVER);
		INSTANCE.registerMessage(MessageVillagerSyncToServerHandler.class, MessageVillagerSyncToServer.class, 6, Side.SERVER);
		INSTANCE.registerMessage(MessageVillagerSyncHandler.class, MessageVillagerSync.class, 7, Side.CLIENT);
		INSTANCE.registerMessage(MessageShopingSyncToServerHandler.class, MessageShopingSyncToServer.class, 8, Side.SERVER);
		INSTANCE.registerMessage(MessageShopingSyncToClientHandler.class, MessageShopingSyncToClient.class, 9, Side.CLIENT);
		INSTANCE.registerMessage(MessageFlagSyncHandler.class, MessageFlagSync.class, 10, Side.SERVER);
		INSTANCE.registerMessage(MessageShopChangeFlagHandler.class, MessageShopChangeFlag.class, 11, Side.SERVER);
		INSTANCE.registerMessage(MessageTileEntitySyncHandler.class, MessageTileEntitySync.class, 12, Side.SERVER);
		INSTANCE.registerMessage(MessageEarningsSyncToServerHandler.class, MessageEarningsSyncToServer.class, 13, Side.SERVER);
		INSTANCE.registerMessage(MessageEarningsSyncToClientHandler.class, MessageEarningsSyncToClient.class, 14, Side.CLIENT);
		INSTANCE.registerMessage(MessageAdminFlagToServerHandler.class, MessageAdminFlagToServer.class, 15, Side.SERVER);
		INSTANCE.registerMessage(MessageAdminFlagToClientHandler.class, MessageAdminFlagToClient.class, 16, Side.CLIENT);
	}
}