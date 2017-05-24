package forestMoon;

import cpw.mods.fml.common.registry.GameRegistry;
import forestMoon.Items.ItemRecipes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class Recipes {
	 public static void registry()
	    {
	        GameRegistry.addRecipe(
	            new ItemStack( ItemRecipes.ItemSample2, 1 ),
	            new Object[] {
	                " x ",
	                "y y",
	                'x', Blocks.planks,
	                'y', Items.stick } );

	        GameRegistry.addRecipe(
	            new ItemStack( ItemRecipes.ItemSample3, 1 ),
	            new Object[] {
	                "x",
	                "y",
	                'x', Blocks.planks,
	                'y', Items.leather } );

	        GameRegistry.addRecipe(
	            new ItemStack( ItemRecipes.ItemSample4, 9 ),
	            new Object[] {
	                "x",
	                "x",
	                "x",
	                'x', Items.iron_ingot } );

	        GameRegistry.addRecipe(
	            new ItemStack( ItemRecipes.ItemSample1, 1 ),
	            new Object[] {
	                "x",
	                "y",
	                "z",
	                'x', ItemRecipes.ItemSample2,
	                'y', ItemRecipes.ItemSample3,
	                'z', ItemRecipes.ItemSample4 } );

	        return;
	    }
}
