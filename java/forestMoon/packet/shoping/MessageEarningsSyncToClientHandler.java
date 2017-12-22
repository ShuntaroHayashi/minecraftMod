package forestMoon.packet.shoping;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import forestMoon.client.entity.TileEntityChest;
import net.minecraft.client.Minecraft;

public class MessageEarningsSyncToClientHandler implements IMessageHandler<MessageEarningsSyncToClient, IMessage>{

	@Override
	public IMessage onMessage(MessageEarningsSyncToClient message, MessageContext ctx) {
		try {
			TileEntityChest tileEntity = (TileEntityChest)Minecraft.getMinecraft().theWorld.getTileEntity((int)message.x, (int)message.y, (int)message.z);
			tileEntity.setEarnings(message.earnings);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
