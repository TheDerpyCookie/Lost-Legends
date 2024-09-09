package sabledream.studios.lostlegends.client.render.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.entity.AnimationState;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector3f;
import sabledream.studios.lostlegends.client.render.entity.animation.VultureAnimations;
import sabledream.studios.lostlegends.entity.Vulture;

public class VultureModel<T extends Vulture> extends AnimatedEntityModel<T>
{
	private final ModelPart root;
	private final ModelPart vulture;
	private final ModelPart right_wing;
	private final ModelPart right_wing_but_more;
	private final ModelPart left_wing;
	private final ModelPart left_wing_but_more;


	public VultureModel(ModelPart root) {
		super(root);
		this.root = root;
		this.vulture = root.getChild("vulture");
		this.right_wing = this.vulture.getChild("right_wing");
		this.right_wing_but_more = this.right_wing.getChild("right_wing_but_more");
		this.left_wing = this.vulture.getChild("left_wing");
		this.left_wing_but_more = this.left_wing.getChild("left_wing_but_more");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData meshdefinition = new ModelData();
		ModelPartData ModelPartData = meshdefinition.getRoot();

		ModelPartData vulture = ModelPartData.addChild("vulture", ModelPartBuilder.create().uv(-8, 33).cuboid(-6.5F, 0.0F, -11.0F, 13.0F, 0.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 17.0F, 0.0F));

		ModelPartData body_r1 = vulture.addChild("body_r1", ModelPartBuilder.create().uv(21, 11).cuboid(-4.5F, -6.5F, -4.5F, 9.0F, 13.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.5F, 1.5F, 3.1416F, 0.0F, 3.1416F));

		ModelPartData collar_r1 = vulture.addChild("collar_r1", ModelPartBuilder.create().uv(23, 0).cuboid(-3.5F, -3.5F, -1.0F, 7.0F, 7.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -8.5F, 8.0F, 3.1416F, 0.0F, 3.1416F));

		ModelPartData neck = vulture.addChild("neck", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -8.5F, 7.0F));

		ModelPartData neck_1_r1 = neck.addChild("neck_1_r1", ModelPartBuilder.create().uv(12, 11).cuboid(-1.5F, -1.5F, -6.0F, 3.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData neck_but_more = neck.addChild("neck_but_more", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 4.5F));

		ModelPartData neck_2_r1 = neck_but_more.addChild("neck_2_r1", ModelPartBuilder.create().uv(0, 11).cuboid(-1.5F, -5.5F, -1.5F, 3.0F, 7.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData head = neck_but_more.addChild("head", ModelPartBuilder.create().uv(18, 0).cuboid(-0.5F, 0.0F, 8.5F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -5.5F, 0.0F));

		ModelPartData head_r1 = head.addChild("head_r1", ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-2.0F, -4.0F, -5.5F, 4.0F, 4.0F, 7.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 3.1416F));

		ModelPartData beak_r1 = head.addChild("beak_r1", ModelPartBuilder.create().uv(15, 1).cuboid(-0.5F, -1.5F, -3.0F, 1.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.5F, 5.5F, 0.0F, 3.1416F, 0.0F));

		ModelPartData left_wing = vulture.addChild("left_wing", ModelPartBuilder.create(), ModelTransform.pivot(-4.5F, -8.5F, 0.5F));

		ModelPartData left_wing_1_r1 = left_wing.addChild("left_wing_1_r1", ModelPartBuilder.create().uv(0, 41).cuboid(0.0F, -4.5F, -0.5F, 11.0F, 9.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 3.1416F));

		ModelPartData left_wing_but_more = left_wing.addChild("left_wing_but_more", ModelPartBuilder.create(), ModelTransform.pivot(-11.0F, 0.0F, 0.0F));

		ModelPartData left_wing_2_r1 = left_wing_but_more.addChild("left_wing_2_r1", ModelPartBuilder.create().uv(24, 41).cuboid(0.0F, -4.5F, -0.5F, 9.0F, 8.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 3.1416F));

		ModelPartData right_wing = vulture.addChild("right_wing", ModelPartBuilder.create(), ModelTransform.pivot(4.5F, -8.5F, 0.5F));

		ModelPartData right_wing_1_r1 = right_wing.addChild("right_wing_1_r1", ModelPartBuilder.create().uv(0, 51).cuboid(-11.0F, -4.5F, -0.5F, 11.0F, 9.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 3.1416F));

		ModelPartData right_wing_but_more = right_wing.addChild("right_wing_but_more", ModelPartBuilder.create(), ModelTransform.pivot(11.0F, 0.0F, 0.0F));

		ModelPartData right_wing_2_r1 = right_wing_but_more.addChild("right_wing_2_r1", ModelPartBuilder.create().uv(24, 51).cuboid(-9.0F, -4.5F, -0.5F, 9.0F, 8.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 3.1416F));

		ModelPartData left_leg = vulture.addChild("left_leg", ModelPartBuilder.create().uv(43, 0).cuboid(-1.5F, 0.0F, -2.0F, 3.0F, 4.0F, 4.0F, new Dilation(0.0F))
			.uv(48, 8).cuboid(-1.5F, 4.0F, -2.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 0.0F, 0.0F));

		ModelPartData right_leg = vulture.addChild("right_leg", ModelPartBuilder.create().uv(26, 33).cuboid(-1.5F, 0.0F, -2.0F, 3.0F, 4.0F, 4.0F, new Dilation(0.0F))
			.uv(40, 33).cuboid(-1.5F, 4.0F, -2.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 0.0F, 0.0F));

		return TexturedModelData.of(meshdefinition, 64, 64);
	}

	@Override
	public void setAngles(
		T entity,
		float limbSwing,
		float limbSwingAmount,
		float ageInTicks,
		float netHeadYaw,
		float headPitch
	) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.vulture.yaw = 3.1416F;
		float f = ((float) entity.getUniqueFlapTickOffset() + ageInTicks) * 12.448451F * ((float) Math.PI / 180F);
		float f1 = 16.0F;
		this.left_wing.yaw = MathHelper.cos(f) * 16.0F * ((float) Math.PI / 180F);
		this.left_wing_but_more.yaw = MathHelper.cos(f) * 16.0F * ((float) Math.PI / 180F);
		this.right_wing.yaw = -this.left_wing.yaw;
		this.right_wing_but_more.yaw = -this.left_wing_but_more.yaw;

		if (entity.isAttacking()) {
			this.animate(VultureAnimations.FLYING_AGRESSIVE);
		} else {
			this.animate(VultureAnimations.FLYING);
		}
	}
}