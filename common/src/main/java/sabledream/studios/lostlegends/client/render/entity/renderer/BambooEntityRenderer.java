package sabledream.studios.lostlegends.client.render.entity.renderer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.client.render.entity.feature.BambmooFeatureRenderer;
import sabledream.studios.lostlegends.client.render.entity.feature.MoobloomCactusFeatureRenderer;
import sabledream.studios.lostlegends.entity.BambmooEntity;
import sabledream.studios.lostlegends.entity.MoobloomCactusEntity;

public class BambooEntityRenderer extends MobEntityRenderer<BambmooEntity, CowEntityModel<BambmooEntity>>
{
public BambooEntityRenderer(EntityRendererFactory.Context context) {
	super(context, new CowEntityModel<>(context.getPart(EntityModelLayers.MOOSHROOM)), 0.7F);
	addFeature(new BambmooFeatureRenderer<>(this, context.getBlockRenderManager()));
}
	@Override
	public Identifier getTexture(BambmooEntity entity) {
		return LostLegends.makeID("textures/entity/moobloom/moobloom_bamboo.png");
	}

}