package forestMoon.packet.villager;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import forestMoon.client.entity.EntityECVillager;
import net.minecraft.server.MinecraftServer;

public class MessageVillagerHandler implements IMessageHandler<MessageVillager, IMessage>{

	@Override
	public IMessage onMessage(MessageVillager message, MessageContext ctx) {
		// TODO 自動生成されたメソッド・スタブ
//		EntityECVillager villager = (EntityECVillager)Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().getEntityByID(message.id);

		EntityECVillager villager = (EntityECVillager) MinecraftServer.getServer().getEntityWorld().getEntityByID(message.id);


		villager.setShopingItems(message.items);
		villager.setProfession(message.profession);

		return null;
	}
}
