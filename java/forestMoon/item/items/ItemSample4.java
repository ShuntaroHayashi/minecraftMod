package forestMoon.item.items;

import cpw.mods.fml.common.registry.GameRegistry;
import forestMoon.ForestMoon;
import net.minecraft.item.Item;

public class ItemSample4 extends Item {
	public ItemSample4() {
		String name = "Sample4";
		this.setCreativeTab(ForestMoon.forestmoontab);
		this.setUnlocalizedName(name);
		maxStackSize = 1;
		this.setTextureName("forestmoon:sikaku");
		GameRegistry.registerItem(this, name);

		return;
	}
}
