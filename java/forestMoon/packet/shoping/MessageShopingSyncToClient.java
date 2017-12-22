package forestMoon.packet.shoping;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class MessageShopingSyncToClient implements IMessage {

	String adminName;
	int[] sellPrices = new int[27];
	int x;
	int y;
	int z;
	int earings;

	public MessageShopingSyncToClient() {
	}

	public MessageShopingSyncToClient(String adminName,int[] sellPrices,int earings,int x,int y,int z) {
		this.adminName = adminName;
		this.sellPrices = sellPrices;
		this.earings = earings;
		this.x = x;
		this.y = y;
		this.z = z;
	}


	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound compound = ByteBufUtils.readTag(buf);
		this.adminName = compound.getString("adminName");
		this.sellPrices = compound.getIntArray("sellPrices");
		this.earings = compound.getInteger("earings");
		this.x = compound.getInteger("x");
		this.y = compound.getInteger("y");
		this.z = compound.getInteger("z");
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound compound = new NBTTagCompound();

		compound.setString("adminName", adminName);
		compound.setIntArray("sellPrices", sellPrices);
		compound.setInteger("earings", earings);
		compound.setInteger("x", x);
		compound.setInteger("y", y);
		compound.setInteger("z", z);
		ByteBufUtils.writeTag(buf, compound);

	}

}
