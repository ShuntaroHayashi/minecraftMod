package forestMoon.client.guicontainer;


import forestMoon.ExtendedPlayerProperties;
import forestMoon.container.BuyContainer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class BuyGuiContainer extends GuiContainer{
	 private static final ResourceLocation TEXTURE = new ResourceLocation("forestmoon", "textures/guis/background.png");
	 private EntityPlayer player;
	 private GuiButton button;
	 private GuiLabel label;
	 private static final int RED_FLOWER = 0;


	public BuyGuiContainer(EntityPlayer player) {
		super(new BuyContainer(player));
		this.player = player;
		System.out.println(player.getClass());
	}

    /*GUIの文字等の描画処理*/
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseZ) {
    	ExtendedPlayerProperties properties = new ExtendedPlayerProperties().get(player);
    	fontRendererObj.drawString(StatCollector.translateToLocal("買い物"), 8, 6, 4210752);
    	fontRendererObj.drawString(StatCollector.translateToLocal("何を買う？"), 8, 30, 4210752);
    	fontRendererObj.drawString(StatCollector.translateToLocal("￥" + properties.getMoney()), 100, 100, 4210752);
    	buttonList.clear();
    	buttonList.add(button = new GuiButton(RED_FLOWER, this.guiLeft + this.xSize/2 - 4, this.guiTop +4, this.xSize/2, 20, "ポピー ￥100"));


        super.drawGuiContainerForegroundLayer(mouseX, mouseZ);
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
    	EntityPlayerMP playerMP = MinecraftServer.getServer().getConfigurationManager().func_152612_a(player.getCommandSenderName());
		ExtendedPlayerProperties properties = new ExtendedPlayerProperties().get(playerMP);

		ItemStack itemStack = null;

		int price = 0;

    	if(button.id == RED_FLOWER){
    		price = 100;
    		itemStack = new ItemStack(Blocks.red_flower,1,0);
    	}
    	if(itemStack != null && price > 0 ){
    		if(properties.getMoney() >= price){
	    		//ドロップ処理
	    		EntityItem entityItem = playerMP.dropPlayerItemWithRandomChoice(itemStack, false);
				entityItem.delayBeforeCanPickup = 0;
				entityItem.func_145797_a(playerMP.getCommandSenderName());

				properties.changeMoney(price * -1);
				fontRendererObj.drawString(StatCollector.translateToLocal("￥" + properties.getMoney()), 100, 100, 4210752);
				properties.syncPlayerData(playerMP);
    		}else {
    			System.out.println("aaa");
    			fontRendererObj.drawString(StatCollector.translateToLocal("所持金が足りないよ"), 8, 30, 4210752);
			}
    	}
    }

    @Override
    public void keyTyped(char c, int i){
    	super.keyTyped(c, i);
//    	int x = MathHelper.ceiling_double_int(player.posX);
//    	int y = MathHelper.ceiling_double_int(player.posY);
//    	int z = MathHelper.ceiling_double_int(player.posZ);
//
//    	if(c == 'e'){
//    		player.openGui(ForestMoon.instance, ForestMoon.MAIN_GUI_ID , player.worldObj,x , y, z);
//    	}
    }

    @Override
    public void mouseClicked(int i,int j,int k){
//    	textField.mouseClicked(i, j	, k);
    	super.mouseClicked(i, j, k);
//    	System.out.println(textField.isFocused());
    }

}
