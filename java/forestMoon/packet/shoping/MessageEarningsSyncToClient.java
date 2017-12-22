package forestMoon.packet.shoping;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class MessageEarningsSyncToClient implements IMessage{

	int x,y,z,earnings;

	public MessageEarningsSyncToClient() {
	}

	public MessageEarningsSyncToClient(int x, int y, int z, int earnings) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.earnings = earnings;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound compound = ByteBufUtils.readTag(buf);

		this.x = compound.getInteger("x");
		this.y = compound.getInteger("y");
		this.z = compound.getInteger("z");
		this.earnings = compound.getInteger("earnings");

	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound compound = new NBTTagCompound();

		compound.setInteger("x", x);
		compound.setInteger("y", y);
		compound.setInteger("z", z);
		compound.setInteger("earnings", earnings);

		ByteBufUtils.writeTag(buf, compound);
	}
}
