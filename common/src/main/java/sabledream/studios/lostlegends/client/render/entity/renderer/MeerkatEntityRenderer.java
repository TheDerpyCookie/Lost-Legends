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
import sabledream.studios.lostlegends.client.render.entity.model.CrabEntityModel;
import sabledream.studios.lostlegends.client.render.entity.model.MeerkatModel;
import sabledream.studios.lostlegends.client.render.entity.model.PenguinEntityModel;
import sabledream.studios.lostlegends.entity.BarnacleEntity;
import sabledream.studios.lostlegends.entity.CrabEntity;
import sabledream.studios.lostlegends.entity.Meerkat;
import sabledream.studios.lostlegends.entity.PenguinEntity;
import sabledream.studios.lostlegends.init.LostLegendsEntityModelLayer;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public final class MeerkatEntityRenderer extends MobEntityRenderer<Meerkat, MeerkatModel<Meerkat>>
{
	private static final float SHADOW_RADIUS = 0.35F;

	public MeerkatEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new MeerkatModel<>(context.getPart(LostLegendsEntityModelLayer.MEERKAT_LAYER)), SHADOW_RADIUS);
	}

	@Override
	public Identifier getTexture(Meerkat penguin) {
		return LostLegends.makeID("textures/entity/meerkat/meerkat.png");
	}
}