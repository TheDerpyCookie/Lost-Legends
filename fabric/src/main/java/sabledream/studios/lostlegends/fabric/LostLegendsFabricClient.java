package sabledream.studios.lostlegends.fabric;

import sabledream.studios.lostlegends.LostLegendsClient;
import sabledream.studios.lostlegends.client.particle.FireflyParticle;
import sabledream.studios.lostlegends.client.particle.FreezingTotemParticle;
import sabledream.studios.lostlegends.client.particle.IllusionTotemParticle;
import sabledream.studios.lostlegends.events.lifecycle.ClientSetupEvent;
import sabledream.studios.lostlegends.init.LostLegendsParticleTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public final class LostLegendsFabricClient implements ClientModInitializer
{
	@Override
	@Environment(EnvType.CLIENT)
	public void onInitializeClient() {
		LostLegendsClient.init();
		LostLegendsClient.postInit();
		this.registerParticleFactories();
		this.initEvents();
	}

	private void initEvents() {
		ClientSetupEvent.EVENT.invoke(new ClientSetupEvent(Runnable::run));
	}

	private void registerParticleFactories() {
		ParticleFactoryRegistry.getInstance().register(LostLegendsParticleTypes.TOTEM_OF_FREEZING, FreezingTotemParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(LostLegendsParticleTypes.TOTEM_OF_ILLUSION, IllusionTotemParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(LostLegendsParticleTypes.FIREFLY_EMISSION, FireflyParticle.Factory::new);
	}
}

