package forestMoon.Items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemSample1 extends Item{
	public ItemSample1()
    {
        String name = "Sample1";

        this.setCreativeTab( CreativeTabs.tabMisc );
        this.setUnlocalizedName( name );
        maxStackSize = 32;
        this.setTextureName("forestmoon:maru");

        GameRegistry.registerItem( this, name );

        return;
    }
}
