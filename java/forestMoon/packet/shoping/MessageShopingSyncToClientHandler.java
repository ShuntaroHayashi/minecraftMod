package forestMoon.packet.shoping;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import forestMoon.client.entity.TileEntityChest;
import net.minecraft.client.Minecraft;

public class MessageShopingSyncToClientHandler implements IMessageHandler<MessageShopingSyncToClient, IMessage>{

	@Override
	public IMessage onMessage(MessageShopingSyncToClient message, MessageContext ctx) {
		try {
			TileEntityChest tileEntity = (TileEntityChest)Minecraft.getMinecraft().theWorld.getTileEntity((int)message.x, (int)message.y, (int)message.z);
			System.out.println("client adminName:" + tileEntity.getAdminName()+"\n server adminName:" + message.adminName);
			tileEntity.setSellPrices(message.sellPrices);
			tileEntity.setAdminName(message.adminName);
			tileEntity.setEarnings(message.earings);
		} catch (Exception e) {
			System.err.println(e);
		}
		return null;
	}

}
