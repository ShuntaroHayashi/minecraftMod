package forestMoon.Items;

import cpw.mods.fml.common.registry.GameRegistry;
import forestMoon.ForestMoon;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ArmarSample extends ItemArmor{

	public ArmarSample(int type) {
		super(ItemRecipes.SAMPLEARMOR, 0, type);
		this.setCreativeTab(ForestMoon.forestmoontab);
		String name = null;
		switch (type) {
		case 0:
			name = "helmetSample";
			break;
		case 1:
			name = "chestPlateSample";
			break;
		case 2:
			name = "leggingsSample";
			break;
		case 3:
			name = "bootsSample";
		default:
			break;
		}
		this.setUnlocalizedName(name);
		this.setTextureName("forestmoon:" + name);
		GameRegistry.registerItem(this, name);
	}

	@Override
	public String getArmorTexture(ItemStack itemStack, Entity entity, int slot, String type) {
		if (this.armorType == 2) {
			return "forestmoon:textures/model/armar/Sample_layer_2.1.png";
		}
		return "forestmoon:textures/model/armar/Sample_layer_1.1.png";
	}


}
