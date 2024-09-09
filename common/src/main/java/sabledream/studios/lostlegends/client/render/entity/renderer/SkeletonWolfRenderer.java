package sabledream.studios.lostlegends.client.render.entity.renderer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;
import sabledream.studios.lostlegends.client.render.entity.model.SkeletonWolfModel;
import sabledream.studios.lostlegends.client.render.entity.renderer.TextureUtils;
import sabledream.studios.lostlegends.entity.SkeletonWolfEntity;

public class SkeletonWolfRenderer extends MobEntityRenderer<SkeletonWolfEntity, SkeletonWolfModel<SkeletonWolfEntity>>
{

	public SkeletonWolfRenderer(EntityRendererFactory.Context context) {
		super(context, new SkeletonWolfModel<>(context.getPart(EntityModelLayers.WOLF)), 0.5F);
	}

	protected float getAnimationProgress(SkeletonWolfEntity wolfEntity, float f) {
		return wolfEntity.getTailAngle();
	}

	public Identifier getTexture(SkeletonWolfEntity entity) {
		Identifier texture = TextureUtils.getTextureIdentifier("wolf", "skeleton_wolf");
		Identifier textureAngry = TextureUtils.getTextureIdentifier("wolf", "skeleton_wolf", "angry");
		return entity.hasAngerTime() ? textureAngry : texture;
	}

}