package sabledream.studios.lostlegends.client.render.entity.feature;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.SnowGolemEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import sabledream.studios.lostlegends.entity.MelonGolemEntity;
import sabledream.studios.lostlegends.init.LostLegendsBlocks;

public class MelonGolemCarvedMelonFeatureRenderer extends FeatureRenderer<MelonGolemEntity, SnowGolemEntityModel<MelonGolemEntity>> {
	private final BlockRenderManager blockRenderer;
	private final ItemRenderer itemRenderer;

	public MelonGolemCarvedMelonFeatureRenderer(FeatureRendererContext<MelonGolemEntity, SnowGolemEntityModel<MelonGolemEntity>> featureRendererContext, BlockRenderManager blockRenderManager, ItemRenderer itemRenderer) {
		super(featureRendererContext);
		this.blockRenderer = blockRenderManager;
		this.itemRenderer = itemRenderer;
	}

	@SuppressWarnings("deprecation")
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, MelonGolemEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		if (!entity.isMelonEquipped()) {
			return;
		}
		if (entity.isInvisible()) {
			return;
		}

		boolean hasOutline = MinecraftClient.getInstance().hasOutline(entity);

		matrixStack.push();
		getContextModel().getHead().rotate(matrixStack);
		matrixStack.translate(0.0D, -0.34375D, 0.0D);
		matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
		matrixStack.scale(0.625F, -0.625F, -0.625F);
		ItemStack head = new ItemStack(LostLegendsBlocks.CARVED_MELON.get());
		ItemStack headBlink = new ItemStack(LostLegendsBlocks.MELON_GOLEM_HEAD_BLINK.get());
		ItemStack headShoot = new ItemStack(LostLegendsBlocks.MELON_GOLEM_HEAD_SHOOT.get());
		ItemStack itemStack;
		if (entity.isShooting()) {
			itemStack = headShoot;
		} else {
			if (entity.blinkManager.getBlinkRemainingTicks() > 0) itemStack = headBlink;
			else itemStack = head;
		}

		if (hasOutline) {
			BlockState blockState = LostLegendsBlocks.CARVED_MELON.get().getDefaultState();
			BakedModel bakedModel = blockRenderer.getModel(blockState);
			int n = LivingEntityRenderer.getOverlay(entity, 0.0f);
			matrixStack.translate(-0.5f, -0.5f, -0.5f);
			blockRenderer.getModelRenderer().render(matrixStack.peek(), vertexConsumerProvider.getBuffer(RenderLayer.getOutline(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE)), blockState, bakedModel, 0.0f, 0.0f, 0.0f, light, n);
		} else {
			itemRenderer.renderItem(entity, itemStack, ModelTransformationMode.HEAD, false, matrixStack, vertexConsumerProvider, entity.getWorld(), light, LivingEntityRenderer.getOverlay(entity, 0.0f), entity.getId());
		}

		matrixStack.pop();
	}
}