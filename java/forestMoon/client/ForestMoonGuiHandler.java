package forestMoon.client;

import cpw.mods.fml.common.network.IGuiHandler;
import forestMoon.ForestMoon;
import forestMoon.client.entity.TileEntityChest;
import forestMoon.client.guicontainer.PlayerShopingGuiContainer;
import forestMoon.client.guicontainer.ShopAdminGuiContainer;
import forestMoon.client.guicontainer.ShopingGuiContainer;
import forestMoon.container.PlayerShopingContainer;
import forestMoon.container.ShopAdminContainer;
import forestMoon.container.ShopingContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ForestMoonGuiHandler implements IGuiHandler{
	@Override
	public Object getServerGuiElement(final int id,final EntityPlayer player,final World world,final int x,final int y,final int z) {
		if(id == ForestMoon.SHOPING_GUI_ID){
			return new ShopingContainer(player.inventory,true,player);
		}else if (id == ForestMoon.CHEST_GUI_ID) {
			if (!world.blockExists(x, y, z)){
				return null;
			}
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity instanceof TileEntityChest) {
				if (((TileEntityChest) tileEntity).getAdminName().equals(player.getCommandSenderName()) || ((TileEntityChest)tileEntity).getAdminName().equals("NONE")) {
					return new ShopAdminContainer(player, (TileEntityChest) tileEntity);
				}else {
					if(!(((TileEntityChest)tileEntity).isAdminFlag())) {
						return new PlayerShopingContainer((TileEntityChest) tileEntity, player);
					}
				}

			}
		}

		return null;
	}


	@Override
	public Object getClientGuiElement(final int id,final EntityPlayer player,final World world,final int x,final int y,final int z) {
		if(id == ForestMoon.SHOPING_GUI_ID){
			return new ShopingGuiContainer(player,z);
		}else if (id == ForestMoon.CHEST_GUI_ID) {
			if (!world.blockExists(x, y, z)){
				return null;
			}
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity instanceof TileEntityChest) {
				if (((TileEntityChest) tileEntity).getAdminName().equals(player.getCommandSenderName())|| ((TileEntityChest)tileEntity).getAdminName().equals("NONE")) {
					return new ShopAdminGuiContainer(player, (TileEntityChest) tileEntity);
				}else  {
					if(!(((TileEntityChest)tileEntity).isAdminFlag())) {
						return new PlayerShopingGuiContainer(player, (TileEntityChest)tileEntity);
					}
				}

			}
		}
		return null;
	}
}
