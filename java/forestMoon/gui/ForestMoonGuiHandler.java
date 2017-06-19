package forestMoon.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import forestMoon.ContainerChestSample;
import forestMoon.GuiChestSample;
import forestMoon.entity.TileEntityChestSample;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ForestMoonGuiHandler implements IGuiHandler{
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (!world.blockExists(x, y, z))
			return null;
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if (tileentity instanceof TileEntityChestSample) {
			return new ContainerChestSample(player, (TileEntityChestSample) tileentity);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (!world.blockExists(x, y, z))
			return null;
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if (tileentity instanceof TileEntityChestSample) {
			return new GuiChestSample(player, (TileEntityChestSample) tileentity);
		}
		return null;
	}
}
