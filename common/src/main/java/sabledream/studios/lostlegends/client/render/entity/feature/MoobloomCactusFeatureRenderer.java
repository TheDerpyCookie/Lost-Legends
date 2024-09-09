package sabledream.studios.lostlegends.client.render.entity.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CactusBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.util.math.RotationAxis;
import sabledream.studios.lostlegends.entity.MoobloomCactusEntity;
import sabledream.studios.lostlegends.entity.MoolipEntity;
import sabledream.studios.lostlegends.init.LostLegendsBlocks;

@Environment(EnvType.CLIENT)
public class MoobloomCactusFeatureRenderer<T extends MoobloomCactusEntity> extends FeatureRenderer<T,CowEntityModel<T>>
{
	private final BlockRenderManager blockRenderer;

	public MoobloomCactusFeatureRenderer(FeatureRendererContext<T, CowEntityModel<T>> renderLayerParent, BlockRenderManager BlockRenderManager) {
		super(renderLayerParent);
		this.blockRenderer = BlockRenderManager;
	}

	public void render(MatrixStack poseStack, VertexConsumerProvider multiBufferSource, int light, T entity, float f, float g, float h, float j, float k, float l) {
		if (!entity.isBaby()) {
			MinecraftClient minecraft = MinecraftClient.getInstance();
			boolean bl = minecraft.hasOutline(entity) && entity.isInvisible();
			if (!entity.isInvisible() || bl) {

				BlockState blockState = LostLegendsBlocks.TINYCACTUS.get().getDefaultState();
				int coords = LivingEntityRenderer.getOverlay(entity, 0.0F);
				BakedModel bakedModel = this.blockRenderer.getModel(blockState);

				poseStack.push();
				poseStack.translate(0.2D, -0.2D, 0.5D);
				poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-48.0F));
				poseStack.scale(-0.66F, -0.66F, 0.66F);
				poseStack.translate(-0.5D, -0.5D, -0.5D);
				renderCactusBlock(poseStack, multiBufferSource, light, bl, blockState, coords, bakedModel);
				poseStack.pop();
				poseStack.push();
				poseStack.translate(-0.2D, -0.2D, 0.4D);
				poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-48.0F));
				poseStack.scale(-0.66F, -0.66F, 0.66F);
				poseStack.translate(-0.5D, -0.5D, -0.5D);
				renderCactusBlock(poseStack, multiBufferSource, light, bl, blockState, coords, bakedModel);
				poseStack.pop();
				poseStack.push();
				poseStack.translate(-0.15D, -0.2D, -0.3D);
				poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-48.0F));
				poseStack.scale(-0.66F, -0.66F, 0.66F);
				poseStack.translate(-0.5D, -0.5D, -0.5D);
				renderCactusBlock(poseStack, multiBufferSource, light, bl, blockState, coords, bakedModel);
				poseStack.pop();
				poseStack.push();
				poseStack.translate(0.15D, -0.2D, -0.2D);
				poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-48.0F));
				poseStack.scale(-0.66F, -0.66F, 0.66F);
				poseStack.translate(-0.5D, -0.5D, -0.5D);
				renderCactusBlock(poseStack, multiBufferSource, light, bl, blockState, coords, bakedModel);
				poseStack.pop();
				poseStack.push();
				getContextModel().getHead().rotate(poseStack);
				poseStack.translate(0.1D, -0.5D, -0.2D);
				poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-78.0F));
				poseStack.scale(-0.66F, -0.66F, 0.66F);
				poseStack.translate(-0.5D, -0.5D, -0.5D);
				renderCactusBlock(poseStack, multiBufferSource, light, bl, blockState, coords, bakedModel);
				poseStack.pop();
			}
		}
	}

	private void renderCactusBlock(MatrixStack poseStack, VertexConsumerProvider multiBufferSource, int i, boolean bl, BlockState blockState, int j, BakedModel bakedModel) {
		if (bl) {
			blockRenderer.getModelRenderer().render(poseStack.peek(), multiBufferSource.getBuffer(RenderLayer.getOutline(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)), blockState, bakedModel, 0.0F, 0.0F, 0.0F, i, j);
		} else {
			blockRenderer.renderBlockAsEntity(blockState, poseStack, multiBufferSource, i, j);
		}
	}

}