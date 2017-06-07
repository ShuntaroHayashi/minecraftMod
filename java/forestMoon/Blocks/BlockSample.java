package forestMoon.Blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import forestMoon.ForestMoon;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockSample extends Block{

	public BlockSample(Material material) {
		super(material);
		String name = "SampleBlock";
		//クリエイティブタブの登録
		this.setCreativeTab(ForestMoon.forestmoontab);
		//硬さの設定
		this.setHardness(5.0F);
		//爆破耐性の設定
		this.setResistance(2000.0F);
		//ブロックの上を歩いた時の音を登録する。
		this.setStepSound(Block.soundTypeMetal);
		//回収するのに必要なツールを設定する。
		this.setHarvestLevel("pickaxe", 2);
		//明るさの設定
		this.setLightLevel(1.0F);

		this.setBlockName(name);

		this.setBlockTextureName("forestmoon:hoshiBlock");

		GameRegistry.registerBlock(this, name);

	}

}
