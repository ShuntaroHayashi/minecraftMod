package forestMoon.packet.player;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import forestMoon.ExtendedPlayerProperties;
import net.minecraft.entity.player.EntityPlayer;

public class MessagePlayerPropertiesToServerHandler implements IMessageHandler<MessagePlayerPropertieToServer, IMessage> {

	@Override
	public IMessage onMessage(MessagePlayerPropertieToServer message, MessageContext ctx) {
		EntityPlayer player = ctx.getServerHandler().playerEntity;
		ExtendedPlayerProperties.get(player).loadNBTData(message.data);
		return null;
	}

}