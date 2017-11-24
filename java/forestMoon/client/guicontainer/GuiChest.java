package forestMoon.client.guicontainer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestMoon.client.entity.TileEntityChest;
import forestMoon.container.ContainerChest;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

@SideOnly(Side.CLIENT)
public class GuiChest extends GuiContainer {
	private GuiTextField commandTextField;
	private GuiButton doneBtn;
	private GuiButton changeBtn;
	private TileEntityChest tileEntity;
	private Boolean boolShop = false;
	private EntityPlayer player;
	private boolean adminFLAG = false;

	private static final ResourceLocation GUITEXTURE = new ResourceLocation(
			"forestmoon:textures/gui/container/shopGui.png");

	public GuiChest(EntityPlayer player, TileEntityChest tileEntitiy) {
		super(new ContainerChest(player, tileEntitiy));
		this.tileEntity = tileEntitiy;
		this.player = player;
		ySize = 222;
	}

	public void initGui() {
		super.initGui();
		if(tileEntity.getAdminName().equals("NONE")) {
			System.out.println("test 101");
			tileEntity.setAdminName(player.getCommandSenderName());
		}else if (tileEntity.getAdminName().equals(player.getCommandSenderName())) {
			System.out.println("test 102");
			adminFLAG = true;
		}
		if (adminFLAG) {
			this.buttonList.add(this.doneBtn = new GuiButton(0, this.width / 2 + 100, this.height / 2 - 60, 105, 20, "価格決定"));
			this.buttonList.add(this.changeBtn = new GuiButton(1, this.width / 2 + 100, this.height / 2 - 30, 105, 20, "処理切替"));

			this.doneBtn.enabled = false;

			commandTextField = new GuiTextField(this.fontRendererObj, 190, 20, 100, 20);
			commandTextField.setFocused(true);
			commandTextField.setText("");
			commandTextField.setMaxStringLength(5);
		}

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
//		fontRendererObj.drawString(StatCollector.translateToLocal(tileEntity.getInventoryName()), 8, 6, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal(tileEntity.getAdminName()), 8, 6, 4210752);
		if (adminFLAG) {
			commandTextField.setEnabled(boolShop);
			commandTextField.drawTextBox();

			// テキストフィールドに数値が入ってる場合にボタンを有効化
			this.doneBtn.enabled = this.commandTextField.getText().trim().length() > 0;
		}

		super.drawGuiContainerForegroundLayer(p_146979_1_, p_146979_2_);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float tick, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(GUITEXTURE);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
	}

	// マウスでクリックした時のアクション
	public void mouseClickd(int i, int j, int k) {
		System.out.println("i:" + i + "/j:" + j);
		if (boolShop){
			if (0<= i && i<= xSize && 0 <=j && j <= 50 ) {

			}else {
				super.mouseClicked(i, i, k);
			}
			if (adminFLAG) {
				commandTextField.mouseClicked(i, j, k);
			}
		}else {
			super.mouseClicked(i, j, k);
		}



	}

	// キーをタイピングした時のアクション
	protected void keyTyped(char p_73869_1_, int p_73869_2_) {
		super.keyTyped(p_73869_1_, p_73869_2_);
		if (p_73869_2_ == 1) {
			mc.displayGuiScreen(null);
		}
		if (adminFLAG) {
			commandTextField.textboxKeyTyped(p_73869_1_, p_73869_2_);
		}
	}

	// ボタンを押した時のアクション
	public void actionPerformed(GuiButton guibutton) {
		if (adminFLAG) {
			if (guibutton.id == 0) {
				if (boolShop == true) {
					// 価格設定の処理
				}
			}
			if (guibutton.id == 1) {
				// 商品管理と在庫処理のbool切り替え
				boolShop = !boolShop;
			}
		}
	}
}