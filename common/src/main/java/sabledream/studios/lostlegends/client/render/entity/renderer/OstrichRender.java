package sabledream.studios.lostlegends.client.render.entity.renderer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.client.render.entity.model.OstrichModel;
import sabledream.studios.lostlegends.entity.Ostrich;
import sabledream.studios.lostlegends.init.LostLegendsEntityModelLayer;

public class OstrichRender<T extends Ostrich> extends MobEntityRenderer<T, OstrichModel<T>>
{

	private static final Identifier TEXTURE =  Identifier.of(LostLegends.MOD_ID, "textures/entity/ostrich/ostrich.png");
	private static final Identifier ANGRY_TEXTURE =  Identifier.of(LostLegends.MOD_ID, "textures/entity/ostrich/ostrich_angry.png");

	public OstrichRender(EntityRendererFactory.Context p_174304_) {
		super(p_174304_, new OstrichModel<>(p_174304_.getPart(LostLegendsEntityModelLayer.OSTRICH)), 0.5F);
	}

	@Override
	public Identifier getTexture(T p_114482_) {
		return p_114482_.hasAngerTime() ? ANGRY_TEXTURE : TEXTURE;
	}

	@Override
	protected void scale(T p_115314_, MatrixStack p_115315_, float p_115316_) {
		super.scale(p_115314_, p_115315_, p_115316_);
		p_115315_.scale(p_115314_.getScale(), p_115314_.getScale(), p_115314_.getScale());
	}
}