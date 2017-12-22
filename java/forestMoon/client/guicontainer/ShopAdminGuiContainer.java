package forestMoon.client.guicontainer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestMoon.ExtendedPlayerProperties;
import forestMoon.client.entity.TileEntityChest;
import forestMoon.container.ShopAdminContainer;
import forestMoon.packet.PacketHandler;
import forestMoon.packet.player.MessagePlayerPropertieToServer;
import forestMoon.packet.shoping.MessageAdminFlagToServer;
import forestMoon.packet.shoping.MessageEarningsSyncToServer;
import forestMoon.packet.shoping.MessageShopChangeFlag;
import forestMoon.packet.shoping.MessageShopingSyncToServer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

@SideOnly(Side.CLIENT)
public class ShopAdminGuiContainer extends GuiContainer {
	private GuiTextField commandTextField;
	private int x,y,z,moneyX,moneyY;
	private GuiButton doneBtn,changeBtn,salengGetBtn;
	private TileEntityChest tileEntity;
	private boolean boolShop = false;
	private EntityPlayer player;
	private boolean adminFLAG = false;
	private ExtendedPlayerProperties properties;
	private static  ShopAdminContainer container;
	private String itemName = "";
	private String mode="";

	public enum ButtonId{
	    doneBtn(0),
	    changeBtn(1),
	    salengGetBtn(2);

	    private final int id;

	    private ButtonId(int id) {
	        this.id = id;
	    }
	    public int getId() {
	    	return id;
	    }
	}

	private static final ResourceLocation GUITEXTURE = new ResourceLocation(
			"forestmoon:textures/gui/container/shopGui_2.png");

	public ShopAdminGuiContainer(EntityPlayer player, TileEntityChest tileEntitiy) {
		super(container = new ShopAdminContainer(player, tileEntitiy));
		this.tileEntity = tileEntitiy;
		this.player = player;
		ySize = 235;

		x= tileEntity.xCoord;
		y = tileEntity.yCoord;
		z = tileEntity.zCoord;

		new ExtendedPlayerProperties();
		properties = ExtendedPlayerProperties.get(player);
		PacketHandler.INSTANCE.sendToServer(new MessageShopingSyncToServer(x, y, z));
		PacketHandler.INSTANCE.sendToServer(new MessageShopChangeFlag(true,x,y,z));
	}

	public void initGui() {
		super.initGui();
		tileEntity.setSlotClickFlag(true);
		if(tileEntity.getAdminName().equals("NONE")) {
			tileEntity.setAdminName(player.getCommandSenderName());
			PacketHandler.INSTANCE.sendToServer(new MessageShopingSyncToServer(x, y, z, player.getCommandSenderName(),tileEntity.getSellPrices()));
		}else if (tileEntity.getAdminName().equals(player.getCommandSenderName())) {
			adminFLAG = true;
		}
		if (adminFLAG) {
			this.buttonList.add(this.doneBtn = new GuiButton(ButtonId.doneBtn.id, this.width / 2 + 100, this.height / 2 - 60, 105, 20, StatCollector.translateToLocal("doneButton")));
			this.buttonList.add(this.changeBtn = new GuiButton(ButtonId.changeBtn.id, this.width / 2 + 100, this.height / 2 - 30, 105, 20, StatCollector.translateToLocal("changeButton")));
			this.buttonList.add(this.salengGetBtn = new GuiButton(ButtonId.salengGetBtn.id, this.width/2 + 100, this.height/2, 105,20,StatCollector.translateToLocal("salengGetButton")));

			this.doneBtn.enabled = false;

			commandTextField = new GuiTextField(this.fontRendererObj, 190, 20, 100, 20);
			commandTextField.setFocused(true);
			commandTextField.setText("");
			commandTextField.setMaxStringLength(8);
		}

		moneyX = xSize - 62;
		moneyY = this.ySize - 148;

		PacketHandler.INSTANCE.sendToServer(new MessageAdminFlagToServer(x	, y, z, true));

	}

	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
		fontRendererObj.drawString(mode, xSize+18, 2, 16777215);
		fontRendererObj.drawString(StatCollector.translateToLocalFormatted(StatCollector.translateToLocal("sales"), tileEntity.getEarnings()),8, 84, 4210752);
//		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocalFormatted("shop_name",tileEntity.getAdminName()), 8, 6, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal(StatCollector.translateToLocal("money") + properties.getMoney()), moneyX, moneyY, 4210752);
		fontRendererObj.drawString(itemName, 8 , 72, 4210752);
		if (adminFLAG) {
			commandTextField.setEnabled(boolShop);
			commandTextField.drawTextBox();

			// テキストフィールドに数値が入ってる場合にボタンを有効化
			this.doneBtn.enabled = this.commandTextField.getText().trim().length() > 0;
		}

		if (boolShop) {
			mode=StatCollector.translateToLocal("priceChange");
		}else {
			mode=StatCollector.translateToLocal("itemChange");
		}
		itemName = container.slotItemToString(container.getLastSlotNumber());

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
			if (guibutton.id == ButtonId.doneBtn.id) {
				if (boolShop == true) {
					// 価格設定の処理
					try {
						int price = Integer.parseInt(commandTextField.getText());
						tileEntity.setSellPrice(container.getLastSlotNumber(), price);
					} catch (Exception e) {
						// TODO: handle exception
					}

				}
			}
			if (guibutton.id == ButtonId.changeBtn.id) {
				// 商品管理と在庫処理のbool切り替え
				boolShop = !boolShop;
				container.setClickFlag(!container.getClickFlag());

			}
			if(guibutton.id == ButtonId.salengGetBtn.id) {
				properties.changeMoney(tileEntity.getEarnings());
				tileEntity.setEarnings(0);
				PacketHandler.INSTANCE.sendToServer(new MessagePlayerPropertieToServer(player));
				PacketHandler.INSTANCE.sendToServer(new MessageEarningsSyncToServer(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, tileEntity.getEarnings()));
			}
		}
	}
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		PacketHandler.INSTANCE.sendToServer(new MessageShopChangeFlag(false,tileEntity.xCoord,tileEntity.yCoord,tileEntity.zCoord));
		PacketHandler.INSTANCE.sendToServer(new MessageAdminFlagToServer(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, false));
	}
}