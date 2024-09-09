package sabledream.studios.lostlegends.client.render.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.enums.BedPart;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.LightmapCoordinatesRetriever;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
import sabledream.studios.lostlegends.block.RainbowBedBlock;
import sabledream.studios.lostlegends.block.entity.RainbowBedBlockEntity;
import sabledream.studios.lostlegends.init.LostLegendsEntityModelLayer;
import sabledream.studios.lostlegends.init.LostLegendsBlockEntity;

import java.util.HashMap;

@Environment(EnvType.CLIENT)
public class
RainbowBedBlockEntityRenderer implements BlockEntityRenderer<RainbowBedBlockEntity>
{

	public static HashMap<RainbowBedBlockEntity, Identifier> TEXTURES = new HashMap<>();
	private final ModelPart head;
	private final ModelPart foot;

	public RainbowBedBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
		head = context.getLayerModelPart(LostLegendsEntityModelLayer.RAINBOW_BED_HEAD_MODEL_LAYER);
		foot = context.getLayerModelPart(LostLegendsEntityModelLayer.RAINBOW_BED_FOOT_MODEL_LAYER);
	}

	public static TexturedModelData createHeadLayer() {
		ModelData meshDefinition = new ModelData();
		ModelPartData partDefinition = meshDefinition.getRoot();
		partDefinition.addChild("main", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 6.0F), ModelTransform.NONE);
		partDefinition.addChild("left_leg", ModelPartBuilder.create().uv(50, 6).cuboid(0.0F, 6.0F, 0.0F, 3.0F, 3.0F, 3.0F), ModelTransform.rotation(1.5707964F, 0.0F, 1.5707964F));
		partDefinition.addChild("right_leg", ModelPartBuilder.create().uv(50, 18).cuboid(-16.0F, 6.0F, 0.0F, 3.0F, 3.0F, 3.0F), ModelTransform.rotation(1.5707964F, 0.0F, 3.1415927F));
		return TexturedModelData.of(meshDefinition, 64, 64);
	}

	public static TexturedModelData createFootLayer() {
		ModelData meshDefinition = new ModelData();
		ModelPartData partDefinition = meshDefinition.getRoot();
		partDefinition.addChild("main", ModelPartBuilder.create().uv(0, 22).cuboid(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 6.0F), ModelTransform.NONE);
		partDefinition.addChild("left_leg", ModelPartBuilder.create().uv(50, 0).cuboid(0.0F, 6.0F, -16.0F, 3.0F, 3.0F, 3.0F), ModelTransform.rotation(1.5707964F, 0.0F, 0.0F));
		partDefinition.addChild("right_leg", ModelPartBuilder.create().uv(50, 12).cuboid(-16.0F, 6.0F, -16.0F, 3.0F, 3.0F, 3.0F), ModelTransform.rotation(1.5707964F, 0.0F, 4.712389F));
		return TexturedModelData.of(meshDefinition, 64, 64);
	}

	public void render(RainbowBedBlockEntity bedBlockEntity, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, int overlay) {
		SpriteIdentifier spriteIdentifier = new SpriteIdentifier(TexturedRenderLayers.BEDS_ATLAS_TEXTURE, Identifier.of("lostlegends:entity/bed/rainbow"));
		World world = bedBlockEntity.getWorld();
		if (world != null) {
			BlockState blockState = bedBlockEntity.getCachedState();
			DoubleBlockProperties.PropertySource<RainbowBedBlockEntity> propertySource = DoubleBlockProperties.toPropertySource(LostLegendsBlockEntity.RAINBOW_BED.get(), RainbowBedBlock::getBedPart, RainbowBedBlock::getOppositePartDirection, ChestBlock.FACING, blockState, world, bedBlockEntity.getPos(), (levelAccessor, blockPos) -> false);
			int k = propertySource.apply(new LightmapCoordinatesRetriever<>()).get(light);
			renderPiece(matrixStack, vertexConsumerProvider, blockState.get(RainbowBedBlock.PART) == BedPart.HEAD ? head : foot, blockState.get(RainbowBedBlock.FACING), spriteIdentifier, k, overlay, false);
		} else {
			renderPiece(matrixStack, vertexConsumerProvider, head, Direction.SOUTH, spriteIdentifier, light, overlay, false);
			renderPiece(matrixStack, vertexConsumerProvider, foot, Direction.SOUTH, spriteIdentifier, light, overlay, true);
		}
	}

	private void renderPiece(MatrixStack poseStack, VertexConsumerProvider multiBufferSource, ModelPart modelPart, Direction direction, SpriteIdentifier material, int i, int j, boolean bl) {
		poseStack.push();
		poseStack.translate(0.0F, 0.5625F, bl ? -1.0F : 0.0F);
		poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
		poseStack.translate(0.5F, 0.5F, 0.5F);
		poseStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0F + direction.asRotation()));
		poseStack.translate(-0.5F, -0.5F, -0.5F);
		VertexConsumer vertexConsumer = material.getVertexConsumer(multiBufferSource, RenderLayer::getEntitySolid);
		modelPart.render(poseStack, vertexConsumer, i, j);
		poseStack.pop();
	}

}