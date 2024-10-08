package sabledream.studios.lostlegends.config;

import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.LostLegendsClient;
import sabledream.studios.lostlegends.api.SyncBehavior;
import sabledream.studios.lostlegends.config.annotation.Category;
import sabledream.studios.lostlegends.config.annotation.Description;
import sabledream.studios.lostlegends.config.omegaconfig.api.Config;

import java.util.function.Supplier;

import static sabledream.studios.lostlegends.config.LostLegendsConfig.SearchMode.*;

public final class LostLegendsConfig implements Config
{
	@Category("sawmill")
	@Description("Makes Sawmill GUI slightly wider")
	public boolean widegui= true;

	@Category("termite")
	@Description("onlyEatNaturalBlocks")
	public boolean onlyEatNaturalBlocks = true;

	@Description("maxNaturalDistance")
	public int maxNaturalDistance = 10;

	@Description("maxDistance")
	public int maxDistance = 32;

	@Description("logHollowing")
	public boolean logHollowing = true;


	@Category("tumbleweed")
	@Description("spawnTumbleweed")
	public boolean spawnTumbleweed = true;

	@Description("tumbleweedSpawnCap")
	public int tumbleweedSpawnCap = 10;

	@Description("leashedTumbleweed")
	public boolean leashedTumbleweed = false;

	@Description("tumbleweedDestroysCrops")
	public boolean tumbleweedDestroysCrops = true;

	@Description(value = "tumbleweedRotatesToLookDirection")
	public boolean tumbleweedRotatesToLookDirection = false;



	@Category("Barnacle")
	@Description("Enable barnacle")
	public boolean enableBarnacle = true;

	@Description("Enable barnacle spawn")
	public boolean enableBarnacleSpawn = true;

	@Description("Barnacle spawn weight")
	public int barnacleSpawnWeight = 4;

	@Description("Minimal barnacle spawn group size")
	public int barnacleSpawnMinGroupSize = 1;

	@Description("Maximal barnacle spawn group size")
	public int barnacleSpawnMaxGroupSize = 1;

	@Category("Crab")
	@Description("Enable")
	public boolean enableCrab = true;

	@Description("Enable spawn")
	public boolean enableCrabSpawn = true;

	@Description("Spawn weight in less frequent spawn biomes")
	public int crabLessFrequentSpawnWeight = 2;

	@Description("Spawn weight in more frequent spawn biomes")
	public int crabMoreFrequentSpawnWeight = 4;

	@Category("Copper Golem")
	@Description("Enable")
	public boolean enableCopperGolem = true;


	@Description("Generate copper golem area structure in villages")
	public boolean generateCopperGolemAreaStructure = true;

	@Description("Copper Golem area structure spawn chance")
	public int copperGolemAreaStructureWeight = 1;

	@Description("Generate copper golem in the center piece in the ancient cities")
	public boolean generateCopperGolemInAncientCity = true;

	@Description("Generate copper golem in the center piece in the ancient cities spawn chance")
	public int copperGolemAncientCityCenterWeight = 10;

	@Category("Glare")
	@Description("Enable")
	public boolean enableGlare = true;

	@Description("Enable spawn")
	public boolean enableGlareSpawn = true;

	@Description("Whenever will glare shake off glow berries and eat glow berries")
	public boolean enableGlareGriefing = true;

	@Description("Spawn weight")
	public int glareSpawnWeight = 4;

	@Description("Minimal spawn group size")
	public int glareSpawnMinGroupSize = 1;

	@Description("Maximal spawn group size")
	public int glareSpawnMaxGroupSize = 1;
	@Category("Mauler")
	@Description("Enable")
	public boolean enableMauler = true;

	@Description("Enable spawn")
	public boolean enableMaulerSpawn = true;

	@Description("Spawn weight in desert biome")
	public int maulerDesertSpawnWeight = 8;

	@Description("Minimal spawn group size in desert biome")
	public int maulerDesertSpawnMinGroupSize = 1;

	@Description("Maximal spawn group size in desert biome")
	public int maulerDesertSpawnMaxGroupSize = 1;

	@Description("Spawn weight in badlands biome")
	public int maulerBadlandsSpawnWeight = 16;

	@Description("Minimal spawn group size in badlands biome")
	public int maulerBadlandsSpawnMinGroupSize = 1;

	@Description("Maximal spawn group size in badlands biome")
	public int maulerBadlandsSpawnMaxGroupSize = 1;

	@Description("Spawn weight in savanna biome")
	public int maulerSavannaSpawnWeight = 32;

	@Description("Minimal spawn group size in savanna biome")
	public int maulerSavannaSpawnMinGroupSize = 1;

	@Description("Maximal spawn group size in savanna biome")
	public int maulerSavannaSpawnMaxGroupSize = 1;

	@Category("Moobloom")
	@Description("Enable")
	public boolean enableMoobloom = true;

	@Description("Enable spawn")
	public boolean enableMoobloomSpawn = true;

	@Description("Spawn weight")
	public int moobloomSpawnWeight = 4;

	@Description("Minimal spawn group size")
	public int moobloomSpawnMinGroupSize = 2;

	@Description("Maximal spawn group size")
	public int moobloomSpawnMaxGroupSize = 4;

	@Category("Penguin")
	@Description("Enable")
	public boolean enablePenguin = true;

	@Description("Enable spawn")
	public boolean enablePenguinSpawn = true;

	@Description("Spawn weight")
	public int penguinSpawnWeight = 4;

	@Description("Minimal spawn group size")
	public int penguinSpawnMinGroupSize = 2;

	@Description("Maximal spawn group size")
	public int penguinSpawnMaxGroupSize = 4;

	@Category("Iceologer")
	@Description("Enable")
	public boolean enableIceologer = true;

	@Description("Enable spawn")
	public boolean enableIceologerSpawn = true;

	@Description("Enable in raids")
	public boolean enableIceologerInRaids = true;

	@Description("Generate iceologer cabin structure")
	public boolean generateIceologerCabinStructure = true;

	@Category("Illusioner")
	@Description("Enable")
	public boolean enableIllusioner = true;

	@Description("Enable spawn")
	public boolean enableIllusionerSpawn = true;

	@Description("Enable in raids")
	public boolean enableIllusionerInRaids = true;

	@Description("Generate illusioner shack structure")
	public boolean generateIllusionerShackStructure = true;

	@Description("Generate illusioner training grounds")
	public boolean generateIllusionerTrainingGroundsStructure = true;

	@Category("Zombie Horse")
	@Description("Enable trap")
	public boolean enableZombieHorseTrap = true;

	@Category("Rascal")
	@Description("Enable rascal")
	public boolean enableRascal = true;

	@Description("Enable rascal spawn")
	public boolean enableRascalSpawn = true;

	@Category("Tuff Golem")
	@Description("Enable tuff golem")
	public boolean enableTuffGolem = true;

	@Description("Generate tuff golem in stronghold libraries")
	public boolean generateTuffGolemInStronghold = true;

	@Category("Wildfire")
	@Description("Enable wildfire")
	public boolean enableWildfire = true;

	@Description("Generate citadel structure")
	public boolean generateCitadelStructure = true;

	@Category("Beekeeper")
	@Description("Enable villager profession")
	public boolean enableBeekeeperVillagerProfession = true;

	@Description("Generate beekeeper area structure in villages")
	public boolean generateBeekeeperAreaStructure = true;

	@Description("Beekeeper area structure spawn chance")
	public int beekeeperAreaStructureWeight = 2;

	@Override
	public String getName() {
		return LostLegends.MOD_ID;
	}

	public static boolean hasSearchBar(int recipeCount) {
		var s = SEARCH_MODE.get();
		return switch (s) {
			case ON -> true;
			case OFF -> false;
			case AUTOMATIC -> LostLegendsClient.hasManyRecipes();
			case DYNAMIC -> recipeCount > SEARCH_BAR_THRESHOLD.get();
		};
	}
	public enum SearchMode {
		OFF, ON, AUTOMATIC, DYNAMIC
	}

	public static Supplier<SearchMode> SEARCH_MODE;
	public static Supplier<Integer> SEARCH_BAR_THRESHOLD;


}
