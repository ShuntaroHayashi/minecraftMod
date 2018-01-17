package forestMoon.shoping;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class VillagerShopingItem {
	private final String[] professions = {"villager_proffesion_flower","villager_proffesion_fruit"};

	public VillagerShopingItem() {
	}
	//整数から職業名を取得
	public String getProfessionName(int professionNum){
		try {
			return professions[professionNum];
		} catch (Exception e) {
			return professionNum + "";
		}
	}
	//各職業に対応したアイテムの設定ｌ、初期値段の設定
	public ArrayList<ShopingItem> getProfessionItems(int profession){
		ArrayList<ShopingItem> shopingItems = new ArrayList<ShopingItem>();

		switch (profession) {
		case 0:
			shopingItems.add(new ShopingItem(new ItemStack(Blocks.red_flower), 200, -1,3.0,0.1,500,2000));
			shopingItems.add(new ShopingItem(new ItemStack(Blocks.yellow_flower), -1, 100,3.0,0.1,500,1500));
			break;
		case 1:
			shopingItems.add(new ShopingItem(new ItemStack(Items.apple), 100, 30,2.5,0.2,400,1000));
			shopingItems.add(new ShopingItem(new ItemStack(Item.getItemById(364)), 300, 100,3.0,0.2,400,1000 ));
			break;
		}

		return shopingItems;
	}

	//現在設定されている職業数
	public int getProfessionSize(){
		return professions.length;
	}

}
