package forestMoon.container;

import forestMoon.client.entity.TileEntityChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class PlayerShopingContainer extends Container{
	private TileEntityChest tileEntity;

	private static final int index0 = 0;
	private static final int index1 = 27;
	private static final int index2 = 54;
	private static final int index3 = 63;
	private String shopName = "";
	private EntityPlayer player;
	private int lastSlotNumber = -999;



	public PlayerShopingContainer(TileEntityChest tileEntity, EntityPlayer player) {
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
		if(slot.getSlotIndex() < 27 ){
			return false;
		}
		return true;
	}
	public String slotItemToString(int index) {
		String str = "";
		if (0 <= index && index < 27) {
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
	public void onContainerClosed(EntityPlayer p_75134_1_){
		if(tileEntity.getAdminName().equals("NONE")) {

		}else {
//			PacketHandler.INSTANCE.sendToServer(new MessageShopingSyncToServer(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, tileEntity.getAdminName()));
		}

		super.onContainerClosed(p_75134_1_);
	}
}
