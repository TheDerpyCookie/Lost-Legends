package sabledream.studios.lostlegends.client.render.entity.renderer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;
import sabledream.studios.lostlegends.client.render.entity.feature.MoolipPinkDaisyFeatureRenderer;
import sabledream.studios.lostlegends.entity.MoobloomEntity;
import sabledream.studios.lostlegends.entity.MoolipEntity;

public class MoolipEntityRenderer extends MobEntityRenderer<MoolipEntity, CowEntityModel<MoolipEntity>>
{
public MoolipEntityRenderer(EntityRendererFactory.Context context) {
	super(context, new CowEntityModel<>(context.getPart(EntityModelLayers.MOOSHROOM)), 0.7F);
	addFeature(new MoolipPinkDaisyFeatureRenderer<>(this, context.getBlockRenderManager()));
}

public Identifier getTexture(MoolipEntity entity) {
	Identifier texture = TextureUtils.getTextureIdentifier("cow", "moolip");
	Identifier textureBlink = TextureUtils.getTextureIdentifier("cow", "moolip", "blink");
	return entity.blinkManager.getBlinkRemainingTicks() > 0 ? textureBlink : texture;
}
}