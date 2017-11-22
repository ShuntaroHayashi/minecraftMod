package forestMoon.client.entity;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestMoon.ForestMoon;
import forestMoon.shoping.ShopingItem;
import forestMoon.shoping.VillagerShopingItem;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityECVillager extends EntityVillager {

	// boolean flag = false;
	int profession;

	ShopingItem[] shopingItems;
	public Minecraft mc = Minecraft.getMinecraft();

	public EntityECVillager(World world) {
		super(world);

		// System.out.println("constraktor:IN");
		// firstSetting();
		this.firstSetting();

		/* EntiyのAIを登録する */
		this.tasks.taskEntries.clear();// superの登録を削除
		// this.tasks.addTask(1, new EntityAISwimming(this));
		// this.tasks.addTask(2, new EntityAIAttackOnCollide(this,
		// EntityPig.class, 1.0D, false));
		// this.tasks.addTask(2, new EntityAIAvoidEntity(this,
		// EntityOcelot.class, 6.0F, 1.0D, 1.2D));
		// this.tasks.addTask(3, new EntityAIAttackOnCollide(this,
		// EntityPlayer.class, 1.0D, false));
		// this.tasks.addTask(4, new EntityAIWander(this, 0.8D));
		// this.tasks.addTask(5, new EntityAIWatchClosest(this,
		// EntityPlayer.class, 8.0F));
		// this.tasks.addTask(6, new EntityAILookIdle(this));
		// this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this,
		// EntityPig.class, 1,false));
		// this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this,
		// EntityPlayer.class, 1, true));
		// this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
	}

	/** MOBの速度やHPを変更するメソッド */
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(128D);
		// this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(100D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
	}

	/** MOBの属性を返すメソッド */
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEFINED;
	}

	/** MOBのドロップアイテムを返すメソッド */
	@Override
	public Item getDropItem() {
		return Item.getItemFromBlock(Blocks.wool);
	}

	/** MOBのドロップアイテムをドロップさせるメソッド */
	@Override
	protected void dropFewItems(boolean isCanDropRare, int fortuneLv) {
	}

	/** Tickごとに呼ばれるメソッド */
	@Override
	public void onUpdate() {
		int x = (int) this.posX;
		int y = (int) this.posY;
		int z = (int) this.posZ;
		if (this.worldObj.getBlock(x, y - 1, z).getMaterial() == Material.iron) {
			this.worldObj.createExplosion(this, x, y, z, 3F, true);
		}
		super.onUpdate();
	}

	/** MOB死亡時に呼ばれるメソッド */
	public void onDeath(DamageSource source) {
		super.onDeath(source);
		if (source.getSourceOfDamage() != null && source.getSourceOfDamage() instanceof EntityPlayer) {
			// EntityPlayer player = (EntityPlayer) source.getSourceOfDamage();
			if (!this.worldObj.isRemote) {
				// player.addChatMessage(new ChatComponentText("You slayed."));
				// player.triggerAchievement(AchievementList.mineWood);
			}
		}
	}

	/** ダメージを食らうか否かを判定するメソッド */
	@Override
	public boolean attackEntityFrom(DamageSource source, float damage) {
		if (source.isExplosion()) {
			return false;
		} else if (source.isFireDamage()) {
			// return super.attackEntityFrom(source, damage * 2);
			return false;
		} else {
			return super.attackEntityFrom(source, damage);
		}
	}

	// NBTDataの書き込み
	@SideOnly(Side.CLIENT)
	@Override
	public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		System.out.println("writeEntityFromNBT:IN");
		super.writeEntityToNBT(p_70014_1_);
		NBTTagList itemList = new NBTTagList();
		int[] buy = new int[shopingItems.length];
		int[] sell = new int[shopingItems.length];

		for (int i = 0; i < shopingItems.length; i++) {
			if (shopingItems[i] != null) {
				NBTTagCompound compound = new NBTTagCompound();
				compound.setByte("Slot", (byte) i);
				shopingItems[i].getItemStack().writeToNBT(compound);
				buy[i] = shopingItems[i].getBuy();
				sell[i] = shopingItems[i].getSell();
				itemList.appendTag(compound);
			}
		}
		p_70014_1_.setInteger("profession", profession);
		p_70014_1_.setTag("item", itemList);
		p_70014_1_.setIntArray("buy", buy);
		p_70014_1_.setIntArray("sell", sell);
	}

	// NBTDataの読み込み
	@Override
	public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		System.out.println("readEntityFromNBT:IN");
		super.readEntityFromNBT(p_70037_1_);
		// NBTに情報が書き込んであるのかの確認（スポーン時か確認）
		if (p_70037_1_.hasKey("item")) {
			NBTTagList tagList = (NBTTagList) p_70037_1_.getTag("item");
			this.shopingItems = new ShopingItem[tagList.tagCount()];
			int[] buy = p_70037_1_.getIntArray("buy");
			int[] sell = p_70037_1_.getIntArray("sell");
			for (int i = 0; i < tagList.tagCount(); i++) {
				NBTTagCompound compound = tagList.getCompoundTagAt(i);
				ItemStack itemStack = ItemStack.loadItemStackFromNBT(compound);
				shopingItems[i] = new ShopingItem(itemStack, buy[i], sell[i]);
			}

			this.profession = p_70037_1_.getInteger("profession");
			System.out.println(this);
			for (ShopingItem item : shopingItems) {
				System.out.println(item);
			}

		} else {
			// 初期設定
			this.firstSetting();
		}
	}

	// 初期設定（スポーン時）
	private void firstSetting() {
		Random rnd = new Random();
		VillagerShopingItem villagerShopingItem = new VillagerShopingItem();
		this.profession = rnd.nextInt(villagerShopingItem.getProfessionSize());
		ArrayList<ShopingItem> shopItems = villagerShopingItem.getProfessionItems(profession);
		this.shopingItems = new ShopingItem[shopItems.size()];

		for (int i = 0; i < shopItems.size(); i++) {
			this.shopingItems[i] = shopItems.get(i);
		}
	}

	// 右クリック時
	@Override
	public boolean interact(EntityPlayer player) {
		int x = MathHelper.ceiling_double_int(player.posX);
		int y = MathHelper.ceiling_double_int(player.posY);
		int z = this.getEntityId();

		if (!this.worldObj.isRemote) {
			// ショップGUIのオープン
			player.openGui(ForestMoon.instance, ForestMoon.SHOPING_GUI_ID, player.worldObj, x, y, z);
		}
		return true;

	}

	@Override
	public String toString() {
		return (this.getEntityId() + " " + this.getUniqueID() + " " + this.getCommandSenderName());
	}

	public ShopingItem[] getShopingItems() {
		return this.shopingItems;
	}

	public void setShopingItems(ShopingItem[] shopingItems) {
		this.shopingItems = shopingItems;
	}

}
