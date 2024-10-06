package sabledream.studios.lostlegends.init;

import sabledream.studios.lostlegends.platform.RegistryHelper;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;

/**
 * @see ParticleTypes
 */
public final class LostLegendsParticleTypes
{
	public static final SimpleParticleType TOTEM_OF_FREEZING = new SimpleParticleType(false);
	public static final SimpleParticleType TOTEM_OF_ILLUSION = new SimpleParticleType(false);
	public static final SimpleParticleType FIREFLY_EMISSION = new SimpleParticleType(true);
	public static final SimpleParticleType TERMITE = new SimpleParticleType(true);

	public static void init() {
	}

	private LostLegendsParticleTypes() {
	}

	static {
		RegistryHelper.registerParticleType("totem_of_freezing", TOTEM_OF_FREEZING);
		RegistryHelper.registerParticleType("totem_of_illusion", TOTEM_OF_ILLUSION);
		RegistryHelper.registerParticleType("firefly_emission", FIREFLY_EMISSION);
		RegistryHelper.registerParticleType("termite", TERMITE);
	}

}
