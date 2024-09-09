package sabledream.studios.lostlegends.client.render.entity.renderer;

import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.client.render.entity.feature.TuffGolemClosedEyesRenderer;
import sabledream.studios.lostlegends.client.render.entity.feature.TuffGolemClothFeatureRenderer;
import sabledream.studios.lostlegends.client.render.entity.feature.TuffGolemHeldItemFeatureRenderer;
import sabledream.studios.lostlegends.client.render.entity.model.TuffGolemEntityModel;
import sabledream.studios.lostlegends.entity.TuffGolemEntity;
import sabledream.studios.lostlegends.init.LostLegendsEntityModelLayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public final class TuffGolemEntityRenderer extends MobEntityRenderer<TuffGolemEntity, TuffGolemEntityModel<TuffGolemEntity>>
{
	public TuffGolemEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new TuffGolemEntityModel(context.getPart(LostLegendsEntityModelLayer.TUFF_GOLEM_LAYER)), 0.3F);
		this.addFeature(new TuffGolemClosedEyesRenderer(this));
		this.addFeature(new TuffGolemClothFeatureRenderer(this));
		this.addFeature(new TuffGolemHeldItemFeatureRenderer(this, context.getHeldItemRenderer(), context.getItemRenderer()));
	}

	@Override
	public Identifier getTexture(TuffGolemEntity entity) {
		return LostLegends.makeID("textures/entity/tuff_golem/tuff_golem.png");
	}
}