package sabledream.studios.lostlegends.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import sabledream.studios.lostlegends.client.render.entity.animation.OstrichAnimation;
import sabledream.studios.lostlegends.entity.Ostrich;

public class OstrichModel<T extends Ostrich> extends SinglePartEntityModel<T>
{
	private final ModelPart root;

	private final ModelPart body;
	private final ModelPart legR;
	private final ModelPart legL;

	public OstrichModel(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.legR = root.getChild("legR");
		this.legL = root.getChild("legL");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData meshdefinition = new ModelData();
		ModelPartData partdefinition = meshdefinition.getRoot();

		ModelPartData body = partdefinition.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -6.0F, -9.0F, 14.0F, 12.0F, 18.0F), ModelTransform.pivot(1.0F, 4.0F, 0.0F));

		ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(46, 0).cuboid(-4.0F, 0.0F, 0.0F, 8.0F, 8.0F, 4.0F)
			.uv(60, 57).cuboid(-4.0F, 8.0F, 0.0F, 8.0F, 3.0F, 4.0F), ModelTransform.of(0.0F, -1.0F, 9.0F, 0.1745F, 0.0F, 0.0F));

		ModelPartData neck = body.addChild("neck", ModelPartBuilder.create().uv(0, 30).cuboid(-2.0F, -24.0F, -2.0F, 4.0F, 21.0F, 4.0F)
			.uv(0, 0).cuboid(-2.0F, -22.0F, -5.0F, 4.0F, 2.0F, 3.0F), ModelTransform.pivot(0.0F, 3.0F, -11.0F));

		ModelPartData neckthing = neck.addChild("neckthing", ModelPartBuilder.create().uv(40, 30).cuboid(-4.0F, -0.5F, -2.5F, 8.0F, 7.0F, 5.0F)
			.uv(26, 56).cuboid(-4.0F, 6.5F, -2.5F, 8.0F, 2.0F, 5.0F), ModelTransform.pivot(0.0F, -2.5F, -0.5F));

		ModelPartData wingR = body.addChild("wingR", ModelPartBuilder.create().uv(16, 30).cuboid(-1.0F, 0.0F, -5.0F, 1.0F, 8.0F, 10.0F), ModelTransform.of(-7.0F, 1.0F, 2.0F, 0.0F, 0.0F, 0.1745F));

		ModelPartData wingL = body.addChild("wingL", ModelPartBuilder.create().uv(28, 38).cuboid(0.0F, 0.0F, -5.0F, 1.0F, 8.0F, 10.0F), ModelTransform.of(7.0F, 1.0F, 2.0F, 0.0F, 0.0F, -0.1745F));

		ModelPartData legR = partdefinition.addChild("legR", ModelPartBuilder.create().uv(12, 51).cuboid(-1.5F, -1.0F, -4.0F, 3.0F, 15.0F, 4.0F), ModelTransform.pivot(-1.5F, 10.0F, 1.0F));

		ModelPartData legL = partdefinition.addChild("legL", ModelPartBuilder.create().uv(50, 42).cuboid(-1.5F, -1.0F, -4.0F, 3.0F, 15.0F, 4.0F), ModelTransform.pivot(3.5F, 10.0F, 1.0F));

		return TexturedModelData.of(meshdefinition, 128, 128);
	}

	@Override
	public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		//you should reset animation
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		float f = Math.min((float) entity.getVelocity().lengthSquared() * 100.0F, 8.0F);
		float f2 = Math.min((float) entity.getVelocity().lengthSquared() * 50.0F, 8.0F);
		this.animate(OstrichAnimation.IDLE);
		this.animateMovement(OstrichAnimation.WALK, limbSwing, limbSwingAmount * (1.0F - entity.getRunningScale()), 1.0F, 1.5F);

		this.animateMovement(OstrichAnimation.RUN, limbSwing, limbSwingAmount * entity.getRunningScale(), 1.0F, 1.5F);
		this.animate(OstrichAnimation.DIP);
		this.animate(OstrichAnimation.KICK);
	}

	@Override
	public void render(MatrixStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		super.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay,color);
		legR.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
		legL.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
	@Override
	public ModelPart getPart() {
		return this.root;
	}
}