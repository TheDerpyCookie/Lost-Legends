package sabledream.studios.lostlegends.tag;

import sabledream.studios.lostlegends.LostLegends;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.poi.PointOfInterestType;

/**
 * @see BlockTags
 */
public final class LostLegendsTags
{
	public static final TagKey<Block> COPPER_BUTTONS = blockTag("copper_buttons");
	public static final TagKey<Block> LIGHTNING_RODS = blockTag("lightning_rods");
	public static final TagKey<PointOfInterestType> LIGHTNING_ROD_POI = pointOfInterestTypeTag("lightning_rods");
	public static final TagKey<Block> GLARES_SPAWNABLE_ON = blockTag("glares_spawnable_on");
	public static final TagKey<Block> MAULERS_SPAWNABLE_ON = blockTag("maulers_spawnable_on");
	public static final TagKey<Block> CRABS_SPAWNABLE_ON = blockTag("crabs_spawnable_on");
	public static final TagKey<Item> CRAB_TEMPT_ITEMS = itemTag("crab_tempt_items");
	public static final TagKey<Item> PENGUIN_TEMPT_ITEMS = itemTag("penguin_tempt_items");
	public static final TagKey<Block> PENGUIN_SPAWNABLE_ON = blockTag("penguin_spawnable_on");
	public static final TagKey<Block> CRAB_BURROW_SPOT_BLOCKS = blockTag("crab_burrow_spot_blocks");
	public static final TagKey<Item> GLARE_FOOD_ITEMS = itemTag("glare_food_items");
	public static final TagKey<Item> GLARE_TEMPT_ITEMS = itemTag("glare_tempt_items");
	public static final TagKey<Item> OSTRICH_TEMPT_ITEMS = itemTag("ostrich_tempt_items");
	public static final TagKey<Item> TOTEMS = itemTag("totems");
	public static final TagKey<EntityType<?>> MAULER_PREY = entityTypeTag("mauler_prey");
	public static final TagKey<EntityType<?>> WILDFIRE_ALLIES = entityTypeTag("wildfire_allies");
	public static final TagKey<Biome> HAS_BADLANDS_MAULER = biomeTag("has_badlands_mauler");
	public static final TagKey<Biome> HAS_DESERT_MAULER = biomeTag("has_desert_mauler");
	public static final TagKey<Biome> HAS_BARNACLE = biomeTag("has_barnacle");
	public static final TagKey<Biome> HAS_LESS_FREQUENT_CRAB = biomeTag("has_less_frequent_crab");
	public static final TagKey<Biome> HAS_MORE_FREQUENT_CRAB = biomeTag("has_more_frequent_crab");
	public static final TagKey<Biome> HAS_GLARE = biomeTag("has_glare");
	public static final TagKey<Biome> HAS_ICEOLOGER = biomeTag("has_iceologer");
	public static final TagKey<Biome> HAS_ILLUSIONER = biomeTag("has_illusioner");
	public static final TagKey<Biome> HAS_MOOBLOOMS = biomeTag("has_moobloom/any");
	public static final TagKey<Biome> HAS_PENGUIN = biomeTag("has_penguin");
	public static final TagKey<EntityType<?>> BARNACLE_AVOID_TARGETS = entityTypeTag("barnacle_avoid_targets");
	public static final TagKey<EntityType<?>> BARNACLE_PREY = entityTypeTag("barnacle_prey");
	public static final TagKey<EntityType<?>> BARNACLE_ALWAYS_HOSTILES = entityTypeTag("barnacle_always_hostile");

	public static final TagKey<Biome> HAS_CRAB = biomeTag("has_crab");
	public static final TagKey<Biome> HAS_RASCAL = biomeTag("has_rascal");
	public static final TagKey<Biome> HAS_SAVANNA_MAULER = biomeTag("has_savanna_mauler");

	public static final TagKey<Block> BURROW = blockTag("burrow");


	public static final TagKey<Item> TUMBLEWEED_COMMON = itemTag("tumbleweed_common");
	public static final TagKey<Item> TUMBLEWEED_MEDIUM = itemTag("tumbleweed_medium");
	public static final TagKey<Item> TUMBLEWEED_RARE = itemTag("tumbleweed_rare");
	public static final TagKey<EntityType<?>> TUMBLEWEED_PASSES_THROUGH = entityTypeTag("tumbleweed_passes_through");
	public static final TagKey<Block> STOPS_TUMBLEWEED = blockTag("stops_tumbleweed");
	public static final TagKey<Block> BUSH_MAY_PLACE_ON = blockTag("bush_may_place_on");

	public static final TagKey<Item> BLACKLIST = itemTag("blacklist");




	//Termite Stuff
	public static final TagKey<Block> KILLS_TERMITE = blockTag("kills_termite");
	public static final TagKey<Block> TERMITE_BREAKABLE = blockTag("termite_breakable");
	public static final TagKey<Block> BLOCKS_TERMITE = blockTag("blocks_termite");
	public static final TagKey<Block> TERMITE_DISC_REPLACEABLE = blockTag("termite_disc_replaceable");
	public static final TagKey<Block> TERMITE_DISC_BLOCKS = blockTag("termite_disc_blocks");
	public static final TagKey<Biome> HAS_TERMITE_MOUND = biomeTag("has_termite_mound");



	private static TagKey<Block> blockTag(String name) {
		return TagKey.of(RegistryKeys.BLOCK, LostLegends.makeID(name));
	}

	private static TagKey<Item> itemTag(String name) {
		return TagKey.of(RegistryKeys.ITEM, LostLegends.makeID(name));
	}

	private static TagKey<EntityType<?>> entityTypeTag(String name) {
		return TagKey.of(RegistryKeys.ENTITY_TYPE, LostLegends.makeID(name));
	}

	private static TagKey<Biome> biomeTag(String name) {
		return TagKey.of(RegistryKeys.BIOME, LostLegends.makeID(name));
	}

	private static TagKey<PointOfInterestType> pointOfInterestTypeTag(String name) {
		return TagKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, LostLegends.makeID(name));
	}
}
