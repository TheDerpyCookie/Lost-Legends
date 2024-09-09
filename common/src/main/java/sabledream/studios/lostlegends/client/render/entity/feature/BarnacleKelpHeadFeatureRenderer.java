package sabledream.studios.lostlegends.client.render.entity.feature;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.client.render.entity.model.BarnacleEntityModel;
import sabledream.studios.lostlegends.entity.BarnacleEntity;

@Environment(EnvType.CLIENT)
public final class BarnacleKelpHeadFeatureRenderer extends FeatureRenderer<BarnacleEntity, BarnacleEntityModel<BarnacleEntity>>
{
	public BarnacleKelpHeadFeatureRenderer(FeatureRendererContext<BarnacleEntity, BarnacleEntityModel<BarnacleEntity>> featureRendererContext) {
		super(featureRendererContext);
	}

	public void render(
		MatrixStack matrixStack,
		VertexConsumerProvider vertexConsumerProvider,
		int light,
		BarnacleEntity barnacle,
		float f,
		float g,
		float h,
		float j,
		float k,
		float l
	) {
		if (barnacle.isInvisible()) {
			return;
		}

		Identifier identifier = LostLegends.makeID("textures/entity/barnacle/barnacle_kelp.png");

renderModel(
	this.getContextModel(),
	identifier,
	matrixStack,
	vertexConsumerProvider,
	light,
	barnacle,
	1.0F,
	1.0F,
	1.0F
);
	}
	private void renderModel(BarnacleEntityModel<BarnacleEntity> model, Identifier identifier, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, BarnacleEntity barnacle, float v, float v1, float v2) {
	}
}

