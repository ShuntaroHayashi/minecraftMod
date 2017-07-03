package forestMoon;

//import sampleMod.packet.MessagePlayerProperties;
import forestMoon.packet.MessagePlayerProperties;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;;
public class ExtendedPlayerProperties implements IExtendedEntityProperties {
   /* MOD固有の文字列。EntityPlayerに登録時に使用。
   MOD内で複数のIExtendedEntityPropertiesを使う場合は、別の文字列をそれぞれ割り当てること。*/
    public final static String EXT_PROP_NAME = "playerMoneyData";

    private int money = 0;
    private int displayMoney = 0;
    private int countMoney = 0;

    /*EntityPlayerにIExtendedEntityPropertiesを登録。登録文字列はMOD固有のものを割り当てること*/
    public static void register(EntityPlayer player) {
        player.registerExtendedProperties(EXT_PROP_NAME, new ExtendedPlayerProperties());
    }

    /*IExtendedEntityPropertiesをEntityPlayerインスタンスから取得する*/
    public static ExtendedPlayerProperties get(EntityPlayer player) {
        return (ExtendedPlayerProperties)player.getExtendedProperties(EXT_PROP_NAME);
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("money", getMoney());


        //ItemStackの保存
//        NBTTagCompound itemNBT = new NBTTagCompound();
//        nbt.setTag("sampleItemStack", itemNBT);
        //ItemStackの配列の保存

        compound.setTag(EXT_PROP_NAME, nbt);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        NBTTagCompound nbt = (NBTTagCompound)compound.getTag(EXT_PROP_NAME);
        this.money = nbt.getInteger("money");
        //ItemStackの読み込み
        //ItemStackの配列の読み込み
    }

    @Override
    /*初期化メソッド。今のところ使う必要はない。*/
    public void init(Entity entity, World world) {}

    /*以降、各変数のGetterおよびSetter。
    * 使い方としては、EntityPlayerのインスタンスが取得できるメソッド内で、
    * ExtendedPlayerProperties.get(playerインスタンス).setSampleInt(sample)
    * と呼び出す。*/

    public int getDisplayMoney() {
        return displayMoney;
    }

    public void setDisplayMoney(int money) {
        this.displayMoney = money;
    }
	public int getCountMoney() {
		return countMoney;
	}
	public void setCountMoney(int countMoney) {
		this.countMoney = countMoney;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public void setMoney(int money,EntityPlayer player) {
		this.money = money;
	       PacketHandler.INSTANCE.sendTo(new MessagePlayerProperties(player), (EntityPlayerMP)player);
	}
	public void syncPlayerData(EntityPlayer player){
		PacketHandler.INSTANCE.sendTo(new MessagePlayerProperties(player), (EntityPlayerMP)player);
	}

}