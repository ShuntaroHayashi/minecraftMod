package forestMoon.Items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemSample4 extends Item{
	public ItemSample4(){
		String name = "Sample4";
		this.setCreativeTab( CreativeTabs.tabMisc );
        this.setUnlocalizedName( name );
        maxStackSize = 1;
        this.setTextureName("forestmoon:sikaku");
        GameRegistry.registerItem( this, name );

        return;
	}
}
