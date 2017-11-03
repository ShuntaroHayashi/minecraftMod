package forestMoon;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestMoon.Items.ItemRegister;
import forestMoon.client.ForestMoonGuiHandler;
import forestMoon.client.entity.EntityECVillager;
import forestMoon.client.gui.HUD;
import forestMoon.command.CommandRegister;
import forestMoon.event.EntityPropertiesEventHandler;
import forestMoon.event.LivingDeathEventHandler;
import forestMoon.packet.PacketHandler;
import forestMoon.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;

@Mod( modid = ForestMoon.MODID, version = ForestMoon.VERSION ,useMetadata = true)

public class ForestMoon {
	//初期設定
	@Mod.Instance( "ForestMoon" )
    public static final String MODID = "forestmoonmod";
    public static final String VERSION = "beta0.0.1";
	@Instance(MODID)
	public static ForestMoon instance;

	//GUI_ID
	public static final int SHOPING_GUI_ID = 0;
	public static final int CHEST_GUI_ID = 10;

	//プロキシの設定
    @SidedProxy(clientSide = "forestMoon.proxy.ClientProxy", serverSide = "forestMoon.proxy.CommonProxy")
    public static CommonProxy proxy;

	public static CreativeTabs forestmoontab = new CreativeTabs( "forestmoontab" )
    {
    	public Item getTabIconItem()
    	{
    		return ItemRegister.ItemSample1;
    	}
    };


    @EventHandler
    public void preInit( FMLPreInitializationEvent event){
    	ItemRegister.registry( this );
//		NetworkRegistry.INSTANCE.registerGuiHandler(ForestMoon.instance, new ForestMoonGuiHandler());

        if (event.getSide().isClient()) {
            MinecraftForge.EVENT_BUS.register(new HUD());
        }
        //Messageの登録呼び出し
        PacketHandler.init();

    }

    @EventHandler
    public void Init( FMLInitializationEvent event ){
    	MinecraftForge.EVENT_BUS.register(new LivingDeathEventHandler());

    	Recipes.registry();
        //二箇所に登録するので、先にインスタンスを生成しておく。
        EntityPropertiesEventHandler eventHandler = new EntityPropertiesEventHandler();

        //Forge Eventの登録。EntityEvent.EntityConstructingとLivingDeathEventとEntityJoinWorldEvent
        MinecraftForge.EVENT_BUS.register(eventHandler);

        //FML Eventの登録。PlayerRespawnEvent
        FMLCommonHandler.instance().bus().register(eventHandler);

		//Entityを登録する
        EntityRegistry.registerModEntity(EntityECVillager.class, "Villager", 0, this, 250, 1, false);
	    //Entityの自然スポーンを登録する
        EntityRegistry.addSpawn(EntityECVillager.class, 20, 1, 4, EnumCreatureType.creature, BiomeGenBase.plains);
	    /*EntityのRenderを登録する
	     *Client側でのみ登録するため、今回はif文で処理をする。*/
        if(FMLCommonHandler.instance().getSide() == Side.CLIENT) {
        	this.render();
        }

        NetworkRegistry.INSTANCE.registerGuiHandler(ForestMoon.instance, new ForestMoonGuiHandler());


    }

	@SideOnly(Side.CLIENT)
	public void render()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityECVillager.class, new RenderECVillager());
	}

    @EventHandler
	public void serverStarting(FMLServerStartingEvent event){
    	CommandRegister.registry(event);
	}
}
