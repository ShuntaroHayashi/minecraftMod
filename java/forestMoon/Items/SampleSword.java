package forestMoon.Items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;

public class SampleSword extends ItemSword {

	public SampleSword(ToolMaterial material) {
		super(material);

		String name = "samplesword";

		this.setCreativeTab(CreativeTabs.tabCombat);
		this.setUnlocalizedName(name);
		this.setTextureName("forestmoon:SampleSword");
		GameRegistry.registerItem(this, name);
	}
}
