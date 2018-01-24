package forestMoon.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class VilaggerInventory implements IInventory {

	private ItemStack[] items;

	public VilaggerInventory() {
		// InventorySize
		items = new ItemStack[18];
	}

	//インベントリのサイズを返す
	@Override
	public int getSizeInventory() {
		return items.length;
	}

	//スロットナンバーに応じたアイテムを返す
	@Override
	public ItemStack getStackInSlot(int slot) {
		if (slot < items.length) {
			return items[slot];
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
		if (this.items[p_70298_1_] != null) {
			ItemStack itemstack;

			if (this.items[p_70298_1_].stackSize <= p_70298_2_) {
				itemstack = this.items[p_70298_1_];
				this.items[p_70298_1_] = null;
				this.markDirty();
				return itemstack;
			} else {
				itemstack = this.items[p_70298_1_].splitStack(p_70298_2_);

				if (this.items[p_70298_1_].stackSize == 0) {
					this.items[p_70298_1_] = null;
				}

				this.markDirty();
				return itemstack;
			}
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		if (this.items[p_70304_1_] != null) {
			ItemStack itemstack = this.items[p_70304_1_];
			this.items[p_70304_1_] = null;
			return itemstack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack itemStack) {
		if (index < items.length && itemStack != null) {
			this.items[index] = itemStack;
		}
		this.markDirty();
	}

	@Override
	public String getInventoryName() {
		return "InventoryItem";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void markDirty() {
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	/*
	 * Containerが開かれたタイミングでItemStackの持っているNBTからアイテムを読み込んでいる
	 */
	@Override
	public void openInventory() {
	}

	/*
	 * Containerを閉じるときに保存
	 */
	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return true;
	}

}
