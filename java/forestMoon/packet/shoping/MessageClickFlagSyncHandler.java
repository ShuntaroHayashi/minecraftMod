package forestMoon.packet.shoping;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import forestMoon.tileEntity.TileEntityShop;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

public class MessageClickFlagSyncHandler implements IMessageHandler<MessageClickFlagSync, IMessage>{

	@Override
	public IMessage onMessage(MessageClickFlagSync message, MessageContext ctx) {

		try {
			TileEntity tileEnTity= MinecraftServer.getServer().getEntityWorld().getTileEntity(message.x, message.y, message.z);
			if (tileEnTity instanceof TileEntityShop) {
				TileEntityShop tileEntityChest = (TileEntityShop)tileEnTity;
				tileEntityChest.setSlotClickFlag(message.clickFlag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}


}
