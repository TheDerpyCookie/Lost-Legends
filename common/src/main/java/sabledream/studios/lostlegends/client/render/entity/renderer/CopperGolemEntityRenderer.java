package sabledream.studios.lostlegends.client.render.entity.renderer;

import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.client.render.entity.model.CopperGolemEntityModel;
import sabledream.studios.lostlegends.entity.CopperGolemEntity;
import sabledream.studios.lostlegends.init.LostLegendsEntityModelLayer;
import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Oxidizable;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

import java.util.Map;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public final class CopperGolemEntityRenderer extends MobEntityRenderer<CopperGolemEntity, CopperGolemEntityModel<CopperGolemEntity>>
{
	private static final Map<Oxidizable.OxidationLevel, Identifier> OXIDATION_TO_TEXTURE_MAP;

	static {
		OXIDATION_TO_TEXTURE_MAP = ImmutableMap.of(
			Oxidizable.OxidationLevel.UNAFFECTED, LostLegends.makeID("textures/entity/copper_golem/copper_golem.png"),
			Oxidizable.OxidationLevel.EXPOSED, LostLegends.makeID("textures/entity/copper_golem/exposed_copper_golem.png"),
			Oxidizable.OxidationLevel.WEATHERED, LostLegends.makeID("textures/entity/copper_golem/weathered_copper_golem.png"),
			Oxidizable.OxidationLevel.OXIDIZED, LostLegends.makeID("textures/entity/copper_golem/oxidized_copper_golem.png")
		);
	}

	public CopperGolemEntityRenderer(EntityRendererFactory.Context context) {
		super(
			context,
			new CopperGolemEntityModel(context.getPart(LostLegendsEntityModelLayer.COPPER_GOLEM_LAYER)),
			0.35f
		);
	}

	@Override
	public Identifier getTexture(CopperGolemEntity entity) {
		return OXIDATION_TO_TEXTURE_MAP.get(entity.getOxidationLevel());
	}
}