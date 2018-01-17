package forestMoon.shoping;

import net.minecraft.item.ItemStack;

public class ShopingItem {
	private ItemStack itemStack;
	private int buy;
	private int sell;
	private double minCoefficient;
	private double maxCoefficient;
	private int initialValue;
	private int maxValue;




	public ShopingItem(ItemStack itemStack, int buy, int sell, double minCoefficient, double maxCoefficient,
			int initialValue, int maxValue) {
		this.itemStack = itemStack;
		this.buy = buy;
		this.sell = sell;
		this.minCoefficient = minCoefficient;
		this.maxCoefficient = maxCoefficient;
		this.initialValue = initialValue;
		this.maxValue = maxValue;
	}

	public int getBuy(int stock) {
		if(buy < 0) {
			return -1;
		}


		int num = 0;
		if(stock <= initialValue) {
			double coefficient = (minCoefficient - 1) / (double)initialValue;
			coefficient = 1 + (coefficient ) * (initialValue - stock);
			num = (int)(buy * coefficient);
		}else if(stock <= maxValue){
			double coefficient = (1 - maxCoefficient) / (maxValue - initialValue);
			coefficient = 1 - coefficient * (stock - initialValue);
			num = (int)(buy * coefficient);
		}else {
			num = (int)(buy * maxCoefficient);
		}
		return num;
	}

	public int getSell(int stock) {
		if(buy < -1) {
			return -1;
		}


		int num = 0;
		if(stock <= initialValue) {
			double coefficient = (minCoefficient - 1) / (double)initialValue;
			coefficient = 1 + (coefficient ) * (initialValue - stock);
			num = (int)(sell * coefficient);
		}else if(stock <= maxValue){
			double coefficient = (1 - maxCoefficient) / (maxValue - initialValue);
			coefficient = 1 - coefficient * (stock - initialValue);
			num = (int)(sell * coefficient);
		}else {
			num = (int)(sell * maxCoefficient);
		}
		return num;
	}



	public int getInitialValue() {
		return initialValue;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	@Override
	public String toString(){
		return ("ITEM:"+itemStack+" BUY:"+buy+ " SELL:"+sell);
	}


}
