package forestMoon.packet.shoping;

import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import forestMoon.packet.PacketHandler;
import forestMoon.tileEntity.TileEntityShop;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

public class MessageShopSettingFlagToServerHandler implements IMessageHandler<MessageShopSettingFlagToServer, MessageShopSettingFlagToClient>{

	@Override
	public MessageShopSettingFlagToClient onMessage(MessageShopSettingFlagToServer message, MessageContext ctx) {
		TileEntity tileEntity = MinecraftServer.getServer().getEntityWorld().getTileEntity(message.x, message.y, message.z);
		if(tileEntity instanceof TileEntityShop) {
			try {
				TileEntityShop tileEntityChest = (TileEntityShop)tileEntity;
				tileEntityChest.setShopSettingFlag(message.adminFlag);
				PacketHandler.INSTANCE.sendToAll(new MessageShopSettingFlagToClient(message.x, message.y, message.z, message.adminFlag));
			} catch (Exception e) {
				// TODO: handle exception
			}

		}


		return null;
	}

}
