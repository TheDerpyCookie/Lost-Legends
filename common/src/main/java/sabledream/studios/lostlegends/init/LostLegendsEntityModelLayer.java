package sabledream.studios.lostlegends.init;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.network.packet.s2c.play.EntitySetHeadYawS2CPacket;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.client.render.block.RainbowBedBlockEntityRenderer;
import sabledream.studios.lostlegends.client.render.entity.model.*;
import sabledream.studios.lostlegends.client.render.entity.renderer.BarnacleRenderer;
import sabledream.studios.lostlegends.entity.CrabEntity;
import sabledream.studios.lostlegends.entity.Meerkat;
import sabledream.studios.lostlegends.platform.RegistryHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @see EntityModelLayers
 */
@Environment(EnvType.CLIENT)
public final class LostLegendsEntityModelLayer
{
	public static final EntityModelLayer CRAB_LAYER;

	public static final EntityModelLayer MEERKAT_LAYER;
	public static final EntityModelLayer BARNACLE_LAYER;
	public static final EntityModelLayer COPPER_GOLEM_LAYER;
	public static final EntityModelLayer PENGUIN_LAYER;
	public static final EntityModelLayer GLARE_LAYER;
	public static final EntityModelLayer ICEOLOGER_LAYER;
	public static final EntityModelLayer ICEOLOGER_ICE_CHUNK_LAYER;
	public static final EntityModelLayer MAULER_LAYER;
	public static final EntityModelLayer MOOBLOOM_LAYER;
	public static final EntityModelLayer RASCAL_LAYER;
	public static final EntityModelLayer TUFF_GOLEM_LAYER;
	public static final EntityModelLayer WILDFIRE_LAYER;
	public static final EntityModelLayer PLAYER_ILLUSION_LAYER;
	public static final EntityModelLayer VULTURE_LAYER;
	public static final EntityModelLayer OSTRICH;
	public static final EntityModelLayer FIREFLY;


	public static final EntityModelLayer FANCY_CHICKEN;
	public static final EntityModelLayer VILER_WITCH_ENTITY_MODEL_LAYER;



	public static final EntityModelLayer RAINBOW_BED_HEAD_MODEL_LAYER;
	public static final EntityModelLayer RAINBOW_BED_FOOT_MODEL_LAYER;

	public static final EntityModelLayer MUDDY_PIG_ENTITY_MODEL_LAYER;

	public static final EntityModelLayer TUMBLEWEED;

	public static final EntityModelLayer BARNACLEE;


	static {
		FIREFLY = new EntityModelLayer(LostLegends.makeID("firefly"), "main");
		COPPER_GOLEM_LAYER = new EntityModelLayer(LostLegends.makeID("copper_golem"), "main");
		GLARE_LAYER = new EntityModelLayer(LostLegends.makeID("glare"), "main");
		ICEOLOGER_LAYER = new EntityModelLayer(LostLegends.makeID("iceologer"), "main");
		ICEOLOGER_ICE_CHUNK_LAYER = new EntityModelLayer(LostLegends.makeID("iceologer_ice_chunk"), "main");
		MAULER_LAYER = new EntityModelLayer(LostLegends.makeID("mauler"), "main");
		MOOBLOOM_LAYER = new EntityModelLayer(LostLegends.makeID("moobloom"), "main");
		RASCAL_LAYER = new EntityModelLayer(LostLegends.makeID("rascal"), "main");
		TUFF_GOLEM_LAYER = new EntityModelLayer(LostLegends.makeID("tuff_golem"), "main");
		WILDFIRE_LAYER = new EntityModelLayer(LostLegends.makeID("wildfire"), "main");
		PLAYER_ILLUSION_LAYER = new EntityModelLayer(LostLegends.makeID("player_illusion"), "main");
		CRAB_LAYER = new EntityModelLayer(LostLegends.makeID("crab"),"main");
		BARNACLE_LAYER = new EntityModelLayer(LostLegends.makeID("barnacle"), "main");
		PENGUIN_LAYER = new EntityModelLayer(LostLegends.makeID("penguin"), "main");
		MEERKAT_LAYER = new EntityModelLayer(LostLegends.makeID("meerkat"), "main");
		VULTURE_LAYER = new EntityModelLayer(LostLegends.makeID("vulture"), "main");
		FANCY_CHICKEN = new EntityModelLayer(LostLegends.makeID("fancy_chicken"), "main");
		RAINBOW_BED_FOOT_MODEL_LAYER = new EntityModelLayer(LostLegends.makeID("rainbow_bed"), "rainbow_bed_foot");
		RAINBOW_BED_HEAD_MODEL_LAYER = new EntityModelLayer(LostLegends.makeID("rainbow_bed"), "rainbow_bed_head");
		VILER_WITCH_ENTITY_MODEL_LAYER = new EntityModelLayer(LostLegends.makeID("viler_witch"),"main");
		MUDDY_PIG_ENTITY_MODEL_LAYER = new EntityModelLayer(LostLegends.makeID("muddy_pig"),"head");
		OSTRICH = new EntityModelLayer(LostLegends.makeID("ostrich"), "main");
		TUMBLEWEED = new EntityModelLayer(LostLegends.makeID("tumbleweed"), "main");
		BARNACLEE = new EntityModelLayer(LostLegends.makeID("barnaclee"), "main");
	}

	public static void init() {
		RegistryHelper.registerEntityModelLayer(COPPER_GOLEM_LAYER, CopperGolemEntityModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(GLARE_LAYER, GlareEntityModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(ICEOLOGER_LAYER, IllagerEntityModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(ICEOLOGER_ICE_CHUNK_LAYER, IceologerIceChunkModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(MAULER_LAYER, MaulerEntityModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(MOOBLOOM_LAYER, CowEntityModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(RASCAL_LAYER, RascalEntityModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(TUFF_GOLEM_LAYER, TuffGolemEntityModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(WILDFIRE_LAYER, WildfireEntityModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(CRAB_LAYER, CrabEntityModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(PENGUIN_LAYER, PenguinEntityModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(BARNACLE_LAYER, BarnacleEntityModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(MEERKAT_LAYER, MeerkatModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(VULTURE_LAYER, VultureModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(FANCY_CHICKEN, FancyChickenModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(RAINBOW_BED_HEAD_MODEL_LAYER, RainbowBedBlockEntityRenderer::createHeadLayer);
		RegistryHelper.registerEntityModelLayer(RAINBOW_BED_FOOT_MODEL_LAYER, RainbowBedBlockEntityRenderer::createFootLayer);
		RegistryHelper.registerEntityModelLayer(VILER_WITCH_ENTITY_MODEL_LAYER, VilerWitchModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(MUDDY_PIG_ENTITY_MODEL_LAYER, MuddyPigModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(OSTRICH, OstrichModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(FIREFLY, FireflyEntityModel::getTexturedModelData);
		RegistryHelper.registerEntityModelLayer(TUMBLEWEED, TumbleweedModel::createBodyLayer);
		RegistryHelper.registerEntityModelLayer(BARNACLEE,BarnacleModel::createBodyLayer );
	}

	private LostLegendsEntityModelLayer() {
	}
}
