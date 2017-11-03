package forestMoon.client.render;

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
		return new ResourceLocation("forestmoon:textures/entity/villager.png");
	}

}
