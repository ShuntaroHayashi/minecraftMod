package forestMoon.container;

import forestMoon.tileEntity.TileEntityShop;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class PlayerShopAdminContainer extends Container {
	private TileEntityShop tileEntity;
	/** アルミニウムチェストのインベントリの第一スロットの番号 */
	private static final int index0 = 0;
	/** プレイヤーのインベントリの第一スロットの番号 */
	private static final int index1 = 27;// 54
	/** クイックスロットの第一スロットの番号 */
	private static final int index2 = 54;// 81
	/** このコンテナの全体のスロット数 */
	private static final int index3 = 63;// 90
	private String name;
	private EntityPlayer player;
	private int lastSlotNumber = -999;

	public PlayerShopAdminContainer(EntityPlayer player, TileEntityShop tileEntity) {
		// スロットを設定する。
		this.tileEntity = tileEntity;
		this.player = player;
		// チェスト部分の設定
		for (int iy = 0; iy < 3; iy++) {
			for (int ix = 0; ix < 9; ix++) {
				this.addSlotToContainer(new Slot(tileEntity, ix + (iy * 9), 8 + (ix * 18), 17 + (iy * 18)));
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
	public boolean canInteractWith(EntityPlayer player) {
		return tileEntity.isUseableByPlayer(player);
	}

	@Override
	public boolean canDragIntoSlot(Slot slot){
		if(slot.getSlotIndex() < index1 && tileEntity.getAdminName().equals(player.getCommandSenderName())){
			return false;
		}
		return true;
	}

	@Override
	public ItemStack slotClick(int p_75144_1_, int p_75144_2_, int p_75144_3_, EntityPlayer p_75144_4_){
		if(!tileEntity.isSlotClickFlag() && 0<=p_75144_1_ && p_75144_1_ < 27) {
			lastSlotNumber = p_75144_1_;
			return null;
		}else {
			ItemStack itemStack = super.slotClick(p_75144_1_, p_75144_2_, p_75144_3_, p_75144_4_);
			return itemStack;
		}
		//最後にクリックされたスロットを保存
	}

	public String slotItemToString(int index) {
		String str = "";
		if (index0 <= index && index < index1) {
			try {
				if(tileEntity.getStackInSlot(index) != null) {
					str = StatCollector.translateToLocalFormatted("playerShop_itemName", tileEntity.getStackInSlot(index).getDisplayName(),tileEntity.getSellPrice(lastSlotNumber));
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

		return str;
	}

	public void onContainerClosed(EntityPlayer p_75134_1_){
		super.onContainerClosed(p_75134_1_);
	}

	public Boolean getClickFlag() {
		return tileEntity.isSlotClickFlag();
	}

	public void setClickFlag(Boolean clickFlag) {
		tileEntity.setSlotClickFlag(clickFlag);
	}

	public int getLastSlotNumber() {
		return lastSlotNumber;
	}


}
