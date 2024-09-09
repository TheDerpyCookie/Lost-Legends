package sabledream.studios.lostlegends.client.render.entity.renderer;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.client.render.entity.model.BarnacleEntityModel;
import sabledream.studios.lostlegends.client.render.entity.model.PenguinEntityModel;
import sabledream.studios.lostlegends.entity.BarnacleEntity;
import sabledream.studios.lostlegends.entity.PenguinEntity;
import sabledream.studios.lostlegends.init.LostLegendsEntityModelLayer;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public final class PenguinEntityRenderer extends MobEntityRenderer<PenguinEntity, PenguinEntityModel<PenguinEntity>>
{
	private static final float SHADOW_RADIUS = 0.5F;

	public PenguinEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new PenguinEntityModel<>(context.getPart(LostLegendsEntityModelLayer.PENGUIN_LAYER)), SHADOW_RADIUS);
	}

	@Override
	public Identifier getTexture(PenguinEntity penguin) {
		return LostLegends.makeID("textures/entity/penguin/penguin.png");
	}
}