package sabledream.studios.lostlegends.client.render.entity.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.client.render.entity.model.CrabEntityModel;
import sabledream.studios.lostlegends.entity.CrabEntity;
import sabledream.studios.lostlegends.init.LostLegendsEntityModelLayer;

public class CrabEntityRenderer extends MobEntityRenderer<CrabEntity, CrabEntityModel<CrabEntity>>
{
	private static final float SHADOW_RADIUS = 0.5F;

	public CrabEntityRenderer(EntityRendererFactory.Context context){
		super(context, new CrabEntityModel<>(context.getPart(LostLegendsEntityModelLayer.CRAB_LAYER)), SHADOW_RADIUS);
	}

	@Override
	public Identifier getTexture(CrabEntity entity){
		return LostLegends.makeID("textures/entity/crab/crab.png");
	}

	@Override
	public void render(
		CrabEntity crab,
		float f,
		float tickDelta,
		MatrixStack matrixStack,
		VertexConsumerProvider vertexConsumerProvider,
		int i
	){
		this.shadowRadius = SHADOW_RADIUS * crab.getSize().getScaleModifier();

		if (crab.isBaby()) {
			this.shadowRadius *= 0.5F;
		}
		super.render(crab, f, tickDelta, matrixStack, vertexConsumerProvider, i);
	}

	@Override
	protected void scale(CrabEntity crab, MatrixStack matrixStack, float f){
		CrabEntity.CrabSize size = crab.getSize();
		float scaleModifier = size.getScaleModifier();
		matrixStack.scale(scaleModifier,scaleModifier,scaleModifier);

		if(crab.isBaby()) {
			matrixStack.scale(scaleModifier * 0.3F, scaleModifier * 0.3F, scaleModifier * 0.3F);
		}else{
			matrixStack.scale(scaleModifier,scaleModifier,scaleModifier);
		}
	}


}
