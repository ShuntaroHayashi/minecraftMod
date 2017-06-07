package forestMoon.Items;

import cpw.mods.fml.common.registry.GameRegistry;
import forestMoon.ForestMoon;
import net.minecraft.item.Item;

public class ItemSample3 extends Item{
	public ItemSample3(){
		String name = "Sample3";
		this.setCreativeTab( ForestMoon.forestmoontab );
        this.setUnlocalizedName( name );
        maxStackSize = 1;
        this.setTextureName("forestmoon:hosi");
        GameRegistry.registerItem( this, name );

        return;
	}
}
