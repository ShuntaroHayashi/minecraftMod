package forestMoon.Items;

import forestMoon.ForestMoon;
import net.minecraft.item.Item;

public class ItemRecipes {
	public static Item ItemSample1;
    public static Item ItemSample2;
    public static Item ItemSample3;
    public static Item ItemSample4;

    public static void registry( ForestMoon mod )
    {
        ItemSample1 = new ItemSample1();
        ItemSample2 = new ItemSample2();
        ItemSample3 = new ItemSample3();
        ItemSample4 = new ItemSample4();

        return;
    }
}
