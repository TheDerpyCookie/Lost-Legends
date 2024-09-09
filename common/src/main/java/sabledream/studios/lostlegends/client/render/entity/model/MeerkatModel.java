package sabledream.studios.lostlegends.client.render.entity.model;// Made with Blockbench 4.3.1


import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.entity.AnimationState;
import org.joml.Vector3f;
import sabledream.studios.lostlegends.client.render.entity.animation.MeerkatAnimation;
import sabledream.studios.lostlegends.entity.Meerkat;

public final class MeerkatModel<T extends Meerkat> extends AnimatedEntityModel<T>
{
	private final ModelPart root;

	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart rightLeg;
	private final ModelPart leftLeg;
	private final ModelPart rightArm;
	private final ModelPart leftArm;

	public MeerkatModel(ModelPart root) {
		super(root);
		this.root = root.getChild("root");
		this.body = this.root.getChild("body");
		this.rightLeg = this.root.getChild("rightLeg");
		this.leftLeg = this.root.getChild("leftLeg");
		this.rightArm = this.root.getChild("rightArm");
		this.leftArm = this.root.getChild("leftArm");
		this.head = this.root.getChild("head");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData meshdefinition = new ModelData();
		ModelPartData partdefinition = meshdefinition.getRoot();
		ModelPartData root = partdefinition.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 20.0F, 0.0F));

		ModelPartData head = root.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -3.0F, -5.0F, 6.0F, 5.0F, 5.0F, new Dilation(0.0F))
			.uv(17, 0).cuboid(-2.0F, 0.0F, -7.0F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, -2.0F));

		ModelPartData leftEar = head.addChild("leftEar", ModelPartBuilder.create().uv(2, 14).cuboid(0.0F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -1.0F, -2.0F, 0.0F, 0.0F, -0.0873F));

		ModelPartData rightEar = head.addChild("rightEar", ModelPartBuilder.create().uv(2, 10).cuboid(-1.0F, 0.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -1.0F, -2.0F, 0.0F, 0.0F, 0.0873F));

		ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(0, 10).cuboid(-2.0F, -8.0F, -1.0F, 4.0F, 3.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, -1.0F));

		ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(24, 12).cuboid(-1.0F, -8.0F, 0.0F, 2.0F, 8.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -7.0F, 6.0F));

		ModelPartData leftArm = root.addChild("leftArm", ModelPartBuilder.create().uv(6, 21).cuboid(0.01F, 0.0F, -1.0F, 1.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, -2.0F, -1.0F));

		ModelPartData rightArm = root.addChild("rightArm", ModelPartBuilder.create().uv(0, 21).cuboid(-1.01F, 0.0F, -1.0F, 1.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, -2.0F, -1.0F));

		ModelPartData leftLeg = root.addChild("leftLeg", ModelPartBuilder.create().uv(18, 21).cuboid(2.01F, 0.0F, -1.0F, 1.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, -2.0F, 5.0F));

		ModelPartData rightLeg = root.addChild("rightLeg", ModelPartBuilder.create().uv(12, 21).cuboid(-3.01F, 0.0F, -1.0F, 1.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, -2.0F, 5.0F));


		return TexturedModelData.of(meshdefinition, 32, 32);
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

		this.head.pitch = headPitch * ((float) Math.PI / 180F);
		this.head.yaw = netHeadYaw * ((float) Math.PI / 180F);


		this.animate(entity.standingAnimationState, MeerkatAnimation.STAND_UP, ageInTicks);
		this.animate(entity.stopStandingAnimationState, MeerkatAnimation.STOP_STAND, ageInTicks);
		this.animate(entity.diggingAnimationState, MeerkatAnimation.DIGGING, ageInTicks);
		this.animate(entity.digUpAnimationState, MeerkatAnimation.DIGUP, ageInTicks);
		this.animateMovement(MeerkatAnimation.WALK, limbSwing, limbSwingAmount, 1.0F, 2.5F);
		if (this.child) {
			this.animate(MeerkatAnimation.BABY);
		}
	}

	protected void animate(AnimationState p_233382_, Animation p_233383_, float p_233384_) {
		this.animate(p_233382_, p_233383_, p_233384_, 1.0F);
	}

	private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();
	protected void animate(AnimationState p_233386_, Animation p_233387_, float p_233388_, float p_233389_) {
		p_233386_.update(p_233388_, p_233389_);
		p_233386_.run((p_233392_) -> {
			AnimationHelper.animate(this, p_233387_, p_233392_.getTimeRunning(), 1.0F, ANIMATION_VECTOR_CACHE);
		});
	}

}