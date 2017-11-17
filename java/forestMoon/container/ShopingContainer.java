package forestMoon.container;

import forestMoon.packet.MessageInventory;
import forestMoon.packet.PacketHandler;
import forestMoon.shoping.ShopingItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ShopingContainer extends Container{

	private EntityPlayer player;
	public InventoryPlayer inventoryPlayer;
	private VilaggerInventory inventory;
	private int lastSlotNumber;

	public ShopingContainer(final InventoryPlayer p_i1819_1_, boolean p_i1819_2_, EntityPlayer player) {

		inventoryPlayer = p_i1819_1_;
		this.player = player;

		inventory = new VilaggerInventory();

		//プレイヤーインベントリーの設定
		//クイックスロットの設定
		for (int ix = 0; ix < 9; ix++) {
			//inventoryplyer/id/x/y
			this.addSlotToContainer(new Slot(inventoryPlayer, ix , 8 + (ix * 18), 142));
		}
		//インベントリー
		for (int iy = 0; iy < 3; iy++) {
			for (int ix = 0; ix < 9; ix++) {
				this.addSlotToContainer(new Slot(inventoryPlayer,  ix + (iy * 9) + 9, 8 + (ix * 18), 84 + (iy * 18)));
			}
		}

		//販売アイテム
		for(int iy=0;iy<2;iy++){
			for(int ix=0; ix<7;ix++){
				this.addSlotToContainer(new NoMoveInventorySlot(inventory,  ix+ (iy * 7) + 36, 36 + 8+(ix * 18), 10 +(iy * 18)));
			}
		}

	}

	public void ItemSet(ShopingItem[] items){
//		System.out.printf("ItemSet:IN\n");

		for(int i=0; i<14 &&i < items.length;i++){
//			System.out.printf("ItemSet for:%s / %s\n",items[i],this.getSlot(36 + i));
			if(items[i] != null){
				inventory.setInventorySlotContents(36+i, items[i].getItemStack());
				this.getSlot(36+i).putStack(items[i].getItemStack());
				this.putStackInSlot(36+i, items[i].getItemStack());
			}
		}
	}

	//終了時処理
    public void onContainerClosed(EntityPlayer p_75134_1_)
    {
        super.onContainerClosed(p_75134_1_);
        this.inventory.closeInventory();
    }

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

	@Override
	public boolean canDragIntoSlot(Slot slot){

		if(slot.getSlotIndex() < 36){
			return true;
		}
        return false;
    }

	@Override
    public boolean func_94530_a(ItemStack p_94530_1_, Slot p_94530_2_){
		if(p_94530_2_.getSlotIndex() >= 36){
			return false;
		}
		return true;
    }


	@Override
	public ItemStack slotClick(int p_75144_1_, int p_75144_2_, int p_75144_3_, EntityPlayer p_75144_4_){
		ItemStack itemStack = super.slotClick(p_75144_1_, p_75144_2_, p_75144_3_, p_75144_4_);
//		System.out.printf("%d %d %d \n", p_75144_1_,p_75144_2_,p_75144_3_);
		lastSlotNumber = p_75144_1_;
		return itemStack;

	}
    public int getSlotNumber() {
		return 36;
	}

    public ItemStack getItemStack(int i) {
		Slot slot = (Slot) inventorySlots.get(i);
		if(slot != null && slot.getHasStack()){
			return slot.getStack();
		}else {
			return null;
		}
	}

    //販売時のアイテム現象処理
    public void slotChange(int index,ItemStack itemStack,int price) {
    	Slot slot = (Slot) inventorySlots.get(index);
    	slot.getStack().stackSize -= 1;

    	if(slot.getStack().stackSize == 0){
    		this.putStackInSlot(index, (ItemStack)null);
    		inventoryPlayer.setInventorySlotContents(index, (ItemStack)null);
    		player.inventory.setInventorySlotContents(index, (ItemStack)null);
    		slot.putStack((ItemStack)null);
		}else {
			this.putStackInSlot(index, itemStack);
			inventoryPlayer.setInventorySlotContents(index, itemStack);
			player.inventory.setInventorySlotContents(index, itemStack);
			slot.onSlotChanged();
		}

    	this.sendData();
	}
    //サーバーとのインベントリ同期
    private void sendData(){
    	ItemStack[] inventory = player.inventory.mainInventory;
    	NBTTagList tagList = new NBTTagList();
        for(int i = 0; i < inventory.length; i++)
        {
            if(inventory[i] != null)
            {
                NBTTagCompound compound = new NBTTagCompound();
                compound.setByte("Slot", (byte)i);
                inventory[i].writeToNBT(compound);
                tagList.appendTag(compound);
            }
        }

    	PacketHandler.INSTANCE.sendToServer(new MessageInventory(tagList));
    }

	public int getLastSlotNumber() {
		return lastSlotNumber;
	}

}