package forestMoon.shoping;

import net.minecraft.item.ItemStack;

public class ShopingItem {
	private ItemStack itemStack;
	private int buy;
	private int sell;
	private double coefficient;

	public ShopingItem(ItemStack itemStack, int buy, int sell,double corfficient) {
		super();
		this.itemStack = itemStack;
		this.itemStack.stackSize = 1;
		this.buy = buy;
		this.sell = sell;
		this.coefficient = corfficient;
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
	public double getCofficient() {
		return coefficient;
	}
	@Override
	public String toString(){
		return ("ITEM:"+itemStack+" BUY:"+buy+ " SELL:"+sell);
	}


}
