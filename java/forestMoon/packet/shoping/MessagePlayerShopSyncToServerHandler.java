package forestMoon.packet.shoping;

import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import forestMoon.packet.PacketHandler;
import forestMoon.tileEntity.TileEntityShop;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

public class MessagePlayerShopSyncToServerHandler implements IMessageHandler<MessagePlayerShopSyncToServer, MessagePlayerShopSyncToClient>{

	@Override
	public MessagePlayerShopSyncToClient onMessage(MessagePlayerShopSyncToServer message, MessageContext ctx) {
		TileEntity tileEntity = MinecraftServer.getServer().getEntityWorld().getTileEntity(message.x, message.y, message.z);

		if (tileEntity instanceof TileEntityShop) {
			TileEntityShop tileEntityChest = (TileEntityShop)tileEntity;
			if(message.syncFlag){
				tileEntityChest.setAdminName(message.adminName);
				tileEntityChest.setSellPrices(message.sellPrices);
				tileEntityChest.setEarnings(message.earnings);
				tileEntityChest.setItemStacks(message.itemStacks);
				return null;
			}else {
				if(!tileEntityChest.getAdminName().equals("NONE")) {
					PacketHandler.INSTANCE.sendToAll(new MessagePlayerShopSyncToClient(tileEntityChest.getAdminName(),tileEntityChest.getSellPrices(),tileEntityChest.getItemStacks(),tileEntityChest.getEarnings(), message.x, message.y, message.z));
				}
			}
		}

		return null;
	}

}
