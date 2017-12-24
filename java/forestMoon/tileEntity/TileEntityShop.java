package forestMoon.tileEntity;

import forestMoon.packet.PacketHandler;
import forestMoon.packet.shoping.MessageClickFlagSync;
import forestMoon.packet.shoping.MessagePlayerShopSyncToServer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityShop extends TileEntity implements IInventory {
	protected ItemStack[] itemStacks = new ItemStack[27];//売り物
	private String adminName = "NONE";//ショップ管理者名
	private int earnings = 0;//売り上げ
	private boolean slotClickFlag = true;//スロットにアクセスできるかのフラグ
	private boolean shopSettingFlag = false;//ショップを設定中かのフラグ
	private int[] sellPrices = new int[27];//価格一覧
	private boolean breakFlag = true;//ブロックを破壊可能かのフラグ true:可能 false:不可能


	// データの書き込み
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < itemStacks.length; i++) {
			if (itemStacks[i] == null)
				continue;
			NBTTagCompound nbt1 = new NBTTagCompound();
			nbt1.setByte("Slot", (byte) i);
			itemStacks[i].writeToNBT(nbt1);
			nbttaglist.appendTag(nbt1);
		}
		nbt.setTag("Items", nbttaglist);
		nbt.setString("name", adminName);
		nbt.setIntArray("sellPrices", sellPrices);
		nbt.setInteger("earnings", earnings);
	}

	// データ読み取り
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		itemStacks = new ItemStack[27];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbt1 = nbttaglist.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("Slot");
			if (0 <= b0 && b0 < itemStacks.length) {
				itemStacks[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
		adminName = nbt.getString("name");
		earnings = nbt.getInteger("earnings");
		sellPrices = nbt.getIntArray("sellPrices");
	}

	//インベントリのサイズを返す
	@Override
	public int getSizeInventory() {
		return 27;
	}

	//スロットナンバーに応じたアイテムを返す
	@Override
	public ItemStack getStackInSlot(int slot) {
		return itemStacks[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if (itemStacks[slot] == null)
			return null;
		ItemStack itemstack;
		if (itemStacks[slot].stackSize <= amount) {
			itemstack = itemStacks[slot];
			itemStacks[slot] = null;
			return itemstack;
		}
		itemstack = itemStacks[slot].splitStack(amount);
		if (itemStacks[slot].stackSize < 1) {
			itemStacks[slot] = null;
		}
		return itemstack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemStack) {
		itemStacks[slot] = itemStack;
		if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
			itemStack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public String getInventoryName() {
		return "container.forestmoon.PlayerShop";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) != this ? false
				: player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
		return true;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		this.writeToNBT(nbtTagCompound);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.func_148857_g());
	}

	/* getter setter start */
	public String getAdminName() {
		return this.adminName;
	}

	public void setAdminName(String par1) {
		this.adminName = par1;
	}

	public int getMetadata() {
		return this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
	}

	public ItemStack[] getItemStacks() {
		return itemStacks;
	}

	public void setItemStacks(ItemStack[] itemStacks) {
		this.itemStacks = itemStacks;
	}

	public int getEarnings() {
		return earnings;
	}

	public void setEarnings(int earnings) {
		this.earnings = earnings;
	}

	public boolean isSlotClickFlag() {
		return slotClickFlag;
	}

	public void setSlotClickFlag(boolean slotClickFlag) {
		this.slotClickFlag = slotClickFlag;
		if (worldObj.isRemote) {
			PacketHandler.INSTANCE.sendToServer(new MessageClickFlagSync(xCoord, yCoord, zCoord, slotClickFlag));
		}
	}

	public int buy(int index, int num) {
		ItemStack itemStack = this.getStackInSlot(index);
		earnings += (sellPrices[index] * num);
		if (itemStack.stackSize > num) {
			itemStack.stackSize = itemStack.stackSize - num;
			itemStacks[index] = itemStack;
		} else {
			itemStack.stackSize = 0;
			itemStacks[index] = null;
			sellPrices[index] = 0;
		}

		if (worldObj.isRemote) {
			PacketHandler.INSTANCE.sendToServer(
					new MessagePlayerShopSyncToServer(xCoord, yCoord, zCoord, adminName, sellPrices, earnings, itemStacks));
		}
		return num;
	}

	public void sendServer() {
		if (worldObj.isRemote) {
			PacketHandler.INSTANCE.sendToServer(
					new MessagePlayerShopSyncToServer(xCoord, yCoord, zCoord, adminName, sellPrices, earnings, itemStacks));
		}
	}

	public int[] getSellPrices() {
		return sellPrices;
	}

	public void setSellPrices(int[] sellPrices) {
		this.sellPrices = sellPrices;

	}

	public void setSellPrice(int index, int price) {
		try {
			sellPrices[index] = price;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getSellPrice(int index) {
		if (0 <= index && index < 27) {
			try {
				return sellPrices[index];
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

	public boolean isShopSettingFlag() {
		return shopSettingFlag;
	}

	public void setShopSettingFlag(boolean shopSettingFlag) {
		this.shopSettingFlag = shopSettingFlag;
	}

	public boolean isBreakFlag() {
		return breakFlag;
	}

	public void setBreakFlag(boolean breakFlag) {
		this.breakFlag = breakFlag;
	}
	/* getter setter end*/


}
