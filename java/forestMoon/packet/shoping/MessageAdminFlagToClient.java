package forestMoon.packet.shoping;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class MessageAdminFlagToClient implements IMessage{
	int x,y,z;
	boolean adminFlag;

	public MessageAdminFlagToClient() {
	}

	public MessageAdminFlagToClient(int x, int y, int z, boolean adminFlag) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.adminFlag = adminFlag;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound compound = ByteBufUtils.readTag(buf);
		this.adminFlag = compound.getBoolean("adminFlag");
		this.x = compound.getInteger("x");
		this.y = compound.getInteger("y");
		this.z = compound.getInteger("z");
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound compound = new NBTTagCompound();

		compound.setBoolean("adminFlag", adminFlag);
		compound.setInteger("x", x);
		compound.setInteger("y", y);
		compound.setInteger("z", z);

		ByteBufUtils.writeTag(buf, compound);
	}
}
