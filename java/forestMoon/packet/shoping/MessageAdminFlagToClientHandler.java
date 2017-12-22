package forestMoon.packet.shoping;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import forestMoon.client.entity.TileEntityChest;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class MessageAdminFlagToClientHandler implements IMessageHandler<MessageAdminFlagToClient, IMessage>{

	@Override
	public IMessage onMessage(MessageAdminFlagToClient message, MessageContext ctx) {
		System.out.println("test flsg:"+message.adminFlag);
		TileEntity tileEntity = Minecraft.getMinecraft().theWorld.getTileEntity(message.x, message.y, message.z);
		if(tileEntity instanceof TileEntityChest) {
			try {
				TileEntityChest tileEntityChest = (TileEntityChest)tileEntity;
				((TileEntityChest) tileEntity).setAdminFlag(message.adminFlag);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}


		return null;
	}

}
