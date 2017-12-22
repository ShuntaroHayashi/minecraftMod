package forestMoon.packet.shoping;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import forestMoon.tileEntity.TileEntityShop;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class MessageShopSettingFlagToClientHandler
		implements IMessageHandler<MessageShopSettingFlagToClient, IMessage> {

	@Override
	public IMessage onMessage(MessageShopSettingFlagToClient message, MessageContext ctx) {
		TileEntity tileEntity = Minecraft.getMinecraft().theWorld.getTileEntity(message.x, message.y, message.z);
		if (tileEntity instanceof TileEntityShop) {
			try {

				TileEntityShop tileEntityChest = (TileEntityShop) tileEntity;
				tileEntityChest.setShopSettingFlag(message.shopSettingFlag);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

}
