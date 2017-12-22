package forestMoon.packet.shoping;

import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import forestMoon.client.entity.TileEntityChest;
import forestMoon.packet.PacketHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

public class MessageAdminFlagToServerHandler implements IMessageHandler<MessageAdminFlagToServer, MessageAdminFlagToClient>{

	@Override
	public MessageAdminFlagToClient onMessage(MessageAdminFlagToServer message, MessageContext ctx) {
		TileEntity tileEntity = MinecraftServer.getServer().getEntityWorld().getTileEntity(message.x, message.y, message.z);
		if(tileEntity instanceof TileEntityChest) {
			try {
				TileEntityChest tileEntityChest = (TileEntityChest)tileEntity;
				tileEntityChest.setAdminFlag(message.adminFlag);
				PacketHandler.INSTANCE.sendToAll(new MessageAdminFlagToClient(message.x, message.y, message.z, message.adminFlag));
			} catch (Exception e) {
				// TODO: handle exception
			}

		}


		return null;
	}

}
