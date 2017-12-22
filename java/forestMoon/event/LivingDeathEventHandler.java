package forestMoon.event;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import forestMoon.client.entity.EntityECVillager;
import forestMoon.item.ItemRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

public class LivingDeathEventHandler {
	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event) {
		// 別のMOD等でキャンセル済みの場合はなにもしない
		if (event.isCancelable() && event.isCanceled()) {
			return;
		}
		// 別のMOD等で不可とされた場合はなにもしない
		if (event.getResult() == Result.DENY) {
			return;
		}
		// なぜかダメージソースがnullだった場合はなにもしない
		if (event.source.getSourceOfDamage() == null || event.source.getEntity() == null) {
			return;
		}
		if (event.entityLiving.worldObj.isRemote) {
			return;
		}
		if (event.source.getSourceOfDamage() instanceof EntityPlayerMP || event.source.getEntity() instanceof EntityPlayerMP) {

			EntityLivingBase living = event.entityLiving;
			// ゾンビ/スケルトン/蜘蛛/クリーパー/ウィッチ/スライム/ケーブスパイダーだった場合
			if (living instanceof EntityZombie || living instanceof EntitySkeleton || living instanceof EntitySpider
					|| living instanceof EntityCreeper || living instanceof EntityWitch || living instanceof EntitySlime
					|| living instanceof EntityCaveSpider) {
				if (event.entityLiving.worldObj.rand.nextInt(5) == 0) {
					ItemStack itemStack = new ItemStack(ItemRegister.ItemCoin, 1, 0);
					event.entityLiving.entityDropItem(itemStack, 1);
				}
				// ゾンビピッグマン/エンダーマン/ブレイズ/マグマキューブだった場合
			} else if (living instanceof EntityEnderman || living instanceof EntityPigZombie
					|| living instanceof EntityBlaze || living instanceof EntityMagmaCube) {
				if (event.entityLiving.worldObj.rand.nextInt(5) == 0) {
					ItemStack itemStack = new ItemStack(ItemRegister.ItemCoin, 2, 0);
					event.entityLiving.entityDropItem(itemStack, 1);
				}
				// ガスト/シルバーフィッシュだった場合
			} else if (living instanceof EntityGhast || living instanceof EntitySilverfish) {
				ItemStack itemStack = new ItemStack(ItemRegister.ItemCoin, 1, 1);
				event.entityLiving.entityDropItem(itemStack, 1);
				// エンダードラゴン/ウィザーだった場合
			} else if (living instanceof EntityDragon || living instanceof EntityWither) {
				ItemStack itemStack = new ItemStack(ItemRegister.ItemCoin, 1, 3);
				event.entityLiving.entityDropItem(itemStack, 1);
			}
		}
	}

	@SubscribeEvent
	public void onLivingSpawnEvent(LivingSpawnEvent event) {
		EntityLivingBase living = event.entityLiving;
		if (living instanceof EntityECVillager) {
			// System.out.println(living);
		}
	}
}
