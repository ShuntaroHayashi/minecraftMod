package forestMoon.client;

import cpw.mods.fml.common.network.IGuiHandler;
import forestMoon.ForestMoon;
import forestMoon.client.entity.TileEntityChest;
import forestMoon.client.gui.GuiChest;
import forestMoon.client.guicontainer.BuyGuiContainer;
import forestMoon.client.guicontainer.MainGuiConteiner;
import forestMoon.client.guicontainer.SellGuiContainer;
import forestMoon.container.BuyContainer;
import forestMoon.container.ContainerChest;
import forestMoon.container.MainContainer;
import forestMoon.container.SellContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ForestMoonGuiHandler implements IGuiHandler{
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(id == ForestMoon.MAIN_GUI_ID){
			return new MainContainer(x, y, z, player);
		}else if (id == ForestMoon.BUY_GUI_ID) {
			return new BuyContainer(player);
		}else if (id == ForestMoon.SELL_GUI_ID) {
			return new SellContainer();
		}
		if (!world.blockExists(x, y, z)){
			return null;
		}
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if (tileentity instanceof TileEntityChest) {
			return new ContainerChest(player, (TileEntityChest) tileentity);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(id == ForestMoon.MAIN_GUI_ID){
			return new MainGuiConteiner(x, y, z, player);
		}else if (id == ForestMoon.BUY_GUI_ID) {
			return new BuyGuiContainer(player);
		}else if (id == ForestMoon.SELL_GUI_ID) {
			return new SellGuiContainer();
		}

		if (!world.blockExists(x, y, z)){
			return null;
		}
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if (tileentity instanceof TileEntityChest) {
			return new GuiChest(player, (TileEntityChest) tileentity);
		}
		return null;
	}
}
