package sabledream.studios.lostlegends.init;

import net.minecraft.block.Blocks;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import org.apache.http.client.entity.EntityBuilder;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.client.render.entity.renderer.BarnacleEntityRenderer;
import sabledream.studios.lostlegends.client.render.entity.renderer.WildfireEntityRenderer;
import sabledream.studios.lostlegends.entity.*;
import sabledream.studios.lostlegends.entity.projectile.MelonSeedProjectileEntity;
import sabledream.studios.lostlegends.events.lifecycle.AddSpawnBiomeModificationsEvent;
import sabledream.studios.lostlegends.mixin.SpawnRestrictionAccessor;
import sabledream.studios.lostlegends.platform.CustomSpawnGroup;
import sabledream.studios.lostlegends.platform.RegistryHelper;
import sabledream.studios.lostlegends.tag.LostLegendsTags;
import net.minecraft.SharedConstants;
import net.minecraft.world.Heightmap;

import java.util.function.Supplier;

/**
 * @see EntityType
 */
public final class LostLegendsEntityTypes
{
	public static boolean previousUseChoiceTypeRegistrations = SharedConstants.useChoiceTypeRegistrations;

	public static final Supplier<EntityType<CopperGolemEntity>> COPPER_GOLEM;
	public static final Supplier<EntityType<GlareEntity>> GLARE;
	public static final Supplier<EntityType<IceologerEntity>> ICEOLOGER;
	public static final Supplier<EntityType<IceologerIceChunkEntity>> ICE_CHUNK;
	public static final Supplier<EntityType<MaulerEntity>> MAULER;
	public static final Supplier<EntityType<MoobloomEntity>> MOOBLOOM;
	public static final Supplier<EntityType<RascalEntity>> RASCAL;
	public static final Supplier<EntityType<TuffGolemEntity>> TUFF_GOLEM;
	public static final Supplier<EntityType<WildfireEntity>> WILDFIRE;
	public static final Supplier<EntityType<PlayerIllusionEntity>> PLAYER_ILLUSION;
	public static final Supplier<EntityType<CrabEntity>> CRAB;
	public static final Supplier<EntityType<BarnacleEntity>> BARNACLE;
	public static final Supplier<EntityType<PenguinEntity>> PENGUIN;
	public static final Supplier<EntityType<Meerkat>> MEERKAT;
	public static final Supplier<EntityType<CluckshroomEntity>> CLUCKSHROOM;
	public static final Supplier<EntityType<FancyChickenEntity>> FANCYCHICKEN;
	public static final Supplier<EntityType<TropicalSlimeEntity>> TROPICALSLIME;
	public static final Supplier<EntityType<Vulture>> VULTURE;
	public static final Supplier<EntityType<MoolipEntity>> MOOLIP;
	public static final Supplier<EntityType<MoobloomCactusEntity>> MOOBLOOM_CATUS;
	public static final Supplier<EntityType<BambmooEntity>> BAMBMOO;
	public static final Supplier<EntityType<VilerWitchEntity>> VILER_WITCH;

	public static final Supplier<EntityType<MelonSeedProjectileEntity>> MELON_SEED_PROJECTILE;
	public static final Supplier<EntityType<MelonGolemEntity>> MELON_GOLEM;
	public static final Supplier<EntityType<FurnaceGolemEntity>> FURNACE_GOLEM;

	public static final Supplier<EntityType<SkeletonWolfEntity>> SKELETON_WOLF;
	public static final Supplier<EntityType<MuddyPigEntity>> MUDDY_PIG;

	public static final Supplier<EntityType<Ostrich>> OSTRICH;

	static {
		SharedConstants.useChoiceTypeRegistrations = false;
		COPPER_GOLEM = RegistryHelper.registerEntityType("copper_golem", () -> EntityType.Builder.create(CopperGolemEntity::new, SpawnGroup.MISC).dimensions(0.75F, 1.375F).maxTrackingRange(10).build(LostLegends.makeStringID("copper_golem")));
		GLARE = RegistryHelper.registerEntityType("glare", () -> EntityType.Builder.create(GlareEntity::new, CustomSpawnGroup.getGlaresCategory()).dimensions(0.875F, 1.1875F).maxTrackingRange(8).trackingTickInterval(2).build(LostLegends.makeStringID("glare")));
		ICEOLOGER = RegistryHelper.registerEntityType("iceologer", () -> EntityType.Builder.create(IceologerEntity::new, SpawnGroup.MONSTER).dimensions(0.6F, 1.95F).maxTrackingRange(10).build(LostLegends.makeStringID("iceologer")));
		ICE_CHUNK = RegistryHelper.registerEntityType("ice_chunk", () -> EntityType.Builder.create(IceologerIceChunkEntity::new, SpawnGroup.MISC).makeFireImmune().dimensions(2.5F, 1.0F).maxTrackingRange(6).build(LostLegends.makeStringID("ice_chunk")));
		MAULER = RegistryHelper.registerEntityType("mauler", () -> EntityType.Builder.create(MaulerEntity::new, SpawnGroup.CREATURE).dimensions(0.5625F, 0.5625F).maxTrackingRange(10).build(LostLegends.makeStringID("mauler")));
		MOOBLOOM = RegistryHelper.registerEntityType("moobloom", () -> EntityType.Builder.create(MoobloomEntity::new, SpawnGroup.CREATURE).dimensions(0.9F, 1.4F).maxTrackingRange(10).build(LostLegends.makeStringID("moobloom")));
		RASCAL = RegistryHelper.registerEntityType("rascal", () -> EntityType.Builder.create(RascalEntity::new, CustomSpawnGroup.getRascalsCategory()).dimensions(0.9F, 1.25F).maxTrackingRange(10).build(LostLegends.makeStringID("rascal")));
		TUFF_GOLEM = RegistryHelper.registerEntityType("tuff_golem", () -> EntityType.Builder.create(TuffGolemEntity::new, SpawnGroup.MISC).dimensions(0.75F, 1.0625F).maxTrackingRange(10).build(LostLegends.makeStringID("tuff_golem")));
		WILDFIRE = RegistryHelper.registerEntityType("wildfire", () -> EntityType.Builder.create(WildfireEntity::new, SpawnGroup.MONSTER).dimensions(0.7F * WildfireEntityRenderer.SCALE, 1.875F * WildfireEntityRenderer.SCALE).maxTrackingRange(10).makeFireImmune().build(LostLegends.makeStringID("wildfire")));
		PLAYER_ILLUSION = RegistryHelper.registerEntityType("player_illusion", () -> EntityType.Builder.create(PlayerIllusionEntity::new, SpawnGroup.MISC).dimensions(0.7F, 1.875F).maxTrackingRange(10).makeFireImmune().build(LostLegends.makeStringID("player_illusion")));
		CRAB = RegistryHelper.registerEntityType("crab", () -> EntityType.Builder.create(CrabEntity::new, SpawnGroup.CREATURE).dimensions(0.875F, 0.5625F).maxTrackingRange(10).build(LostLegends.makeStringID("crab")));
		BARNACLE = RegistryHelper.registerEntityType("barnacle", () -> EntityType.Builder.create(BarnacleEntity::new, SpawnGroup.MONSTER).dimensions(1.69125F * BarnacleEntityRenderer.SCALE, 0.75F * BarnacleEntityRenderer.SCALE).maxTrackingRange(10).build(LostLegends.makeStringID("barnacle")));
		PENGUIN = RegistryHelper.registerEntityType("penguin", () -> EntityType.Builder.create(PenguinEntity::new, SpawnGroup.CREATURE).dimensions(0.875F, 1.1875F).maxTrackingRange(8).trackingTickInterval(2).build(LostLegends.makeStringID("penguin")));
		MEERKAT = RegistryHelper.registerEntityType("meerkat", () -> EntityType.Builder.create(Meerkat::new, SpawnGroup.CREATURE).dimensions(0.6F, 0.7F).maxTrackingRange(8).build(LostLegends.makeStringID("meerkat")));
		CLUCKSHROOM = RegistryHelper.registerEntityType("cluckshroom", () -> EntityType.Builder.create(CluckshroomEntity::new, SpawnGroup.CREATURE).dimensions(EntityType.CHICKEN.getWidth(),EntityType.CHICKEN.getHeight()).maxTrackingRange(10).build(LostLegends.makeStringID("cluckshroom")));
		FANCYCHICKEN = RegistryHelper.registerEntityType("fancychicken", () -> EntityType.Builder.create(FancyChickenEntity::new, SpawnGroup.CREATURE).dimensions(EntityType.CHICKEN.getWidth(),EntityType.CHICKEN.getHeight()).maxTrackingRange(10).build(LostLegends.makeStringID("fancychicken")));
		TROPICALSLIME = RegistryHelper.registerEntityType("tropical_slime", () -> EntityType.Builder.create(TropicalSlimeEntity::new, SpawnGroup.CREATURE).dimensions(2.04F,2.04F).makeFireImmune().maxTrackingRange(10).build(LostLegends.makeStringID("tropical_slime")));
		VULTURE = RegistryHelper.registerEntityType("vulture", () -> EntityType.Builder.create(Vulture::new, SpawnGroup.CREATURE).dimensions(0.85F, 0.85F).maxTrackingRange(8).build(LostLegends.makeStringID("vulture")));
		MOOLIP = RegistryHelper.registerEntityType("moolip", () -> EntityType.Builder.create(MoolipEntity::new, SpawnGroup.CREATURE).dimensions(EntityType.COW.getWidth(), EntityType.COW.getHeight()).maxTrackingRange(10).build(LostLegends.makeStringID("moolip")));
		MOOBLOOM_CATUS = RegistryHelper.registerEntityType("moobloom_cactus", () -> EntityType.Builder.create(MoobloomCactusEntity::new, SpawnGroup.CREATURE).dimensions(0.9F, 1.4F).maxTrackingRange(10).build(LostLegends.makeStringID("moobloom_cactus")));
		BAMBMOO = RegistryHelper.registerEntityType("bambmoo", () -> EntityType.Builder.create(BambmooEntity::new, SpawnGroup.CREATURE).dimensions(0.9F, 1.4F).maxTrackingRange(10).build(LostLegends.makeStringID("bambmoo")));
		MELON_SEED_PROJECTILE = RegistryHelper.registerEntityType("melon_seed_projectile", () -> EntityType.Builder.<MelonSeedProjectileEntity>create(MelonSeedProjectileEntity::new, SpawnGroup.MISC).dimensions(0.25F, 0.25F).maxTrackingRange(4).build(LostLegends.makeStringID("melon_seed_projectile")));
		MELON_GOLEM=RegistryHelper.registerEntityType("melon_golem", () -> EntityType.Builder.create(MelonGolemEntity::new, SpawnGroup.MISC).dimensions(EntityType.SNOW_GOLEM.getWidth(),EntityType.SNOW_GOLEM.getHeight()).allowSpawningInside(Blocks.POWDER_SNOW).maxTrackingRange(8).build(LostLegends.makeStringID("melon_golem")));
		FURNACE_GOLEM=RegistryHelper.registerEntityType("furnace_golem", () -> EntityType.Builder.create(FurnaceGolemEntity::new, SpawnGroup.MISC).dimensions(EntityType.IRON_GOLEM.getWidth(),EntityType.IRON_GOLEM.getHeight()).maxTrackingRange(8).makeFireImmune().build(LostLegends.makeStringID("furnace_golem")));
		SKELETON_WOLF = RegistryHelper.registerEntityType("skeleton_wolf", () -> EntityType.Builder.create(SkeletonWolfEntity::new, SpawnGroup.MONSTER).dimensions(EntityType.WOLF.getWidth(), EntityType.WOLF.getHeight()).maxTrackingRange(10).build(LostLegends.makeStringID("skeleton_wolf")));
		VILER_WITCH = RegistryHelper.registerEntityType("viler_witch", () -> EntityType.Builder.create(VilerWitchEntity::new, SpawnGroup.MONSTER).dimensions(EntityType.WITCH.getWidth(), EntityType.WITCH.getHeight()).maxTrackingRange(8).build(LostLegends.makeStringID("viler_witch")));
		MUDDY_PIG = RegistryHelper.registerEntityType("muddy_pig", () -> EntityType.Builder.create(MuddyPigEntity::new, SpawnGroup.CREATURE).dimensions(EntityType.PIG.getWidth(), EntityType.PIG.getHeight()).maxTrackingRange(10).build(LostLegends.makeStringID("muddy_pig")));
		OSTRICH = RegistryHelper.registerEntityType("ostrich", ()-> EntityType.Builder.create(Ostrich::new, SpawnGroup.CREATURE).dimensions(0.9F, 2.5F).maxTrackingRange(8).build(LostLegends.makeStringID("ostrich")));

		SharedConstants.useChoiceTypeRegistrations = previousUseChoiceTypeRegistrations;
	}

	public static void init() {
		createMobAttributes();
	}

	public static void postInit() {
		initSpawnRestrictions();
	}

	public static void createMobAttributes() {
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.COPPER_GOLEM, CopperGolemEntity::createAttributes);
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.GLARE, GlareEntity::createAttributes);
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.ICEOLOGER, IceologerEntity::createAttributes);
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.MAULER, MaulerEntity::createAttributes);
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.MOOBLOOM, MoobloomEntity::createCowAttributes);
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.RASCAL, RascalEntity::createAttributes);
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.TUFF_GOLEM, TuffGolemEntity::createAttributes);
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.WILDFIRE, WildfireEntity::createAttributes);
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.PLAYER_ILLUSION, PlayerIllusionEntity::createMobAttributes);
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.CRAB, CrabEntity::createCrabAttributes);
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.PENGUIN, PenguinEntity::createPenguinAttributes);
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.BARNACLE, BarnacleEntity::createBarnacleAttributes);
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.MEERKAT, Meerkat::createAttributes);
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.CLUCKSHROOM, CluckshroomEntity::createChickenAttributes);
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.FANCYCHICKEN, FancyChickenEntity::createChickenAttributes);
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.TROPICALSLIME, TropicalSlimeEntity::createHostileAttributes);
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.VULTURE, Vulture::createAttributes);
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.MOOLIP, MoolipEntity::createCowAttributes);
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.MOOBLOOM_CATUS, MoolipEntity::createCowAttributes);
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.BAMBMOO, MoolipEntity::createCowAttributes);
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.MELON_GOLEM, MelonGolemEntity::createMelonGolemAttributes);
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.FURNACE_GOLEM, FurnaceGolemEntity::createIronGolemAttributes);
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.SKELETON_WOLF, SkeletonWolfEntity::createSkeletonWolfAttributes);
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.VILER_WITCH, WitchEntity::createWitchAttributes);
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.MUDDY_PIG, E2JBasePigEntity::createPigAttributes);
		RegistryHelper.registerEntityAttribute(LostLegendsEntityTypes.OSTRICH, Ostrich::createOstrichAttributes);
	}

	public static void initSpawnRestrictions() {
		SpawnRestrictionAccessor.callRegister(GLARE.get(), SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GlareEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(ICEOLOGER.get(), SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, IceologerEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(MAULER.get(), SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MaulerEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(MOOBLOOM.get(), SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MoobloomEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(RASCAL.get(), SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, RascalEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(CRAB.get(), SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CrabEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(PENGUIN.get(), SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PenguinEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(BARNACLE.get(), SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BarnacleEntity::canSpawn);

	}

	public static void addSpawnBiomeModifications(AddSpawnBiomeModificationsEvent event) {
		var config = LostLegends.getConfig();
		if (config.enableBarnacle && config.enableBarnacleSpawn) {
			event.add(LostLegendsTags.HAS_BARNACLE, SpawnGroup.MONSTER, BARNACLE.get(), config.barnacleSpawnWeight, config.barnacleSpawnMinGroupSize, config.barnacleSpawnMaxGroupSize);
		}
		if (config.enablePenguin && config.enablePenguinSpawn) {
			event.add(LostLegendsTags.HAS_PENGUIN, SpawnGroup.CREATURE, PENGUIN.get(), config.penguinSpawnWeight, config.penguinSpawnMinGroupSize, config.penguinSpawnMaxGroupSize);
		}

		if (config.enableGlare && config.enableGlareSpawn) {
			event.add(LostLegendsTags.HAS_GLARE, CustomSpawnGroup.getGlaresCategory(), GLARE.get(), config.glareSpawnWeight, config.glareSpawnMinGroupSize, config.glareSpawnMaxGroupSize);
		}

		if (config.enableMauler && config.enableMaulerSpawn) {
			event.add(LostLegendsTags.HAS_BADLANDS_MAULER, SpawnGroup.CREATURE, MAULER.get(), config.maulerBadlandsSpawnWeight, config.maulerBadlandsSpawnMinGroupSize, config.maulerBadlandsSpawnMaxGroupSize);
			event.add(LostLegendsTags.HAS_DESERT_MAULER, SpawnGroup.CREATURE, MAULER.get(), config.maulerDesertSpawnWeight, config.maulerDesertSpawnMinGroupSize, config.maulerDesertSpawnMaxGroupSize);
			event.add(LostLegendsTags.HAS_SAVANNA_MAULER, SpawnGroup.CREATURE, MAULER.get(), config.maulerSavannaSpawnWeight, config.maulerSavannaSpawnMinGroupSize, config.maulerSavannaSpawnMaxGroupSize);
		}

		if (config.enableMoobloom && config.enableMoobloomSpawn) {
			event.add(LostLegendsTags.HAS_MOOBLOOMS, SpawnGroup.CREATURE, MOOBLOOM.get(), config.moobloomSpawnWeight, config.moobloomSpawnMinGroupSize, config.moobloomSpawnMaxGroupSize);
		}

		if (config.enableRascal && config.enableRascalSpawn) {
			event.add(LostLegendsTags.HAS_RASCAL, CustomSpawnGroup.getRascalsCategory(), RASCAL.get(), 4, 1, 1);
		}
		if (config.enableCrab && config.enableCrabSpawn) {
			event.add(LostLegendsTags.HAS_LESS_FREQUENT_CRAB, SpawnGroup.CREATURE, CRAB.get(), config.crabLessFrequentSpawnWeight, 2, 2);
			event.add(LostLegendsTags.HAS_MORE_FREQUENT_CRAB, SpawnGroup.CREATURE, CRAB.get(), config.crabMoreFrequentSpawnWeight, 2, 2);
		}
	}

	private LostLegendsEntityTypes() {
	}
}
