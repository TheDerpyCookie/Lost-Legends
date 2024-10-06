package sabledream.studios.lostlegends.api.wind;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.LocalRandom;
import net.minecraft.util.math.random.ThreadSafeRandom;
import net.minecraft.util.math.random.Xoroshiro128PlusPlusRandom;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class EasyNoiseSampler {
	@Contract("_ -> new")
	public static @NotNull PerlinNoiseSampler createCheckedNoise(long seed) {
		return new PerlinNoiseSampler(new CheckedRandom(seed));
	}

	@Contract("_ -> new")
	public static @NotNull PerlinNoiseSampler createLegacyThreadSafeNoise(long seed) {
		return new PerlinNoiseSampler(new ThreadSafeRandom(seed));
	}

	@Contract("_ -> new")
	public static @NotNull PerlinNoiseSampler createLocalNoise(long seed) {
		return new PerlinNoiseSampler(new LocalRandom(seed));
	}

	@Contract("_ -> new")
	public static @NotNull PerlinNoiseSampler createXoroNoise(long seed) {
		return new PerlinNoiseSampler(new Xoroshiro128PlusPlusRandom(seed));
	}

	public static double sample(PerlinNoiseSampler sampler, Vec3i pos, double multiplier, boolean multiplyY, boolean useY) {
		if (useY) {
			if (multiplyY) {
				return sampler.sample(pos.getX() * multiplier, pos.getY() * multiplier, pos.getZ() * multiplier);
			}
			return sampler.sample(pos.getX() * multiplier, pos.getY(), pos.getZ() * multiplier);
		}
		return sampler.sample(pos.getX() * multiplier, 64, pos.getZ() * multiplier);
	}

	public static double sampleAbs(PerlinNoiseSampler sampler, Vec3i pos, double multiplier, boolean multiplyY, boolean useY) {
		return Math.abs(sample(sampler, pos, multiplier, multiplyY, useY));
	}

	public static double sample(PerlinNoiseSampler sampler, Vec3d pos, double multiplier, boolean multiplyY, boolean useY) {
		if (useY) {
			if (multiplyY) {
				return sampler.sample(pos.getX() * multiplier, pos.getY() * multiplier, pos.getZ() * multiplier);
			}
			return sampler.sample(pos.getX() * multiplier, pos.getY(), pos.getZ() * multiplier);
		}
		return sampler.sample(pos.getX() * multiplier, 64, pos.getZ() * multiplier);
	}
}