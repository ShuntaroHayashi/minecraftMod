package forestMoon.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class MessageInventoryHandler implements IMessageHandler<MessageInventory, IMessage>	 {
	 @Override//IMessageHandlerのメソッド
	    public IMessage onMessage(MessageInventory message, MessageContext ctx) {
	        //クライアントへ送った際に、EntityPlayerインスタンスはこのように取れる。
	        //EntityPlayer player = SamplePacketMod.proxy.getEntityPlayerInstance();
	        //サーバーへ送った際に、EntityPlayerインスタンス（EntityPlayerMPインスタンス）はこのように取れる。
	        //EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;
	        //Do something.

		 	//サーバーでのEntityPlayer取得
			 EntityPlayer entityPlayer = ctx.getServerHandler().playerEntity;

			 //インベントリ情報取得
			 ItemStack[] inventoryItem = new ItemStack[36];
			 NBTTagList nbtTagList = (NBTTagList) message.data.getTag("inventory");
			 for (int i = 0; i < nbtTagList.tagCount(); i++) {
				 NBTTagCompound tagCompound = nbtTagList.getCompoundTagAt(i);
				 int slot = tagCompound.getByte("Slot");
				 if(slot >= 0 && slot < inventoryItem.length)
				 {
					 inventoryItem[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
				 }
			 }
			 //インベントリ情報書き込み
			 entityPlayer.inventory.mainInventory = inventoryItem;

			 return null;//本来は返答用IMessageインスタンスを返すのだが、旧来のパケットの使い方をするなら必要ない。
	    }
}
