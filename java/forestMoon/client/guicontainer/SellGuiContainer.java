package forestMoon.client.guicontainer;

import forestMoon.container.SellContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class SellGuiContainer  extends GuiContainer{
	 private static final ResourceLocation TEXTURE = new ResourceLocation("forestmoon", "textures/guis/background.png");


	 public SellGuiContainer() {
		 super(new SellContainer());
		// TODO 自動生成されたコンストラクター・スタブ
	}

   /*GUIの文字等の描画処理*/
   @Override
   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseZ) {
	   fontRendererObj.drawString(StatCollector.translateToLocal("sell"), 8, 6, 4210752);
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
   public void keyTyped(char c, int i){
	   	super.keyTyped(c, i);

//	   	int x = MathHelper.ceiling_double_int(mc.thePlayer.posX);
//	   	int y = MathHelper.ceiling_double_int(mc.thePlayer.posY);
//	   	int z = MathHelper.ceiling_double_int(mc.thePlayer.posZ);
//
//	   	if(c == 'e'){
//	   		mc.thePlayer.openGui(EntityVillagerMod.INSTANCE, EntityVillagerMod.MAIN_GUI_ID , mc.thePlayer.worldObj,x , y, z);
//
//	   	}
   }

}
