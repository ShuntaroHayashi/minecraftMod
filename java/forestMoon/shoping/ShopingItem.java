package forestMoon.shoping;

import net.minecraft.item.ItemStack;

public class ShopingItem {
	private ItemStack itemStack;
	private int buy;	//売値基準値
	private int sell; //買取価格基準値
	private double minCoefficient; //在庫1個時の係数
	private double maxCoefficient; //在庫最大時の係数
	private int initialValue; //標準在庫
	private int maxValue; //最大在庫




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

	//現在の在庫に応じた販売価格を返す
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
	//現在の在庫に応じた買取価格を返す
	public int getSell(int stock) {
		if(sell < 0) {
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
