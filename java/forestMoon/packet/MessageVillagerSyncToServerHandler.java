package forestMoon.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import forestMoon.client.entity.EntityECVillager;
import net.minecraft.server.MinecraftServer;

public class MessageVillagerSyncToServerHandler implements IMessageHandler<MessageVillagerSyncToServer, MessageVillagerSync>{

	@Override
	public MessageVillagerSync onMessage(MessageVillagerSyncToServer message, MessageContext ctx) {

		try {
//			EntityECVillager villager = (EntityECVillager)Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().getEntityByID(message.id);
			EntityECVillager villager = (EntityECVillager) MinecraftServer.getServer().getEntityWorld().getEntityByID(message.id);

			return new MessageVillagerSync(villager.getShopingItems(), villager.getProfession(), message.id);

		} catch (Exception e) {
			System.err.println(e);
		}
		return null;
	}

}
