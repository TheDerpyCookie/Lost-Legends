package sabledream.studios.lostlegends.neoforge;

import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.events.lifecycle.DatapackSyncEvent;
import sabledream.studios.lostlegends.events.lifecycle.RegisterReloadListenerEvent;
import sabledream.studios.lostlegends.events.lifecycle.SetupEvent;
import sabledream.studios.lostlegends.init.LostLegendsEntityTypes;
import sabledream.studios.lostlegends.init.LostLegendsStructurePoolElements;
import sabledream.studios.lostlegends.neoforge.init.LostLegendsBiomeModifiers;
import sabledream.studios.lostlegends.network.neoforge.NeoForgeNetworking;
import sabledream.studios.lostlegends.platform.neoforge.RegistryHelperImpl;
import sabledream.studios.lostlegends.util.ClientUtil;
import sabledream.studios.lostlegends.util.ServerWorldSpawnersUtil;
import sabledream.studios.lostlegends.world.spawner.IceologerSpawner;
import sabledream.studios.lostlegends.world.spawner.IllusionerSpawner;
import net.minecraft.SharedConstants;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.item.ItemGroup;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.dimension.DimensionTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

import java.util.Map;
import java.util.function.Supplier;

@Mod(LostLegends.MOD_ID)
public final class LostLegendsNeoForge
{
	public LostLegendsNeoForge(ModContainer modContainer, IEventBus modEventBus) {

		var eventBus = NeoForge.EVENT_BUS;

		LostLegends.init();
		LostLegendsBiomeModifiers.BIOME_MODIFIERS.register(modEventBus);

		if (FMLEnvironment.dist == Dist.CLIENT) {
			LostLegendsNeoForgeClient.init(modEventBus, eventBus);
		}

		modEventBus.addListener(LostLegendsNeoForge::onSetup);

		RegistryHelperImpl.ACTIVITIES.register(modEventBus);
		RegistryHelperImpl.BLOCKS.register(modEventBus);
		RegistryHelperImpl.BLOCKENTITY.register(modEventBus);
		LostLegendsEntityTypes.previousUseChoiceTypeRegistrations = SharedConstants.useChoiceTypeRegistrations;
		SharedConstants.useChoiceTypeRegistrations = false;
		RegistryHelperImpl.ENTITY_TYPES.register(modEventBus);
		SharedConstants.useChoiceTypeRegistrations = LostLegendsEntityTypes.previousUseChoiceTypeRegistrations;
		RegistryHelperImpl.ITEMS.register(modEventBus);
		RegistryHelperImpl.MEMORY_MODULE_TYPES.register(modEventBus);
		RegistryHelperImpl.SENSOR_TYPES.register(modEventBus);
		RegistryHelperImpl.PARTICLE_TYPES.register(modEventBus);
		RegistryHelperImpl.POINT_OF_INTEREST_TYPES.register(modEventBus);
		RegistryHelperImpl.SOUND_EVENTS.register(modEventBus);
		RegistryHelperImpl.STRUCTURE_TYPES.register(modEventBus);
		RegistryHelperImpl.STRUCTURE_PROCESSOR_TYPES.register(modEventBus);
		RegistryHelperImpl.VILLAGER_PROFESSIONS.register(modEventBus);
		RegistryHelperImpl.CRITERIA.register(modEventBus);
		RegistryHelperImpl.ARMOR_MATERIAL.register(modEventBus);

		modEventBus.addListener(LostLegendsNeoForge::init);
		modEventBus.addListener(LostLegendsNeoForge::registerEntityAttributes);
		modEventBus.addListener(LostLegendsNeoForge::addItemsToTabs);
		modEventBus.addListener(LostLegendsNeoForge::onNetworkSetup);
		modEventBus.addListener(this::clientSetup);

		eventBus.addListener(LostLegendsNeoForge::initSpawners);
		eventBus.addListener(LostLegendsNeoForge::onServerAboutToStartEvent);
		eventBus.addListener(LostLegendsNeoForge::onAddReloadListeners);
		eventBus.addListener(LostLegendsNeoForge::onDatapackSync);

	}

	private static void init(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			LostLegends.postInit();
		});
	}

	private void clientSetup(final FMLClientSetupEvent event) {
		ClientUtil.doClientStuff();
	}


	private static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		for (Map.Entry<Supplier<? extends EntityType<? extends LivingEntity>>, Supplier<DefaultAttributeContainer.Builder>> entry : RegistryHelperImpl.ENTITY_ATTRIBUTES.entrySet()) {
			event.put(entry.getKey().get(), entry.getValue().get().build());
		}
	}

	private static void addItemsToTabs(BuildCreativeModeTabContentsEvent event) {
		RegistryHelperImpl.ITEMS_TO_ADD_BEFORE.forEach((itemGroup, itemPairs) -> {
			if (event.getTabKey() == itemGroup) {
				itemPairs.forEach((item, before) -> {
					event.insertBefore(before.getDefaultStack(), item.getDefaultStack(), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
				});
			}
		});

		RegistryHelperImpl.ITEMS_TO_ADD_AFTER.forEach((itemGroup, itemPairs) -> {
			if (event.getTabKey() == itemGroup) {
				itemPairs.forEach((item, after) -> {
					event.insertAfter(after.getDefaultStack(), item.getDefaultStack(), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
				});
			}
		});
	}

	private static void onAddReloadListeners(AddReloadListenerEvent event) {
		RegisterReloadListenerEvent.EVENT.invoke(new RegisterReloadListenerEvent((id, listener) -> event.addListener(listener)));
	}

	private static void
	onSetup(FMLCommonSetupEvent event) {
		SetupEvent.EVENT.invoke(new SetupEvent(event::enqueueWork));
	}

	private static void onDatapackSync(OnDatapackSyncEvent event) {
		if (event.getPlayer() != null) {
			DatapackSyncEvent.EVENT.invoke(new DatapackSyncEvent(event.getPlayer()));
		} else {
			event.getPlayerList().getPlayerList().forEach(player -> DatapackSyncEvent.EVENT.invoke(new DatapackSyncEvent(player)));
		}
	}

	public static void onNetworkSetup(RegisterPayloadHandlersEvent event) {
		NeoForgeNetworking.setupNetwork(event);
	}

	private static void initSpawners(final LevelEvent.Load event) {
		if (
			event.getLevel().isClient()
			|| ((ServerWorld) event.getLevel()).getDimensionEntry() != DimensionTypes.OVERWORLD) {
			return;
		}

		var server = event.getLevel().getServer();

		if (server == null) {
			return;
		}

		var world = server.getOverworld();

		if (world == null) {
			return;
		}

		ServerWorldSpawnersUtil.register(world, new IceologerSpawner());
		ServerWorldSpawnersUtil.register(world, new IllusionerSpawner());
	}

	public static void onServerAboutToStartEvent(ServerAboutToStartEvent event) {
		LostLegendsStructurePoolElements.init(event.getServer());
	}
}
