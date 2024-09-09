package sabledream.studios.lostlegends.client.render.entity.renderer;

import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.client.render.entity.model.RascalEntityModel;
import sabledream.studios.lostlegends.entity.RascalEntity;
import sabledream.studios.lostlegends.init.LostLegendsEntityModelLayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public final class RascalEntityRenderer extends MobEntityRenderer<RascalEntity, RascalEntityModel<RascalEntity>>
{
	public RascalEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new RascalEntityModel<RascalEntity>(context.getPart(LostLegendsEntityModelLayer.RASCAL_LAYER)), 0.5F);
	}

	@Override
	public Identifier getTexture(RascalEntity entity) {
		return LostLegends.makeID("textures/entity/rascal/rascal.png");
	}
}
