package forestMoon.shoping;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class VillagerShopingItem {
	private String[] professions = {"flower","fruit"};

	public VillagerShopingItem() {

	}

	public int getProfessionNum(String name){
		for(int i=0;i<professions.length;i++){
			if (professions[i].equals(name)) {
				return i;
			}
		}
		return 0;
	}

	public String getProfessionName(int professionNum){
		return professions[professionNum];
	}

	public ArrayList<ShopingItem> getProfessionItems(int profession){
		ArrayList<ShopingItem> shopingItems = new ArrayList<ShopingItem>();

		switch (profession) {
		case 0:
			shopingItems.add(new ShopingItem(new ItemStack(Blocks.red_flower), 200, 100));
			shopingItems.add(new ShopingItem(new ItemStack(Blocks.yellow_flower), 300, 200));
			break;

		case 1:
			shopingItems.add(new ShopingItem(new ItemStack(Items.apple), 100, 50));
			break;
		}

		return shopingItems;
	}

	public int getProfessionSize(){
		return professions.length;
	}

}
