package forestMoon.Items;

import forestMoon.ForestMoon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemInventory extends Item
{
    public ItemInventory()
    {
        super();
        //スタックサイズは1コ

        this.setCreativeTab(ForestMoon.forestmoontab);

        this.setMaxStackSize(1);
    }

    /*
        Itemが右クリックされた時のメソッド
     */
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
        /*
            GUIを開く。インスタンス, GUIのID, World, X, Y, Z
         */
        player.openGui(ForestMoon.instance, 10, world, (int)player.posX, (int)player.posY, (int)player.posZ);
        return itemStack;
    }
}