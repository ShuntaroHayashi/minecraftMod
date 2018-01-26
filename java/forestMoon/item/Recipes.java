package forestMoon.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class Recipes {
	public static void registry() {
		GameRegistry.addRecipe(new ItemStack(ItemRegister.BonePickaxe, 1),
				new Object[] {
						"xxx",
						" y ",
						" y ",
						'x',Items.bone,
						'y', Items.stick }
		);
		GameRegistry.addRecipe(new ItemStack(ItemRegister.SampleSword, 1),
				new Object[] {
						" x ",
						" x ",
						" y ",
						'x', Items.bone,
						'y', Items.stick }
		);
		GameRegistry.addRecipe(new ItemStack(ItemRegister.ItemSample2, 1),
				new Object[] {
						" x ",
						"y y",
						'x', Blocks.planks,
						'y', Items.stick }
		);
		GameRegistry.addRecipe(new ItemStack(ItemRegister.ItemSample3, 1),
				new Object[] {
						"x",
						"y",
						'x', Blocks.planks,
						'y', Items.leather }
		);
		GameRegistry.addRecipe(new ItemStack(ItemRegister.ItemSample4, 9),
				new Object[] {
						"x",
						"x",
						"x",
						'x', Items.iron_ingot }
		);
		GameRegistry.addRecipe(new ItemStack(ItemRegister.ItemSample1, 1),
				new Object[] {
						"x",
						"y",
						"z",
						'x',ItemRegister.ItemSample2,
						'y', ItemRegister.ItemSample3,
						'z', ItemRegister.ItemSample4 }
		);
		GameRegistry.addRecipe(new ItemStack(ItemRegister.chestSample, 1),
				new Object[] {
						"xxx",
						"xxx",
						"yzy",
						'x',Items.iron_ingot,
						'y',Blocks.planks,
						'z',Blocks.chest	}
		);

		return;
	}
}
