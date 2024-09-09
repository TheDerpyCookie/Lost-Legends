package sabledream.studios.lostlegends.client.render.entity.feature;

import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.client.render.entity.model.TuffGolemEntityModel;
import sabledream.studios.lostlegends.entity.TuffGolemEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public final class TuffGolemClothFeatureRenderer extends FeatureRenderer<TuffGolemEntity, TuffGolemEntityModel<TuffGolemEntity>>
{
	public TuffGolemClothFeatureRenderer(FeatureRendererContext<TuffGolemEntity, TuffGolemEntityModel<TuffGolemEntity>> featureRendererContext) {
		super(featureRendererContext);
	}

	public void render(
		MatrixStack matrixStack,
		VertexConsumerProvider vertexConsumerProvider,
		int light,
		TuffGolemEntity tuffGolem,
		float f,
		float g,
		float h,
		float j,
		float k,
		float l
	) {
		if (tuffGolem.isInvisible()) {
			return;
		}

		Identifier identifier = LostLegends.makeID("textures/entity/tuff_golem/" + tuffGolem.getColor().getName() + ".png");

		renderModel(
			this.getContextModel(),
			identifier,
			matrixStack,
			vertexConsumerProvider,
			light,
			tuffGolem,
			-1
		);
	}
}

