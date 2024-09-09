package sabledream.studios.lostlegends.client.render.entity.renderer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SnowGolemEntityModel;
import net.minecraft.util.Identifier;
import sabledream.studios.lostlegends.client.render.entity.feature.MelonGolemCarvedMelonFeatureRenderer;
import sabledream.studios.lostlegends.entity.MelonGolemEntity;
public class MelonGolemRenderer extends MobEntityRenderer<MelonGolemEntity, SnowGolemEntityModel<MelonGolemEntity>>
{
	private static final Identifier SNOW_MAN_TEXTURES =   Identifier.ofVanilla("textures/entity/snow_golem.png");
	public MelonGolemRenderer(EntityRendererFactory.Context context) {
		super(context, new SnowGolemEntityModel<>(context.getPart(EntityModelLayers.SNOW_GOLEM)), 0.5F);
		addFeature(new MelonGolemCarvedMelonFeatureRenderer(this, context.getBlockRenderManager(), context.getItemRenderer()));
	}

	public Identifier getTexture(MelonGolemEntity entity) {
		return SNOW_MAN_TEXTURES;
	}

}