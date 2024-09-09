package sabledream.studios.lostlegends.client.render.entity.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import sabledream.studios.lostlegends.client.render.entity.model.CluckshroomModel;
import sabledream.studios.lostlegends.entity.CluckshroomEntity;


@Environment(EnvType.CLIENT)
public class CluckshroomMushroomFeatureRenderer<T extends CluckshroomEntity> extends FeatureRenderer<T, CluckshroomModel<T>> {
	private final BlockRenderManager blockRenderer;

	public CluckshroomMushroomFeatureRenderer(FeatureRendererContext<T, CluckshroomModel<T>> renderLayerParent, BlockRenderManager blockRenderDispatcher) {
		super(renderLayerParent);
		this.blockRenderer = blockRenderDispatcher;

	}

	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (!entity.isBaby()) {
			MinecraftClient minecraft = MinecraftClient.getInstance();
			boolean bl = minecraft.hasOutline(entity) && entity.isInvisible();
			if (!entity.isInvisible() || bl) {
				BlockState blockState = Blocks.RED_MUSHROOM.getDefaultState();
				int i = LivingEntityRenderer.getOverlay(entity, 0.0F);
				BakedModel bakedModel = blockRenderer.getModel(blockState);
				matrixStack.push();
				matrixStack.translate(-0.1F, 0.6F, 0.05D);
				matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-48.0F));
				matrixStack.scale(-0.5F, -0.5F, 0.5F);
				matrixStack.translate(-0.5D, -0.5D, -0.5D);
				renderMushroomBlock(matrixStack, vertexConsumerProvider, i, bl, blockState, i, bakedModel);
				matrixStack.pop();
				matrixStack.push();
				getContextModel().getHead().rotate(matrixStack);
				matrixStack.translate(0.05F, -0.6F, 0.0D);
				matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-78.0F));
				matrixStack.scale(-0.5F, -0.5F, 0.5F);
				matrixStack.translate(-0.5D, -0.5D, -0.5D);
				renderMushroomBlock(matrixStack, vertexConsumerProvider, i, bl, blockState, i, bakedModel);
				matrixStack.pop();
			}
		}
	}

	private void renderMushroomBlock(MatrixStack poseStack, VertexConsumerProvider multiBufferSource, int i, boolean bl, BlockState blockState, int j, BakedModel bakedModel) {
		if (bl) {
			this.blockRenderer.getModelRenderer().render(poseStack.peek(), multiBufferSource.getBuffer(RenderLayer.getOutline(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)), blockState, bakedModel, 0.0F, 0.0F, 0.0F, i, j);
		} else {
			this.blockRenderer.renderBlockAsEntity(blockState, poseStack, multiBufferSource, i, j);
		}

	}
}
