package forestMoon;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import forestMoon.Items.ItemRecipes;

@Mod( modid = ForestMoon.MODID, version = ForestMoon.VERSION )

public class ForestMoon {
	@Mod.Instance( "ForestMoon" )
    public static final String MODID = "forestmoonmod";
    public static final String VERSION = "beta0.0.1";

    @EventHandler
    public void preInit( FMLPreInitializationEvent e )
    {
    	ItemRecipes.registry( this );
    }

    @EventHandler
    public void Init( FMLInitializationEvent e )
    {
    	Recipes.registry();
    }
}
