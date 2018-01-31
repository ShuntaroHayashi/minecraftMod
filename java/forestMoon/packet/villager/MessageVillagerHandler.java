package forestMoon.packet.villager;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import forestMoon.client.entity.EntityECVillager;
import forestMoon.packet.PacketHandler;
import net.minecraft.server.MinecraftServer;

public class MessageVillagerHandler implements IMessageHandler<MessageVillager, IMessage> {

	@Override
	public IMessage onMessage(MessageVillager message, MessageContext ctx) {
		EntityECVillager villager = (EntityECVillager) MinecraftServer.getServer().getEntityWorld()
				.getEntityByID(message.id);

		villager.setBuyCount(message.buyCount);
		villager.setProfession(message.profession);

		PacketHandler.INSTANCE.sendToAll(new MessageVillagerSync(message.buyCount, message.profession, message.id));

		return null;
	}
}
