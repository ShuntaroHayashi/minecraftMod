package forestMoon.packet.shoping;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class MessageShopingSyncToServer implements IMessage{
	boolean syncFlag = false;
	int x;
	int y;
	int z;
	String adminName = "NONE";
	int[] sellPrices = new int[27];

	public MessageShopingSyncToServer() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public MessageShopingSyncToServer(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public MessageShopingSyncToServer(int x, int y, int z, String adminName,int[] sellPrices) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.adminName = adminName;
		this.syncFlag = true;
		this.sellPrices = sellPrices;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO 自動生成されたメソッド・スタブ
		NBTTagCompound compound = ByteBufUtils.readTag(buf);

		this.syncFlag = compound.getBoolean("syncFlag");

		if(syncFlag) {
			this.adminName = compound.getString("adminName");
			this.sellPrices = compound.getIntArray("sellPrices");
		}
		this.x = compound.getInteger("x");
		this.y = compound.getInteger("y");
		this.z = compound.getInteger("z");



	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound compound = new NBTTagCompound();
		if (syncFlag) {
			compound.setString("adminName", adminName);
			compound.setIntArray("sellPrices", sellPrices);
		}
		compound.setBoolean("syncFlag", syncFlag);
		compound.setInteger("x", x);
		compound.setInteger("y", y);
		compound.setInteger("z", z);


		ByteBufUtils.writeTag(buf, compound);
	}


}
