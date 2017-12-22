package forestMoon.container;

import forestMoon.packet.PacketHandler;
import forestMoon.packet.player.MessageInventory;
import forestMoon.shoping.ShopingItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ShopingContainer extends Container{

	public EntityPlayer player;
	public InventoryPlayer inventoryPlayer;
	private VilaggerInventory inventory;
	private int lastSlotNumber;

	public ShopingContainer(final InventoryPlayer p_i1819_1_, boolean p_i1819_2_, EntityPlayer player) {
		this.inventoryPlayer = p_i1819_1_;
		this.player = player;
		this.inventory = new VilaggerInventory();

		//販売アイテム
		for(int iy=0;iy<2;iy++){
			for(int ix=0; ix<7;ix++){
				this.addSlotToContainer(new NoMoveInventorySlot(inventory,  ix+ (iy * 7) , 36 + 8+(ix * 18), 10 +(iy * 18)));
			}
		}

		//プレイヤーインベントリーの設定
		//クイックスロットの設定
		for (int ix = 0; ix < 9; ix++) {
			//inventoryplyer/id/x/y
			this.addSlotToContainer(new Slot(inventoryPlayer, ix , 8 + (ix * 18) , 142));
		}
		//インベントリー
		for (int iy = 0; iy < 3; iy++) {
			for (int ix = 0; ix < 9; ix++) {
				this.addSlotToContainer(new Slot(inventoryPlayer,  ix + (iy * 9) + 9 , 8 + (ix * 18), 84 + (iy * 18)));
			}
		}
	}

	//読み込んだアイテムデータをスロットに設定
	public void ItemSet(ShopingItem[] items){
		for(int i=0; i<14 &&i < items.length;i++){
			if(items[i] != null){
				this.putStackInSlot(i, items[i].getItemStack());
			}
		}
	}

	//終了時処理
	public void onContainerClosed(EntityPlayer p_75134_1_){
		super.onContainerClosed(p_75134_1_);
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}

	@Override
	public boolean canDragIntoSlot(Slot slot){

		if(slot.getSlotIndex() < 14){
			return false;
		}
		return true;
	}

	@Override
	public boolean func_94530_a(ItemStack p_94530_1_, Slot p_94530_2_){
		if(p_94530_2_.getSlotIndex() <14){
			return false;
		}
		return true;
	}

	//スロットクリック時の処理
	@Override
	public ItemStack slotClick(int p_75144_1_, int p_75144_2_, int p_75144_3_, EntityPlayer p_75144_4_){
		ItemStack itemStack = super.slotClick(p_75144_1_, p_75144_2_, p_75144_3_, p_75144_4_);
		//最後にクリックされたスロットを保存
		lastSlotNumber = p_75144_1_;
		return itemStack;
	}

	//スロットの個数を返す
	public int getSlotNumber() {
		return 36 + 14;
	}
	//スロットナンバーに応じたアイテムスタックを返す
	public ItemStack getItemStack(int i) {
		Slot slot = (Slot) inventorySlots.get(i);
		if(slot != null && slot.getHasStack()){
			return slot.getStack();
		}else {
			return null;
		}
	}

	//販売時のアイテム減少処理
	public void slotChange(int index,ItemStack itemStack) {
		Slot slot = (Slot) inventorySlots.get(index);
		if(itemStack.stackSize == 0){
			player.inventory.setInventorySlotContents(index - 14, (ItemStack)null);
		}else {
			player.inventory.setInventorySlotContents(index - 14, itemStack);
		}
		this.sendData();
	}
	//サーバーとのインベントリ同期
	private void sendData(){
		ItemStack[] inventory = player.inventory.mainInventory;
		NBTTagList tagList = new NBTTagList();
		for(int i = 0; i < inventory.length; i++){
			if(inventory[i] != null){
				NBTTagCompound compound = new NBTTagCompound();
				compound.setByte("Slot", (byte)i);
				inventory[i].writeToNBT(compound);
				tagList.appendTag(compound);
			}
		}
		PacketHandler.INSTANCE.sendToServer(new MessageInventory(tagList));
	}

	//最後にクリックされたスロットのスロットナンバーを返す
	public int getLastSlotNumber() {
		return lastSlotNumber;
	}

}