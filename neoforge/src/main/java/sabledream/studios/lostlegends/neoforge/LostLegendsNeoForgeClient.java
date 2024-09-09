package sabledream.studios.lostlegends.neoforge;

import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.LostLegendsClient;
import sabledream.studios.lostlegends.client.particle.FreezingTotemParticle;
import sabledream.studios.lostlegends.client.particle.IllusionTotemParticle;
import sabledream.studios.lostlegends.config.ConfigScreenBuilder;
import sabledream.studios.lostlegends.init.LostLegendsParticleTypes;
import sabledream.studios.lostlegends.platform.neoforge.RegistryHelperImpl;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.util.Map;
import java.util.function.Supplier;

public final class LostLegendsNeoForgeClient
{
	public static void init(IEventBus modEventBus, IEventBus eventBus) {
		LostLegendsClient.init();

		modEventBus.addListener(LostLegendsNeoForgeClient::onClientSetup);
		modEventBus.addListener(LostLegendsNeoForgeClient::registerLayerDefinitions);
		modEventBus.addListener(LostLegendsNeoForgeClient::registerParticleFactory);
	}

	private static void onClientSetup(final FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			LostLegendsClient.postInit();

			if (ModList.get().isLoaded("cloth_config")) {
				ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (client, screen) -> {
					return ConfigScreenBuilder.createConfigScreen(LostLegends.getConfig(), screen);
				});
			}
		});
	}

	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		for (Map.Entry<EntityModelLayer, Supplier<TexturedModelData>> entry : RegistryHelperImpl.ENTITY_MODEL_LAYERS.entrySet()) {
			event.registerLayerDefinition(entry.getKey(), entry.getValue());
		}
	}

	public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(LostLegendsParticleTypes.TOTEM_OF_FREEZING, FreezingTotemParticle.Factory::new);
		event.registerSpriteSet(LostLegendsParticleTypes.TOTEM_OF_ILLUSION, IllusionTotemParticle.Factory::new);
	}
}
