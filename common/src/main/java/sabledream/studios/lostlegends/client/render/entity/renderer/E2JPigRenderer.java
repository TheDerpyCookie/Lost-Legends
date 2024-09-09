package sabledream.studios.lostlegends.client.render.entity.renderer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.SaddleFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.util.Identifier;
import sabledream.studios.lostlegends.entity.E2JBasePigEntity;

public class E2JPigRenderer extends MobEntityRenderer<E2JBasePigEntity, PigEntityModel<E2JBasePigEntity>>
{

	private final String registryName;

	public E2JPigRenderer(EntityRendererFactory.Context context, String registryName) {
		super(context, new PigEntityModel<>(context.getPart(EntityModelLayers.PIG)), 0.7F);
		addFeature(new SaddleFeatureRenderer<>(this, new PigEntityModel<>(context.getPart(EntityModelLayers.PIG_SADDLE)), Identifier.ofVanilla("textures/entity/pig/pig_saddle.png")));
		this.registryName = registryName;
	}

	@Override
	public Identifier getTexture(E2JBasePigEntity entity) {
		Identifier texture = TextureUtils.getTextureIdentifier("pig", registryName);
		Identifier textureBlink = TextureUtils.getTextureIdentifier("pig", registryName, "blink");
		return entity.blinkManager.getBlinkRemainingTicks() > 0 ? textureBlink : texture;
	}

}
