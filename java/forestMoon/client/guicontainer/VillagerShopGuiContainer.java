package forestMoon.client.guicontainer;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestMoon.ExtendedPlayerProperties;
import forestMoon.client.entity.EntityECVillager;
import forestMoon.client.gui.MyGuiButton;
import forestMoon.container.VillagerShopContainer;
import forestMoon.packet.PacketHandler;
import forestMoon.packet.player.MessagePlayerPropertieToServer;
import forestMoon.packet.shoping.MessageSpawnItemStack;
import forestMoon.packet.villager.MessageVillager;
import forestMoon.packet.villager.MessageVillagerSyncToServer;
import forestMoon.shoping.ShopingItem;
import forestMoon.shoping.VillagerShopingItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

@SideOnly(Side.CLIENT)
public class VillagerShopGuiContainer extends GuiContainer {
	private static final ResourceLocation TEXTURE = new ResourceLocation("forestmoon",
			"textures/guis/villagerShopGui.png");
	private static VillagerShopContainer container;
	private final int BUY_BUTTON = 0;
	private final int SELL_BUTTON = 1;
	private int moneyX = 0;
	private int moneyY = 0;
	private ShopingItem[] shopingItems;
	private int profession = 0;
	private EntityPlayer player;
	private EntityECVillager villager;
	private ExtendedPlayerProperties properties;
	private GuiTextField textField;
	private ShopingItem currentItem;
	private String itemStr = "";
	private String buyStr = "";
	private MyGuiButton buyBtn, sellBtn;
	private int buy,sell;
	private int[] buyCount;

	// コンストラクター
	public VillagerShopGuiContainer(EntityPlayer player) {
		super(container = new VillagerShopContainer(player.inventory, true, player));

		this.allowUserInput = true;
		this.player = player;

		moneyX = this.guiLeft + 8;
		moneyY = this.guiTop + this.ySize - 85;

		new ExtendedPlayerProperties();
		properties = ExtendedPlayerProperties.get(player);
	}

	public VillagerShopGuiContainer(EntityPlayer player, int id) {
		this(player);

		villager = (EntityECVillager) (Minecraft.getMinecraft().theWorld.getEntityByID(id));
		PacketHandler.INSTANCE.sendToServer(new MessageVillagerSyncToServer(villager.getEntityId()));
		this.readItemToEntity(villager);

		VillagerShopingItem vItem = new VillagerShopingItem();

		shopingItems = (ShopingItem[]) vItem.getProfessionItems(villager.getEconomicsProfession())
				.toArray(new ShopingItem[14]);

		container.ItemSet(shopingItems);
	}

	// イニット
	public void initGui() {
		super.initGui();

//		textField = new GuiTextField(fontRendererObj, (this.xSize) / 2 + 18, 72, 20, 15);
//		textField.setFocused(true);
//		textField.setText("");
//		textField.setMaxStringLength(3);

		buy = -1;
		sell = -1;

		this.ySize += 7;

		buttonList.add(buyBtn = new MyGuiButton(BUY_BUTTON, this.guiLeft + (this.xSize / 4) * 3 - 4, this.guiTop + 56,
				this.xSize / 4, 15, StatCollector.translateToLocal("buy_button")));
		buttonList.add(sellBtn = new MyGuiButton(SELL_BUTTON, this.guiLeft + (this.xSize / 4) * 3 - 4, this.guiTop + 72,
				this.xSize / 4, 15, StatCollector.translateToLocal("sell_button")));

		buyBtn.enabled = false;
		sellBtn.enabled = false;

	}

	/* GUIの文字等の描画処理 */
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseZ) {

		VillagerShopingItem shopingItem = new VillagerShopingItem();
		fontRendererObj.drawString(itemStr, 8, 55, 4210752);
		fontRendererObj.drawString(buyStr, 8, 64, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal(shopingItem.getProfessionName(profession)), 8, 6,
				4210752);
//		fontRendererObj.drawString(StatCollector.translateToLocal("shopingGui_num"), (this.xSize) / 2 - 4, 77, 4210752);
		fontRendererObj.drawString(
				StatCollector.translateToLocal(StatCollector.translateToLocal("money") + properties.getMoney()), moneyX,
				moneyY, 4210752);

//		textField.setEnabled(true);
//		textField.drawTextBox();


		int slotNumber = container.getLastSlotNumber();

		// 最後にクリックされたスロットが村人のスロットだった場合
		if (between(slotNumber, 13, 0)) {
		}else {
			itemStr = "";
			buyStr = "";
		}

		if(villager.getBuyCountSlot(slotNumber) <= 0) {
			buy = -1;
			villager.setBuyCountSlot(slotNumber, 0);
		}

		buyBtn.enabled = (buy >= 0);
		sellBtn.enabled = (sell >= 0);

//		buyBtn.enabled = !itemStr.equals("");
//		sellBtn.enabled = !itemStr.equals("");

		super.drawGuiContainerForegroundLayer(mouseX, mouseZ);
	}

	/* GUIの背景の描画処理 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseZ) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(TEXTURE);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, xSize, ySize);
	}

	/* GUIが開いている時にゲームの処理を止めるかどうか。 */
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	// ボタン押下時
	@Override
	protected void actionPerformed(GuiButton button) {
		ItemStack itemStack;
		int price;

		if (currentItem != null) {
			// 押下ボタン判定
			switch (button.id) {
			default:
				break;
			case BUY_BUTTON:
				// 販売処理
//				price = buyCalc(currentItem.getBuy(), villager.getBuyCountSlot(container.getLastSlotNumber()),
//						currentItem.getCofficient());
				price = currentItem.getBuy(villager.getBuyCountSlot(container.getLastSlotNumber()));
				itemStack = new ItemStack(currentItem.getItemStack().getItem());
//				try {
//					int num = Integer.parseInt(textField.getText());
//					buy(itemStack, price, num);
//				} catch (NumberFormatException exception) {
//					ChatComponentText chatText;
//					chatText = new ChatComponentText(StatCollector.translateToLocal("shopingError_1"));
//					player.addChatMessage(chatText);
//				}
				if(super.isShiftKeyDown()) {
					buy(itemStack, price, 64);
				}else {
					buy(itemStack, price, 1);
				}
				PacketHandler.INSTANCE.sendToServer(new MessageVillager(villager.getBuyCount(),
						villager.getEconomicsProfession(), villager.getEntityId()));
				break;
			case SELL_BUTTON:
				// 買取処理
//				price = sellCalc(currentItem.getSell(), villager.getBuyCountSlot(container.getLastSlotNumber()),
//						currentItem.getCofficient());

				price = currentItem.getSell(villager.getBuyCountSlot(container.getLastSlotNumber()));

				itemStack = currentItem.getItemStack();
//				try {
//					int num = Integer.parseInt(textField.getText());
//					sell(itemStack, price, num);
//				} catch (NumberFormatException e) {
//					ChatComponentText chatText;
//					chatText = new ChatComponentText(StatCollector.translateToLocal("shopingError_1"));
//					player.addChatMessage(chatText);
//				}
				if(super.isShiftKeyDown()) {
					sell(itemStack, price, 64);
				}else {
					sell(itemStack, price, 1);
				}
				PacketHandler.INSTANCE.sendToServer(new MessageVillager(villager.getBuyCount(),
						villager.getEconomicsProfession(), villager.getEntityId()));
				break;
			}

		}
	}

	private int buyCalc(int price, int count, double coefficient) {
		if(price >= 0) {
			int num = 0;
			if (count > 0) {
				num = (int) ((count / coefficient) * price);
			}
			num = num <= price ? price  : num;

			return num;
		}
		return -1;
	}

	private int sellCalc(int price, int count, double coefficient) {
		if(price >= 0) {
		int num = 0;
		if (count > 0) {
			num = (int) ((count / coefficient) * price);

		}
		num = num < 0 ? 0 : num;
		return num;
		}
		return -1;
	}

	// 販売処理
	private void buy(ItemStack itemStack, int price, int num) {
		long money = properties.getMoney();
		int stackSize = 0;
		for (int i = 0; (i < num && money >= price); i++) {
			stackSize++;
			money -= price;
		}

		if (stackSize >= 1) {
			double x = player.lastTickPosX;
			double y = player.lastTickPosY;
			double z = player.lastTickPosZ;

			PacketHandler.INSTANCE.sendToServer(new MessageSpawnItemStack(itemStack, x, y, z, stackSize));
		}
		int slotNum = container.getLastSlotNumber();
		int count = villager.getBuyCountSlot(slotNum);

		count -= stackSize;
		if(count < 0) count = 0;

		villager.setBuyCountSlot(slotNum,count);

		properties.changeMoney(price * stackSize * -1);

		fontRendererObj.drawString(
				StatCollector.translateToLocal(StatCollector.translateToLocal("money") + properties.getMoney()), moneyX,
				moneyY, 4210752);
		PacketHandler.INSTANCE.sendToServer(new MessagePlayerPropertieToServer(player));

	}

	// 買取処理
	private void sell(ItemStack itemStack, int price, int num) {
		// インベントリのアイテムをひとつづつ確認
		for (int index = 14; index < 50; index++) {
			if (container.getItemStack(index) != null) {
				ItemStack slotItem = container.getItemStack(index);
				// 同じアイテムか確認
				if (itemStack.getItem() == slotItem.getItem()) {
					int number = container.getLastSlotNumber();
					if (slotItem.stackSize >= num) {
						properties.changeMoney(price * num);
						PacketHandler.INSTANCE.sendToServer(new MessagePlayerPropertieToServer(player));

						itemStack.stackSize = slotItem.stackSize - num;
						container.slotChange(index, itemStack);

						villager.setBuyCountSlot(number, villager.getBuyCountSlot(number) + num);

						break;
					} else {
						properties.changeMoney(price * slotItem.stackSize);
						PacketHandler.INSTANCE.sendToServer(new MessagePlayerPropertieToServer(player));

						villager.setBuyCountSlot(number, villager.getBuyCountSlot(number) + slotItem.stackSize);

						num -= slotItem.stackSize;
						itemStack.stackSize = 0;

						container.slotChange(index, itemStack);

					}
				}
			}
		}
	}

	// マウスクリック時の処理
	@Override
	public void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
//		textField.mouseClicked(i - this.guiLeft, j - this.guiTop, k);

		int slotNumber = container.getLastSlotNumber();

		// 最後にクリックされたスロットが村人のスロットだった場合
		if (between(slotNumber, 13, 0)) {
			if (slotNumber < shopingItems.length && shopingItems[slotNumber] != null) {
				ShopingItem item = shopingItems[slotNumber];
				currentItem = item;

				itemStr = item.getItemStack().getDisplayName();
//				buy = buyCalc(item.getBuy(buyCount[slotNumber]), villager.getBuyCountSlot(slotNumber), item.getCofficient());
//				sell = sellCalc(item.getSell(), villager.getBuyCountSlot(slotNumber), item.getCofficient());

				buy = item.getBuy(villager.getBuyCountSlot(slotNumber));
				sell = item.getSell(villager.getBuyCountSlot(slotNumber));

				if(buy < 0) {
					buyStr = StatCollector.translateToLocalFormatted("sellOnry", sell);
				}else if(sell < 0) {
					buyStr = StatCollector.translateToLocalFormatted("buyOnry", buy);
				}else {
					buyStr = StatCollector.translateToLocalFormatted("villagerBuy", buy, sell);
				}

			} else {
				itemStr = "";
				buy = -1;
				sell = -1;
				buyStr = "";
			}
		}else {
			itemStr = "";
			buy = -1;
			sell = -1;
			buyStr = "";
		}

	}


	// 引数１の値が引数3以上 引数2以下か確認
	private boolean between(int chekNum, int maxNum, int minNum) {
		if (minNum <= chekNum && chekNum <= maxNum) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void keyTyped(char c, int i) {
		super.keyTyped(c, i);
		if (i == 1) {
			mc.displayGuiScreen(null);
		}
//		textField.textboxKeyTyped(c, i);
	}

	// データの読み取り
	private void readItemToEntity(EntityECVillager villager) {
		this.profession = villager.getEconomicsProfession();
	}

	// 終了時処理
	public void onGuiClosed() {
		if (this.mc.thePlayer != null) {
			this.inventorySlots.onContainerClosed(this.mc.thePlayer);
			this.guiClose();
		}
	}

	// 買い物終了時に取引内容に応じて値段を変動
	public void guiClose() {
		PacketHandler.INSTANCE.sendToServer(
				new MessageVillager(villager.getBuyCount(), villager.getEconomicsProfession(), villager.getEntityId()));
	}

}
