package sabledream.studios.lostlegends.client.render.entity.renderer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.client.render.entity.feature.MoobloomCactusFeatureRenderer;
import sabledream.studios.lostlegends.client.render.entity.feature.MoolipPinkDaisyFeatureRenderer;
import sabledream.studios.lostlegends.entity.MoobloomCactusEntity;
import sabledream.studios.lostlegends.entity.MoolipEntity;
import sabledream.studios.lostlegends.entity.RascalEntity;

public class MoobloomCactusEntityRenderer extends MobEntityRenderer<MoobloomCactusEntity, CowEntityModel<MoobloomCactusEntity>>
{
public MoobloomCactusEntityRenderer(EntityRendererFactory.Context context) {
	super(context, new CowEntityModel<>(context.getPart(EntityModelLayers.MOOSHROOM)), 0.7F);
	addFeature(new MoobloomCactusFeatureRenderer<>(this, context.getBlockRenderManager()));
}
	@Override
	public Identifier getTexture(MoobloomCactusEntity entity) {
		return LostLegends.makeID("textures/entity/moobloom/moobloom_cactus.png");
	}

}