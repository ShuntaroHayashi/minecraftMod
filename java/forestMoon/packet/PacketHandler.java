package forestMoon.packet;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import forestMoon.ForestMoon;

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
		INSTANCE.registerMessage(MessagePlayerJoinInAnoucementHandler.class, MessagePlayerJoinInAnnouncement.class, 1,
				Side.SERVER);
		INSTANCE.registerMessage(MessageInventoryHandler.class, MessageInventory.class, 2, Side.SERVER);
		INSTANCE.registerMessage(MessagePlayerPropertiesToServerHandler.class,MessagePlayerPropertieToServer.class , 3, Side.SERVER);
		INSTANCE.registerMessage(MessageVillagerHandler.class, MessageVillager.class, 4, Side.SERVER);
		INSTANCE.registerMessage(MessageSpawnItemStackHandler.class, MessageSpawnItemstack.class, 5, Side.SERVER);
	}
}