package forestMoon;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import forestMoon.Items.ItemRecipes;
import forestMoon.client.gui.ForestMoonGuiHandler;
import forestMoon.client.gui.HUD;
import forestMoon.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

@Mod( modid = ForestMoon.MODID, version = ForestMoon.VERSION ,useMetadata = true)

public class ForestMoon {
	@Mod.Instance( "ForestMoon" )
    public static final String MODID = "forestmoonmod";
    public static final String VERSION = "beta0.0.1";
	@Instance(MODID)
	public static ForestMoon instance;

    @SidedProxy(clientSide = "forestMoon.proxy.ClientProxy", serverSide = "forestMoon.proxy.CommonProxy")
    public static CommonProxy proxy;

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

        if (e.getSide().isClient()) {
            MinecraftForge.EVENT_BUS.register(new HUD());
        }

        //Messageの登録呼び出し
        PacketHandler.init();
    }

    @EventHandler
    public void Init( FMLInitializationEvent e )
    {
    	Recipes.registry();
        //二箇所に登録するので、先にインスタンスを生成しておく。
        SampleEntityPropertiesEventHandler eventHandler = new SampleEntityPropertiesEventHandler();

        //Forge Eventの登録。EntityEvent.EntityConstructingとLivingDeathEventとEntityJoinWorldEvent
        MinecraftForge.EVENT_BUS.register(eventHandler);

        //FML Eventの登録。PlayerRespawnEvent
        FMLCommonHandler.instance().bus().register(eventHandler);
    }



}
