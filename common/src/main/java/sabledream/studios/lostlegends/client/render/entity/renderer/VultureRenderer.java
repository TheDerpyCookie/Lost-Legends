package sabledream.studios.lostlegends.client.render.entity.renderer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.client.render.entity.model.VultureModel;
import sabledream.studios.lostlegends.entity.Vulture;
import sabledream.studios.lostlegends.init.LostLegendsEntityModelLayer;

public class VultureRenderer<T extends Vulture> extends MobEntityRenderer<T, VultureModel<T>>
{

	private static final Identifier TEXTURE =Identifier.of(LostLegends.MOD_ID, "textures/entity/vulture/vulture.png");

	public VultureRenderer(EntityRendererFactory.Context p_174304_) {
		super(p_174304_, new VultureModel<>(p_174304_.getPart(LostLegendsEntityModelLayer.VULTURE_LAYER)), 0.5F);
	}

	@Override
	public Identifier getTexture(T p_114482_) {
		return TEXTURE;
	}

	@Override
	protected void scale(T p_115314_, MatrixStack p_115315_, float p_115316_) {
		super.scale(p_115314_, p_115315_, p_115316_);
		p_115315_.scale(p_115314_.getScale(), p_115314_.getScale(), p_115314_.getScale());
	}
}