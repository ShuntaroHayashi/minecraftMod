package forestMoon.Items;

import forestMoon.ForestMoon;
import forestMoon.Blocks.BlockSample;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;

public class ItemRecipes {
	public static Item ItemSample1;
    public static Item ItemSample2;
    public static Item ItemSample3;
    public static Item ItemSample4;
    public static Block BlockSample;
    public static ItemSword SampleSword;
	public static ArmorMaterial SAMPLEARMOR;
	public static ToolMaterial SAMPLESWORD;
    public static Item helmetSample;
	public static Item chestPlateSample;
	public static Item leggingsSample;
	public static Item bootsSample;

    public static void registry( ForestMoon mod )
    {
        ItemSample1 = new ItemSample1();
        ItemSample2 = new ItemSample2();
        ItemSample3 = new ItemSample3();
        ItemSample4 = new ItemSample4();
        BlockSample = new BlockSample(Material.rock);
        SAMPLESWORD = EnumHelper.addToolMaterial("SampleSword", 1, 131, 1F, 1000F, 1)
        		.setRepairItem(new ItemStack(Items.bone));
        SampleSword = new SampleSword(SAMPLESWORD);
		SAMPLEARMOR = EnumHelper.addArmorMaterial("SampleArmar", 33, new int[] { 20, 8, 6, 3 }, 10);
		helmetSample = new ArmarSample(0);
		chestPlateSample = new ArmarSample(1);
		leggingsSample = new ArmarSample(2);
		bootsSample = new ArmarSample(3);

        return;
    }
}
