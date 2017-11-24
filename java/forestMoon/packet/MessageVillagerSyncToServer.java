package forestMoon.packet;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class MessageVillagerSyncToServer implements IMessage{

	int id;
	public MessageVillagerSyncToServer() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	public MessageVillagerSyncToServer(int id) {
		this.id = id;
	}


	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO 自動生成されたメソッド・スタブ
		NBTTagCompound AtCompound = ByteBufUtils.readTag(buf);
		this.id = AtCompound.getInteger("id");

	}

	@Override
	public void toBytes(ByteBuf buf) {
		// TODO 自動生成されたメソッド・スタブ
		NBTTagCompound test = new NBTTagCompound();

		test.setInteger("id", id);

		ByteBufUtils.writeTag(buf, test);

	}

}
