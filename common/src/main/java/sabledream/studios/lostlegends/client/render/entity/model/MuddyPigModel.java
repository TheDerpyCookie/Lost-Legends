package sabledream.studios.lostlegends.client.render.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.PigEntityModel;
import sabledream.studios.lostlegends.entity.MuddyPigEntity;

public class MuddyPigModel<T extends MuddyPigEntity> extends PigEntityModel<T>
{

	public MuddyPigModel(ModelPart root) {
		super(root);
	}

	public static TexturedModelData getTexturedModelData() {
		float mudBoxX = -1.0F;
		float mudBoxY = -5.0F;
		float mudBoxZ = -7.0F;
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create()
				.uv(0, 0).cuboid(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F)
				.uv(16, 16).cuboid(-2.0F, 0.0F, -9.0F, 4.0F, 3.0F, 1.0F)
				.uv(24, 0).cuboid(mudBoxX, mudBoxY, mudBoxZ, 4.0F, 1.0F, 4.0F)
				.uv(40, 0).cuboid(mudBoxX, mudBoxY - 6.0F, mudBoxZ + 2.0F, 4.0F, 6.0F, 1.0F),
			ModelTransform.pivot(0.0F, 12.0F, -6.0F));
 modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(28, 8).cuboid(-5.0F, -10.0F, -7.0F, 10.0F, 16.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 11.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

 modelPartData.addChild(EntityModelPartNames.RIGHT_HIND_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 18.0F, 7.0F));

modelPartData.addChild(EntityModelPartNames.LEFT_HIND_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, 18.0F, 7.0F));

modelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 18.0F, -5.0F));

modelPartData.addChild(EntityModelPartNames.LEFT_FRONT_LEG, ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, 18.0F, -5.0F));

		return TexturedModelData.of(modelData, 64, 32);
}
	
	

	public void animateModel(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
		head.roll = entityIn.getShakeAngle(partialTick, -0.07F);
		head.pitch = (float) Math.PI / 8.0F;
		body.roll = entityIn.getShakeAngle(partialTick, -0.14F);
	}
}