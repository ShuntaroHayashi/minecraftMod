package forestMoon.packet.shoping;

import java.util.Random;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class MessageSpawnItemStackHandler implements IMessageHandler<MessageSpawnItemStack, IMessage> {

	@Override
	public IMessage onMessage(MessageSpawnItemStack message, MessageContext ctx) {
		World world = MinecraftServer.getServer().getEntityWorld();

		ItemStack itemStack = message.item;
		int num = message.num;

		Random random = new Random();
		while (num > 0) {
			float f = random.nextFloat() * 0.6F + 0.1F;
			float f1 = random.nextFloat() * 0.6F + 0.1F;
			float f2 = random.nextFloat() * 0.6F + 0.1F;

			int j = random.nextInt(21) + 10;
			if (j > num) {
				j = num;
			}
			num -= j;

			EntityItem entityItem = new EntityItem(world, message.x + f, message.y + f1, message.z + f2,
					new ItemStack(itemStack.getItem(), j, itemStack.getItemDamage()));

			if (itemStack.hasTagCompound()) {
				entityItem.getEntityItem().setTagCompound(((NBTTagCompound) itemStack.getTagCompound().copy()));
			}
			float f3 = 0.025F;
			entityItem.motionX = (float) random.nextGaussian() * f3;
			entityItem.motionY = (float) random.nextGaussian() * f3 + 0.1F;
			entityItem.motionZ = (float) random.nextGaussian() * f3;
			world.spawnEntityInWorld(entityItem);
		}

		return null;
	}

}
