package forestMoon.packet.shoping;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class MessageTileEntitySync implements IMessage {
	int x,y,z;
	ItemStack[] itemStacks;

	public MessageTileEntitySync() {
	}

	public MessageTileEntitySync(int x, int y, int z, ItemStack[] itemStacks) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.itemStacks = itemStacks;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound compound = ByteBufUtils.readTag(buf);

		NBTTagList tagList = (NBTTagList)compound.getTag("item");
		itemStacks = new ItemStack[27];
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound cmp = tagList.getCompoundTagAt(i);
			byte b = cmp.getByte("Slot");
			if(0<= b && b < itemStacks.length) {
				itemStacks[b] = ItemStack.loadItemStackFromNBT(cmp);
			}
		}
		this.x = compound.getInteger("x");
		this.y = compound.getInteger("y");
		this.z = compound.getInteger("z");
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound compound = new NBTTagCompound();

		NBTTagList tagList = new NBTTagList();

		for (int i = 0; i < itemStacks.length; i++) {
			if(itemStacks[i] != null) {
				NBTTagCompound cmp = new NBTTagCompound();
				cmp.setByte("Slot", (byte)i);
				itemStacks[i].writeToNBT(cmp);
				tagList.appendTag(cmp);
			}
		}
		compound.setTag("item", tagList);
		compound.setInteger("x", x);
		compound.setInteger("y", y);
		compound.setInteger("z", z);

		ByteBufUtils.writeTag(buf, compound);
	}

}
