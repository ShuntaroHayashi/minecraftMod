package forestMoon.packet.villager;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import forestMoon.shoping.ShopingItem;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class MessageVillager implements IMessage {
	ShopingItem[] items;
	int profession;
	int id;

	public MessageVillager() {
	}

	public MessageVillager(ShopingItem[] items, int profesion, int id) {
		this.items = items;
		this.profession = profesion;
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound AtCompound = ByteBufUtils.readTag(buf);

		NBTTagList tagList = (NBTTagList) AtCompound.getTag("item");
		items = new ShopingItem[tagList.tagCount()];
		int[] buy = AtCompound.getIntArray("buy");
		int[] sell = AtCompound.getIntArray("sell");
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound compound = tagList.getCompoundTagAt(i);
			ItemStack itemStack = ItemStack.loadItemStackFromNBT(compound);
			items[i] = new ShopingItem(itemStack, buy[i], sell[i]);
		}
		this.profession = AtCompound.getInteger("profession");
		this.id = AtCompound.getInteger("id");
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound test = new NBTTagCompound();

		NBTTagList itemList = new NBTTagList();
		int[] buy = new int[items.length];
		int[] sell = new int[items.length];

		for (int i = 0; i < items.length; i++) {
			if (items[i] != null) {
				NBTTagCompound compound = new NBTTagCompound();
				compound.setByte("Slot", (byte) i);
				items[i].getItemStack().writeToNBT(compound);
				buy[i] = items[i].getBuy();
				sell[i] = items[i].getSell();
				itemList.appendTag(compound);
			}
		}
		test.setInteger("profession", profession);
		test.setTag("item", itemList);
		test.setIntArray("buy", buy);
		test.setIntArray("sell", sell);
		test.setInteger("id", id);

		ByteBufUtils.writeTag(buf, test);
	}

}
