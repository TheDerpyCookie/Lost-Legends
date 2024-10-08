package sabledream.studios.lostlegends.client.render.entity.renderer;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import sabledream.studios.lostlegends.client.render.entity.model.FireflyEntityModel;
import sabledream.studios.lostlegends.entity.FireflyEntity;
import sabledream.studios.lostlegends.init.LostLegendsEntityModelLayer;

import static sabledream.studios.lostlegends.LostLegends.MOD_ID;

@Environment(EnvType.CLIENT)
public class FireflyEntityRenderer extends MobEntityRenderer<FireflyEntity, FireflyEntityModel> {
	private final Identifier DEFAULT_TEXTURE =  Identifier.of(MOD_ID, "textures/entity/firefly/firefly.png");
	private final Identifier GLOWING_TEXTURE_1 =  Identifier.of(MOD_ID, "textures/entity/firefly/firefly_glowing_1.png");
	private final Identifier GLOWING_TEXTURE_2 =  Identifier.of(MOD_ID, "textures/entity/firefly/firefly_glowing_2.png");
	private final Identifier GLOWING_TEXTURE_3 =  Identifier.of(MOD_ID, "textures/entity/firefly/firefly_glowing_3.png");

	public FireflyEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new FireflyEntityModel(context.getPart(LostLegendsEntityModelLayer.FIREFLY)), 0.2f);
	}

	@Override
	public Identifier getTexture(FireflyEntity entity) {
		return switch (entity.getLuminance()) {
			default -> this.DEFAULT_TEXTURE;
			case 1 -> this.GLOWING_TEXTURE_1;
			case 2 -> this.GLOWING_TEXTURE_2;
			case 3 -> this.GLOWING_TEXTURE_3;
		};
	}
}