package forestMoon.Items;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestMoon.ExtendedPlayerProperties;
import forestMoon.ForestMoon;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemCoin extends Item {
	public ItemCoin()
    {
        String name = "Coin";

        this.setCreativeTab(ForestMoon.forestmoontab);
        this.setUnlocalizedName( name );
        maxStackSize = 64;
        this.setTextureName("forestmoon:Coin");
        this.setHasSubtypes(true);
        this.setMaxDamage(0);

        GameRegistry.registerItem( this, name );

        return;
    }

	public static int metaToCoin(int meta) {
		int work = 0;
		switch (meta) {
			case 0:work = 100;
			break;
			case 1:work=1000;
			break;
			case 2: work = 10000;
			break;
		default:
			break;
		}

		return work;
	}

	private IIcon[] iicon = new IIcon[3];

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iicon) {
		for (int i = 0; i < 3; i ++) {
			this.iicon[i] = iicon.registerIcon(this.getIconString() + "-" + metaToCoin(i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return iicon[meta];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
		for (int i = 0; i < 3; i ++) {
			list.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return super.getUnlocalizedName() + "." + itemStack.getItemDamage();
	}


	@Override
	public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
		ExtendedPlayerProperties properties = ExtendedPlayerProperties.get(p_77648_2_);
		int addMoney = 100;
		for(int i =0 ;i < p_77648_1_.getItemDamage() ;i++){
			addMoney *= 10;
		}
		 properties.setMoney(properties.getMoney()+addMoney);

		 p_77648_1_.stackSize -= 1;

	//		 ExtendedPlayerProperties.get(p_77648_2_).setMoney(,p_77648_2_);

	     return true;

    }
    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
		 ExtendedPlayerProperties properties = ExtendedPlayerProperties.get(p_77659_3_);
//		 properties.setMoney(properties.getMoney()+100);
			int addMoney = 100;
			for(int i =0 ;i < p_77659_1_.getItemDamage() ;i++){
				addMoney *= 10;
			}
			 properties.setMoney(properties.getMoney()+addMoney);

		 p_77659_1_.stackSize -= 1;
        return p_77659_1_;
    }

}
