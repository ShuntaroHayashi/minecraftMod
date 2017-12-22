package forestMoon.packet.shoping;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import forestMoon.tileEntity.TileEntityShop;
import net.minecraft.client.Minecraft;

public class MessagePlayerShopSyncToClientHandler implements IMessageHandler<MessagePlayerShopSyncToClient, IMessage>{

	@Override
	public IMessage onMessage(MessagePlayerShopSyncToClient message, MessageContext ctx) {
		try {
			TileEntityShop tileEntity = (TileEntityShop)Minecraft.getMinecraft().theWorld.getTileEntity((int)message.x, (int)message.y, (int)message.z);
			tileEntity.setSellPrices(message.sellPrices);
			tileEntity.setAdminName(message.adminName);
			tileEntity.setEarnings(message.earings);
			tileEntity.setItemStacks(message.itemStacks);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
