package sabledream.studios.lostlegends.init;

import sabledream.studios.lostlegends.client.render.entity.renderer.SkeletonWolfRenderer;
import sabledream.studios.lostlegends.client.render.entity.renderer.*;
import sabledream.studios.lostlegends.entity.PlayerIllusionEntity;
import sabledream.studios.lostlegends.entity.projectile.E2JThrowableItemProjectile;
import sabledream.studios.lostlegends.platform.RegistryHelper;
import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.client.util.SkinTextures;

import java.util.Map;

/**
 * @see EntityRenderers
 */
@Environment(EnvType.CLIENT)
public final class LostLegendsEntityRenderer
{

	private static final Map<SkinTextures.Model, EntityRendererFactory<PlayerIllusionEntity>> PLAYER_ILLUSION_RENDERER_FACTORIES = Map.of(SkinTextures.Model.WIDE, (context) -> {
		return new PlayerIllusionEntityRenderer(context, false);
	}, SkinTextures.Model.SLIM, (context) -> {
		return new PlayerIllusionEntityRenderer(context, true);
	});

	public static void postInit() {
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.BARNACLE, BarnacleEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.PENGUIN, PenguinEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.COPPER_GOLEM, CopperGolemEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.GLARE, GlareEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.ICEOLOGER, IceologerEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.ICE_CHUNK, IceologerIceChunkRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.MAULER, MaulerEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.MOOBLOOM, MoobloomEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.RASCAL, RascalEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.TUFF_GOLEM, TuffGolemEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.WILDFIRE, WildfireEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.CRAB, CrabEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.MEERKAT, MeerkatEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.CLUCKSHROOM, CluckshroomEntityRender::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.FANCYCHICKEN, FancyChickenEntityRender::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.TROPICALSLIME, TropicalSlimeRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.VULTURE, VultureRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.MOOLIP, MoolipEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.BAMBMOO, BambooEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.MOOBLOOM_CATUS, MoobloomCactusEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.MELON_GOLEM, MelonGolemRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.MELON_SEED_PROJECTILE, ThrownItemRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.FURNACE_GOLEM, FurnaceGolemRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.SKELETON_WOLF, SkeletonWolfRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.VILER_WITCH, VilerWitchRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.MUDDY_PIG, MuddyPigRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.OSTRICH, OstrichRender::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.FIREFLY, FireflyEntityRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.TUMBLEWEED, TumbleweedRenderer::new);
		RegistryHelper.registerEntityRenderer(LostLegendsEntityTypes.CUSTOM_SNOWBALL_ENTITY, ThrownItemRenderer::new);
	}

	public static Map<SkinTextures.Model, EntityRenderer<? extends PlayerIllusionEntity>> reloadPlayerIllusionRenderers(
		EntityRendererFactory.Context ctx
	) {
		ImmutableMap.Builder builder = ImmutableMap.builder();
		PLAYER_ILLUSION_RENDERER_FACTORIES.forEach((model, factory) -> {
			try {
				builder.put(model, factory.create(ctx));
			} catch (Exception exception) {
				throw new IllegalArgumentException("Failed to create player illusion model for " + model, exception);
			}
		});
		return builder.build();
	}

	private LostLegendsEntityRenderer() {
	}
}
