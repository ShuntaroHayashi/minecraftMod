package forestMoon.command;

import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;

public class CommandRegister {
	public static void registry(FMLServerStartingEvent event) {
		ICommandManager commandManager = event.getServer().getCommandManager();
		ServerCommandManager serverCommandManager = ((ServerCommandManager)commandManager);
		serverCommandManager.registerCommand(new CommandMoney());
		serverCommandManager.registerCommand(new CommandCoin());
	}
}
