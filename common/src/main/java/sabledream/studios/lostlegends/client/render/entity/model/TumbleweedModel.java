package sabledream.studios.lostlegends.client.render.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.NotNull;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.entity.TumbleweedEntity;

public class TumbleweedModel<T extends TumbleweedEntity> extends SinglePartEntityModel<T>
{
	private final ModelPart root;
	private final ModelPart tumbleweed;
	private float partialTick;
	private float prevPitch;
	private float pitch;
	private float prevRoll;
	private float roll;
	private float prevTumble;
	private float tumble;

	public TumbleweedModel(@NotNull ModelPart root) {
		super(RenderLayer::getEntityCutoutNoCull);
		this.root = root;
		this.tumbleweed = root.getChild("tumbleweed");
	}

	@NotNull
	public static TexturedModelData createBodyLayer() {
		ModelData meshdefinition = new ModelData();
		meshdefinition.getRoot().addChild("tumbleweed", ModelPartBuilder.create()
			.uv(0, 28).cuboid(-6F, -6F, -6F, 12F, 12F, 12F)
			.uv(0, 0).cuboid(-7F, -7F, -7F, 14F, 14F, 14F), ModelTransform.NONE);

		return TexturedModelData.of(meshdefinition, 64, 64);
	}

	@Override
	public void animateModel(@NotNull T entity, float limbSwing, float limbSwingAmount, float partialTick) {
		super.animateModel(entity, limbSwing, limbSwingAmount, partialTick);
		this.partialTick = partialTick;
		this.prevPitch = entity.prevPitch;
		this.pitch = entity.pitch;
		this.prevRoll = entity.prevRoll;
		this.roll = entity.roll;
		this.prevTumble = entity.prevTumble;
		this.tumble = entity.tumble;
	}

	@Override
	public void setAngles(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (LostLegends.getConfig().tumbleweedRotatesToLookDirection) {
			this.root.pitch = (MathHelper.lerp(this.partialTick, this.prevTumble, this.tumble)) * MathHelper.RADIANS_PER_DEGREE;
		} else {
			this.root.pitch = 0F;
		}
	}

	@Override
	public void render(@NotNull MatrixStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		poseStack.push();
		poseStack.translate(0D, 1.3D, 0D);
		if (!LostLegends.getConfig().tumbleweedRotatesToLookDirection) {
			poseStack.push();
			poseStack.multiply(RotationAxis.POSITIVE_X.rotation(MathHelper.lerp(this.partialTick, this.prevPitch, this.pitch) * MathHelper.RADIANS_PER_DEGREE));
			poseStack.push();
			poseStack.multiply(RotationAxis.POSITIVE_Z.rotation(MathHelper.lerp(this.partialTick, this.prevRoll, this.roll) * MathHelper.RADIANS_PER_DEGREE));
			poseStack.push();
			this.root.render(poseStack, vertexConsumer, packedLight, OverlayTexture.DEFAULT_UV, color);
			poseStack.pop();
			poseStack.pop();
			poseStack.pop();
		} else {
			this.root.render(poseStack, vertexConsumer, packedLight, OverlayTexture.DEFAULT_UV, color);
		}
		poseStack.pop();
	}

	@Override
	@NotNull
	public ModelPart getPart() {
		return this.root;
	}

}