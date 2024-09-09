package sabledream.studios.lostlegends.client.render.entity.feature;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.IronGolemEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import sabledream.studios.lostlegends.entity.FurnaceGolemEntity;

import java.text.MessageFormat;

public class FurnaceGolemFlamesFeatureRenderer extends FeatureRenderer<FurnaceGolemEntity, IronGolemEntityModel<FurnaceGolemEntity>>
{

	private static final int ANIMATION_FRAMES = 6;
	private static final float ANIMATION_TIME = 6.0F;
	private int currentFrame = 0;

	public FurnaceGolemFlamesFeatureRenderer(FeatureRendererContext<FurnaceGolemEntity, IronGolemEntityModel<FurnaceGolemEntity>> featureRendererContext) {
		super(featureRendererContext);
	}
@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, FurnaceGolemEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		if (!entity.isInvisible() && entity.isAngry()) {
			currentFrame = (int) (Math.floor(entity.age / ANIMATION_TIME) % ANIMATION_FRAMES);
			String frameLocation = MessageFormat.format("lostlegends:textures/entity/iron_golem/furnace_golem/furnace_golem_flames_layer_anim_{0}.png", (currentFrame + 1));
			Identifier identifier = Identifier.of(frameLocation);
			renderModel(getContextModel(), identifier, matrices, vertexConsumers, light, entity, 0xFFFFFFFF);
		}
	}
}