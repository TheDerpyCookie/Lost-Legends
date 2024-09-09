package sabledream.studios.lostlegends.init;

import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.item.CrabClawItem;
import sabledream.studios.lostlegends.platform.RegistryHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraft.util.Rarity;

import java.util.function.Supplier;

/**
 * @see Items
 */
public final class LostLegendsItems
{
	public final static Supplier<Item> COPPER_GOLEM_SPAWN_EGG;
	public static final Supplier<Item> CRAB_CLAW;

	public static final Supplier<Item> CRAB_SPAWN_EGG;

	public static final Supplier<Item> MEERKAT_SPAWN_EGG;
	public static final Supplier<Item> CRAB_EGG;
	public static final Supplier<Item> OSTRICH_EGG;
	public static final Supplier<Item> COOKED_OSTRICH;
	public static final Supplier<Item> RAW_OSTRICH;
	public final static Supplier<Item> GLARE_SPAWN_EGG;
	public final static Supplier<Item> ICEOLOGER_SPAWN_EGG;
	public final static Supplier<Item> ILLUSIONER_SPAWN_EGG;
	public final static Supplier<Item> MAULER_SPAWN_EGG;
	public final static Supplier<Item> MOOBLOOM_SPAWN_EGG;
	public final static Supplier<Item> RASCAL_SPAWN_EGG;
	public final static Supplier<Item> TUFF_GOLEM_SPAWN_EGG;
	public final static Supplier<Item> WILDFIRE_SPAWN_EGG;
	public static final Supplier<Item> BUTTERCUP;
	public static final Supplier<Item> PINKDAISY;
	public static final Supplier<Item> TINYCACTUS;
	public static final Supplier<Item> ACACIA_BEEHIVE;
	public static final Supplier<Item> BAMBOO_BEEHIVE;
	public static final Supplier<Item> BIRCH_BEEHIVE;
	public static final Supplier<Item> CHERRY_BEEHIVE;
	public static final Supplier<Item> CRIMSON_BEEHIVE;
	public static final Supplier<Item> DARK_OAK_BEEHIVE;
	public static final Supplier<Item> JUNGLE_BEEHIVE;
	public static final Supplier<Item> MANGROVE_BEEHIVE;
	public static final Supplier<Item> SPRUCE_BEEHIVE;
	public static final Supplier<Item> WARPED_BEEHIVE;
	public static final Supplier<Item> COPPER_BUTTON;
	public static final Supplier<Item> EXPOSED_COPPER_BUTTON;
	public static final Supplier<Item> WEATHERED_COPPER_BUTTON;
	public static final Supplier<Item> OXIDIZED_COPPER_BUTTON;
	public static final Supplier<Item> WAXED_COPPER_BUTTON;
	public static final Supplier<Item> WAXED_EXPOSED_COPPER_BUTTON;
	public static final Supplier<Item> WAXED_WEATHERED_COPPER_BUTTON;
	public static final Supplier<Item> WAXED_OXIDIZED_COPPER_BUTTON;
	public static final Supplier<Item> EXPOSED_LIGHTNING_ROD;
	public static final Supplier<Item> WEATHERED_LIGHTNING_ROD;
	public static final Supplier<Item> OXIDIZED_LIGHTNING_ROD;
	public static final Supplier<Item> WAXED_LIGHTNING_ROD;
	public static final Supplier<Item> WAXED_EXPOSED_LIGHTNING_ROD;
	public static final Supplier<Item> WAXED_WEATHERED_LIGHTNING_ROD;
	public static final Supplier<Item> WAXED_OXIDIZED_LIGHTNING_ROD;
	public static final Supplier<Item> WILDFIRE_CROWN;
	public static final Supplier<Item> WILDFIRE_CROWN_FRAGMENT;
	public static final Supplier<Item> TOTEM_OF_FREEZING;
	public static final Supplier<Item> TOTEM_OF_ILLUSION;
	public static final Supplier<Item> PENGUIN_SPAWN_EGG;
	public static final Supplier<Item> BARNACLE_SPAWN_EGG;
	public static final Supplier<Item> CLUCKSHROOM_SPAWN_EGG;
	public static final Supplier<Item> FANCYCHICKEN_SPAWN_EGG;
	public static final Supplier<Item> TROPICAL_SLIME_SPAWN_EGG;
	public static final Supplier<Item> VULTURE_SPAWN_EGG;
	public static final Supplier<BlockItem> BURROW;
	public static final Supplier<Item> MOOLIP_SPAWN_EGG;
	public static final Supplier<Item> COWTUS_SPAWN_EGG;
	public static final Supplier<Item> BAMBMOO_SPAWN_EGG;
	public static final Supplier<Item> MELON_GOLEM_SPAWN_EGG;

	public static final Supplier<BlockItem> CARVED_MELON;
	public static final Supplier<BlockItem> MELON_GOLEM_HEAD_BLINK;
	public static final Supplier<BlockItem> MELON_GOLEM_HEAD_SHOOT;
	public static final Supplier<BlockItem> MELON_LANTERN;
	public static final Supplier<BlockItem> RAINBOW_BED;



	static {
		RAINBOW_BED = RegistryHelper.registerItem("rainbow_bed", () -> new BlockItem(LostLegendsBlocks.RAINBOW_BED.get(), new Item.Settings().maxCount(64)));
		CARVED_MELON = RegistryHelper.registerItem("carved_melon", () -> new BlockItem(LostLegendsBlocks.CARVED_MELON.get(), new Item.Settings().maxCount(64)));
		MELON_GOLEM_HEAD_BLINK = RegistryHelper.registerItem("melon_golem_blink", () -> new BlockItem(LostLegendsBlocks.MELON_GOLEM_HEAD_BLINK.get(), new Item.Settings().maxCount(64)));
		MELON_GOLEM_HEAD_SHOOT = RegistryHelper.registerItem("melon_golem_shoot", () -> new BlockItem(LostLegendsBlocks.MELON_GOLEM_HEAD_SHOOT.get(), new Item.Settings().maxCount(64)));
		MELON_LANTERN = RegistryHelper.registerItem("melon_lantern", () -> new BlockItem(LostLegendsBlocks.MELON_LANTERN.get(), new Item.Settings().maxCount(64)));

		MELON_GOLEM_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("melon_golem_spawn_egg", LostLegendsEntityTypes.MELON_GOLEM, 0x9A5038, 0xE3826C, new Item.Settings().maxCount(64));
		COPPER_GOLEM_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("copper_golem_spawn_egg", LostLegendsEntityTypes.COPPER_GOLEM, 0x9A5038, 0xE3826C, new Item.Settings().maxCount(64));
		GLARE_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("glare_spawn_egg", LostLegendsEntityTypes.GLARE, 0x70922D, 0x6A5227, new Item.Settings().maxCount(64));
		ICEOLOGER_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("iceologer_spawn_egg", LostLegendsEntityTypes.ICEOLOGER, 0x173873, 0x949B9B, new Item.Settings().maxCount(64));
		ILLUSIONER_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("illusioner_spawn_egg", () -> EntityType.ILLUSIONER, 0x603E5C, 0x888E8E, new Item.Settings().maxCount(64));
		MAULER_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("mauler_spawn_egg", LostLegendsEntityTypes.MAULER, 0x534F25, 0x817B39, new Item.Settings().maxCount(64));
		MOOBLOOM_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("moobloom_spawn_egg", LostLegendsEntityTypes.MOOBLOOM, 0xF7EDC1, 0xFACA00, new Item.Settings().maxCount(64));
		RASCAL_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("rascal_spawn_egg", LostLegendsEntityTypes.RASCAL, 0x05736A, 0x8A521C, new Item.Settings().maxCount(64));
		TUFF_GOLEM_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("tuff_golem_spawn_egg", LostLegendsEntityTypes.TUFF_GOLEM, 0xA0A297, 0x5D5D52, new Item.Settings().maxCount(64));
		WILDFIRE_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("wildfire_spawn_egg", LostLegendsEntityTypes.WILDFIRE, 0x6C3100, 0xFFD528, new Item.Settings().maxCount(64));
		BUTTERCUP = RegistryHelper.registerItem("buttercup", () -> new BlockItem(LostLegendsBlocks.BUTTERCUP.get(), new Item.Settings().maxCount(64)));
		TINYCACTUS = RegistryHelper.registerItem("tiny_cactus", () -> new BlockItem(LostLegendsBlocks.TINYCACTUS.get(), new Item.Settings().maxCount(64)));

		PINKDAISY = RegistryHelper.registerItem("pinkdaisy", () -> new BlockItem(LostLegendsBlocks.PINKDAISY.get(), new Item.Settings().maxCount(64)));
		ACACIA_BEEHIVE = RegistryHelper.registerItem("acacia_beehive", () -> new BlockItem(LostLegendsBlocks.ACACIA_BEEHIVE.get(), new Item.Settings().maxCount(64)));
		BAMBOO_BEEHIVE = RegistryHelper.registerItem("bamboo_beehive", () -> new BlockItem(LostLegendsBlocks.BAMBOO_BEEHIVE.get(), new Item.Settings().maxCount(64)));
		BIRCH_BEEHIVE = RegistryHelper.registerItem("birch_beehive", () -> new BlockItem(LostLegendsBlocks.BIRCH_BEEHIVE.get(), new Item.Settings().maxCount(64)));
		CHERRY_BEEHIVE = RegistryHelper.registerItem("cherry_beehive", () -> new BlockItem(LostLegendsBlocks.CHERRY_BEEHIVE.get(), new Item.Settings().maxCount(64)));
		CRIMSON_BEEHIVE = RegistryHelper.registerItem("crimson_beehive", () -> new BlockItem(LostLegendsBlocks.CRIMSON_BEEHIVE.get(), new Item.Settings().maxCount(64)));
		DARK_OAK_BEEHIVE = RegistryHelper.registerItem("dark_oak_beehive", () -> new BlockItem(LostLegendsBlocks.DARK_OAK_BEEHIVE.get(), new Item.Settings().maxCount(64)));
		JUNGLE_BEEHIVE = RegistryHelper.registerItem("jungle_beehive", () -> new BlockItem(LostLegendsBlocks.JUNGLE_BEEHIVE.get(), new Item.Settings().maxCount(64)));
		MANGROVE_BEEHIVE = RegistryHelper.registerItem("mangrove_beehive", () -> new BlockItem(LostLegendsBlocks.MANGROVE_BEEHIVE.get(), new Item.Settings().maxCount(64)));
		SPRUCE_BEEHIVE = RegistryHelper.registerItem("spruce_beehive", () -> new BlockItem(LostLegendsBlocks.SPRUCE_BEEHIVE.get(), new Item.Settings().maxCount(64)));
		WARPED_BEEHIVE = RegistryHelper.registerItem("warped_beehive", () -> new BlockItem(LostLegendsBlocks.WARPED_BEEHIVE.get(), new Item.Settings().maxCount(64)));
		COPPER_BUTTON = RegistryHelper.registerItem("copper_button", () -> new BlockItem(LostLegendsBlocks.COPPER_BUTTON.get(), new Item.Settings().maxCount(64)));
		EXPOSED_COPPER_BUTTON = RegistryHelper.registerItem("exposed_copper_button", () -> new BlockItem(LostLegendsBlocks.EXPOSED_COPPER_BUTTON.get(), new Item.Settings().maxCount(64)));
		WEATHERED_COPPER_BUTTON = RegistryHelper.registerItem("weathered_copper_button", () -> new BlockItem(LostLegendsBlocks.WEATHERED_COPPER_BUTTON.get(), new Item.Settings().maxCount(64)));
		OXIDIZED_COPPER_BUTTON = RegistryHelper.registerItem("oxidized_copper_button", () -> new BlockItem(LostLegendsBlocks.OXIDIZED_COPPER_BUTTON.get(), new Item.Settings().maxCount(64)));
		WAXED_COPPER_BUTTON = RegistryHelper.registerItem("waxed_copper_button", () -> new BlockItem(LostLegendsBlocks.WAXED_COPPER_BUTTON.get(), new Item.Settings().maxCount(64)));
		WAXED_EXPOSED_COPPER_BUTTON = RegistryHelper.registerItem("waxed_exposed_copper_button", () -> new BlockItem(LostLegendsBlocks.WAXED_EXPOSED_COPPER_BUTTON.get(), new Item.Settings().maxCount(64)));
		WAXED_WEATHERED_COPPER_BUTTON = RegistryHelper.registerItem("waxed_weathered_copper_button", () -> new BlockItem(LostLegendsBlocks.WAXED_WEATHERED_COPPER_BUTTON.get(), new Item.Settings().maxCount(64)));
		WAXED_OXIDIZED_COPPER_BUTTON = RegistryHelper.registerItem("waxed_oxidized_copper_button", () -> new BlockItem(LostLegendsBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get(), new Item.Settings().maxCount(64)));
		EXPOSED_LIGHTNING_ROD = RegistryHelper.registerItem("exposed_lightning_rod", () -> new BlockItem(LostLegendsBlocks.EXPOSED_LIGHTNING_ROD.get(), new Item.Settings().maxCount(64)));
		WEATHERED_LIGHTNING_ROD = RegistryHelper.registerItem("weathered_lightning_rod", () -> new BlockItem(LostLegendsBlocks.WEATHERED_LIGHTNING_ROD.get(), new Item.Settings().maxCount(64)));
		OXIDIZED_LIGHTNING_ROD = RegistryHelper.registerItem("oxidized_lightning_rod", () -> new BlockItem(LostLegendsBlocks.OXIDIZED_LIGHTNING_ROD.get(), new Item.Settings().maxCount(64)));
		WAXED_LIGHTNING_ROD = RegistryHelper.registerItem("waxed_lightning_rod", () -> new BlockItem(LostLegendsBlocks.WAXED_LIGHTNING_ROD.get(), new Item.Settings().maxCount(64)));
		WAXED_EXPOSED_LIGHTNING_ROD = RegistryHelper.registerItem("waxed_exposed_lightning_rod", () -> new BlockItem(LostLegendsBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get(), new Item.Settings().maxCount(64)));
		WAXED_WEATHERED_LIGHTNING_ROD = RegistryHelper.registerItem("waxed_weathered_lightning_rod", () -> new BlockItem(LostLegendsBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get(), new Item.Settings().maxCount(64)));
		WAXED_OXIDIZED_LIGHTNING_ROD = RegistryHelper.registerItem("waxed_oxidized_lightning_rod", () -> new BlockItem(LostLegendsBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get(), new Item.Settings().maxCount(64)));
		WILDFIRE_CROWN = RegistryHelper.registerItem("wildfire_crown", () -> new ArmorItem(LostLegendsArmorMaterials.WILDFIRE, ArmorItem.Type.HELMET, (new Item.Settings().maxCount(1)).fireproof().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(37))));
		WILDFIRE_CROWN_FRAGMENT = RegistryHelper.registerItem("wildfire_crown_fragment", () -> new Item((new Item.Settings()).fireproof()));
		TOTEM_OF_FREEZING = RegistryHelper.registerItem("totem_of_freezing", () -> new Item((new Item.Settings()).maxCount(1).rarity(Rarity.UNCOMMON)));
		TOTEM_OF_ILLUSION = RegistryHelper.registerItem("totem_of_illusion", () -> new Item((new Item.Settings()).maxCount(1).rarity(Rarity.UNCOMMON)));
		CRAB_CLAW = RegistryHelper.registerItem("crab_claw", () -> new CrabClawItem(new Item.Settings().maxCount(32)));
		CRAB_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("crab_spawn_egg", LostLegendsEntityTypes.CRAB, 0x333077, 0xFE984B, new Item.Settings().maxCount(64));
		CRAB_EGG = RegistryHelper.registerItem("crab_egg", () -> new BlockItem(LostLegendsBlocks.CRAB_EGG.get(), new Item.Settings().maxCount(64)));
		OSTRICH_EGG = RegistryHelper.registerItem("ostrich_egg", () -> new BlockItem(LostLegendsBlocks.OSTRICH_EGG.get(), new Item.Settings().maxCount(64)));

		BARNACLE_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("barnacle_spawn_egg", LostLegendsEntityTypes.BARNACLE, 0x56847E, 0x27514B, new Item.Settings().maxCount(64));
		PENGUIN_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("penguin_spawn_egg", LostLegendsEntityTypes.PENGUIN, 9804699, 1973274, new Item.Settings().maxCount(64));
		MEERKAT_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("meerkat_spawn_egg", LostLegendsEntityTypes.MEERKAT, 0xBC895C,0x302B31, new Item.Settings().maxCount(64));
		CLUCKSHROOM_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("cluckshroom_spawn_egg", LostLegendsEntityTypes.CLUCKSHROOM, 0xef0000,0xffffee, new Item.Settings().maxCount(64));
		FANCYCHICKEN_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("fancychicken_spawn_egg", LostLegendsEntityTypes.FANCYCHICKEN, 0xf7b035,0x478e8b, new Item.Settings().maxCount(64));
		TROPICAL_SLIME_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("tropical_slime_spawn_egg", LostLegendsEntityTypes.TROPICALSLIME, 0x0e496e,0x8ed3ff, new Item.Settings().maxCount(64));
		BURROW = RegistryHelper.registerItem("burrow", () -> new BlockItem(LostLegendsBlocks.BURROW.get(), new Item.Settings().maxCount(64)));
		VULTURE_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("vulture_spawn_egg", LostLegendsEntityTypes.VULTURE, 0x654225,0xD49076, new Item.Settings().maxCount(64));
		MOOLIP_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("moolip_spawn_egg", LostLegendsEntityTypes.MOOLIP, 0xea88be,0xf9e7eb, new Item.Settings().maxCount(64));
		COWTUS_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("cowtus_spawn_egg", LostLegendsEntityTypes.MOOBLOOM_CATUS, 0x70922D,0xf9e7eb, new Item.Settings().maxCount(64));
		BAMBMOO_SPAWN_EGG = RegistryHelper.registerSpawnEggItem("bambmoo_spawn_egg", LostLegendsEntityTypes.BAMBMOO, 8978176,0xf9e7eb, new Item.Settings().maxCount(64));
		RAW_OSTRICH = RegistryHelper.registerItem("raw_ostrich", () -> new Item(new Item.Settings().food(LostLegendsFood.RAW_OSTRICH)));
		COOKED_OSTRICH = RegistryHelper.registerItem("cooked_ostrich", () -> new Item(new Item.Settings().food(LostLegendsFood.COOKED_OSTRICH)));
	}

	public static void init() {
	}

	public static void postInit() {
		addToItemGroups();
	}

	private static void addToItemGroups() {
		RegistryHelper.addToItemGroupBefore(ItemGroups.SPAWN_EGGS, CRAB_SPAWN_EGG.get(), Items.CREEPER_SPAWN_EGG);
		RegistryHelper.addToItemGroupAfter(ItemGroups.SPAWN_EGGS, COPPER_GOLEM_SPAWN_EGG.get(), Items.CAVE_SPIDER_SPAWN_EGG);
		RegistryHelper.addToItemGroupAfter(ItemGroups.SPAWN_EGGS, GLARE_SPAWN_EGG.get(), Items.CAVE_SPIDER_SPAWN_EGG);
		RegistryHelper.addToItemGroupAfter(ItemGroups.SPAWN_EGGS, ICEOLOGER_SPAWN_EGG.get(), Items.HORSE_SPAWN_EGG);
		RegistryHelper.addToItemGroupAfter(ItemGroups.SPAWN_EGGS, ILLUSIONER_SPAWN_EGG.get(), Items.HORSE_SPAWN_EGG);
		RegistryHelper.addToItemGroupAfter(ItemGroups.SPAWN_EGGS, MAULER_SPAWN_EGG.get(), Items.MAGMA_CUBE_SPAWN_EGG);
		RegistryHelper.addToItemGroupAfter(ItemGroups.SPAWN_EGGS, MEERKAT_SPAWN_EGG.get(), Items.MAGMA_CUBE_SPAWN_EGG);
		RegistryHelper.addToItemGroupBefore(ItemGroups.SPAWN_EGGS, MOOBLOOM_SPAWN_EGG.get(), Items.MOOSHROOM_SPAWN_EGG);
		RegistryHelper.addToItemGroupBefore(ItemGroups.SPAWN_EGGS, TUFF_GOLEM_SPAWN_EGG.get(), Items.TURTLE_SPAWN_EGG);
		RegistryHelper.addToItemGroupBefore(ItemGroups.SPAWN_EGGS, RASCAL_SPAWN_EGG.get(), Items.RAVAGER_SPAWN_EGG);
		RegistryHelper.addToItemGroupBefore(ItemGroups.SPAWN_EGGS, WILDFIRE_SPAWN_EGG.get(), Items.WITCH_SPAWN_EGG);
		RegistryHelper.addToItemGroupAfter(ItemGroups.SPAWN_EGGS, BARNACLE_SPAWN_EGG.get(), Items.BAT_SPAWN_EGG);
		RegistryHelper.addToItemGroupAfter(ItemGroups.SPAWN_EGGS, PENGUIN_SPAWN_EGG.get(), Items.PARROT_SPAWN_EGG);
		RegistryHelper.addToItemGroupAfter(ItemGroups.SPAWN_EGGS, VULTURE_SPAWN_EGG.get(), Items.PARROT_SPAWN_EGG);
		RegistryHelper.addToItemGroupAfter(ItemGroups.SPAWN_EGGS, MOOLIP_SPAWN_EGG.get(), Items.PARROT_SPAWN_EGG);
		RegistryHelper.addToItemGroupAfter(ItemGroups.SPAWN_EGGS, TROPICAL_SLIME_SPAWN_EGG.get(), Items.PARROT_SPAWN_EGG);
		RegistryHelper.addToItemGroupAfter(ItemGroups.SPAWN_EGGS, FANCYCHICKEN_SPAWN_EGG.get(), Items.PARROT_SPAWN_EGG);
		RegistryHelper.addToItemGroupAfter(ItemGroups.SPAWN_EGGS, CLUCKSHROOM_SPAWN_EGG.get(), Items.PARROT_SPAWN_EGG);
		RegistryHelper.addToItemGroupAfter(ItemGroups.SPAWN_EGGS, BAMBMOO_SPAWN_EGG.get(), Items.PARROT_SPAWN_EGG);
		RegistryHelper.addToItemGroupAfter(ItemGroups.SPAWN_EGGS, COWTUS_SPAWN_EGG.get(), Items.PARROT_SPAWN_EGG);

		RegistryHelper.addToItemGroupAfter(ItemGroups.NATURAL, BUTTERCUP.get(), Items.DANDELION);
		RegistryHelper.addToItemGroupAfter(ItemGroups.NATURAL, PINKDAISY.get(), Items.DANDELION);
		RegistryHelper.addToItemGroupAfter(ItemGroups.NATURAL, TINYCACTUS.get(), Items.DANDELION);




		RegistryHelper.addToItemGroupAfter(ItemGroups.FUNCTIONAL, SPRUCE_BEEHIVE.get(), Items.BEEHIVE);
		RegistryHelper.addToItemGroupAfter(ItemGroups.FUNCTIONAL, BAMBOO_BEEHIVE.get(), Items.BEEHIVE);
		RegistryHelper.addToItemGroupAfter(ItemGroups.FUNCTIONAL, BIRCH_BEEHIVE.get(), Items.BEEHIVE);
		RegistryHelper.addToItemGroupAfter(ItemGroups.FUNCTIONAL, JUNGLE_BEEHIVE.get(), Items.BEEHIVE);
		RegistryHelper.addToItemGroupAfter(ItemGroups.FUNCTIONAL, ACACIA_BEEHIVE.get(), Items.BEEHIVE);
		RegistryHelper.addToItemGroupAfter(ItemGroups.FUNCTIONAL, DARK_OAK_BEEHIVE.get(), Items.BEEHIVE);
		RegistryHelper.addToItemGroupAfter(ItemGroups.FUNCTIONAL, MANGROVE_BEEHIVE.get(), Items.BEEHIVE);
		RegistryHelper.addToItemGroupAfter(ItemGroups.FUNCTIONAL, CHERRY_BEEHIVE.get(), Items.BEEHIVE);
		RegistryHelper.addToItemGroupAfter(ItemGroups.FUNCTIONAL, CRIMSON_BEEHIVE.get(), Items.BEEHIVE);
		RegistryHelper.addToItemGroupAfter(ItemGroups.FUNCTIONAL, WARPED_BEEHIVE.get(), Items.BEEHIVE);


		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, COPPER_BUTTON.get(), Items.STONE_BUTTON);
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, EXPOSED_COPPER_BUTTON.get(), Items.STONE_BUTTON);
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, WEATHERED_COPPER_BUTTON.get(), Items.STONE_BUTTON);
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, OXIDIZED_COPPER_BUTTON.get(), Items.STONE_BUTTON);
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, WAXED_COPPER_BUTTON.get(), Items.STONE_BUTTON);
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, WAXED_EXPOSED_COPPER_BUTTON.get(), Items.STONE_BUTTON);
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, WAXED_WEATHERED_COPPER_BUTTON.get(), Items.STONE_BUTTON);
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, WAXED_OXIDIZED_COPPER_BUTTON.get(), Items.STONE_BUTTON);
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, EXPOSED_LIGHTNING_ROD.get(), Items.LIGHTNING_ROD);
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, WEATHERED_LIGHTNING_ROD.get(), Items.LIGHTNING_ROD);
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, OXIDIZED_LIGHTNING_ROD.get(), Items.LIGHTNING_ROD);
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, WAXED_LIGHTNING_ROD.get(), Items.LIGHTNING_ROD);
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, WAXED_EXPOSED_LIGHTNING_ROD.get(), Items.LIGHTNING_ROD);
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, WAXED_WEATHERED_LIGHTNING_ROD.get(), Items.LIGHTNING_ROD);
		RegistryHelper.addToItemGroupAfter(ItemGroups.REDSTONE, WAXED_OXIDIZED_LIGHTNING_ROD.get(), Items.LIGHTNING_ROD);

		RegistryHelper.addToItemGroupAfter(ItemGroups.INGREDIENTS, WILDFIRE_CROWN_FRAGMENT.get(), Items.TURTLE_SCUTE);
		RegistryHelper.addToItemGroupAfter(ItemGroups.COMBAT, WILDFIRE_CROWN.get(), Items.TURTLE_HELMET);
		RegistryHelper.addToItemGroupAfter(ItemGroups.COMBAT, TOTEM_OF_FREEZING.get(), Items.TOTEM_OF_UNDYING);
		RegistryHelper.addToItemGroupBefore(ItemGroups.COMBAT, TOTEM_OF_ILLUSION.get(), Items.TOTEM_OF_UNDYING);


		RegistryHelper.addToItemGroupBefore(ItemGroups.NATURAL, CRAB_EGG.get(), Items.TURTLE_EGG);
		RegistryHelper.addToItemGroupBefore(ItemGroups.INGREDIENTS, CRAB_CLAW.get(), Items.DRAGON_BREATH);
	}

	private LostLegendsItems() {
	}
}
