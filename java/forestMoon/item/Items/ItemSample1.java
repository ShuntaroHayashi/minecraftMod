package forestMoon.item.Items;

import cpw.mods.fml.common.registry.GameRegistry;
import forestMoon.ForestMoon;
import net.minecraft.item.Item;

public class ItemSample1 extends Item{
	public ItemSample1()
    {
        String name = "Sample1";

        this.setCreativeTab(ForestMoon.forestmoontab);
        this.setUnlocalizedName( name );
        maxStackSize = 32;
        this.setTextureName("forestmoon:maru");

        GameRegistry.registerItem( this, name );

        return;
    }
}
