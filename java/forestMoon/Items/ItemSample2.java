package forestMoon.Items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemSample2 extends Item{
	public ItemSample2(){
		String name = "Sample2";

        this.setCreativeTab( CreativeTabs.tabMisc );
        this.setUnlocalizedName( name );
        maxStackSize = 1;
        this.setTextureName("forestmoon:batu");

        GameRegistry.registerItem( this, name );

        return;
	}
}
