package forestMoon.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class BuyContainer extends Container{

	private EntityPlayer player;

	public BuyContainer(EntityPlayer player) {
		this.player = player;
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
    public void onContainerClosed(EntityPlayer player){
//		ExtendedPlayerProperties properties = ExtendedPlayerProperties.get(player);
    }


}
