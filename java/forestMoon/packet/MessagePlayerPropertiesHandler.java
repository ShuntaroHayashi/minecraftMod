package forestMoon.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import forestMoon.ExtendedPlayerProperties;
import forestMoon.ForestMoon;;

public class MessagePlayerPropertiesHandler implements IMessageHandler<MessagePlayerProperties, IMessage> {

	@Override
	public IMessage onMessage(MessagePlayerProperties message, MessageContext ctx) {
		// Client側にIExtendedEntityPropertiesを渡す。
		ExtendedPlayerProperties.get(ForestMoon.proxy.getEntityPlayerInstance()).loadNBTData(message.data);
		// REPLYは送らないので、nullを返す。
		return null;
	}
}