package sabledream.studios.lostlegends.client.render.entity.renderer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import sabledream.studios.lostlegends.client.render.entity.model.CluckshroomModel;
import sabledream.studios.lostlegends.client.render.entity.model.FancyChickenModel;
import sabledream.studios.lostlegends.entity.CluckshroomEntity;
import sabledream.studios.lostlegends.entity.FancyChickenEntity;
import sabledream.studios.lostlegends.init.LostLegendsEntityModelLayer;

public class FancyChickenEntityRender extends MobEntityRenderer<FancyChickenEntity, FancyChickenModel<FancyChickenEntity>>
{
	public FancyChickenEntityRender(EntityRendererFactory.Context context){
		super(context, new FancyChickenModel<>(context.getPart(LostLegendsEntityModelLayer.FANCY_CHICKEN)), 0.3F);
	}

	@Override
	protected float getAnimationProgress(FancyChickenEntity chickenEntity, float f) {
		float g = MathHelper.lerp(f, chickenEntity.prevFlapProgress, chickenEntity.flapProgress);
		float h = MathHelper.lerp(f, chickenEntity.prevMaxWingDeviation, chickenEntity.flapSpeed);
		return (MathHelper.sin(g) + 1.0F) * h;
	}

	@Override
	public Identifier getTexture(FancyChickenEntity entity){
		Identifier texture = TextureUtils.getTextureIdentifier("chicken", "fancychicken");
		Identifier textureBlink = TextureUtils.getTextureIdentifier("chicken", "fancychicken", "blink");
		return entity.blinkManager.getBlinkRemainingTicks() > 0 ? textureBlink : texture;
	}

}
