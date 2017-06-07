package forestMoon.Items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ArmarSample extends ItemArmor{

	public ArmarSample(int type) {
		super(ItemRecipes.SAMPLEARMOR, 0, type);
		this.setCreativeTab(CreativeTabs.tabCombat);
//	     this.setTextureName("forestmoon:ArmarSample");
		String name;
		switch (type) {
		case 0:
			name = "helmetSample";
			this.setUnlocalizedName("helmetSample");
			this.setTextureName("forestmoon:helmetSample");
			GameRegistry.registerItem(this, "helmetSample");
			break;
		case 1:
			name = "chestPlateSample";
			this.setUnlocalizedName("chestPlateSample");
			this.setTextureName("forestmoon:chestPlateSample");
			GameRegistry.registerItem(this, "chestPlateSample");
			break;
		case 2:
			this.setUnlocalizedName("leggingsSample");
			this.setTextureName("forestmoon:leggingsSample");
			GameRegistry.registerItem(this, "leggingsSample");
			break;
		case 3:
			this.setUnlocalizedName("bootsSample");
			this.setTextureName("forestmoon:bootsSample");
			GameRegistry.registerItem(this, "bootssample");

		default:
			break;
		}
	}

	@Override
	public String getArmorTexture(ItemStack itemStack, Entity entity, int slot, String type) {
		if (this.armorType == 2) {
			return "forestmoon:textures/model/armar/Sample_layer_2.1.png";
//			aluminiummod:textures/models/armor/aluminium_layer_2.png
		}
		return "forestmoon:textures/model/armar/Sample_layer_1.1.png";
	}


}
