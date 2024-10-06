package sabledream.studios.lostlegends.init;

import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import sabledream.studios.lostlegends.block.*;
import sabledream.studios.lostlegends.block.Oxidizable;
import sabledream.studios.lostlegends.item.api.axe.AxeBehaviors;
import sabledream.studios.lostlegends.platform.RegistryHelper;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.BlockSoundGroup;

import java.util.function.Supplier;

import static net.minecraft.block.MapColor.BROWN;
import static net.minecraft.block.MapColor.PALE_YELLOW;

/**
 * @see Blocks
 */
public final class LostLegendsBlocks
{
	public static final Supplier<Block> TUMBLEWEED;
	public static final Supplier<Block> POTTED_TUMBLEWEED_PLANT;
	public static final Supplier<Block> TUMBLEWEED_PLANT;
	public static final Supplier<Block> BUTTERCUP;
	public static final Supplier<Block> PINKDAISY;
	public static final Supplier<Block> POTTED_PINKDAISY;
	public static final Supplier<Block> POTTED_BUTTERCUP;
	public static final Supplier<Block> ACACIA_BEEHIVE;
	public static final Supplier<Block> BAMBOO_BEEHIVE;
	public static final Supplier<Block> BIRCH_BEEHIVE;
	public static final Supplier<Block> CHERRY_BEEHIVE;
	public static final Supplier<Block> CRIMSON_BEEHIVE;
	public static final Supplier<Block> DARK_OAK_BEEHIVE;
	public static final Supplier<Block> JUNGLE_BEEHIVE;
	public static final Supplier<Block> MANGROVE_BEEHIVE;
	public static final Supplier<Block> SPRUCE_BEEHIVE;
	public static final Supplier<Block> WARPED_BEEHIVE;
	public static final Supplier<Block> COPPER_BUTTON;
	public static final Supplier<Block> EXPOSED_COPPER_BUTTON;
	public static final Supplier<Block> WEATHERED_COPPER_BUTTON;
	public static final Supplier<Block> OXIDIZED_COPPER_BUTTON;
	public static final Supplier<Block> WAXED_COPPER_BUTTON;
	public static final Supplier<Block> WAXED_EXPOSED_COPPER_BUTTON;
	public static final Supplier<Block> WAXED_WEATHERED_COPPER_BUTTON;
	public static final Supplier<Block> WAXED_OXIDIZED_COPPER_BUTTON;
	public static final Supplier<Block> EXPOSED_LIGHTNING_ROD;
	public static final Supplier<Block> WEATHERED_LIGHTNING_ROD;
	public static final Supplier<Block> OXIDIZED_LIGHTNING_ROD;
	public static final Supplier<Block> WAXED_LIGHTNING_ROD;
	public static final Supplier<Block> WAXED_EXPOSED_LIGHTNING_ROD;
	public static final Supplier<Block> WAXED_WEATHERED_LIGHTNING_ROD;
	public static final Supplier<Block> WAXED_OXIDIZED_LIGHTNING_ROD;
	public static final Supplier<Block> CRAB_EGG;
	public static final Supplier<Block> OSTRICH_EGG;
	public static final Supplier<Block> BURROW;
	public static final Supplier<Block> TINYCACTUS;
	public static final Supplier<Block> POTTED_TINYCACTUS;
	public static final Supplier<Block> CARVED_MELON;
	public static final Supplier<Block> MELON_GOLEM_HEAD_BLINK;
	public static final Supplier<Block> MELON_GOLEM_HEAD_SHOOT;
	public static final Supplier<Block> MELON_LANTERN;
	public static final Supplier<Block> RAINBOW_BED;
	public static final Supplier<Block> RAINBOW_WOOL;
	public static final Supplier<Block> RAINBOW_CARPET;
	private static final AbstractBlock.Settings CARVED_MELON_SETTINGS = AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(1.0F).sounds(BlockSoundGroup.WOOD).allowsSpawning((state, world, pos, type) -> true).pistonBehavior(PistonBehavior.DESTROY);
	public static final Supplier<Block> TERMITE_MOUND;
	public static final Supplier<Block> HOLLOWED_MANGROVE_LOG;
	public static final Supplier<Block> STRIPPED_HOLLOWED_OAK_LOG;
	public static final Supplier<Block> STRIPPED_HOLLOWED_SPRUCE_LOG;
	public static final Supplier<Block> STRIPPED_HOLLOWED_BIRCH_LOG;
	public static final Supplier<Block> STRIPPED_HOLLOWED_CHERRY_LOG;
	public static final Supplier<Block> STRIPPED_HOLLOWED_JUNGLE_LOG;
	public static final Supplier<Block> STRIPPED_HOLLOWED_ACACIA_LOG;
	public static final Supplier<Block> STRIPPED_HOLLOWED_DARK_OAK_LOG;
	public static final Supplier<Block> STRIPPED_HOLLOWED_MANGROVE_LOG;
	public static final Supplier<Block> STRIPPED_HOLLOWED_CRIMSON_STEM;
	public static final Supplier<Block> STRIPPED_HOLLOWED_WARPED_STEM;
	public static final Supplier<Block> HOLLOWED_OAK_LOG;
	public static final Supplier<Block> HOLLOWED_SPRUCE_LOG;
	public static final Supplier<Block> HOLLOWED_BIRCH_LOG;
	public static final Supplier<Block> HOLLOWED_JUNGLE_LOG;
	public static final Supplier<Block> HOLLOWED_ACACIA_LOG;
	public static final Supplier<Block> HOLLOWED_DARK_OAK_LOG;
	public static final Supplier<Block> HOLLOWED_CHERRY_LOG;
	public static final Supplier<Block> HOLLOWED_WARPED_STEM;
	public static final Supplier<Block> HOLLOWED_CRIMSON_STEM;

	public static final Supplier<Block> SAWMILL_BLOCK;



	static {
		SAWMILL_BLOCK = RegistryHelper.registerBlock("sawmill", () -> new SawmillBlock());
		RAINBOW_CARPET = RegistryHelper.registerBlock("rainbow_carpet", () -> new CarpetBlock(AbstractBlock.Settings.create().mapColor(MapColor.WHITE).strength(0.1F).sounds(BlockSoundGroup.WOOL).burnable()));
		HOLLOWED_WARPED_STEM = RegistryHelper.registerBlock("hollowed_warped_stem", () -> new HollowedLogBlock(AbstractBlock.Settings.create().mapColor(MapColor.SPRUCE_BROWN).sounds(BlockSoundGroup.WOOD)));
		HOLLOWED_CRIMSON_STEM = RegistryHelper.registerBlock("hollowed_crimson_stem", () -> new HollowedLogBlock(AbstractBlock.Settings.create().mapColor(MapColor.SPRUCE_BROWN).sounds(BlockSoundGroup.WOOD)));
		STRIPPED_HOLLOWED_OAK_LOG = RegistryHelper.registerBlock("stripped_hollowed_oak_log", () -> new HollowedLogBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_OAK_LOG).sounds(BlockSoundGroup.WOOD)));
		STRIPPED_HOLLOWED_SPRUCE_LOG = RegistryHelper.registerBlock("stripped_hollowed_spruce_log", () -> new HollowedLogBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_SPRUCE_LOG).sounds(BlockSoundGroup.WOOD)));
		STRIPPED_HOLLOWED_BIRCH_LOG = RegistryHelper.registerBlock("stripped_hollowed_birch_log", () -> new HollowedLogBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_BIRCH_LOG).sounds(BlockSoundGroup.WOOD)));
		STRIPPED_HOLLOWED_CHERRY_LOG = RegistryHelper.registerBlock("stripped_hollowed_cherry_log", () -> new HollowedLogBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_CHERRY_LOG).sounds(BlockSoundGroup.WOOD)));
		STRIPPED_HOLLOWED_JUNGLE_LOG = RegistryHelper.registerBlock("stripped_hollowed_jungle_log", () -> new HollowedLogBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_JUNGLE_LOG).sounds(BlockSoundGroup.WOOD)));
		STRIPPED_HOLLOWED_ACACIA_LOG = RegistryHelper.registerBlock("stripped_hollowed_acacia_log", () -> new HollowedLogBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_ACACIA_LOG).sounds(BlockSoundGroup.WOOD)));
		STRIPPED_HOLLOWED_DARK_OAK_LOG = RegistryHelper.registerBlock("stripped_hollowed_dark_oak_log", () -> new HollowedLogBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_DARK_OAK_LOG).sounds(BlockSoundGroup.WOOD)));
		STRIPPED_HOLLOWED_MANGROVE_LOG = RegistryHelper.registerBlock("stripped_hollowed_mangrove_log", () -> new HollowedLogBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_MANGROVE_LOG).sounds(BlockSoundGroup.WOOD)));
		STRIPPED_HOLLOWED_CRIMSON_STEM = RegistryHelper.registerBlock("stripped_hollowed_crimson_stem", () -> new HollowedLogBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_CRIMSON_STEM)));
		STRIPPED_HOLLOWED_WARPED_STEM = RegistryHelper.registerBlock("stripped_hollowed_warped_stem", () -> new HollowedLogBlock(AbstractBlock.Settings.copy(Blocks.STRIPPED_WARPED_STEM)));
		HOLLOWED_OAK_LOG = RegistryHelper.registerBlock("hollowed_oak_log", () -> new HollowedLogBlock(AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).sounds(BlockSoundGroup.WOOD)));
		HOLLOWED_SPRUCE_LOG = RegistryHelper.registerBlock("hollowed_spruce_log", () -> new HollowedLogBlock(AbstractBlock.Settings.create().mapColor(MapColor.SPRUCE_BROWN).sounds(BlockSoundGroup.WOOD)));
		HOLLOWED_BIRCH_LOG = RegistryHelper.registerBlock("hollowed_birch_log", () -> new HollowedLogBlock(AbstractBlock.Settings.create().mapColor(MapColor.PALE_YELLOW).sounds(BlockSoundGroup.WOOD)));
		HOLLOWED_JUNGLE_LOG = RegistryHelper.registerBlock("hollowed_jungle_log", () -> new HollowedLogBlock(AbstractBlock.Settings.create().mapColor(MapColor.DIRT_BROWN).sounds(BlockSoundGroup.WOOD)));
		HOLLOWED_ACACIA_LOG = RegistryHelper.registerBlock("hollowed_acacia_log", () -> new HollowedLogBlock(AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).sounds(BlockSoundGroup.WOOD)));
		HOLLOWED_DARK_OAK_LOG = RegistryHelper.registerBlock("hollowed_dark_oak_log", () -> new HollowedLogBlock(AbstractBlock.Settings.create().mapColor(MapColor.BROWN).sounds(BlockSoundGroup.WOOD)));
		HOLLOWED_MANGROVE_LOG = RegistryHelper.registerBlock("hollowed_mangrove_log", () -> new HollowedLogBlock(AbstractBlock.Settings.create().mapColor(MapColor.RED).sounds(BlockSoundGroup.WOOD)));
		HOLLOWED_CHERRY_LOG = RegistryHelper.registerBlock("hollowed_cherry_log", () -> new HollowedLogBlock(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_WHITE).sounds(LostLegendsBlockSoundGroup.HOLLOWED_LOG)));
		TERMITE_MOUND = RegistryHelper.registerBlock("termite_mound", () -> new TermiteMoundBlock(AbstractBlock.Settings.create().mapColor(BROWN).strength(0.3F).sounds(LostLegendsBlockSoundGroup.TERMITEMOUND).ticksRandomly()));
		TUMBLEWEED_PLANT = RegistryHelper.registerBlock("tumbleweed_plant", () -> new TumbleweedBlock(AbstractBlock.Settings.create().breakInstantly().noCollision().sounds(LostLegendsBlockSoundGroup.TUMBLEWEED_PLANT).ticksRandomly()));
		TUMBLEWEED = RegistryHelper.registerBlock("tumbleweed", () -> new TumbleweedPlantBlock(AbstractBlock.Settings.create().breakInstantly().noCollision().sounds(LostLegendsBlockSoundGroup.TUMBLEWEED_PLANT).ticksRandomly()));
		POTTED_TUMBLEWEED_PLANT = RegistryHelper.registerBlock("potted_tumbleweed_plant", () -> new FlowerPotBlock(TUMBLEWEED.get(), AbstractBlock.Settings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
		RAINBOW_BED = RegistryHelper.registerBlock("rainbow_bed", () -> new RainbowBedBlock(DyeColor.WHITE, AbstractBlock.Settings.create().mapColor(MapColor.WHITE_GRAY).sounds(BlockSoundGroup.WOOD).strength(0.2F).nonOpaque().burnable().pistonBehavior(PistonBehavior.DESTROY)));
		BUTTERCUP = RegistryHelper.registerBlock("buttercup", () -> new FlowerBlock(StatusEffects.SATURATION, 6, AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XZ).pistonBehavior(PistonBehavior.DESTROY)));
		POTTED_BUTTERCUP = RegistryHelper.registerBlock("potted_buttercup", () -> new FlowerPotBlock(BUTTERCUP.get(), AbstractBlock.Settings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
		PINKDAISY = RegistryHelper.registerBlock("pinkdaisy", () -> new FlowerBlock(StatusEffects.SATURATION, 6, AbstractBlock.Settings.create().mapColor(MapColor.DARK_GREEN).noCollision().breakInstantly().sounds(BlockSoundGroup.GRASS).offset(AbstractBlock.OffsetType.XZ).pistonBehavior(PistonBehavior.DESTROY)));
		POTTED_PINKDAISY = RegistryHelper.registerBlock("potted_pinkdaisy", () -> new FlowerPotBlock(PINKDAISY.get(), AbstractBlock.Settings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
		TINYCACTUS = RegistryHelper.registerBlock("tiny_cactus", () -> new TinyCactusBlock(AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).mapColor(MapColor.GREEN).noCollision().breakInstantly().sounds(BlockSoundGroup.WOOL).offset(AbstractBlock.OffsetType.XZ)));
		POTTED_TINYCACTUS = RegistryHelper.registerBlock("potted_tiny_cactus", () -> new FlowerPotBlock(TINYCACTUS.get(), AbstractBlock.Settings.create().breakInstantly().nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
		ACACIA_BEEHIVE = RegistryHelper.registerBlock("acacia_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.create().mapColor(MapColor.ORANGE).strength(0.6F).sounds(BlockSoundGroup.WOOD).instrument(NoteBlockInstrument.BASS).burnable()));
		BAMBOO_BEEHIVE = RegistryHelper.registerBlock("bamboo_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.create().mapColor(MapColor.YELLOW).strength(0.6F).sounds(BlockSoundGroup.WOOD).instrument(NoteBlockInstrument.BASS).burnable()));
		BIRCH_BEEHIVE = RegistryHelper.registerBlock("birch_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.create().mapColor(PALE_YELLOW).strength(0.6F).sounds(BlockSoundGroup.WOOD).instrument(NoteBlockInstrument.BASS).burnable()));
		CHERRY_BEEHIVE = RegistryHelper.registerBlock("cherry_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_WHITE).strength(0.6F).sounds(BlockSoundGroup.WOOD).instrument(NoteBlockInstrument.BASS).burnable()));
		CRIMSON_BEEHIVE = RegistryHelper.registerBlock("crimson_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.create().mapColor(MapColor.DULL_PINK).strength(0.6F).sounds(BlockSoundGroup.WOOD).instrument(NoteBlockInstrument.BASS).burnable()));
		DARK_OAK_BEEHIVE = RegistryHelper.registerBlock("dark_oak_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.create().mapColor(MapColor.BROWN).strength(0.6F).sounds(BlockSoundGroup.WOOD).instrument(NoteBlockInstrument.BASS).burnable()));
		JUNGLE_BEEHIVE = RegistryHelper.registerBlock("jungle_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.create().mapColor(MapColor.DIRT_BROWN).strength(0.6F).sounds(BlockSoundGroup.WOOD).instrument(NoteBlockInstrument.BASS).burnable()));
		MANGROVE_BEEHIVE = RegistryHelper.registerBlock("mangrove_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.create().mapColor(MapColor.RED).strength(0.6F).sounds(BlockSoundGroup.WOOD).instrument(NoteBlockInstrument.BASS).burnable()));
		SPRUCE_BEEHIVE = RegistryHelper.registerBlock("spruce_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.create().mapColor(MapColor.SPRUCE_BROWN).strength(0.6F).sounds(BlockSoundGroup.WOOD).instrument(NoteBlockInstrument.BASS).burnable()));
		WARPED_BEEHIVE = RegistryHelper.registerBlock("warped_beehive", () -> new BeehiveBlock(AbstractBlock.Settings.create().mapColor(MapColor.DARK_AQUA).strength(0.6F).sounds(BlockSoundGroup.WOOD).instrument(NoteBlockInstrument.BASS).burnable()));
		COPPER_BUTTON = RegistryHelper.registerBlock("copper_button", () -> new OxidizableButtonBlock(Oxidizable.OxidationLevel.UNAFFECTED, AbstractBlock.Settings.create().noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER), 10));
		EXPOSED_COPPER_BUTTON = RegistryHelper.registerBlock("exposed_copper_button", () -> new OxidizableButtonBlock(Oxidizable.OxidationLevel.EXPOSED, AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER), 7));
		WEATHERED_COPPER_BUTTON = RegistryHelper.registerBlock("weathered_copper_button", () -> new OxidizableButtonBlock(Oxidizable.OxidationLevel.WEATHERED, AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER), 4));
		OXIDIZED_COPPER_BUTTON = RegistryHelper.registerBlock("oxidized_copper_button", () -> new OxidizableButtonBlock(Oxidizable.OxidationLevel.OXIDIZED, AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).noCollision().strength(0.5F).sounds(BlockSoundGroup.COPPER), 1));
		WAXED_COPPER_BUTTON = RegistryHelper.registerBlock("waxed_copper_button", () -> new CopperButtonBlock(AbstractBlock.Settings.copy(COPPER_BUTTON.get()), 10));
		WAXED_EXPOSED_COPPER_BUTTON = RegistryHelper.registerBlock("waxed_exposed_copper_button", () -> new CopperButtonBlock(AbstractBlock.Settings.copy(COPPER_BUTTON.get()), 7));
		WAXED_WEATHERED_COPPER_BUTTON = RegistryHelper.registerBlock("waxed_weathered_copper_button", () -> new CopperButtonBlock(AbstractBlock.Settings.copy(COPPER_BUTTON.get()), 4));
		WAXED_OXIDIZED_COPPER_BUTTON = RegistryHelper.registerBlock("waxed_oxidized_copper_button", () -> new CopperButtonBlock(AbstractBlock.Settings.copy(COPPER_BUTTON.get()), 1));
		EXPOSED_LIGHTNING_ROD = RegistryHelper.registerBlock("exposed_lightning_rod", () -> new OxidizableLightningRodBlock(Oxidizable.OxidationLevel.EXPOSED, AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
		WEATHERED_LIGHTNING_ROD = RegistryHelper.registerBlock("weathered_lightning_rod", () -> new OxidizableLightningRodBlock(Oxidizable.OxidationLevel.WEATHERED, AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
		OXIDIZED_LIGHTNING_ROD = RegistryHelper.registerBlock("oxidized_lightning_rod", () -> new OxidizableLightningRodBlock(Oxidizable.OxidationLevel.OXIDIZED, AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
		WAXED_LIGHTNING_ROD = RegistryHelper.registerBlock("waxed_lightning_rod", () -> new LightningRodBlock(AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
		WAXED_EXPOSED_LIGHTNING_ROD = RegistryHelper.registerBlock("waxed_exposed_lightning_rod", () -> new LightningRodBlock(AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
		WAXED_WEATHERED_LIGHTNING_ROD = RegistryHelper.registerBlock("waxed_weathered_lightning_rod", () -> new LightningRodBlock(AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
		WAXED_OXIDIZED_LIGHTNING_ROD = RegistryHelper.registerBlock("waxed_oxidized_lightning_rod", () -> new LightningRodBlock(AbstractBlock.Settings.copy(Blocks.LIGHTNING_ROD)));
		CRAB_EGG = RegistryHelper.registerBlock("crab_egg", () -> new CrabEggBlock(AbstractBlock.Settings.create().mapColor(PALE_YELLOW).strength(0.5F).sounds(BlockSoundGroup.METAL).ticksRandomly().nonOpaque()));
		BURROW = RegistryHelper.registerBlock("burrow", () -> new BurrowBlock(AbstractBlock.Settings.create().mapColor(PALE_YELLOW).strength(0.5F).sounds(BlockSoundGroup.SAND)));
		CARVED_MELON = RegistryHelper.registerBlock("carved_melon", () -> new CarvedMelonBlock(CARVED_MELON_SETTINGS));
		MELON_GOLEM_HEAD_BLINK = RegistryHelper.registerBlock("melon_golem_blink", () -> new CarvedMelonBlock(CARVED_MELON_SETTINGS));
		MELON_GOLEM_HEAD_SHOOT = RegistryHelper.registerBlock("melon_golem_shoot", () -> new CarvedMelonBlock(CARVED_MELON_SETTINGS));
		MELON_LANTERN = RegistryHelper.registerBlock("melon_lantern", () -> new CarvedMelonBlock(CARVED_MELON_SETTINGS.luminance(state -> 15)));
		RAINBOW_WOOL = RegistryHelper.registerBlock("rainbow_wool", () -> new Block(AbstractBlock.Settings.create().mapColor(MapColor.WHITE).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sounds(BlockSoundGroup.WOOL).burnable()));
		OSTRICH_EGG = RegistryHelper.registerBlock("ostrich_egg", () -> new OstrichEggBlock(AbstractBlock.Settings.create().mapColor(MapColor.TERRACOTTA_WHITE).strength(1.0F).sounds(BlockSoundGroup.METAL)));


	}
	public static void init() {
	}

	public static void postInit() {
		registerFlammableBlocks();
		registerAxe();
	}

	public static void registerAxe() {
		AxeBehaviors.register(Blocks.OAK_LOG, HollowedLogBlock.createHollowBehavior(LostLegendsBlocks.HOLLOWED_OAK_LOG.get(), false));
		AxeBehaviors.register(Blocks.BIRCH_LOG, HollowedLogBlock.createHollowBehavior(LostLegendsBlocks.HOLLOWED_BIRCH_LOG.get(), false));
		AxeBehaviors.register(Blocks.CHERRY_LOG, HollowedLogBlock.createHollowBehavior(LostLegendsBlocks.HOLLOWED_CHERRY_LOG.get(), false));
		AxeBehaviors.register(Blocks.SPRUCE_LOG, HollowedLogBlock.createHollowBehavior(LostLegendsBlocks.HOLLOWED_SPRUCE_LOG.get(), false));
		AxeBehaviors.register(Blocks.DARK_OAK_LOG, HollowedLogBlock.createHollowBehavior(LostLegendsBlocks.HOLLOWED_DARK_OAK_LOG.get(), false));
		AxeBehaviors.register(Blocks.JUNGLE_LOG, HollowedLogBlock.createHollowBehavior(LostLegendsBlocks.HOLLOWED_JUNGLE_LOG.get(), false));
		AxeBehaviors.register(Blocks.ACACIA_LOG, HollowedLogBlock.createHollowBehavior(LostLegendsBlocks.HOLLOWED_ACACIA_LOG.get(), false));
		AxeBehaviors.register(Blocks.MANGROVE_LOG, HollowedLogBlock.createHollowBehavior(LostLegendsBlocks.HOLLOWED_MANGROVE_LOG.get(), false));
		AxeBehaviors.register(Blocks.CRIMSON_STEM, HollowedLogBlock.createHollowBehavior(LostLegendsBlocks.HOLLOWED_CRIMSON_STEM.get(), true));
		AxeBehaviors.register(Blocks.WARPED_STEM, HollowedLogBlock.createHollowBehavior(LostLegendsBlocks.HOLLOWED_WARPED_STEM.get(), true));//STRIPPED
		AxeBehaviors.register(Blocks.STRIPPED_OAK_LOG, HollowedLogBlock.createHollowBehavior(LostLegendsBlocks.STRIPPED_HOLLOWED_OAK_LOG.get(), false));
		AxeBehaviors.register(Blocks.STRIPPED_BIRCH_LOG, HollowedLogBlock.createHollowBehavior(LostLegendsBlocks.STRIPPED_HOLLOWED_BIRCH_LOG.get(), false));
		AxeBehaviors.register(Blocks.STRIPPED_CHERRY_LOG, HollowedLogBlock.createHollowBehavior(LostLegendsBlocks.STRIPPED_HOLLOWED_CHERRY_LOG.get(), false));
		AxeBehaviors.register(Blocks.STRIPPED_SPRUCE_LOG, HollowedLogBlock.createHollowBehavior(LostLegendsBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG.get(), false));
		AxeBehaviors.register(Blocks.STRIPPED_DARK_OAK_LOG, HollowedLogBlock.createHollowBehavior(LostLegendsBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG.get(), false));
		AxeBehaviors.register(Blocks.STRIPPED_JUNGLE_LOG, HollowedLogBlock.createHollowBehavior(LostLegendsBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG.get(), false));
		AxeBehaviors.register(Blocks.STRIPPED_ACACIA_LOG, HollowedLogBlock.createHollowBehavior(LostLegendsBlocks.STRIPPED_HOLLOWED_ACACIA_LOG.get(), false));
		AxeBehaviors.register(Blocks.STRIPPED_MANGROVE_LOG, HollowedLogBlock.createHollowBehavior(LostLegendsBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG.get(), false));
		AxeBehaviors.register(Blocks.STRIPPED_CRIMSON_STEM, HollowedLogBlock.createHollowBehavior(LostLegendsBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM.get(), true));
		AxeBehaviors.register(Blocks.STRIPPED_WARPED_STEM, HollowedLogBlock.createHollowBehavior(LostLegendsBlocks.STRIPPED_HOLLOWED_WARPED_STEM.get(), true));
	}

	private static void registerFlammableBlocks() {
		int beehiveBurnChance = 5;
		int beehiveSpreadChance = 20;

		RegistryHelper.registerFlammableBlock(ACACIA_BEEHIVE, beehiveBurnChance, beehiveSpreadChance);
		RegistryHelper.registerFlammableBlock(BAMBOO_BEEHIVE, beehiveBurnChance, beehiveSpreadChance);
		RegistryHelper.registerFlammableBlock(BIRCH_BEEHIVE, beehiveBurnChance, beehiveSpreadChance);
		RegistryHelper.registerFlammableBlock(CHERRY_BEEHIVE, beehiveBurnChance, beehiveSpreadChance);
		RegistryHelper.registerFlammableBlock(CRIMSON_BEEHIVE, beehiveBurnChance, beehiveSpreadChance);
		RegistryHelper.registerFlammableBlock(DARK_OAK_BEEHIVE, beehiveBurnChance, beehiveSpreadChance);
		RegistryHelper.registerFlammableBlock(JUNGLE_BEEHIVE, beehiveBurnChance, beehiveSpreadChance);
		RegistryHelper.registerFlammableBlock(MANGROVE_BEEHIVE, beehiveBurnChance, beehiveSpreadChance);
		RegistryHelper.registerFlammableBlock(SPRUCE_BEEHIVE, beehiveBurnChance, beehiveSpreadChance);
		RegistryHelper.registerFlammableBlock(WARPED_BEEHIVE, beehiveBurnChance, beehiveSpreadChance);
	}



	public static HollowedLogBlock createStrippedHollowedLogBlock(MapColor mapColor, BlockSoundGroup soundGroup) {
		return new HollowedLogBlock(AbstractBlock.Settings.create()
			.mapColor(mapColor)
			.strength(2F)
			.sounds(soundGroup)
			.burnable());
	}

	public static HollowedLogBlock createHollowedLogBlock(MapColor topMapColor, MapColor sideMapColor) {
		return new HollowedLogBlock(AbstractBlock.Settings.create()
			.mapColor(state -> state.get(HollowedLogBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor)
			.strength(2F)
			.sounds(LostLegendsBlockSoundGroup.HOLLOWED_LOG)
			.burnable());
	}

	public static HollowedLogBlock createHollowedLogBlock(MapColor topMapColor, MapColor sideMapColor, BlockSoundGroup soundGroup) {
		return new HollowedLogBlock(AbstractBlock.Settings.create()
			.mapColor(state -> state.get(HollowedLogBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor)
			.strength(2F)
			.sounds(soundGroup)
			.burnable());
	}

	public static HollowedLogBlock createStrippedHollowedStemBlock(MapColor mapColor) {
		return new HollowedLogBlock(AbstractBlock.Settings.create()
			.mapColor(mapColor)
			.strength(2F)
			.sounds(LostLegendsBlockSoundGroup.HOLLOWED_STEM)
			.burnable());
	}

	private LostLegendsBlocks() {
	}
}
