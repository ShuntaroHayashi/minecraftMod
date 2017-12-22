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
import forestMoon.shoping.ShopingItem;
import forestMoon.shoping.VillagerShopingItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

@SideOnly(Side.CLIENT)
public class VillagerShopGuiContainer extends GuiContainer{
	private static final ResourceLocation TEXTURE = new ResourceLocation("forestmoon", "textures/guis/buyGui.png");
	private static VillagerShopContainer shopingContainer;
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
	private String buyStr="";

	//コンストラクター
	public VillagerShopGuiContainer(EntityPlayer player) {
		super(shopingContainer = new VillagerShopContainer(player.inventory, true, player));

		this.allowUserInput = true;
		this.player = player;

		moneyX = this.guiLeft + 8;
		moneyY = this.guiTop + this.ySize - 92;

		new ExtendedPlayerProperties();
		properties = ExtendedPlayerProperties.get(player);
	}

	public VillagerShopGuiContainer(EntityPlayer player,int id){
		this(player);

		villager = (EntityECVillager)(Minecraft.getMinecraft().theWorld.getEntityByID(id));
		this.readItemToEntity(villager);

		shopingContainer.ItemSet(shopingItems);
	}

	//イニット
	public void initGui() {
		super.initGui();

		textField = new GuiTextField(fontRendererObj,(this.xSize ) / 2 + 18, 65, 20, 15);
		textField.setFocused(true);
		textField.setText("");
		textField.setMaxStringLength(3);

		buttonList.add(new MyGuiButton(BUY_BUTTON, this.guiLeft + (this.xSize/4) * 3 - 4, this.guiTop +49, this.xSize/4, 15, StatCollector.translateToLocal("buy_button")));
		buttonList.add(new MyGuiButton(SELL_BUTTON,this.guiLeft + (this.xSize/4) * 3 - 4, this.guiTop + 65, this.xSize/4,15,StatCollector.translateToLocal("sell_button")));

	}

	/*GUIの文字等の描画処理*/
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseZ) {

		VillagerShopingItem shopingItem = new VillagerShopingItem();
		fontRendererObj.drawString(itemStr, 4, 48, 4210752);
		fontRendererObj.drawString(buyStr, 4, 57, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal(shopingItem.getProfessionName(profession)), 8, 6, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("shopingGui_num"),(this.xSize) / 2 - 4, 70, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal(StatCollector.translateToLocal("money") + properties.getMoney()), moneyX, moneyY, 4210752);

		textField.setEnabled(true);
		textField.drawTextBox();

		super.drawGuiContainerForegroundLayer(mouseX, mouseZ);

	}

	/*GUIの背景の描画処理*/
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseZ) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(TEXTURE);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, xSize, ySize);
	}

	/*GUIが開いている時にゲームの処理を止めるかどうか。*/
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	//ボタン押下時
	@Override
	protected void actionPerformed(GuiButton button) {
		ItemStack itemStack;
		int price;

		if(currentItem != null){
			//押下ボタン判定
			switch (button.id) {
				default:
					break;
				case BUY_BUTTON:
					//販売処理
					price = currentItem.getBuy();
					itemStack = new ItemStack(currentItem.getItemStack().getItem());
					try{
						int num = Integer.parseInt(textField.getText());
						buy(itemStack, price, num);
					}catch(NumberFormatException exception){
						ChatComponentText chatText;
						chatText = new ChatComponentText(StatCollector.translateToLocal("shopingError_1"));
							player.addChatMessage(chatText);
					}
					break;
				case SELL_BUTTON:
					//買取処理
					price  = currentItem.getSell();
					itemStack = currentItem.getItemStack();
					try {
						int num = Integer.parseInt(textField.getText());
						sell(itemStack, price,num);
					} catch (NumberFormatException e) {
						ChatComponentText chatText;
						chatText = new ChatComponentText(StatCollector.translateToLocal("shopingError_1"));
						player.addChatMessage(chatText);
					}
					break;
			}
		}
	}

	//販売処理
	private void buy(ItemStack itemStack,int price,int num){
		long money = properties.getMoney();
		int stackSize = 0;
		for(int i=0;( i<num && money >= price );i++){
			stackSize++;
			money -= price;
		}

		if(stackSize >= 1 ){
			double x = player.lastTickPosX;
			double y = player.lastTickPosY;
			double z = player.lastTickPosZ;

			PacketHandler.INSTANCE.sendToServer(new MessageSpawnItemStack(itemStack, x, y, z,stackSize));
		}

		properties.changeMoney(price * stackSize * -1);

		fontRendererObj.drawString(StatCollector.translateToLocal(StatCollector.translateToLocal("money") + properties.getMoney()), moneyX, moneyY, 4210752);
		PacketHandler.INSTANCE.sendToServer(new MessagePlayerPropertieToServer(player));

	}

	//買取処理
	private void sell(ItemStack itemStack , int price,int num){
		for(int index = 14;index<50;index++){
			if(shopingContainer.getItemStack(index) != null){
				ItemStack slotItem = shopingContainer.getItemStack(index);
				if(itemStack.getItem() == slotItem.getItem()){
					if(slotItem.stackSize >= num ){
						properties.changeMoney(price * num);
						PacketHandler.INSTANCE.sendToServer(new MessagePlayerPropertieToServer(player));

						itemStack.stackSize = slotItem.stackSize - num;
						shopingContainer.slotChange(index, itemStack);

						fontRendererObj.drawString(StatCollector.translateToLocal(StatCollector.translateToLocal("money") + properties.getMoney()), moneyX, moneyY, 4210752);

					break;
					}else{
						properties.changeMoney(price * slotItem.stackSize);
						PacketHandler.INSTANCE.sendToServer(new MessagePlayerPropertieToServer(player));

						num -= slotItem.stackSize;
						itemStack.stackSize = 0;

						shopingContainer.slotChange(index, itemStack);
						fontRendererObj.drawString(StatCollector.translateToLocal(StatCollector.translateToLocal("money") + properties.getMoney()), moneyX, moneyY, 4210752);

					}
				}
			}
		}
	}
	//マウスクリック時の処理
	@Override
	public void mouseClicked(int i,int j,int k){
		super.mouseClicked(i, j, k);
		textField.mouseClicked(i-this.guiLeft, j-this.guiTop, k);

		//最後にクリックされたスロットが村人のスロットだった場合
		if(between(shopingContainer.getLastSlotNumber(), 13, 0)){
			if(shopingContainer.getLastSlotNumber() < shopingItems.length){
				ShopingItem item = shopingItems[ shopingContainer.getLastSlotNumber()];
				currentItem = item;
//				String string = new String(item.getItemStack().getDisplayName()+" 売値"+item.getBuy()+" 買値" + item.getSell());
//				itemField.setText(string);
				itemStr = new String(item.getItemStack().getDisplayName());
				buyStr = new String(StatCollector.translateToLocal("buy") + item.getBuy() + " " + StatCollector.translateToLocal("sell") + item.getSell());
			}
		}
	}
	//引数１の値が引数3以上 引数2以下か確認
	private boolean between(int chekNum, int maxNum,int minNum){
		if(minNum <= chekNum && chekNum <= maxNum){
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void keyTyped(char c , int i){
		super.keyTyped(c, i);
		if(i== 1){
			mc.displayGuiScreen(null);
		}
		textField.textboxKeyTyped(c, i);
	}

	//データの読み取り
	private void readItemToEntity(EntityECVillager villager){
		this.shopingItems = villager.getShopingItems();
		this.profession = villager.getEconomicsProfession();
	}

	//終了時処理
	public void onGuiClosed(){
		if (this.mc.thePlayer != null){
			this.inventorySlots.onContainerClosed(this.mc.thePlayer);
			this.guiClose();
		}
	}
	//買い物終了時に取引内容に応じて値段を変動
	public void guiClose(){
		for (ShopingItem item : shopingItems) {
			item.setBuy(item.getBuy()+10);
			item.setSell(item.getSell()+5);
		}
		villager.setShopingItems(shopingItems);
		PacketHandler.INSTANCE.sendToServer(new MessageVillager(villager.getShopingItems(), villager.getProfession(), villager.getEntityId()));
	}

}
