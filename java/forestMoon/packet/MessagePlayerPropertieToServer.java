package forestMoon.packet;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import forestMoon.ExtendedPlayerProperties;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class MessagePlayerPropertieToServer implements IMessage {

	// server,client上では変数に直接アクセス。各端末間での通信ではByteBufに書き込んだ情報のみを送受信できる。

	public NBTTagCompound data;

	public MessagePlayerPropertieToServer() {

	}

	// インベントリをdataに保存
	public MessagePlayerPropertieToServer(EntityPlayer player) {
		data = new NBTTagCompound();
		ExtendedPlayerProperties.get(player).saveNBTData(data);
	}

	@Override // IMessageのメソッド。ByteBufからデータを読み取る。 受信処理
	public void fromBytes(ByteBuf buf) {
		// ByteBufからデータを読み込む
		data = ByteBufUtils.readTag(buf);
	}

	@Override // IMessageのメソッド。ByteBufにデータを書き込む。 送信処理
	public void toBytes(ByteBuf buf) {
		// ByteBufにデータを書き込む
		ByteBufUtils.writeTag(buf, data);
	}

}
