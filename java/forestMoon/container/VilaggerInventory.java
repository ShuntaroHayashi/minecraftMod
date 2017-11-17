package forestMoon.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class VilaggerInventory implements IInventory{

	    private ItemStack[] items;

	    public VilaggerInventory(){
	        //InventorySize
	        items = new ItemStack[14];
	    }

	    public void printItems(){
	    	for(int i=0 ; i<items.length;i++){
	    		System.out.printf("%d : %s\n",i,items[i]);
	    	}
	    }

	    @Override
	    public int getSizeInventory()
	    {
	        return items.length;
	    }

	    @Override
	    public ItemStack getStackInSlot(int slot){
//	    	System.out.printf("getStackInSlot : %d\n",slot - 36);
	    	if(slot -36 < items.length){
	    		return items[slot - 36];
	    	}
	    	return null;
	    }

	    @Override
	    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_){
	        if (this.items[p_70298_1_] != null){
	            ItemStack itemstack;

	            if (this.items[p_70298_1_].stackSize <= p_70298_2_){
	                itemstack = this.items[p_70298_1_];
	                this.items[p_70298_1_] = null;
	                this.markDirty();
	                return itemstack;
	            }
	            else{
	                itemstack = this.items[p_70298_1_].splitStack(p_70298_2_);

	                if (this.items[p_70298_1_].stackSize == 0){
	                    this.items[p_70298_1_] = null;
	                }

	                this.markDirty();
	                return itemstack;
	            }
	        }
	        return null;
	    }

	    @Override
	    public ItemStack getStackInSlotOnClosing(int p_70304_1_){
	        if (this.items[p_70304_1_] != null){
	            ItemStack itemstack = this.items[p_70304_1_];
	            this.items[p_70304_1_] = null;
	            return itemstack;
	        }
	        return null;
	    }

	    @Override
	    public void setInventorySlotContents(int index, ItemStack itemStack){
//	        System.out.printf("setInventorySlotContents :%d %s\n",index - 36,itemStack);
	    	System.out.println("set:" + (index - 36) + " "+itemStack);

	        if(index - 36 < items.length && itemStack != null){
	        	this.items[index -36] = itemStack;
	        }

//	        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()){
//	            itemStack.stackSize = this.getInventoryStackLimit();
//	        }

	        this.markDirty();
	    }

	    @Override
	    public String getInventoryName(){
	        return "InventoryItem";
	    }

	    @Override
	    public boolean hasCustomInventoryName(){
	        return false;
	    }

	    @Override
	    public int getInventoryStackLimit(){
	        return 1;
	    }

	    @Override
	    public void markDirty() {}

	    @Override
	    public boolean isUseableByPlayer(EntityPlayer p_70300_1_){
	        return true;
	    }

	    /*
	        Containerが開かれたタイミングでItemStackの持っているNBTからアイテムを読み込んでいる
	     */
	    @Override
	    public void openInventory(){
	    }

	    /*
	        Containerを閉じるときに保存
	     */
	    @Override
	    public void closeInventory(){
	    }

	    @Override
	    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_){
	        return true;
	    }

}
