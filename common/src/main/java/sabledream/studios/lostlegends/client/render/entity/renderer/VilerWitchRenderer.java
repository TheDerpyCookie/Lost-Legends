package sabledream.studios.lostlegends.client.render.entity.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import sabledream.studios.lostlegends.client.render.entity.feature.VilerWitchHeldItemFeatureRenderer;
import sabledream.studios.lostlegends.client.render.entity.model.VilerWitchModel;
import sabledream.studios.lostlegends.entity.VilerWitchEntity;
import sabledream.studios.lostlegends.init.LostLegendsEntityModelLayer;

public class VilerWitchRenderer extends MobEntityRenderer<VilerWitchEntity, VilerWitchModel<VilerWitchEntity>>
{


	public VilerWitchRenderer(EntityRendererFactory.Context context) {
		super(context, new VilerWitchModel<>(context.getPart(LostLegendsEntityModelLayer.VILER_WITCH_ENTITY_MODEL_LAYER)), 0.5F);
		addFeature(new VilerWitchHeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()));
	}

	@Override
	public void render(VilerWitchEntity witchEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		model.setLiftingNose(!witchEntity.getMainHandStack().isEmpty());
		super.render(witchEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	@Override
	public Identifier getTexture(VilerWitchEntity entity) {
		Identifier texture = TextureUtils.getTextureIdentifier("witch", "viler_witch");
		Identifier textureBlink = TextureUtils.getTextureIdentifier("witch", "viler_witch", "blink");
		return entity.blinkManager.getBlinkRemainingTicks() > 0 ? textureBlink : texture;
	}

	@Override
	protected void scale(VilerWitchEntity witchEntity, MatrixStack matrixStack, float f) {
		matrixStack.scale(0.9375F, 0.9375F, 0.9375F);
	}
}