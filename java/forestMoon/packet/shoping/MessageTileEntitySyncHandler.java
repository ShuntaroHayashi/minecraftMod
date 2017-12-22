package forestMoon.packet.shoping;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import forestMoon.client.entity.TileEntityChest;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

public class MessageTileEntitySyncHandler implements IMessageHandler<MessageTileEntitySync, IMessage> {

	@Override
	public IMessage onMessage(MessageTileEntitySync message, MessageContext ctx) {
		TileEntity tileEntity = MinecraftServer.getServer().getEntityWorld().getTileEntity(message.x, message.y, message.z);
		if(tileEntity instanceof TileEntityChest) {
			try {
				TileEntityChest tileEntityChest = (TileEntityChest)tileEntity;
				tileEntityChest.setItemStacks(message.itemStacks);
			} catch (Exception e) {
			}
		}

		return null;
	}



}
