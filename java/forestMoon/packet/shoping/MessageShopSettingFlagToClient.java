package forestMoon.packet.shoping;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class MessageShopSettingFlagToClient implements IMessage {
	int x, y, z;
	boolean shopSettingFlag;

	public MessageShopSettingFlagToClient() {
	}

	public MessageShopSettingFlagToClient(int x, int y, int z, boolean shopSettingFlag) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.shopSettingFlag = shopSettingFlag;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound compound = ByteBufUtils.readTag(buf);
		this.shopSettingFlag = compound.getBoolean("shopSettintgFlag");
		this.x = compound.getInteger("x");
		this.y = compound.getInteger("y");
		this.z = compound.getInteger("z");
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound compound = new NBTTagCompound();

		compound.setBoolean("shopSettingFlag", shopSettingFlag);
		compound.setInteger("x", x);
		compound.setInteger("y", y);
		compound.setInteger("z", z);

		ByteBufUtils.writeTag(buf, compound);
	}
}
