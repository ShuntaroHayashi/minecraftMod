package forestMoon.shoping;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class VillagerShopingMaster {
	private final String[] professions = { "flower", "fruit", "ore" };

	public VillagerShopingMaster() {
	}

	// 整数から職業名を取得
	public String getProfessionName(int professionNum) {
		try {
			return  "villager.proffesion." + professions[professionNum];
		} catch (Exception e) {
			return professionNum + " is no setting";
		}
	}

	// 各職業に対応したアイテムの設定、初期値段の設定
	public ArrayList<ShopingItem> getProfessionItems(int profession) {
		ArrayList<ShopingItem> shopingItems = new ArrayList<ShopingItem>();

		switch (profession) {
		case 0:
			shopingItems.add(new ShopingItem(new ItemStack(Blocks.red_flower), 200, -1, 3.0, 0.05, 500, 2000));
			shopingItems.add(new ShopingItem(new ItemStack(Blocks.yellow_flower, 1, 1), -1, 100, 3.0, 0.05, 500, 1500));
			shopingItems.add(new ShopingItem(new ItemStack(Blocks.red_flower, 1, 1), 200, 60, 3.0, 0.05, 1000, 3000));
			shopingItems.add(new ShopingItem(new ItemStack(Blocks.red_flower, 1, 2), 200, 60, 3.0, 0.05, 1000, 3000));
			shopingItems.add(new ShopingItem(new ItemStack(Blocks.red_flower, 1, 3), 200, 60, 3.0, 0.05, 1000, 3000));
			shopingItems.add(new ShopingItem(new ItemStack(Blocks.red_flower, 1, 4), 200, 60, 3.0, 0.05, 1000, 3000));
			shopingItems.add(new ShopingItem(new ItemStack(Blocks.red_flower, 1, 5), 200, 60, 3.0, 0.05, 1000, 3000));
			shopingItems.add(new ShopingItem(new ItemStack(Blocks.red_flower, 1, 6), 200, 60, 3.0, 0.05, 1000, 3000));
			shopingItems.add(new ShopingItem(new ItemStack(Blocks.red_flower, 1, 7), 200, 60, 3.0, 0.05, 1000, 3000));
			shopingItems.add(new ShopingItem(new ItemStack(Blocks.red_flower, 1, 8), 200, 60, 3.0, 0.05, 1000, 3000));
			shopingItems.add(new ShopingItem(new ItemStack(Blocks.double_plant), 200, 60, 3.0, 0.05, 1000, 3000));
			shopingItems.add(new ShopingItem(new ItemStack(Blocks.double_plant, 1, 1), 200, 60, 3.0, 0.05, 1000, 3000));
			shopingItems.add(new ShopingItem(new ItemStack(Blocks.double_plant, 1, 4), 200, 60, 3.0, 0.05, 1000, 3000));
			shopingItems.add(new ShopingItem(new ItemStack(Blocks.double_plant, 1, 5), 200, 60, 3.0, 0.05, 1000, 3000));
			break;
		case 1:
			shopingItems.add(new ShopingItem(new ItemStack(Items.apple), 100, 30, 2.5, 0.2, 400, 1000));
			shopingItems.add(new ShopingItem(new ItemStack(Items.bread), 300, 100, 3.0, 0.2, 400, 1000));
			shopingItems.add(new ShopingItem(new ItemStack(Items.cooked_beef), 300, 100, 3.0, 0.2, 400, 1000));
			shopingItems.add(new ShopingItem(new ItemStack(Items.cooked_chicken), 300, 100, 3.0, 0.2, 400, 1000));
			shopingItems.add(new ShopingItem(new ItemStack(Items.cooked_fished), 300, 100, 3.0, 0.2, 400, 1000));
			shopingItems.add(new ShopingItem(new ItemStack(Items.cooked_porkchop), 300, 100, 3.0, 0.2, 400, 1000));
			break;
		case 2:
			shopingItems.add(new ShopingItem(new ItemStack(Items.diamond), 10000, 3000, 3.0, 0.5, 300, 600));
			break;
		}

		return shopingItems;
	}

	// 現在設定されている職業数
	public int getProfessionSize() {
		return professions.length;
	}

}
