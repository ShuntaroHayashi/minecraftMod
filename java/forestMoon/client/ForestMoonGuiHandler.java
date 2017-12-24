package forestMoon.client;

import cpw.mods.fml.common.network.IGuiHandler;
import forestMoon.ForestMoon.GuiId;
import forestMoon.client.guicontainer.PlayerShopAdminGuiContainer;
import forestMoon.client.guicontainer.PlayerShopGuiContainer;
import forestMoon.client.guicontainer.VillagerShopGuiContainer;
import forestMoon.container.PlayerShopAdminContainer;
import forestMoon.container.PlayerShopContainer;
import forestMoon.container.VillagerShopContainer;
import forestMoon.tileEntity.TileEntityShop;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ForestMoonGuiHandler implements IGuiHandler {
	@Override
	public Object getServerGuiElement(final int id, final EntityPlayer player, final World world, final int x,
			final int y, final int z) {
		if (id == GuiId.VILLAGERSHOP.getId()) {
			return new VillagerShopContainer(player.inventory, true, player);
		} else if (id == GuiId.PLAYERSHOP.getId()) {
			if (!world.blockExists(x, y, z)) {
				return null;
			}
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity instanceof TileEntityShop) {
				if (((TileEntityShop) tileEntity).getAdminName().equals(player.getCommandSenderName())
						|| ((TileEntityShop) tileEntity).getAdminName().equals("NONE")) {
					return new PlayerShopAdminContainer(player, (TileEntityShop) tileEntity);
				} else {
					return new PlayerShopContainer((TileEntityShop) tileEntity, player);
				}

			}
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(final int id, final EntityPlayer player, final World world, final int x,
			final int y, final int z) {
		if (id == GuiId.VILLAGERSHOP.getId()) {
			return new VillagerShopGuiContainer(player, z);
		} else if (id == GuiId.PLAYERSHOP.getId()) {
			if (!world.blockExists(x, y, z)) {
				return null;
			}
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity instanceof TileEntityShop) {
				if (((TileEntityShop) tileEntity).getAdminName().equals(player.getCommandSenderName())
						|| ((TileEntityShop) tileEntity).getAdminName().equals("NONE")) {
					return new PlayerShopAdminGuiContainer(player, (TileEntityShop) tileEntity);
				} else {
					return new PlayerShopGuiContainer(player, (TileEntityShop) tileEntity);
				}

			}
		}
		return null;
	}
}
