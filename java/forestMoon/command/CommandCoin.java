package forestMoon.command;

import java.util.Random;

import forestMoon.ExtendedPlayerProperties;
import forestMoon.Items.ItemCoin;
import forestMoon.Items.ItemRegister;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class CommandCoin extends CommandBase{


	@Override
	public String getCommandName() {
		return "coin";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "CommandCoin";
	}

	@Override
	public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_) {
		Random random = new Random();
		EntityPlayerMP player = getCommandSenderAsPlayer(p_71515_1_);;
		String scanMoney="";
		//引数の確認
		if(0 < p_71515_2_.length  && p_71515_2_.length <= 2){
			if(p_71515_2_.length == 1){
				scanMoney = p_71515_2_[0];
			}else if(p_71515_2_.length == 2){
				player = getPlayer(p_71515_1_, p_71515_2_[1]);
				scanMoney = p_71515_2_[0];
			}
			if(scanMoney.length() <= 10){
				ExtendedPlayerProperties properties = ExtendedPlayerProperties.get(player);
				long money = Integer.parseInt(scanMoney);
				if(properties.getMoney() >= money){
					money = money - (money % 100);
					properties.changeMoney(-1 * money);
					properties.syncPlayerData(player);

					  //コインドロップ処理
		        	for (int i = 0; i < 7; i++) {

		        		//Itemの作成
		            	ItemStack itemStack = new ItemStack(ItemRegister.ItemCoin,0,6-i);
						itemStack.setItemDamage(6-i);
						//ドロップ数計算
						int stackSize = 0;
						while(money>=ItemCoin.metaToCoin(6-i)){
							System.out.println(money);
							money -= ItemCoin.metaToCoin(6-i);
							stackSize++;
						}
						itemStack.stackSize = stackSize;
						//ドロップ処理
						if (itemStack != null) {
							//ドロップアイテムを散らばす為の変数
							float f = random.nextFloat() * 0.6F + 0.1F;
							float f1 = random.nextFloat() * 0.6F + 0.1F;
							float f2 = random.nextFloat() * 0.6F + 0.1F;
							//アイテムの一つ当たりのドロップ数
							while (itemStack.stackSize > 0) {
								int j = random.nextInt(21) + 10;

								if (j > itemStack.stackSize) {
									j = itemStack.stackSize;
								}

								itemStack.stackSize -= j;

								//ドロップさせるための各種変数
								World world = player.worldObj;
								double x = player.lastTickPosX;
								double y = player.lastTickPosY;
								double z = player.lastTickPosZ;

								//ドロップ処理
								EntityItem entityItem = new EntityItem(world, x + f, y + f1, z + f2,
										new ItemStack(itemStack.getItem(), j, itemStack.getItemDamage()));

								float f3 = 0.025F;
								entityItem.motionX = (float) random.nextGaussian() * f3;
								entityItem.motionY = (float) random.nextGaussian() * f3 + 0.1F;
								entityItem.motionZ = (float) random.nextGaussian() * f3;
								player.worldObj.spawnEntityInWorld(entityItem);
							}

						}
					}
				}else{
					player.addChatMessage(new ChatComponentText("Value is bigger than possession money"));
				}
			}else{
				player.addChatMessage(new ChatComponentText("Num value is too large"));
			}
		}else {
			player.addChatMessage(new ChatComponentText("Invalid argument"));
		}

	}
}