package sabledream.studios.lostlegends.client.render.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.client.render.entity.animation.BarnacleAnimation;
import sabledream.studios.lostlegends.entity.Barnacle;

public class BarnacleModel<T extends Barnacle> extends SinglePartEntityModel<T>
{ private final ModelPart root;

	public BarnacleModel(ModelPart root) {
		this.root = root.getChild("monster");
	}

	public static TexturedModelData createBodyLayer() {
		ModelData meshdefinition = new ModelData();
		ModelPartData partdefinition = meshdefinition.getRoot();
		ModelPartData monster = partdefinition.addChild("monster", ModelPartBuilder.create(), ModelTransform.of(0.0F, 11.5F, 0.0F, -1.5708F, 0.0F, 1.5708F));
		ModelPartData body = monster.addChild("body", ModelPartBuilder.create().uv(0, 32).cuboid(0.0F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new Dilation(0.0F)).uv(27, 27).cuboid(5.0F, -2.5F, -2.5F, 6.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, 0.0F, 0.0F));
		body.addChild("piercer", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -0.5f, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.1F, 0.0F, 0.0F));
		ModelPartData segmentCluster1 = body.addChild("segmentCluster1", ModelPartBuilder.create(), ModelTransform.pivot(0.1F, 0.0F, 0.0F));
		segmentCluster1.addChild("segment1", ModelPartBuilder.create().uv(0, 24).cuboid(-12.0F, -4.0F, -2.0F, 12.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 2.0F));
		segmentCluster1.addChild("segment3", ModelPartBuilder.create().uv(0, 8).cuboid(-12.0F, 0.0F, -2.0F, 12.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 2.0F));
		ModelPartData segmentCluster2 = body.addChild("segmentCluster2", ModelPartBuilder.create(), ModelTransform.pivot(0.1F, 0.0F, 0.0F));
		segmentCluster2.addChild("segment2", ModelPartBuilder.create().uv(0, 16).cuboid(-12.0F, -4.0F, -2.0F, 12.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -2.0F));
		segmentCluster2.addChild("segment4", ModelPartBuilder.create().uv(0, 0).cuboid(-12.0F, 0.0F, -2.0F, 12.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -2.0F));
		return TexturedModelData.of(meshdefinition, 64, 64);
	}

	@Override
	public void setAngles(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		getPart().traverse().forEach(ModelPart::resetTransform);
		updateAnimation(entity.impaleAnimationState, BarnacleAnimation.IMPALE, ageInTicks, 1.0f);
		updateAnimation(entity.hurtAnimationState, BarnacleAnimation.HURT, ageInTicks, 1.0f);
		updateAnimation(entity.swimAnimationState, BarnacleAnimation.SWIM, ageInTicks, 1.0f);
	}

	@NotNull
	@Override
	public ModelPart getPart() {
		return root;
	}

}