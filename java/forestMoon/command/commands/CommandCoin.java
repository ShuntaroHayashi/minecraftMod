package forestMoon.command.commands;

import java.util.Random;

import forestMoon.ExtendedPlayerProperties;
import forestMoon.item.ItemRegister;
import forestMoon.item.items.ItemCoin;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CommandCoin extends CommandBase {

	@Override
	public String getCommandName() {
		return "coin";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "command.coin.help";
	}

	// OP権限のないユーザーにも使用可能にする
	public int getRequiredPermissionLevel() {
		return 0;
	}

	@Override
	public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_) {
		Random random = new Random();
		EntityPlayerMP player = getCommandSenderAsPlayer(p_71515_1_);
		String scanMoney = "";
		// 引数の確認
		if (0 < p_71515_2_.length && p_71515_2_.length <= 2) {
			if (p_71515_2_.length == 1) {
				try {
					scanMoney = p_71515_2_[0];
				}catch (Exception e) {
					e.printStackTrace();
					func_152373_a(p_71515_1_, this, "command.error", new Object[] {});
					return;
				}
			} else if (p_71515_2_.length == 2) {
				try {
					player = getPlayer(p_71515_1_, p_71515_2_[0]);
					scanMoney = p_71515_2_[1];
				}catch (Exception e) {
					e.printStackTrace();
					func_152373_a(p_71515_1_, this, "command.coin.error", new Object[] {});
					return;
				}
			}
			if (scanMoney.length() <= 10) {
				long money = 0;
				ExtendedPlayerProperties properties = ExtendedPlayerProperties.get(player);
				try {
					money = Integer.parseInt(scanMoney);
					money = money < 0 ? -1 * money : money;
				}catch (NumberFormatException e) {
					e.printStackTrace();
					func_152373_a(p_71515_1_, this, "command.coin.error", new Object[] {});
					return;
				}
				money -= (money % 100);
				long chatMoney = money;
				if (properties.getMoney() >= money) {
					properties.changeMoney(-1 * money);
					properties.syncPlayerData(player);

					// コインドロップ処理
					for (int i = 0; i < 7; i++) {

						// Itemの作成
						ItemStack itemStack = new ItemStack(ItemRegister.ItemCoin, 0, 6 - i);
						itemStack.setItemDamage(6 - i);
						// ドロップ数計算
						int stackSize = 0;
						while (money >= ItemCoin.metaToCoin(6 - i)) {
							money -= ItemCoin.metaToCoin(6 - i);
							stackSize++;
						}
						itemStack.stackSize = stackSize;
						// ドロップ処理
						if (itemStack != null) {
							// ドロップアイテムを散らばす為の変数
							float f = random.nextFloat() * 0.6F + 0.1F;
							float f1 = random.nextFloat() * 0.6F + 0.1F;
							float f2 = random.nextFloat() * 0.6F + 0.1F;
							// アイテムの一つ当たりのドロップ数
							while (itemStack.stackSize > 0) {
								int j = random.nextInt(21) + 10;

								if (j > itemStack.stackSize) {
									j = itemStack.stackSize;
								}

								itemStack.stackSize -= j;

								// ドロップさせるための各種変数
								World world = player.worldObj;
								double x = player.lastTickPosX;
								double y = player.lastTickPosY;
								double z = player.lastTickPosZ;

								// ドロップ処理
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
					 func_152373_a(p_71515_1_, this, "command.coin", new Object[] { player.getCommandSenderName(),chatMoney});
				} else {
					func_152373_a(p_71515_1_, this, "command.error.1", new Object[] {});
				}
			} else {
				func_152373_a(p_71515_1_, this, "command.error.2", new Object[] {});
			}
		} else {
			func_152373_a(p_71515_1_, this, "command.coin.error", new Object[] {});
		}
	}
}
