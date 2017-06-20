package forestMoon;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import forestMoon.Items.ItemRecipes;
import forestMoon.client.gui.ForestMoonGuiHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

@Mod( modid = ForestMoon.MODID, version = ForestMoon.VERSION )

public class ForestMoon {
	@Mod.Instance( "ForestMoon" )
    public static final String MODID = "forestmoonmod";
    public static final String VERSION = "beta0.0.1";
	@Instance(MODID)
	public static ForestMoon instance;

	public static CreativeTabs forestmoontab = new CreativeTabs( "forestmoontab" )
    {
    	public Item getTabIconItem()
    	{
    		return ItemRecipes.ItemSample1;
    	}
    };


    @EventHandler
    public void preInit( FMLPreInitializationEvent e )
    {
    	ItemRecipes.registry( this );
		NetworkRegistry.INSTANCE.registerGuiHandler(ForestMoon.instance, new ForestMoonGuiHandler());
    }

    @EventHandler
    public void Init( FMLInitializationEvent e )
    {
    	Recipes.registry();
    }



}
