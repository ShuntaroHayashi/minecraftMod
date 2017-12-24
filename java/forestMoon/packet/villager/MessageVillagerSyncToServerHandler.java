package forestMoon.packet.villager;

import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import forestMoon.client.entity.EntityECVillager;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;

public class MessageVillagerSyncToServerHandler
		implements IMessageHandler<MessageVillagerSyncToServer, MessageVillagerSync> {

	@Override
	public MessageVillagerSync onMessage(MessageVillagerSyncToServer message, MessageContext ctx) {

		try {
			Entity entity = MinecraftServer.getServer().getEntityWorld().getEntityByID(message.id);
			if (entity instanceof EntityECVillager && entity != null) {
				EntityECVillager villager = (EntityECVillager) entity;
				MessageVillagerSync returnMessage = new MessageVillagerSync(villager.getShopingItems(),
						villager.getEconomicsProfession(), message.id);
				return returnMessage;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
