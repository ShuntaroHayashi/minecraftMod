package forestMoon.packet;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MessageSpawnItemstack implements IMessage{
	ItemStack item;
	double x;
	double y;
	double z;
	int num;

	public MessageSpawnItemstack() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public MessageSpawnItemstack(ItemStack item, double x, double y, double z,int num) {
		this.item = item;
		this.x = x;
		this.y = y;
		this.z = z;
		this.num = num;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound compound = ByteBufUtils.readTag(buf);

		x = compound.getDouble("x");
		y = compound.getDouble("y");
		z = compound.getDouble("z");
		num = compound.getInteger("num");
		item = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("item"));
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound compound = new NBTTagCompound();
		NBTTagCompound itemCompound = new NBTTagCompound();

		item.writeToNBT(itemCompound);

		compound.setDouble("x", x);
		compound.setDouble("y", y);
		compound.setDouble("z", z);
		compound.setInteger("num", num);
		compound.setTag("item", itemCompound);

		ByteBufUtils.writeTag(buf, compound);
	}

}
