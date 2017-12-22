package forestMoon.packet.shoping;

import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import forestMoon.client.entity.TileEntityChest;
import forestMoon.packet.PacketHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

public class MessageEarningsSyncToServerHandler implements IMessageHandler<MessageEarningsSyncToServer, MessageEarningsSyncToClient>{

	@Override
	public MessageEarningsSyncToClient onMessage(MessageEarningsSyncToServer message, MessageContext ctx) {
		TileEntity tileEntity = MinecraftServer.getServer().getEntityWorld().getTileEntity(message.x, message.y, message.z);
		if(tileEntity instanceof TileEntityChest) {
			try {
				TileEntityChest tileEntityChest = (TileEntityChest)tileEntity;
				tileEntityChest.setEarnings(message.earnings);
				PacketHandler.INSTANCE.sendToAll(new MessageEarningsSyncToClient(message.x, message.y, message.z, message.earnings));
				return new MessageEarningsSyncToClient(message.x,message.y,message.z,message.earnings);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}

}
