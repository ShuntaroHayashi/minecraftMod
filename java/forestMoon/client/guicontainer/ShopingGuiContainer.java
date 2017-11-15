package forestMoon.client.guicontainer;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestMoon.ExtendedPlayerProperties;
import forestMoon.client.entity.EntityECVillager;
import forestMoon.container.ShopingContainer;
import forestMoon.shoping.ShopingItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

@SideOnly(Side.CLIENT)
public class ShopingGuiContainer extends GuiContainer{
	 private static final ResourceLocation TEXTURE = new ResourceLocation("forestmoon", "textures/guis/buyGui.png");
	 private static ShopingContainer shopingContainer;
	 private final int RED_FLOWER_BUY = 0;
	 private final int RED_FLOWER_SELL = 1;
	 private int moneyX = 0;
	 private int moneyY = 0;
	 private ShopingItem[] shopingItems;
	 private int profession;
	 private EntityPlayer player;
	 private EntityECVillager villager;
	 private EntityPlayerMP playerMP;
	 private ExtendedPlayerProperties properties;

	public ShopingGuiContainer(EntityPlayer player) {
		super(shopingContainer = new ShopingContainer(player.inventory, true, player));
		this.allowUserInput = true;
		this.player = player;

		moneyX = this.guiLeft + this.xSize - 18;
		moneyY = this.guiTop + this.ySize - 92;

		playerMP = MinecraftServer.getServer().getConfigurationManager().func_152612_a(player.getCommandSenderName());
		new ExtendedPlayerProperties();
		properties = ExtendedPlayerProperties.get(playerMP);

	}

	public ShopingGuiContainer(EntityPlayer player,int id){
		this(player);

//		EntityECVillager villager = (EntityECVillager)Minecraft.getMinecraft().theWorld.getEntityByID(id);
		villager = (EntityECVillager)(Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().getEntityByID(id));
		this.readItemToEntity(villager);

	}

	public void initGui() {
        this.buttonList.clear();

        if (this.mc.playerController.isInCreativeMode())
        {
            this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.thePlayer));
        }
        else
        {
            super.initGui();
        }
	}

    /*GUIの文字等の描画処理*/
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseZ) {

    	fontRendererObj.drawString(StatCollector.translateToLocal("買い物"), 8, 6, 4210752);
    	fontRendererObj.drawString(StatCollector.translateToLocal("何を買う？"), 8, 30, 4210752);
    	fontRendererObj.drawString(StatCollector.translateToLocal(StatCollector.translateToLocal("money") + properties.getMoney()), getMoneyPosition(), moneyY, 4210752);

    	buttonList.clear();

//    	for(int i=0;i<shopingItems.length;i++){
//
//    	}
    	buttonList.add(new GuiButton(RED_FLOWER_BUY, this.guiLeft + this.xSize/2 - 4, this.guiTop +4, this.xSize/2, 20, "ポピー ￥100"));
    	buttonList.add(new GuiButton(RED_FLOWER_SELL,this.guiLeft+ this.xSize/2 - 4, this.guiTop + 40, this.xSize/2,20,"ポピー ￥100"));

    }

    /*GUIの背景の描画処理*/
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseZ) {
        this.mc.renderEngine.bindTexture(TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, xSize, ySize);

    }

    /*GUIが開いている時にゲームの処理を止めるかどうか。*/
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }


    @Override
    protected void actionPerformed(GuiButton button) {
		ItemStack itemStack;
		int price;

		//押下ボタン判定
    	switch (button.id) {
    		default:
    			price = 0;
				itemStack = null;
			break;
    		case RED_FLOWER_BUY:
				price = -100;
				itemStack = new ItemStack(Blocks.red_flower,1,0);
			break;
    		case RED_FLOWER_SELL:
    			price  = 100;
    			itemStack = new ItemStack(Blocks.red_flower,1,0);
    			break;
		}

    	//買う
    	if(itemStack != null && price < 0 ){
    		buy(itemStack, price);
    	//売る
    	}else if (itemStack != null && price > 0) {
    		sell(itemStack, price);
    	}
    }

    //買い物処理
    private void buy(ItemStack itemStack,int price){
		if(properties.getMoney() >= price * -1){
    		//ドロップ処理
    		EntityItem entityItem = playerMP.dropPlayerItemWithRandomChoice(itemStack, false);
			entityItem.delayBeforeCanPickup = 0;
			entityItem.func_145797_a(playerMP.getCommandSenderName());

			properties.changeMoney(price);

			fontRendererObj.drawString(StatCollector.translateToLocal(StatCollector.translateToLocal("money") + properties.getMoney()), getMoneyPosition(), moneyY, 4210752);
			properties.syncPlayerData(playerMP);

			shopingItems[0].setBuy(shopingItems[0].getBuy()+1);
		}
    }

    //販売処理
    private void sell(ItemStack itemStack,int price){
    	for(int i = 0; i< shopingContainer.inventorySlots.size();i++){
			if(shopingContainer.getItemStack(i) != null){
				if(itemStack.getItem() == shopingContainer.getItemStack(i).getItem()){
					properties.changeMoney(price);
    				properties.syncPlayerData(playerMP);

					itemStack.stackSize = shopingContainer.getItemStack(i).stackSize - 1;
    				shopingContainer.slotChange(i, itemStack,price);

    				fontRendererObj.drawString(StatCollector.translateToLocal(StatCollector.translateToLocal("money") + properties.getMoney()), getMoneyPosition(), moneyY, 4210752);

    				break;
    			}
			}
		}
    }

    @Override
    public void mouseClicked(int i,int j,int k){
    	super.mouseClicked(i, j, k);
    }

    //所持金表示位置計算
    private int getMoneyPosition(){
    	long money = properties.getMoney();
    	int work = 0;

    	while(money >= 100){
    		work += 4;
    		money /= 10;
    	}
		return moneyX - work;
    }


    private void readItemToEntity(EntityECVillager villager){

    	System.out.println(villager);

    	this.shopingItems = villager.getShopingItems();
    	this.profession = villager.getProfession();

    	for (ShopingItem item : shopingItems) {
			System.out.println(item);
		}

    }

    //終了時処理
    public void onGuiClosed()
    {
        if (this.mc.thePlayer != null){
            this.inventorySlots.onContainerClosed(this.mc.thePlayer);

            this.guiClose();
        }
    }
    public void guiClose(){
    	System.out.println(player);
        for (ShopingItem item : shopingItems) {
			item.setBuy(item.getBuy()+100);
			item.setSell(item.getSell()+50);
		}

            villager.setShopingItems(shopingItems);
    }

}
