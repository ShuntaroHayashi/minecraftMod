package forestMoon.packet.shoping;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class MessagePlayerShopSyncToClient implements IMessage {

	int x, y, z;
	String adminName;
	int[] sellPrices = new int[27];
	int earings;
	ItemStack[] itemStacks = new ItemStack[27];

	public MessagePlayerShopSyncToClient() {
	}

	public MessagePlayerShopSyncToClient(String adminName, int[] sellPrices, ItemStack[] itemStacks, int earings, int x,
			int y, int z) {
		this.adminName = adminName;
		this.sellPrices = sellPrices;
		this.earings = earings;
		this.itemStacks = itemStacks;
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
		NBTTagList tagList = (NBTTagList) compound.getTag("item");
		itemStacks = new ItemStack[27];
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound cmp = tagList.getCompoundTagAt(i);
			byte b = cmp.getByte("Slot");
			if (0 <= b && b < itemStacks.length) {
				itemStacks[b] = ItemStack.loadItemStackFromNBT(cmp);
			}
		}

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
		NBTTagList tagList = new NBTTagList();
		for (int i = 0; i < itemStacks.length; i++) {
			if (itemStacks[i] != null) {
				NBTTagCompound cmp = new NBTTagCompound();
				cmp.setByte("Slot", (byte) i);
				itemStacks[i].writeToNBT(cmp);
				tagList.appendTag(cmp);
			}
		}
		compound.setTag("item", tagList);

		ByteBufUtils.writeTag(buf, compound);

	}

}
