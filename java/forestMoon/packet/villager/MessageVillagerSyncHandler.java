package forestMoon.packet.villager;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import forestMoon.client.entity.EntityECVillager;
import net.minecraft.client.Minecraft;

public class MessageVillagerSyncHandler implements IMessageHandler<MessageVillagerSync, IMessage>{

	@Override
	public IMessage onMessage(MessageVillagerSync message, MessageContext ctx) {
		EntityECVillager villager =  (EntityECVillager)Minecraft.getMinecraft().theWorld.getEntityByID(message.id);

		try {
			villager.setShopingItems(message.items);
			villager.setEconomicsProfession(message.profession);
		} catch (Exception e) {
			System.err.println(e);
		}

		return null;
	}
}