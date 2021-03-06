package forestMoon.client.guicontainer;

import org.lwjgl.opengl.GL11;

import forestMoon.ExtendedPlayerProperties;
import forestMoon.client.gui.MyGuiButton;
import forestMoon.container.PlayerShopContainer;
import forestMoon.packet.PacketHandler;
import forestMoon.packet.player.MessagePlayerPropertieToServer;
import forestMoon.packet.shoping.MessagePlayerShopSyncToServer;
import forestMoon.packet.shoping.MessageSpawnItemStack;
import forestMoon.tileEntity.TileEntityShop;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class PlayerShopGuiContainer extends GuiContainer {

	private EntityPlayer player;
	private TileEntityShop tileEntity;
	private MyGuiButton buyBtn;
	private String shopName;// 店の名前
	private String itemName = "";// 選択中のアイテムの名前
	private ExtendedPlayerProperties properties;
	private int moneyX, moneyY;
	private static PlayerShopContainer container;
	private static final ResourceLocation GUITEXTURE = new ResourceLocation(
			"forestmoon:textures/gui/container/shopGui_2.png");

	public PlayerShopGuiContainer(EntityPlayer player, TileEntityShop tileEntity) {
		super(container = new PlayerShopContainer(tileEntity, player));
		this.player = player;
		this.tileEntity = tileEntity;
		ySize = 235;
		shopName = StatCollector.translateToLocalFormatted(StatCollector.translateToLocal("gui.playershop.name"),
				tileEntity.getAdminName());

		PacketHandler.INSTANCE.sendToServer(
				new MessagePlayerShopSyncToServer(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord));

		new ExtendedPlayerProperties();
		properties = ExtendedPlayerProperties.get(player);
	}

	public void initGui() {
		super.initGui();
		moneyX = xSize - 62;
		moneyY = this.ySize - 148;

		this.buttonList.add(buyBtn = new MyGuiButton(0, guiLeft + xSize - 50, guiTop + ySize - 165, 40, 15,
				StatCollector.translateToLocal("gui.button.buy")));
		buyBtn.enabled = false;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {

		fontRendererObj.drawString(shopName, 8, 4, 4210752);
		fontRendererObj.drawString(itemName, 8, 74, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("gui.money") + properties.getMoney(), moneyX, moneyY,
				4210752);
		itemName = container.slotItemToString(container.getLastSlotNumber());

		super.drawGuiContainerForegroundLayer(p_146979_1_, p_146979_2_);

		if (tileEntity.isShopSettingFlag()) {
			buyBtn.enabled = false;
		} else {
			buyBtn.enabled = !itemName.equals("");
		}

	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(GUITEXTURE);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
	}

	// ボタン押下時
	@Override
	protected void actionPerformed(GuiButton button) {
		ItemStack itemStack;
		int price;

		if (button.id == 0) {
			if (!itemName.equals("") && tileEntity.getStackInSlot(container.getLastSlotNumber()).stackSize > 0
					&& !tileEntity.isShopSettingFlag()) {
				price = tileEntity.getSellPrice(container.getLastSlotNumber());
				itemStack = tileEntity.getStackInSlot(container.getLastSlotNumber());
				buy(itemStack, price);
			}
		}
	}

	// 販売処理
	private void buy(ItemStack itemStack, int price) {
		long money = properties.getMoney();
		int num = 1;
		final int maxSize = tileEntity.getStackInSlot(container.getLastSlotNumber()).stackSize;

		if (super.isShiftKeyDown()) {
			while (num < maxSize && (price * num + price) <= money) {
				num++;
			}
		}
		if (price * num <= money) {
			double x = player.lastTickPosX;
			double y = player.lastTickPosY;
			double z = player.lastTickPosZ;

			PacketHandler.INSTANCE.sendToServer(new MessageSpawnItemStack(itemStack, x, y, z, num));

			properties.changeMoney(price * -1 * num);
			tileEntity.buy(container.getLastSlotNumber(), num);
			PacketHandler.INSTANCE.sendToServer(new MessagePlayerPropertieToServer(player));
		}
	}

	@Override
	protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
		super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
	}
}
