package forestMoon.packet.shoping;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import forestMoon.client.entity.TileEntityChest;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

public class MessageShopChangeFlagHandler implements IMessageHandler<MessageShopChangeFlag, IMessage>{

	@Override
	public IMessage onMessage(MessageShopChangeFlag message, MessageContext ctx) {
		try {
			TileEntity tileEnTity= MinecraftServer.getServer().getEntityWorld().getTileEntity(message.x, message.y, message.z);
			if (tileEnTity instanceof TileEntityChest) {
				TileEntityChest tileEntityChest = (TileEntityChest)tileEnTity;
				tileEntityChest.setShopChangeFlag(message.shopChangeFlag);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}


}
