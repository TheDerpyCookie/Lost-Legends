package sabledream.studios.lostlegends.client.render.entity.renderer;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.client.render.entity.feature.BarnacleKelpFeatureRenderer;
import sabledream.studios.lostlegends.client.render.entity.feature.BarnacleKelpHeadFeatureRenderer;
import sabledream.studios.lostlegends.client.render.entity.model.BarnacleEntityModel;
import sabledream.studios.lostlegends.entity.BarnacleEntity;
import sabledream.studios.lostlegends.init.LostLegendsEntityModelLayer;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public final class BarnacleEntityRenderer extends MobEntityRenderer<BarnacleEntity, BarnacleEntityModel<BarnacleEntity>>
{
	public static final float SCALE = 1.6F;

	public BarnacleEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new BarnacleEntityModel<>(context.getPart(LostLegendsEntityModelLayer.BARNACLE_LAYER)), 0.5F);
		this.addFeature(new BarnacleKelpFeatureRenderer(this));
		this.addFeature(new BarnacleKelpHeadFeatureRenderer(this));
	}

	@Override
	public Identifier getTexture(BarnacleEntity entity) {
		return LostLegends.makeID("textures/entity/barnacle/barnacle.png");
	}

	@Override
	protected void scale(BarnacleEntity barnacle, MatrixStack matrixStack, float f) {
		matrixStack.scale(SCALE, SCALE, SCALE);
	}
}
