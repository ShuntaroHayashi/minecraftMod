package forestMoon.client.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import forestMoon.client.entity.TileEntityChest;
import forestMoon.container.ContainerChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ForestMoonGuiHandler implements IGuiHandler{
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
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
