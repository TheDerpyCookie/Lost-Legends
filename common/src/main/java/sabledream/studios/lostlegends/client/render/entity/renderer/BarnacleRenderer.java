package sabledream.studios.lostlegends.client.render.entity.renderer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.NotNull;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.client.render.entity.model.BarnacleModel;
import sabledream.studios.lostlegends.entity.Barnacle;
import sabledream.studios.lostlegends.init.LostLegendsEntityModelLayer;

public class BarnacleRenderer<T extends Barnacle> extends MobEntityRenderer<T, BarnacleModel<T>>
{
	private static final Identifier BARNACLE_LOCATION = Identifier.of(LostLegends.MOD_ID, "textures/entity/barnac/barnacle.png");

	public BarnacleRenderer(EntityRendererFactory.Context context) {
		super(context, new BarnacleModel<>(context.getPart(LostLegendsEntityModelLayer.BARNACLEE)), 0.7F);
	}

	@NotNull
	@Override
	public Identifier getTexture(@NotNull T entity) {
		return BARNACLE_LOCATION;
	}

	@Override
	protected void scale(@NotNull T entity, @NotNull MatrixStack poseStack, float $$2) {
		poseStack.scale(1.5f, 1.5f, 1.5f);
	}

	@Override
	protected void setupTransforms(T entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta, float scale) {
		if (entity.deathTime > 0) {
			float $$5 = ((float) entity.deathTime - 1.0F) / 20.0F * 1.6F;
			$$5 = MathHelper.sqrt($$5);
			if ($$5 > 1.0F) $$5 = 1.0F;
			matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees($$5 * getLyingAngle(entity)));
		} else if (shouldFlipUpsideDown(entity)) {
			matrices.translate(0.0F, entity.getHeight() + 0.1F, 0.0F);
			matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0F));
		}
		float f = MathHelper.lerp(scale, entity.prevTiltAngle, entity.tiltAngle);
		float f1 = MathHelper.lerp(scale, entity.prevRollAngle, entity.rollAngle);
		float f2 = MathHelper.lerp(scale, entity.prevBodyYaw, entity.bodyYaw);
		matrices.translate(0.0F, 0.5F, 0.0F);
		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - f2));
		matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(f));
		matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(f1));
		matrices.translate(0.0F, -1.2F, 0.0F);
	}


}