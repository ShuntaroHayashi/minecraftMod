package forestMoon.Items;

import cpw.mods.fml.common.registry.GameRegistry;
import forestMoon.ForestMoon;
import net.minecraft.item.ItemPickaxe;

public class BonePickaxe extends ItemPickaxe{

	protected BonePickaxe(ToolMaterial p_i45347_1_) {
		super(p_i45347_1_);
		String name = "BonePickaxe";
		this.setCreativeTab(ForestMoon.forestmoontab);
		this.setUnlocalizedName(name);
		this.setTextureName("forestmoon:"+name);
		GameRegistry.registerItem(this, name);
	}

}
