package forestMoon.client.render;

import forestMoon.client.entity.EntityECVillager;
import net.minecraft.client.renderer.entity.RenderVillager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderECVillager extends RenderVillager{
	public RenderECVillager() {
		super();
	}

	/**テクスチャを登録するメソッド*/
	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		EntityECVillager villager = (EntityECVillager)entity;
		return new ResourceLocation("forestmoon:textures/entity/villager-" + villager.getEconomicsProfession() + ".png");
	}

}
