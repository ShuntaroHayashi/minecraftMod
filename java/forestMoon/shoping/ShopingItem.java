package forestMoon.shoping;

import net.minecraft.item.ItemStack;

public class ShopingItem {
	private ItemStack itemStack;
	private int buy;
	private int sell;

	public ShopingItem(ItemStack itemStack, int buy, int sell) {
		super();
		this.itemStack = itemStack;
		this.itemStack.stackSize = 1;
		this.buy = buy;
		this.sell = sell;
	}
	//getter
	public ItemStack getItemStack(){
		return itemStack;
	}
	public int getBuy() {
		return buy;
	}
	public int getSell() {
		return sell;
	}
	//setter
	public void setBuy(int buy) {
		this.buy = buy;
	}
	public void setSell(int sell) {
		this.sell = sell;
	}
	@Override
	public String toString(){
		return ("ITEM:"+itemStack+" BUY:"+buy+ " SELL:"+sell);
	}


}
