package forestMoon.Items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSword;

public class SampleSword extends ItemSword {

	public SampleSword(ToolMaterial material) {
		super(material);

		String name = "samplesword";

		this.setCreativeTab(CreativeTabs.tabCombat);/*クリエイティブのタブ*/
		this.setUnlocalizedName(name);/*システム名の登録*/
		this.setTextureName("forestmoon:SampleSword");/*テクスチャの指定*/
		//アイテムの登録。登録文字列はMOD内で被らなければ何でも良い。

		GameRegistry.registerItem(this, name);
	}
}
