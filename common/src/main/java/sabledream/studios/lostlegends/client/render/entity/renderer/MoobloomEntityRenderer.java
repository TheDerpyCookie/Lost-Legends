package sabledream.studios.lostlegends.client.render.entity.renderer;

import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.client.render.entity.feature.MoobloomFlowerFeatureRenderer;
import sabledream.studios.lostlegends.entity.MoobloomEntity;
import sabledream.studios.lostlegends.init.LostLegendsEntityModelLayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public final class MoobloomEntityRenderer extends MobEntityRenderer<MoobloomEntity, CowEntityModel<MoobloomEntity>>
{
	public MoobloomEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CowEntityModel(context.getPart(LostLegendsEntityModelLayer.MOOBLOOM_LAYER)), 0.7F);
		this.addFeature(new MoobloomFlowerFeatureRenderer(this));
	}

	@Override
	public Identifier getTexture(MoobloomEntity entity) {
		return LostLegends.makeID("textures/entity/moobloom/moobloom_" + entity.getVariant().getName() + ".png");
	}
}