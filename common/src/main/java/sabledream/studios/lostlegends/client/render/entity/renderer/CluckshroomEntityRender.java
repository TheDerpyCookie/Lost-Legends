package sabledream.studios.lostlegends.client.render.entity.renderer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import sabledream.studios.lostlegends.client.render.entity.feature.CluckshroomMushroomFeatureRenderer;
import sabledream.studios.lostlegends.client.render.entity.model.CluckshroomModel;
import sabledream.studios.lostlegends.entity.CluckshroomEntity;

public class CluckshroomEntityRender extends MobEntityRenderer<CluckshroomEntity, CluckshroomModel<CluckshroomEntity>>
{
	public CluckshroomEntityRender(EntityRendererFactory.Context context) {
		super(context, new CluckshroomModel<>(context.getPart(EntityModelLayers.CHICKEN)), 0.3F);
		addFeature(new CluckshroomMushroomFeatureRenderer<>(this, context.getBlockRenderManager()));
	}

	@Override
	protected float getAnimationProgress(CluckshroomEntity chickenEntity, float f) {
		float g = MathHelper.lerp(f, chickenEntity.prevFlapProgress, chickenEntity.flapProgress);
		float h = MathHelper.lerp(f, chickenEntity.prevMaxWingDeviation, chickenEntity.flapSpeed);
		return (MathHelper.sin(g) + 1.0F) * h;
	}

	@Override
	public Identifier getTexture(CluckshroomEntity entity){
		Identifier texture = TextureUtils.getTextureIdentifier("chicken", "cluckshroom");
		Identifier textureBlink = TextureUtils.getTextureIdentifier("chicken", "cluckshroom", "blink");
		return entity.blinkManager.getBlinkRemainingTicks() > 0 ? textureBlink : texture;
	}

}
