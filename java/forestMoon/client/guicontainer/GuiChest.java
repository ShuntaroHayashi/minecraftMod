package forestMoon.client.guicontainer;

import org.lwjgl.opengl.GL11;

import forestMoon.client.entity.TileEntityChest;
import forestMoon.container.ContainerChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiChest extends GuiContainer{
	private TileEntityChest tileEntity;
	private static final ResourceLocation GUITEXTURE = new ResourceLocation("forestmoon:textures/gui/container/shopGui.png");

	public GuiChest(EntityPlayer player, TileEntityChest tileEnttiy) {
		super(new ContainerChest(player, tileEnttiy));
		this.tileEntity = tileEnttiy;
		ySize = 222;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRendererObj.drawString(StatCollector.translateToLocal(tileEntity.getInventoryName()), 8, 6, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 150 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float tick, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(GUITEXTURE);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
	}
}
