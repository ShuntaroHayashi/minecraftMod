package forestMoon.packet.shoping;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class MessageClickFlagSync implements IMessage{

	boolean clickFlag;
	int x;
	int y;
	int z;

	public MessageClickFlagSync() {
	}

	public MessageClickFlagSync(int x, int y, int z,boolean clickflag) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.clickFlag = clickflag;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound compound = ByteBufUtils.readTag(buf);
		this.clickFlag = compound.getBoolean("clickFlag");
		this.x = compound.getInteger("x");
		this.y = compound.getInteger("y");
		this.z = compound.getInteger("z");
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound compound = new NBTTagCompound();

		compound.setBoolean("clickFlag", clickFlag);
		compound.setInteger("x", x);
		compound.setInteger("y", y);
		compound.setInteger("z", z);

		ByteBufUtils.writeTag(buf, compound);
	}

}
