package sabledream.studios.lostlegends;


import sabledream.studios.lostlegends.api.MoobloomVariantManager;
import sabledream.studios.lostlegends.api.TermiteManager;
import sabledream.studios.lostlegends.config.LostLegendsConfig;
import sabledream.studios.lostlegends.config.omegaconfig.OmegaConfig;
import sabledream.studios.lostlegends.events.lifecycle.AddSpawnBiomeModificationsEvent;
import sabledream.studios.lostlegends.events.lifecycle.DatapackSyncEvent;
import sabledream.studios.lostlegends.events.lifecycle.RegisterReloadListenerEvent;
import sabledream.studios.lostlegends.events.lifecycle.SetupEvent;
import sabledream.studios.lostlegends.init.*;
import sabledream.studios.lostlegends.modcompat.ModChecker;
import sabledream.studios.lostlegends.packets.MessageHandler;
import sabledream.studios.lostlegends.packets.MoobloomVariantsSyncPacket;
import sabledream.studios.lostlegends.platform.BiomeModifications;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class LostLegends
{
	private static final Logger LOGGER = LoggerFactory.getLogger(LostLegends.MOD_ID);
	private static final LostLegendsConfig CONFIG = OmegaConfig.register(LostLegendsConfig.class);
	public static final String MOD_ID = "lostlegends";

	public static Identifier makeID(String path) {
		return Identifier.of(
			MOD_ID,
			path
		);
	}
	public static String makeStringID(String name) {
		return MOD_ID + ":" + name;
	}


	public static LostLegendsConfig getConfig() {
		return CONFIG;
	}
	public static Logger getLogger() {
		return LOGGER;
	}

	public static void init() {
		ModChecker.setupModCompat();
		LostLegendsBlockEntity.TILES.register();
		LostLegendsActivities.init();
		LostLegendsArmorMaterials.init();
		LostLegendsBlockSetTypes.init();
		LostLegendsBlocks.init();
		LostLegendsCriteria.init();
		LostLegendsEntityTypes.init();
		LostLegendsItems.init();
		LostLegendsMemoryModuleTypes.init();
		LostLegendsMemorySensorType.init();
		LostLegendsParticleTypes.init();
		LostLegendsPointOfInterestTypes.init();
		LostLegendsSoundEvents.init();
		LostLegendsStructureProcessorTypes.init();
		LostLegendsStructureTypes.init();
		LostLegendsVillagerProfessions.init();

		RegisterReloadListenerEvent.EVENT.addListener(LostLegends::registerServerDataListeners);
		AddSpawnBiomeModificationsEvent.EVENT.addListener(LostLegendsEntityTypes::addSpawnBiomeModifications);
		SetupEvent.EVENT.addListener(LostLegends::setup);
		DatapackSyncEvent.EVENT.addListener(MoobloomVariantsSyncPacket::sendToClient);
	}


	public static void postInit() {
		BiomeModifications.addButtercupFeature();
		BiomeModifications.addTinycactusFeature();
		LostLegendsBlocks.postInit();
		LostLegendsEntityTypes.postInit();
		LostLegendsItems.postInit();
		LostLegendsBlockEntityTypes.postInit();
		LostLegendsVillagerProfessions.postInit();
		TermiteManager.Termite.addDegradableBlocks();
		TermiteManager.Termite.addNaturalDegradableBlocks();
	}

	private static void registerServerDataListeners(final RegisterReloadListenerEvent event) {
		event.register(LostLegends.makeID("moobloom_variants"), MoobloomVariantManager.MOOBLOOM_VARIANT_MANAGER);
	}

	private static void setup(final SetupEvent event) {
		MessageHandler.init();
	}
}
