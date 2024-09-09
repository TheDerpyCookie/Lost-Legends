package sabledream.studios.lostlegends.client.render.entity.renderer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.SaddleFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;
import sabledream.studios.lostlegends.client.render.entity.model.MuddyPigModel;
import sabledream.studios.lostlegends.entity.MuddyPigEntity;
import sabledream.studios.lostlegends.init.LostLegendsEntityModelLayer;

public class MuddyPigRenderer extends MobEntityRenderer<MuddyPigEntity, MuddyPigModel<MuddyPigEntity>>
{

	public MuddyPigRenderer(EntityRendererFactory.Context context) {
		super(context, new MuddyPigModel<>(context.getPart(LostLegendsEntityModelLayer.MUDDY_PIG_ENTITY_MODEL_LAYER)), 0.7F);
		addFeature(new SaddleFeatureRenderer<>(this, new MuddyPigModel<>(context.getPart(EntityModelLayers.PIG_SADDLE)), Identifier.ofVanilla("textures/entity/pig/pig_saddle.png")));
	}

	public Identifier getTexture(MuddyPigEntity entity) {
		Identifier texture = TextureUtils.getTextureIdentifier("pig", "muddy_pig");
		Identifier textureBlink = TextureUtils.getTextureIdentifier("pig", "muddy_pig", "blink");
		Identifier textureDried = TextureUtils.getTextureIdentifier("pig", "muddy_pig", "dried");
		Identifier textureDriedBlink = TextureUtils.getTextureIdentifier("pig", "muddy_pig", "dried_blink");
		boolean blink = entity.blinkManager.getBlinkRemainingTicks() > 0;
		if (entity.isInMuddyState()) {
			return blink ? textureBlink : texture;
		}

		return blink ? textureDriedBlink : textureDried;
	}
}