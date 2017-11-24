package forestMoon.item;

import forestMoon.ForestMoon;
import forestMoon.block.BlockSample;
import forestMoon.block.ChestSample;
import forestMoon.block.ColorBlock;
import forestMoon.item.items.ArmarSample;
import forestMoon.item.items.BonePickaxe;
import forestMoon.item.items.BoneShovel;
import forestMoon.item.items.ItemCoin;
import forestMoon.item.items.ItemECVillagerEgg;
import forestMoon.item.items.ItemSample1;
import forestMoon.item.items.ItemSample2;
import forestMoon.item.items.ItemSample3;
import forestMoon.item.items.ItemSample4;
import forestMoon.item.items.SampleSword;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;

public class ItemRegister {
	public static Item ItemSample1;
	public static Item ItemSample2;
	public static Item ItemSample3;
	public static Item ItemSample4;
	public static Item BonePickaxe;
	public static Item BoneShovel;
	public static Item ItemCoin;
	public static Item ECVillagerEgg;

	public static Block BlockSample;
	public static Block ColorBlock;
	public static Block ShopBlock;
	public static Block chestSample;

	public static ItemSword SampleSword;
	public static ArmorMaterial SAMPLEARMOR;
	public static ToolMaterial BONETOOL;
	public static Item helmetSample;
	public static Item chestPlateSample;
	public static Item leggingsSample;
	public static Item bootsSample;

	public static void registry(ForestMoon mod) {
		ItemSample1 = new ItemSample1();
		ItemSample2 = new ItemSample2();
		ItemSample3 = new ItemSample3();
		ItemSample4 = new ItemSample4();
		ECVillagerEgg = new ItemECVillagerEgg();
		BlockSample = new BlockSample(Material.rock);
		ColorBlock = new ColorBlock();
		ShopBlock = new forestMoon.block.ShopBlock(Material.rock);
		chestSample = new ChestSample();
		BONETOOL = EnumHelper.addToolMaterial("SampleSword", 1, 55, 1500F, 1F, 1);
		// 名前、採掘レベル、耐久度、採掘速度、攻撃力、エンチャント

//		BONETOOL.setRepairItem(new ItemStack(Items.bone));
		BonePickaxe = new BonePickaxe(BONETOOL);
		BoneShovel = new BoneShovel(BONETOOL);

		ItemCoin = new ItemCoin();

		SampleSword = new SampleSword(BONETOOL);
		SAMPLEARMOR = EnumHelper.addArmorMaterial("SampleArmar", 33, new int[] { 20, 8, 6, 3 }, 10);
		helmetSample = new ArmarSample(0);
		chestPlateSample = new ArmarSample(1);
		leggingsSample = new ArmarSample(2);
		bootsSample = new ArmarSample(3);

		return;
	}
}
