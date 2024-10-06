package sabledream.studios.lostlegends.client.render.entity.renderer;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.client.render.entity.model.TumbleweedModel;
import sabledream.studios.lostlegends.entity.TumbleweedEntity;
import sabledream.studios.lostlegends.init.LostLegendsEntityModelLayer;

public class TumbleweedRenderer extends MobEntityRenderer<TumbleweedEntity, TumbleweedModel<TumbleweedEntity>>
{
	private final ItemRenderer itemRenderer;

	public TumbleweedRenderer(@NotNull EntityRendererFactory.Context context) {
		super(context, new TumbleweedModel<>(context.getPart(LostLegendsEntityModelLayer.TUMBLEWEED)), 0.6F);
		this.itemRenderer = context.getItemRenderer();
	}

	@Override
	@NotNull
	public Vec3d getPositionOffset(@NotNull TumbleweedEntity entity, float partialTicks) {
		return new Vec3d(0D, 0.2375D, 0D);
	}

	@Override
	public void render(@NotNull TumbleweedEntity entity, float entityYaw, float partialTick, @NotNull MatrixStack poseStack, @NotNull VertexConsumerProvider buffer, int packedLight) {
		super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
		ItemStack stack = entity.getActiveItem();
		if (!stack.isEmpty()) {
			poseStack.push();
			poseStack.translate(entity.itemX, 0.4375D, entity.itemZ);
			poseStack.multiply(RotationAxis.POSITIVE_X.rotation(-MathHelper.lerp(partialTick, entity.prevPitch, entity.pitch) * MathHelper.RADIANS_PER_DEGREE));
			poseStack.push();
			poseStack.multiply(RotationAxis.POSITIVE_Z.rotation(MathHelper.lerp(partialTick, entity.prevRoll, entity.roll) * MathHelper.RADIANS_PER_DEGREE));
			this.itemRenderer.renderItem(stack, ModelTransformationMode.GROUND, packedLight, OverlayTexture.DEFAULT_UV, poseStack, buffer, entity.getWorld(), 1);
			poseStack.pop();
			poseStack.pop();
		}
	}

	@Override
	protected void setupTransforms(@NotNull TumbleweedEntity entityLiving, @NotNull MatrixStack matrixStack, float ageInTicks, float rotationYaw, float partialTick, float scale) {
		if (LostLegends.getConfig().tumbleweedRotatesToLookDirection) {
			matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180F - rotationYaw));
		}
	}

	@Override
	@NotNull
	public Identifier getTexture(@NotNull TumbleweedEntity entity) {
		return LostLegends.makeID("textures/entity/tumbleweed/tumbleweed.png");
	}

}