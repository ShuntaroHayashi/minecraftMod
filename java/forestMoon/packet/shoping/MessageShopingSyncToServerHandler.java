package forestMoon.packet.shoping;

import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import forestMoon.client.entity.TileEntityChest;
import forestMoon.packet.PacketHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

public class MessageShopingSyncToServerHandler implements IMessageHandler<MessageShopingSyncToServer, MessageShopingSyncToClient>{

	@Override
	public MessageShopingSyncToClient onMessage(MessageShopingSyncToServer message, MessageContext ctx) {
		TileEntity tileEntity = MinecraftServer.getServer().getEntityWorld().getTileEntity(message.x, message.y, message.z);

		if (tileEntity instanceof TileEntityChest) {
			TileEntityChest tileEntityChest = (TileEntityChest)tileEntity;
			if(message.syncFlag){
				tileEntityChest.setAdminName(message.adminName);
				tileEntityChest.setSellPrices(message.sellPrices);
				System.out.println("set server adminName at:"+ tileEntityChest.getAdminName());

				return null;
			}else {
				if(!tileEntityChest.getAdminName().equals("NONE")) {
					System.out.println("send client adminName:" + tileEntityChest.getAdminName());
					PacketHandler.INSTANCE.sendToAll(new MessageShopingSyncToClient(tileEntityChest.getAdminName(),tileEntityChest.getSellPrices(),tileEntityChest.getEarnings(), message.x, message.y, message.z));
//					return new MessageShopingSyncToClient(tileEntityChest.getAdminName(),tileEntityChest.getSellPrices(),tileEntityChest.getEarnings(), message.x, message.y, message.z);
				}
			}
		}

		return null;
	}

}
