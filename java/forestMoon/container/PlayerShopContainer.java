package forestMoon.container;

import forestMoon.tileEntity.TileEntityShop;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class PlayerShopContainer extends Container{
	private TileEntityShop tileEntity;

	private static final int index0 = 0;
	private static final int index1 = 27;
	private static final int index2 = 54;
	private static final int index3 = 63;
	private String shopName = "";
	private EntityPlayer player;
	private int lastSlotNumber = -999;



	public PlayerShopContainer(TileEntityShop tileEntity, EntityPlayer player) {
		super();
		this.tileEntity = tileEntity;
		this.player = player;
		shopName = tileEntity.getAdminName();

		for(int iy=0;iy<3;iy++) {
			for(int ix=0;ix<9;ix++) {
				this.addSlotToContainer(new NoMoveInventorySlot(tileEntity, ix + (iy * 9), 8 + (ix * 18), 17 + (iy * 18)));
				// Entity/スロットナンバー/x/y
			}
		}
		// プレイヤーインベントリーの設定
		for (int iy = 0; iy < 3; iy++) {
			for (int ix = 0; ix < 9; ix++) {
				this.addSlotToContainer(new Slot(player.inventory, ix + (iy * 9) + 9, 8 + (ix * 18), 97 + (iy * 18)));
			}
		}
		// クイックスロットの設定
		for (int ix = 0; ix < 9; ix++) {
			this.addSlotToContainer(new Slot(player.inventory, ix, 8 + (ix * 18), 155));
		}
	}

	@Override
	public ItemStack slotClick(int p_75144_1_, int p_75144_2_, int p_75144_3_, EntityPlayer p_75144_4_){
		if( 0<=p_75144_1_ && p_75144_1_ < 27) {
			lastSlotNumber = p_75144_1_;
			return null;
		}else {
			ItemStack itemStack = super.slotClick(p_75144_1_, p_75144_2_, p_75144_3_, p_75144_4_);
			return itemStack;
		}
		//最後にクリックされたスロットを保存
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tileEntity.isUseableByPlayer(player);
	}

	@Override
	public boolean canDragIntoSlot(Slot slot){
		if(slot.getSlotIndex() < index1 ){
			return false;
		}
		return true;
	}
	public String slotItemToString(int index) {
		String str = "";
		if (index0 <= index && index < index1) {
			try {
				if(tileEntity.getStackInSlot(index) != null) {
					if (tileEntity.getStackInSlot(index).stackSize > 0) {
						str = StatCollector.translateToLocalFormatted("playerShop_itemName", tileEntity.getStackInSlot(index).getDisplayName(),tileEntity.getSellPrice(lastSlotNumber));
//						str = StatCollector.translateToLocal(tileEntity.getStackInSlot(index).getDisplayName()) + "price :" + tileEntity.getSellPrice(lastSlotNumber);
					}else {
						str = "売り切れ";
					}

				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

		return str;
	}
	public int getLastSlotNumber() {
		return lastSlotNumber;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
//		ItemStack itemStack = null;
//		Slot slot = (Slot) inventorySlots.get(slotNumber);
//		if (slot != null && slot.getHasStack()) {
//			ItemStack itemStack1 = slot.getStack();
//			itemStack = itemStack1.copy();
//			if (index0 <= slotNumber && slotNumber < index1) {
//				// チェストのインベントリならプレイヤーのインベントリに移動。
//				if (!this.mergeItemStack(itemStack1, index1, index3, true)) {
//					return null;
//				}
//			} else {
//				// プレイヤーのインベントリならチェストのインベントリに移動。
//				if (!this.mergeItemStack(itemStack1, index0, index1, false)) {
//					return null;
//				}
//			}
//
//			if (itemStack1.stackSize == 0) {
//				slot.putStack((ItemStack) null);
//			} else {
//				slot.onSlotChanged();
//			}
//			if (itemStack1.stackSize == itemStack.stackSize) {
//				return null;
//			}
//			slot.onPickupFromSlot(player, itemStack1);
//		}
		return null;
	}

	public void onContainerClosed(EntityPlayer p_75134_1_){
		if(tileEntity.getAdminName().equals("NONE")) {

		}else {
//			PacketHandler.INSTANCE.sendToServer(new MessageShopingSyncToServer(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, tileEntity.getAdminName()));
		}

		super.onContainerClosed(p_75134_1_);
	}
}
