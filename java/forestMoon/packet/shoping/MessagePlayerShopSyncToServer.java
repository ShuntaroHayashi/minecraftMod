package forestMoon.packet.shoping;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class MessagePlayerShopSyncToServer implements IMessage{
	boolean syncFlag = false;
	int x,y,z,earnings;
	String adminName = "NONE";
	int[] sellPrices = new int[27];
	ItemStack[] itemStacks = new ItemStack[27];

	public MessagePlayerShopSyncToServer() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public MessagePlayerShopSyncToServer(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public MessagePlayerShopSyncToServer(int x, int y, int z, String adminName,int[] sellPrices,int earnings,ItemStack[] itemstacks) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.adminName = adminName;
		this.syncFlag = true;
		this.sellPrices = sellPrices;
		this.earnings = earnings;
		this.itemStacks = itemstacks;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO 自動生成されたメソッド・スタブ
		NBTTagCompound compound = ByteBufUtils.readTag(buf);

		this.syncFlag = compound.getBoolean("syncFlag");

		if(syncFlag) {
			this.adminName = compound.getString("adminName");
			this.sellPrices = compound.getIntArray("sellPrices");
			this.earnings = compound.getInteger("earnings");

			NBTTagList tagList = (NBTTagList)compound.getTag("item");
			itemStacks = new ItemStack[27];
			for (int i = 0; i < tagList.tagCount(); i++) {
				NBTTagCompound cmp = tagList.getCompoundTagAt(i);
				byte b = cmp.getByte("Slot");
				if(0<= b && b < itemStacks.length) {
					itemStacks[b] = ItemStack.loadItemStackFromNBT(cmp);
				}
			}

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
			compound.setInteger("earnings", earnings);

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


		}
		compound.setBoolean("syncFlag", syncFlag);
		compound.setInteger("x", x);
		compound.setInteger("y", y);
		compound.setInteger("z", z);

		ByteBufUtils.writeTag(buf, compound);
	}


}
