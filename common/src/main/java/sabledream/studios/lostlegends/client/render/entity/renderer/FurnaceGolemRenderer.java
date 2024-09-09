package sabledream.studios.lostlegends.client.render.entity.renderer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.IronGolemEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import sabledream.studios.lostlegends.client.render.entity.feature.FurnaceGolemFlamesFeatureRenderer;
import sabledream.studios.lostlegends.client.render.entity.feature.FurnaceGolemTorchFeatureRenderer;
import sabledream.studios.lostlegends.entity.FurnaceGolemEntity;

public class FurnaceGolemRenderer extends MobEntityRenderer<FurnaceGolemEntity, IronGolemEntityModel<FurnaceGolemEntity>>
{

	public FurnaceGolemRenderer(EntityRendererFactory.Context context) {
		super(context, new IronGolemEntityModel<>(context.getPart(EntityModelLayers.IRON_GOLEM)), 0.7F);
		addFeature(new FurnaceGolemFlamesFeatureRenderer(this));
		addFeature(new FurnaceGolemTorchFeatureRenderer(this,context.getBlockRenderManager()));
	}

	@Override
	protected void setupTransforms(FurnaceGolemEntity furnaceGolemEntity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, float i) {
		super.setupTransforms(furnaceGolemEntity, matrices, animationProgress, bodyYaw, tickDelta, i);
		if ((double) furnaceGolemEntity.limbAnimator.getSpeed() < 0.01) {
			return;
		}

		float f1 = furnaceGolemEntity.limbAnimator.getPos(tickDelta) + 6.0F;
		float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
		matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(6.5F * f2));
	}

	public Identifier getTexture(FurnaceGolemEntity entity) {
		Identifier texture = TextureUtils.getTextureIdentifier("iron_golem", "furnace_golem");
		Identifier textureBlink = TextureUtils.getTextureIdentifier("iron_golem", "furnace_golem", "blink");
		Identifier textureAngry = TextureUtils.getTextureIdentifier("iron_golem", "furnace_golem", "angry");
		if (entity.isAngry()) {
			return textureAngry;
		}
		if (entity.blinkManager.getBlinkRemainingTicks() > 0) return textureBlink;
		return texture;
	}

}