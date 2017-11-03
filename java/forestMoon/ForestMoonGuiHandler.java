package forestMoon;

import cpw.mods.fml.common.network.IGuiHandler;
import forestMoon.client.entity.TileEntityChest;
import forestMoon.client.gui.GuiChest;
import forestMoon.client.guicontainer.ShopingGuiContainer;
import forestMoon.container.ContainerChest;
import forestMoon.container.ShopingContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ForestMoonGuiHandler implements IGuiHandler{
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(id == ForestMoon.SHOPING_GUI_ID){
			return new ShopingContainer(player.inventory,true,player);
		}else if(id == ForestMoon.CHEST_GUI_ID){
			if (!world.blockExists(x, y, z)){
				return null;
			}
			TileEntity tileentity = world.getTileEntity(x, y, z);
			if (tileentity instanceof TileEntityChest) {
				return new ContainerChest(player, (TileEntityChest) tileentity);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(id == ForestMoon.SHOPING_GUI_ID){
			return new ShopingGuiContainer(player);
		}else if (id == ForestMoon.CHEST_GUI_ID) {
			if (!world.blockExists(x, y, z)){
				return null;
			}
			TileEntity tileentity = world.getTileEntity(x, y, z);
			if (tileentity instanceof TileEntityChest) {
				return new GuiChest(player, (TileEntityChest) tileentity);
			}
		}

		return null;
	}
}
