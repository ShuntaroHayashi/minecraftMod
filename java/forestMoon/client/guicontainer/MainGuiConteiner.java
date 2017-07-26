package forestMoon.client.guicontainer;

import forestMoon.ForestMoon;
import forestMoon.container.MainContainer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class MainGuiConteiner extends GuiContainer {
//    private static final ResourceLocation TEXTURE = new ResourceLocation("<DomainName>", "textures/background.png");
	 private static final ResourceLocation TEXTURE = new ResourceLocation("forestmoon", "textures/guis/background.png");
	 public static final ResourceLocation icons = new ResourceLocation("forestmoon:textures/guis/money.png");

	 GuiButton buyButton;
	 GuiButton sellButton;
	 EntityPlayer player;
	 GuiTextField textField;

	 public MainGuiConteiner(int x, int y, int z ,EntityPlayer player) {
        super(new MainContainer(x, y, z,player));
        this.player = player;

    }

    /*GUIの文字等の描画処理*/
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseZ) {
    	//文字、ｘ、ｙ、色
    	fontRendererObj.drawString(StatCollector.translateToLocal("花屋"), 8, 6, 4210752);
    	fontRendererObj.drawString(StatCollector.translateToLocal("いらっしゃいませ。"), 8, 70, 4210752);
    	fontRendererObj.drawString(StatCollector.translateToLocal("本日はどのようなご用件でしょうか。"), 8, 80, 4210752);
    	buttonList.clear();
        //id, xpos, ypos,width,height, displayString
        buttonList.add(buyButton = new GuiButton(0, this.guiLeft + this.xSize/2 - 4, this.guiTop +4, this.xSize/2, 20, "買いにきた"));
        buttonList.add(sellButton = new GuiButton(1,this.guiLeft + this.xSize/2 - 4, this.guiTop + 30+4, this.xSize/2, 20	 ,"売りに来た"));

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
    	int x = MathHelper.ceiling_double_int(mc.thePlayer.posX);
    	int y = MathHelper.ceiling_double_int(mc.thePlayer.posY);
    	int z = MathHelper.ceiling_double_int(mc.thePlayer.posZ);

    	if(button.id == 0){
    		player.openGui(ForestMoon.instance, ForestMoon.BUY_GUI_ID , player.worldObj,x , y, z);
    	}else if(button.id == 1){
    		mc.thePlayer.openGui(ForestMoon.instance, ForestMoon.SELL_GUI_ID , mc.thePlayer.worldObj,x , y, z);
    	}

    }

}