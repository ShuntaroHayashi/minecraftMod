package forestMoon;

import forestMoon.client.entity.TileEntityChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerChest extends Container{
	private TileEntityChest tileEntity;
	/** アルミニウムチェストのインベントリの第一スロットの番号 */
	private static final int index0 = 0;
	/** プレイヤーのインベントリの第一スロットの番号 */
	private static final int index1 = 27;//54
	/** クイックスロットの第一スロットの番号 */
	private static final int index2 = 54;//81
	/** このコンテナの全体のスロット数 */
	private static final int index3 = 63;//90

	public ContainerChest(EntityPlayer player, TileEntityChest tileEntity) {
		// スロットを設定する。
		this.tileEntity = tileEntity;
		//チェスト部分の設定
//		for (int iy = 0; iy < 6; iy++) {
		for (int iy = 0; iy < 3; iy++) {
//			for (int ix = 0; ix < 9; ix++) {
			for (int ix = 0; ix < 9; ix++) {
				this.addSlotToContainer(new Slot(tileEntity, ix + (iy * 9), 8 + (ix * 18), 17 + (iy * 18)));
				//Entity/スロットナンバー/x/y
			}
		}
			//プレイヤーインベントリーの設定
		for (int iy = 0; iy < 3; iy++) {
			for (int ix = 0; ix < 9; ix++) {
				this.addSlotToContainer(new Slot(player.inventory, ix + (iy * 9) + 9, 8 + (ix * 18), 84 + (iy * 18)));
			}
		}
		//クイックスロットの設定
		for (int ix = 0; ix < 9; ix++) {
			this.addSlotToContainer(new Slot(player.inventory, ix, 8 + (ix * 18), 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tileEntity.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
		ItemStack itemStack = null;
		Slot slot = (Slot) inventorySlots.get(slotNumber);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemStack1 = slot.getStack();
			itemStack = itemStack1.copy();
			if (index0 <= slotNumber && slotNumber < index1) {
				// アルミニウムチェストのインベントリならプレイヤーのインベントリに移動。
				if (!this.mergeItemStack(itemStack1, index1, index3, true)) {
					return null;
				}
			} else {
				// プレイヤーのインベントリならアルミニウムチェストのインベントリに移動。
				if (!this.mergeItemStack(itemStack1, index0, index1, false)) {
					return null;
				}
			}

			if (itemStack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
			if (itemStack1.stackSize == itemStack.stackSize) {
				return null;
			}
			slot.onPickupFromSlot(player, itemStack1);
		}
		return itemStack;
	}
}
