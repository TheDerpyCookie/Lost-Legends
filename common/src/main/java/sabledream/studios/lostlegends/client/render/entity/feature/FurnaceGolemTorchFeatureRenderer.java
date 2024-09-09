package sabledream.studios.lostlegends.client.render.entity.feature;

import net.minecraft.block.Blocks;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.IronGolemEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.gen.feature.Feature;
import sabledream.studios.lostlegends.entity.FurnaceGolemEntity;

public class FurnaceGolemTorchFeatureRenderer extends FeatureRenderer<FurnaceGolemEntity, IronGolemEntityModel<FurnaceGolemEntity>>
{
	private final BlockRenderManager blockRenderer;

	public FurnaceGolemTorchFeatureRenderer(FeatureRendererContext<FurnaceGolemEntity, IronGolemEntityModel<FurnaceGolemEntity>> featureRendererContext, BlockRenderManager blockRenderDispatcher) {
		super(featureRendererContext);
		this.blockRenderer = blockRenderDispatcher;
	}

	public void render(MatrixStack poseStack, VertexConsumerProvider multiBufferSource, int i, FurnaceGolemEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		if (entity.getLookingAtVillagerTicks() != 0) {
			poseStack.push();
			ModelPart modelPart = this.getContextModel().getRightArm();
			modelPart.rotate(poseStack);
			poseStack.translate(-1.1875F, 1.0625F, -0.9375F);
			poseStack.translate(0.5F, 0.5F, 0.5F);
			float m = 0.5F;
			poseStack.scale(0.5F, 0.5F, 0.5F);
			poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90.0F));
			poseStack.translate(-0.5F, -0.5F, -0.5F);
			this.blockRenderer.renderBlockAsEntity(Blocks.TORCH.getDefaultState(),poseStack, multiBufferSource, i, OverlayTexture.DEFAULT_UV);
			poseStack.pop();
		}
	}
}