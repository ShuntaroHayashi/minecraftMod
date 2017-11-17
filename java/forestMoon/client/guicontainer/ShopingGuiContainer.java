package forestMoon.client.guicontainer;


import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestMoon.ExtendedPlayerProperties;
import forestMoon.client.entity.EntityECVillager;
import forestMoon.client.gui.MyGuiButton;
import forestMoon.container.ShopingContainer;
import forestMoon.shoping.ShopingItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

@SideOnly(Side.CLIENT)
public class ShopingGuiContainer extends GuiContainer{
	 private static final ResourceLocation TEXTURE = new ResourceLocation("forestmoon", "textures/guis/buyGui.png");
	 private static ShopingContainer shopingContainer;
	 private final int BUY_BUTTON = 0;
	 private final int SELL_BUTTON = 1;
	 private int moneyX = 0;
	 private int moneyY = 0;
	 private ShopingItem[] shopingItems;
	 private int profession;
	 private EntityPlayer player;
	 private EntityECVillager villager;
	 private EntityPlayerMP playerMP;
	 private ExtendedPlayerProperties properties;
	 private GuiTextField textField;
	 private GuiTextField itemField;
	 private ShopingItem currentItem;

	public ShopingGuiContainer(EntityPlayer player) {
		super(shopingContainer = new ShopingContainer(player.inventory, true, player));

		this.allowUserInput = true;
		this.player = player;

		moneyX = this.guiLeft + this.xSize - 18;
		moneyY = this.guiTop + this.ySize - 92;

		playerMP = MinecraftServer.getServer().getConfigurationManager().func_152612_a(player.getCommandSenderName());
		new ExtendedPlayerProperties();
		properties = ExtendedPlayerProperties.get(playerMP);

	}

	public ShopingGuiContainer(EntityPlayer player,int id){
		this(player);

		villager = (EntityECVillager)(Minecraft.getMinecraft().getIntegratedServer().getEntityWorld().getEntityByID(id));
		this.readItemToEntity(villager);

		shopingContainer.ItemSet(shopingItems);
	}

	public void initGui() {
		super.initGui();

        textField = new GuiTextField(fontRendererObj,(this.xSize - 20) / 2 , 65, 20, 15);
        textField.setFocused(true);
        textField.setText("");
        textField.setMaxStringLength(3);

        itemField = new GuiTextField(fontRendererObj, 4, 48, (this.xSize / 4) * 3 -10, 15);
        itemField.setFocused(false);
        itemField.setText("");
        itemField.setMaxStringLength(200);

        buttonList.add(new MyGuiButton(BUY_BUTTON, this.guiLeft + (this.xSize/4) * 3 - 4, this.guiTop +49, this.xSize/4, 15, "買う"));
    	buttonList.add(new MyGuiButton(SELL_BUTTON,this.guiLeft + (this.xSize/4) * 3 - 4, this.guiTop + 65, this.xSize/4,15,"売る"));

	}

    /*GUIの文字等の描画処理*/
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseZ) {

    	fontRendererObj.drawString(StatCollector.translateToLocal("花屋"), 8, 6, 4210752);
    	fontRendererObj.drawString("" , 8 ,50 , 4210752);
//    	fontRendererObj.drawString(StatCollector.translateToLocal("何を買う？"), 8, 30, 4210752);
    	fontRendererObj.drawString(StatCollector.translateToLocal(StatCollector.translateToLocal("money") + properties.getMoney()), getMoneyPosition(), moneyY, 4210752);

    	textField.setEnabled(true);
    	textField.drawTextBox();

    	itemField.setEnabled(true);
    	itemField.drawTextBox();

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


    @Override
    protected void actionPerformed(GuiButton button) {
		ItemStack itemStack;
		int price;


		if(currentItem != null){
			//押下ボタン判定
	    	switch (button.id) {
	    		default:
	    			price = 0;
					itemStack = null;
					break;
	    		case BUY_BUTTON:
	//    			price = -100;
//	    			itemStack = new ItemStack(Items.apple);
//	    			System.out.println(itemStack +"/"+ currentItem.getItemStack() );
	    			price = currentItem.getBuy() * -1;
//					itemStack = currentItem.getItemStack();
	    			itemStack = new ItemStack(currentItem.getItemStack().getItem());
	    			break;
	    		case SELL_BUTTON:
	    			price  = currentItem.getSell();
	    			itemStack = currentItem.getItemStack();
	    			break;
			}

	    	//買う
	    	if(itemStack != null && price < 0 ){
//	    		buy(itemStack, price);
	    		try{
	    		int num = Integer.parseInt(textField.getText());
	    			buy(itemStack, price, num);
	    		}catch(NumberFormatException exception){
	    			ChatComponentText chatText;
	    			chatText = new ChatComponentText(StatCollector.translateToLocal("shopingError_1"));
	    			player.addChatMessage(chatText);

//	    			System.err.println("整数以外が入力されました");
//	    			System.err.println(exception.getMessage());
	    		}

	    	//売る
	    	}else if (itemStack != null && price > 0) {
	    		sell(itemStack, price);
	    	}
		}
    }

    //買い物処理
    private void buy(ItemStack itemStack,int price,int num){

    	long money = properties.getMoney();
    	int stackSize=0;
    	int buyCount = 0;;
    	for(int i=0; i<num && money > price;i++){
    		if(stackSize == 64){
    			itemStack.stackSize = stackSize;
    			EntityItem entityItem = playerMP.dropPlayerItemWithRandomChoice(itemStack, false);
    			entityItem.delayBeforeCanPickup = 0;
    			entityItem.func_145797_a(playerMP.getCommandSenderName());

    			stackSize = 0;
    		}else {
				stackSize++;
			}
    		money -= price;
    		buyCount = i + 1;
    	}
    	if(stackSize >= 1 ){
			itemStack.stackSize = stackSize;
			EntityItem entityItem = playerMP.dropPlayerItemWithRandomChoice(itemStack, false);
			entityItem.delayBeforeCanPickup = 0;
			entityItem.func_145797_a(playerMP.getCommandSenderName());
    	}

    	properties.changeMoney(price * buyCount);

		fontRendererObj.drawString(StatCollector.translateToLocal(StatCollector.translateToLocal("money") + properties.getMoney()), getMoneyPosition(), moneyY, 4210752);
		properties.syncPlayerData(playerMP);

    }

    //販売処理
    private void sell(ItemStack itemStack,int price){
    	for(int i = 0; i< 36;i++){
			if(shopingContainer.getItemStack(i) != null){
				if(itemStack.getItem() == shopingContainer.getItemStack(i).getItem()){
					properties.changeMoney(price);
    				properties.syncPlayerData(playerMP);

					itemStack.stackSize = shopingContainer.getItemStack(i).stackSize - 1;
    				shopingContainer.slotChange(i, itemStack,price);

    				fontRendererObj.drawString(StatCollector.translateToLocal(StatCollector.translateToLocal("money") + properties.getMoney()), getMoneyPosition(), moneyY, 4210752);

    				break;
    			}
			}
		}
    }

    @Override
    public void mouseClicked(int i,int j,int k){
    	super.mouseClicked(i, j, k);
    	textField.mouseClicked(i-this.guiLeft, j-this.guiTop, k);

    	if(shopingContainer.getLastSlotNumber() >=36 && shopingContainer.getLastSlotNumber() < 50){
    		if(shopingContainer.getLastSlotNumber() - 36 < shopingItems.length){
	    		ShopingItem item = shopingItems[ shopingContainer.getLastSlotNumber()-36];
	    			currentItem = item;

//	    		System.out.println(item);

	    		String string = new String(item.getItemStack().getDisplayName()+" 売値"+item.getBuy()+" 買値" + item.getSell());

	    		itemField.setText(string);

	//    		fontRendererObj.drawString(item.getItemStack().getDisplayName()+" 売値"+item.getBuy()+" 買値" + item.getSell() , 8 ,50 , 4210752);
	    		fontRendererObj.drawString(StatCollector.translateToLocal(string)  , 8 ,50 , 4210752);
    		}
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

    //所持金表示位置計算
    private int getMoneyPosition(){
    	long money = properties.getMoney();
    	int work = 0;

    	while(money >= 100){
    		work += 4;
    		money /= 10;
    	}
		return moneyX - work;
    }


    private void readItemToEntity(EntityECVillager villager){

//    	System.out.println(villager);

    	this.shopingItems = villager.getShopingItems();
    	this.profession = villager.getProfession();

//    	for (ShopingItem item : shopingItems) {
//			System.out.println(item);
//		}

    }

    public void click(){

    }

    //終了時処理
    public void onGuiClosed()
    {
        if (this.mc.thePlayer != null){
            this.inventorySlots.onContainerClosed(this.mc.thePlayer);

            this.guiClose();
        }
    }
    public void guiClose(){
//    	System.out.println(player);
        for (ShopingItem item : shopingItems) {
			item.setBuy(item.getBuy()+100);
			item.setSell(item.getSell()+50);
		}

            villager.setShopingItems(shopingItems);
    }

}
