package sabledream.studios.lostlegends.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.util.math.MathHelper;
import sabledream.studios.lostlegends.entity.CluckshroomEntity;

public class CluckshroomModel<T extends CluckshroomEntity> extends ChickenEntityModel<T>
{
	public static final String RED_THING = "red_thing";
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart rightLeg;
	private final ModelPart leftLeg;
	private final ModelPart rightWing;
	private final ModelPart leftWing;
	private final ModelPart beak;
	private final ModelPart wattle;

	public CluckshroomModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
		this.beak = root.getChild("beak");
		this.wattle = root.getChild("red_thing");
		this.body = root.getChild("body");
		this.rightLeg = root.getChild("right_leg");
		this.leftLeg = root.getChild("left_leg");
		this.rightWing = root.getChild("right_wing");
		this.leftWing = root.getChild("left_wing");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		boolean i = true;
		modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F), ModelTransform.pivot(0.0F, 15.0F, -4.0F));
		modelPartData.addChild("beak", ModelPartBuilder.create().uv(14, 0).cuboid(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F), ModelTransform.pivot(0.0F, 15.0F, -4.0F));
		modelPartData.addChild("red_thing", ModelPartBuilder.create().uv(14, 4).cuboid(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F), ModelTransform.pivot(0.0F, 15.0F, -4.0F));
		modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 9).cuboid(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F), ModelTransform.of(0.0F, 16.0F, 0.0F, 1.5707964F, 0.0F, 0.0F));
		ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(26, 0).cuboid(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F);
		modelPartData.addChild("right_leg", modelPartBuilder, ModelTransform.pivot(-2.0F, 19.0F, 1.0F));
		modelPartData.addChild("left_leg", modelPartBuilder, ModelTransform.pivot(1.0F, 19.0F, 1.0F));
		modelPartData.addChild("right_wing", ModelPartBuilder.create().uv(24, 13).cuboid(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F), ModelTransform.pivot(-4.0F, 13.0F, 0.0F));
		modelPartData.addChild("left_wing", ModelPartBuilder.create().uv(24, 13).cuboid(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F), ModelTransform.pivot(4.0F, 13.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 32);
	}

	protected Iterable<ModelPart> getHeadParts() {
		return ImmutableList.of(this.head, this.beak, this.wattle);
	}

	protected Iterable<ModelPart> getBodyParts() {
		return ImmutableList.of(this.body, this.rightLeg, this.leftLeg, this.rightWing, this.leftWing);
	}

	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.head.pitch = headPitch * 0.017453292F;
		this.head.yaw = headYaw * 0.017453292F;
		this.beak.pitch = this.head.pitch;
		this.beak.yaw = this.head.yaw;
		this.wattle.pitch = this.head.pitch;
		this.wattle.yaw = this.head.yaw;
		this.rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
		this.leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
		this.rightWing.roll = animationProgress;
		this.leftWing.roll = -animationProgress;
	}

	public ModelPart getHead() {
		return this.head;
	}
}
