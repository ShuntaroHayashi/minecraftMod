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
import forestMoon.shoping.VillagerShopingMaster;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
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
	private ShopingItem currentItem;
	private String itemStr = "";
	private String buyStr = "";
	private MyGuiButton buyBtn, sellBtn;
	private int buy, sell;

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

		VillagerShopingMaster vItem = new VillagerShopingMaster();

		shopingItems = (ShopingItem[]) vItem.getProfessionItems(villager.getEconomicsProfession())
				.toArray(new ShopingItem[18]);

		container.ItemSet(shopingItems);
	}

	// イニット
	public void initGui() {
		super.initGui();

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

		VillagerShopingMaster vShopMaster = new VillagerShopingMaster();
		fontRendererObj.drawString(itemStr, 8, 55, 4210752);
		fontRendererObj.drawString(buyStr, 8, 64, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal(vShopMaster.getProfessionName(profession)), 8, 6,
				4210752);
		fontRendererObj.drawString(
				StatCollector.translateToLocal(StatCollector.translateToLocal("money") + properties.getMoney()), moneyX,
				moneyY, 4210752);

		int slotNumber = container.getLastSlotNumber();

		// 最後にクリックされたスロットが村人のスロットだった場合
		if (container.isVillagaerSlot(slotNumber)) {
			if (slotNumber < shopingItems.length && shopingItems[slotNumber] != null) {
				ShopingItem item = shopingItems[slotNumber];
				currentItem = item;

				itemStr = item.getItemStack().getDisplayName();

				buy = item.getBuy(villager.getBuyCountSlot(slotNumber));
				sell = item.getSell(villager.getBuyCountSlot(slotNumber));

				if (buy < 0) {
					buyStr = StatCollector.translateToLocalFormatted("sellOnry", sell);
				} else if (sell < 0) {
					buyStr = StatCollector.translateToLocalFormatted("buyOnry", buy);
				} else {
					buyStr = StatCollector.translateToLocalFormatted("villagerBuy", buy, sell);
				}

			} else {
				itemStr = "";
				buy = -1;
				sell = -1;
				buyStr = "";
			}

			if (villager.getBuyCountSlot(slotNumber) <= 0) {
				buy = -1;
				villager.setBuyCountSlot(slotNumber, 0);
			}
		} else {
			itemStr = "";
			buyStr = "";
		}

		buyBtn.enabled = buy >= 0 ? true : false;
		sellBtn.enabled = sell >= 0 ? true : false;

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
				price = currentItem.getBuy(villager.getBuyCountSlot(container.getLastSlotNumber()));
				itemStack = new ItemStack(currentItem.getItemStack().getItem(), 1,
						currentItem.getItemStack().getItemDamage());
				if (super.isShiftKeyDown()) {
					buy(itemStack, price, 64);
				} else {
					buy(itemStack, price, 1);
				}
				PacketHandler.INSTANCE.sendToServer(new MessageVillager(villager.getBuyCount(),
						villager.getEconomicsProfession(), villager.getEntityId()));
				break;
			case SELL_BUTTON:
				// 買取処理
				price = currentItem.getSell(villager.getBuyCountSlot(container.getLastSlotNumber()));
				itemStack = currentItem.getItemStack();
				if (super.isShiftKeyDown()) {
					sell(itemStack, price, 64);
				} else {
					sell(itemStack, price, 1);
				}
				PacketHandler.INSTANCE.sendToServer(new MessageVillager(villager.getBuyCount(),
						villager.getEconomicsProfession(), villager.getEntityId()));
				break;
			}
		}
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
		count = count < 0 ? 0 : count;

		villager.setBuyCountSlot(slotNum, count);
		properties.changeMoney(price * stackSize * -1);
		PacketHandler.INSTANCE.sendToServer(new MessagePlayerPropertieToServer(player));

	}

	// 買取処理
	private void sell(ItemStack itemStack, int price, int num) {
		// インベントリのアイテムをひとつづつ確認
		for (int index = 18; index < 50; index++) {
			if (container.getItemStack(index) != null) {
				ItemStack slotItem = container.getItemStack(index);
				// 同じアイテムか確認
				if (itemStack.getItem() == slotItem.getItem()
						&& itemStack.getItemDamage() == slotItem.getItemDamage()) {
					int number = container.getLastSlotNumber();
					if (slotItem.stackSize >= num) {
						properties.changeMoney(price * num);
						PacketHandler.INSTANCE.sendToServer(new MessagePlayerPropertieToServer(player));

						slotItem.stackSize -= num;
						container.slotChange(index, slotItem);

						villager.setBuyCountSlot(number, villager.getBuyCountSlot(number) + num);
						break;
					} else {
						properties.changeMoney(price * slotItem.stackSize);
						PacketHandler.INSTANCE.sendToServer(new MessagePlayerPropertieToServer(player));

						villager.setBuyCountSlot(number, villager.getBuyCountSlot(number) + slotItem.stackSize);

						num -= slotItem.stackSize;
						slotItem.stackSize = 0;

						container.slotChange(index, slotItem);
					}
				}
			}
		}
	}

	// マウスクリック時の処理
	@Override
	public void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);

		int slotNumber = container.getLastSlotNumber();

		// 最後にクリックされたスロットが村人のスロットだった場合
		// if (between(slotNumber, container.index1, container.index0)) {
		if (container.isVillagaerSlot(slotNumber)) {
			if (slotNumber < shopingItems.length && shopingItems[slotNumber] != null) {
				ShopingItem item = shopingItems[slotNumber];
				currentItem = item;

				itemStr = item.getItemStack().getDisplayName();

				buy = item.getBuy(villager.getBuyCountSlot(slotNumber));
				sell = item.getSell(villager.getBuyCountSlot(slotNumber));

				if (buy < 0) {
					buyStr = StatCollector.translateToLocalFormatted("sellOnry", sell);
				} else if (sell < 0) {
					buyStr = StatCollector.translateToLocalFormatted("buyOnry", buy);
				} else {
					buyStr = StatCollector.translateToLocalFormatted("villagerBuy", buy, sell);
				}

			} else {
				itemStr = "";
				buy = -1;
				sell = -1;
				buyStr = "";
			}
		} else {
			itemStr = "";
			buy = -1;
			sell = -1;
			buyStr = "";
		}
	}

	@Override
	public void keyTyped(char c, int i) {
		super.keyTyped(c, i);
		if (i == 1) {
			mc.displayGuiScreen(null);
		}
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

	// GUIが閉じられたとき
	public void guiClose() {
		PacketHandler.INSTANCE.sendToServer(
				new MessageVillager(villager.getBuyCount(), villager.getEconomicsProfession(), villager.getEntityId()));
	}

}
