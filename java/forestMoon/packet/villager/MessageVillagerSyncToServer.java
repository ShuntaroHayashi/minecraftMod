package forestMoon.packet.villager;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class MessageVillagerSyncToServer implements IMessage {

	int id;

	public MessageVillagerSyncToServer() {
	}

	public MessageVillagerSyncToServer(int id) {
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound AtCompound = ByteBufUtils.readTag(buf);
		this.id = AtCompound.getInteger("id");

	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound test = new NBTTagCompound();

		test.setInteger("id", id);

		ByteBufUtils.writeTag(buf, test);

	}

}
