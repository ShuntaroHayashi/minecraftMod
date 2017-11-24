package forestMoon.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class MessageSpawnItemStackHandler implements IMessageHandler<MessageSpawnItemstack, IMessage>{

	@Override
	public IMessage onMessage(MessageSpawnItemstack message, MessageContext ctx) {
//		Minecraft.getMinecraft().getIntegratedServer().getEntityWorld()
		World world = MinecraftServer.getServer().getEntityWorld();
		EntityItem entityItem = new EntityItem(world, message.x, message.y, message.z,message.item);
		world.spawnEntityInWorld(entityItem);

		return null;
	}

}
