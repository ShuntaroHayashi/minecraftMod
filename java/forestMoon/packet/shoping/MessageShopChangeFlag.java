package forestMoon.packet.shoping;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class MessageShopChangeFlag implements IMessage{
	boolean shopChangeFlag;
	int x;
	int y;
	int z;


	public MessageShopChangeFlag() {
	}

	public MessageShopChangeFlag(boolean shopChangeFlag, int x, int y, int z) {
		this.shopChangeFlag = shopChangeFlag;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound compound = ByteBufUtils.readTag(buf);
		this.shopChangeFlag	= compound.getBoolean("shopChangeFlag");
		this.x = compound.getInteger("x");
		this.y = compound.getInteger("y");
		this.z = compound.getInteger("z");

	}
	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound compound = new NBTTagCompound();

		compound.setBoolean("shopChangeFlag",shopChangeFlag);
		compound.setInteger("x", x);
		compound.setInteger("y", y);
		compound.setInteger("z", z);

		ByteBufUtils.writeTag(buf, compound);
	}


}
