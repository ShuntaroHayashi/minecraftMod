package forestMoon.client.entity;

import forestMoon.ForestMoon;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityECVillager extends EntityVillager  {

	boolean flag = false;
	ItemStack[] items = {new ItemStack(Items.apple),new ItemStack(Items.arrow)};
	public Minecraft mc = Minecraft.getMinecraft();
    private EntityPlayer buyingPlayer;


	public EntityECVillager(World world){
		super(world);
		/*EntiyのAIを登録する*/
		this.tasks.taskEntries.clear();//superの登録を削除
//			this.tasks.addTask(1, new EntityAISwimming(this));
//			this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPig.class, 1.0D, false));
//	        this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
//	        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
//	        this.tasks.addTask(4, new EntityAIWander(this, 0.8D));
//	        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
//	        this.tasks.addTask(6, new EntityAILookIdle(this));
//	        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPig.class, 1,false));
//	        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 1, true));
//	        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));


	}

	/**MOBの速度やHPを変更するメソッド*/
	@Override
	protected void applyEntityAttributes(){
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(128D);
//		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(100D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
	}

	/**MOBの属性を返すメソッド*/
	@Override
    public EnumCreatureAttribute getCreatureAttribute() {
	   return EnumCreatureAttribute.UNDEFINED;
	  }

	/**MOBのドロップアイテムを返すメソッド*/
    @Override
    public Item getDropItem() {
    	return Item.getItemFromBlock(Blocks.wool) ;
    }

    /**MOBのドロップアイテムをドロップさせるメソッド*/
    @Override
    protected void dropFewItems(boolean isCanDropRare, int fortuneLv){
    }

    /**Tickごとに呼ばれるメソッド*/
    @Override
    public void onUpdate(){
    	int x = (int) this.posX;
    	int y = (int) this.posY;
    	int z = (int) this.posZ;
    	if(this.worldObj.getBlock(x, y - 1, z).getMaterial() == Material.iron){
    		this.worldObj.createExplosion(this, x, y, z, 3F, true);
    	}

    	super.onUpdate();
    }

    /**MOB死亡時に呼ばれるメソッド*/
    public void onDeath(DamageSource source){
    	super.onDeath(source);
    	if(source.getSourceOfDamage() != null && source.getSourceOfDamage() instanceof EntityPlayer){
    		EntityPlayer player = (EntityPlayer) source.getSourceOfDamage();
    		 if(!this.worldObj.isRemote){
				   player.addChatMessage(new ChatComponentText("You slayed."));
				   player.triggerAchievement(AchievementList.mineWood);
    		 }
    	}
    }

    /**ダメージを食らうか否かを判定するメソッド*/
    @Override
    public boolean attackEntityFrom(DamageSource source, float damage){
    	if(source.isExplosion()){
    		return false;
    	}
    	else if(source.isFireDamage()){
//    		return super.attackEntityFrom(source, damage * 2);
    		return false;
    	}
    	else{
    		return super.attackEntityFrom(source, damage);
    	}
    }
    public void setCustomer(EntityPlayer p_70932_1_){
        this.buyingPlayer = p_70932_1_;
    }

    public EntityPlayer getCustomer(){
        return this.buyingPlayer;
    }
    public boolean isTrading(){
        return this.buyingPlayer != null;
    }

    //右クリック時
    @Override
    public boolean interact(EntityPlayer player){
    	int x = MathHelper.ceiling_double_int(player.posX);
    	int y = MathHelper.ceiling_double_int(player.posY);
    	int z = MathHelper.ceiling_double_int(player.posZ);
    	//ショップGUIのオープン
		player.openGui(ForestMoon.instance, ForestMoon.SHOPING_GUI_ID , player.worldObj, x, y, z);
		return true;

    }
}
