package forestMoon.item.items;

import cpw.mods.fml.common.registry.GameRegistry;
import forestMoon.ForestMoon;
import net.minecraft.item.ItemSpade;

public class BoneShovel extends ItemSpade{

	public BoneShovel(ToolMaterial material) {
		super(material);

		String name = "BoneShovel";
		this.setCreativeTab(ForestMoon.forestmoontab);
		this.setUnlocalizedName(name);
		this.setTextureName("forestmoon:"+name);
		GameRegistry.registerItem(this, name);
	}

}
