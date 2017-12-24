package forestMoon.packet.villager;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class MessageVillagerSync implements IMessage {
	int[] buyCount;
	int profession;
	int id;

	public MessageVillagerSync() {
	}

	public MessageVillagerSync(int[] buyCount, int profesion, int id) {
		this.buyCount = buyCount;
		this.profession = profesion;
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound AtCompound = ByteBufUtils.readTag(buf);

		this.buyCount = AtCompound.getIntArray("buyCount");
		this.profession = AtCompound.getInteger("profession");
		this.id = AtCompound.getInteger("id");
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound test = new NBTTagCompound();

		test.setIntArray("buyCount", buyCount);
		test.setInteger("profession", profession);
		test.setInteger("id", id);

		ByteBufUtils.writeTag(buf, test);
	}

}
