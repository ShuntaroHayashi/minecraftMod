package forestMoon.item.Items;

import cpw.mods.fml.common.registry.GameRegistry;
import forestMoon.ForestMoon;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;

public class SampleSword extends ItemSword {

	public SampleSword(ToolMaterial material) {
		super(EnumHelper.addToolMaterial(material.name(),
				material.getHarvestLevel(),
				material.getMaxUses(),
				material.getEfficiencyOnProperMaterial(),
				100f,
				material.getEnchantability())
				);
		String name = "samplesword";
		this.setCreativeTab(ForestMoon.forestmoontab);
		this.setUnlocalizedName(name);
		this.setTextureName("forestmoon:SampleSword");
		GameRegistry.registerItem(this, name);
	}
}
