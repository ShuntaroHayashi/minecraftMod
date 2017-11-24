package forestMoon.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import forestMoon.client.entity.EntityECVillager;
import net.minecraft.client.Minecraft;

public class MessageVillagerSyncHandler implements IMessageHandler<MessageVillagerSync, IMessage>{

	@Override
	public IMessage onMessage(MessageVillagerSync message, MessageContext ctx) {
		EntityECVillager villager =  (EntityECVillager)Minecraft.getMinecraft().theWorld.getEntityByID(message.id);

		villager.setShopingItems(message.items);
		villager.setProfession(message.profession);

		return null;
	}
}
