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
import forestMoon.packet.shoping.MessageClickFlagSync;
import forestMoon.packet.shoping.MessageClickFlagSyncHandler;
import forestMoon.packet.shoping.MessagePlayerShopSyncToClient;
import forestMoon.packet.shoping.MessagePlayerShopSyncToClientHandler;
import forestMoon.packet.shoping.MessagePlayerShopSyncToServer;
import forestMoon.packet.shoping.MessagePlayerShopSyncToServerHandler;
import forestMoon.packet.shoping.MessageShopSettingFlagToClient;
import forestMoon.packet.shoping.MessageShopSettingFlagToClientHandler;
import forestMoon.packet.shoping.MessageShopSettingFlagToServer;
import forestMoon.packet.shoping.MessageShopSettingFlagToServerHandler;
import forestMoon.packet.shoping.MessageSpawnItemStackHandler;
import forestMoon.packet.shoping.MessageSpawnItemStack;
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
		INSTANCE.registerMessage(MessageSpawnItemStackHandler.class, MessageSpawnItemStack.class, 5, Side.SERVER);
		INSTANCE.registerMessage(MessageVillagerSyncToServerHandler.class, MessageVillagerSyncToServer.class, 6, Side.SERVER);
		INSTANCE.registerMessage(MessageVillagerSyncHandler.class, MessageVillagerSync.class, 7, Side.CLIENT);
		INSTANCE.registerMessage(MessagePlayerShopSyncToServerHandler.class, MessagePlayerShopSyncToServer.class, 8, Side.SERVER);
		INSTANCE.registerMessage(MessagePlayerShopSyncToClientHandler.class, MessagePlayerShopSyncToClient.class, 9, Side.CLIENT);
		INSTANCE.registerMessage(MessageClickFlagSyncHandler.class, MessageClickFlagSync.class, 10, Side.SERVER);
		INSTANCE.registerMessage(MessageShopSettingFlagToServerHandler.class, MessageShopSettingFlagToServer.class, 15, Side.SERVER);
		INSTANCE.registerMessage(MessageShopSettingFlagToClientHandler.class, MessageShopSettingFlagToClient.class, 16, Side.CLIENT);
	}
}