package forestMoon.command;

import forestMoon.ExtendedPlayerProperties;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;

public class CommandMoney extends CommandBase{


	@Override
	public String getCommandName() {
		return "money";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) {
		return "CommandMoney";
	}

	@Override
	public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_) {
		EntityPlayerMP player = getCommandSenderAsPlayer(p_71515_1_);;
		boolean setFlag = false;
		String scanMoney="";
		ChatComponentText chatText;
		//引数の確認
		if(0 < p_71515_2_.length  && p_71515_2_.length <= 3){
			if(p_71515_2_.length == 1){
				scanMoney = p_71515_2_[0];
			}else if(p_71515_2_.length == 2){
				if(p_71515_2_[0].equals("set")){
					setFlag = true;
					scanMoney = p_71515_2_[1];
				}else {
					player = getPlayer(p_71515_1_, p_71515_2_[1]);
					scanMoney = p_71515_2_[0];
				}
			}else if (p_71515_2_.length == 3) {
				player = getPlayer(p_71515_1_, p_71515_2_[2]);
				scanMoney = p_71515_2_[1];
				setFlag = true;
			}
			int maxLength = 9;
			if (scanMoney.substring(0, 1).equals("-")) {
				maxLength = 10;
			}
			if(scanMoney.length() <= maxLength){
				ExtendedPlayerProperties properties = ExtendedPlayerProperties.get(player);
				long money = Integer.parseInt(scanMoney);
				if (setFlag) {
					properties.setMoney(money);
				}else {
					properties.changeMoney(money);
				}
				properties.syncPlayerData(player);
				chatText =new ChatComponentText(
						StatCollector.translateToLocal("comandMoney_1")
						+ player.getCommandSenderName()
						+ StatCollector.translateToLocal("comandMoney_2")
						+ properties.getMoney()
						+ StatCollector.translateToLocal("comandMoney_3"));
			}else{
				chatText = new ChatComponentText(StatCollector.translateToLocal("comandError_2"));
			}
		}else {
			chatText = new ChatComponentText(StatCollector.translateToLocal("comandMoney_Error"));
		}
		player.addChatMessage(chatText);
	}
}
