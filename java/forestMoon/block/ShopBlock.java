package forestMoon.block;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestMoon.ForestMoon;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class ShopBlock extends Block {

    @SideOnly(Side.CLIENT)
    private IIcon TopIcon;

//    @SideOnly(Side.CLIENT)
//    private IIcon SideIcon;
//
//    @SideOnly(Side.CLIENT)
//    private IIcon BottomIcon;

    @SideOnly(Side.CLIENT)
    private IIcon texture0;

    @SideOnly(Side.CLIENT)
    private IIcon texture1;

    @SideOnly(Side.CLIENT)
    private IIcon texture2;

    @SideOnly(Side.CLIENT)
    private IIcon texture3;

    @SideOnly(Side.CLIENT)
    private IIcon texture4;

    @SideOnly(Side.CLIENT)
    private IIcon texture5;


	public ShopBlock(Material material){
		super(material);
		String name = "ShopBlock";
		//クリエイティブタブの登録
		this.setCreativeTab(ForestMoon.forestmoontab);
		//硬さの設定
		this.setHardness(5.0F);
		//爆破耐性の設定
		this.setResistance(2500.0F);
		//ブロックの上を歩いた時の音を登録する。
		this.setStepSound(Block.soundTypeMetal);
		//回収するのに必要なツールを設定する。
		this.setHarvestLevel("pickaxe", 2);
		//明るさの設定
		this.setLightLevel(0.0F);

		this.setBlockName(name);

//		this.setBlockTextureName("forestmoon:shopItem");

		GameRegistry.registerBlock(this, name);
	}

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.TopIcon = par1IconRegister.registerIcon("samplemod:block_sample");
        this.texture0 = par1IconRegister.registerIcon("forestmoon:0");
        this.texture1 = par1IconRegister.registerIcon("forestmoon:1");
        this.texture2 = par1IconRegister.registerIcon("forestmoon:2");
        this.texture3 = par1IconRegister.registerIcon("forestmoon:3");
        this.texture4 = par1IconRegister.registerIcon("forestmoon:4");
        this.texture5 = par1IconRegister.registerIcon("forestmoon:5");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int par1, int par2)
    {
    	switch (par1) {
		case 0:
			return texture0;
		case 1:
			return texture1;
		case 2:
			return texture2;
		case 3:
			return texture3;
		case 4:
			return texture4;
		case 5:
			return texture5;
		default:
			return TopIcon;
		}
    }
}
