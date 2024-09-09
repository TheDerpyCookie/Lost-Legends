package sabledream.studios.lostlegends.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import sabledream.studios.lostlegends.entity.FancyChickenEntity;

public class FancyChickenModel<T extends FancyChickenEntity> extends ChickenEntityModel<T>
{
	public static final String RED_THING = "red_thing";
	private final ModelPart head;
	private  final ModelPart body;
	private final ModelPart rightLeg;
	private final ModelPart leftLeg;
	private final ModelPart rightWing;
	private final ModelPart leftWing;
	private final ModelPart beak;
	private final ModelPart wattle;
	private final ModelPart tail;

	public FancyChickenModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
		this.beak = root.getChild("beak");
		this.wattle = root.getChild("red_thing");
		this.body = root.getChild("body");
		this.tail = root.getChild(EntityModelPartNames.TAIL);
		this.rightLeg = root.getChild("right_leg");
		this.leftLeg = root.getChild("left_leg");
		this.rightWing = root.getChild("right_wing");
		this.leftWing = root.getChild("left_wing");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		boolean i = true;
		modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 23).cuboid(-4.0F, -7.005F, -4.5F, 8.0F, 1.0F, 8.0F).uv(36, 22).cuboid(-2.5F, -11.0F, -3.0F, 5.0F, 5.0F, 5.0F).uv(0, 0).cuboid(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F), ModelTransform.pivot(0.0F, 15.0F, -4.0F));
//		modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F).uv(30, 11).cuboid(-4.0F, -6.0F, -4.0F, 8.0F, 0.0F, 7.0F).uv(32, 8).cuboid(-3.0F, -12.0F, -3.0F, 6.0F, 6.0F, 5.0F), ModelTransform.pivot(0.0F, 15.0F, -4.0F));
		modelPartData.addChild("beak", ModelPartBuilder.create().uv(14, 0).cuboid(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F), ModelTransform.pivot(0.0F, 15.0F, -4.0F));
		modelPartData.addChild("red_thing", ModelPartBuilder.create().uv(14, 4).cuboid(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F), ModelTransform.pivot(0.0F, 15.0F, -4.0F));
		modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 9).cuboid(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F), ModelTransform.of(0.0F, 16.0F, 0.0F, 1.5707964F, 0.0F, 0.0F));ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(26, 0).cuboid(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F);
		modelPartData.addChild("right_leg", modelPartBuilder, ModelTransform.pivot(-2.0F, 19.0F, 1.0F));
		modelPartData.addChild("left_leg", modelPartBuilder, ModelTransform.pivot(1.0F, 19.0F, 1.0F));
		modelPartData.addChild("right_wing", ModelPartBuilder.create().uv(24, 13).cuboid(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F), ModelTransform.pivot(-4.0F, 13.0F, 0.0F));
		modelPartData.addChild("left_wing", ModelPartBuilder.create().uv(24, 13).cuboid(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F), ModelTransform.pivot(4.0F, 13.0F, 0.0F));
		modelPartData.addChild(EntityModelPartNames.TAIL, ModelPartBuilder.create().uv(43, 5).cuboid(-1.0F, -10.0F, -1.0F, 0.0F, 10.0F, 7.0F), ModelTransform.pivot(1.0F, 15.0F, 5.0F));
		return TexturedModelData.of(modelData, 64, 32);
	}

	public ImmutableList<ModelPart> getBody() {
		return new ImmutableList.Builder<ModelPart>().addAll(super.getBodyParts()).add(tail).build();
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

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		head.render(matrices, vertices, light, overlay);
		beak.render(matrices, vertices, light, overlay);
		wattle.render(matrices, vertices, light, overlay);
		body.render(matrices, vertices, light, overlay);
		tail.render(matrices, vertices, light, overlay);
		rightLeg.render(matrices, vertices, light, overlay);
		leftLeg.render(matrices, vertices, light, overlay);
		rightWing.render(matrices, vertices, light, overlay);
		leftWing.render(matrices, vertices, light, overlay);
	}
}