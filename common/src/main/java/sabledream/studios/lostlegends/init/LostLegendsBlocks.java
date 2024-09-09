package sabledream.studios.lostlegends.init;

import net.minecraft.util.DyeColor;
import sabledream.studios.lostlegends.block.*;
import sabledream.studios.lostlegends.block.Oxidizable;
import sabledream.studios.lostlegends.platform.RegistryHelper;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.BlockSoundGroup;

import java.util.function.Supplier;

import static net.minecraft.block.MapColor.PALE_YELLOW;

/**
 * @see Blocks
 */
public final class LostLegendsBlocks
{
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

	private static final AbstractBlock.Settings CARVED_MELON_SETTINGS = AbstractBlock.Settings.create().mapColor(MapColor.GREEN).strength(1.0F).sounds(BlockSoundGroup.WOOD).allowsSpawning((state, world, pos, type) -> true).pistonBehavior(PistonBehavior.DESTROY);



	static {
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

	private LostLegendsBlocks() {
	}
}
