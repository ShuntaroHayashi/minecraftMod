package forestMoon.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forestMoon.item.ItemRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class LivingDeathEventHandler {
	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event) {
		if(event.entityLiving.worldObj.isRemote) {
			return;
		}
		EntityLivingBase living = event.entityLiving;
		//ゾンビ/スケルトン/蜘蛛/クリーパー/ウィッチだった場合
		if(living instanceof EntityZombie || living instanceof EntitySkeleton ||living instanceof EntitySpider ||
				living instanceof EntityCreeper || living instanceof EntityWitch) {
			if(event.entityLiving.worldObj.rand.nextInt(10) == 0) {
				ItemStack itemStack = new ItemStack(ItemRegister.ItemCoin, 1, 0);
				event.entityLiving.entityDropItem(itemStack, 1);
			}
		//ゾンビピッグマン/エンダーマンだった場合
		}else if(living instanceof EntityEnderman || living instanceof EntityPigZombie || living instanceof EntityBlaze){
			if(event.entityLiving.worldObj.rand.nextInt(10) == 0) {
				ItemStack itemStack = new ItemStack(ItemRegister.ItemCoin, 2, 0);
				event.entityLiving.entityDropItem(itemStack, 1);
			}
			//エンダードラゴンだった場合
		}else if (living instanceof EntityDragon) {
			ItemStack itemStack = new ItemStack(ItemRegister.ItemCoin, 1, 2);
			event.entityLiving.entityDropItem(itemStack, 1);
		}
	}
}
