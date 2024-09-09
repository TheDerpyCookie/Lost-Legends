package sabledream.studios.lostlegends.neoforge.worldgen;

import sabledream.studios.lostlegends.events.lifecycle.AddSpawnBiomeModificationsEvent;
import sabledream.studios.lostlegends.neoforge.init.LostLegendsBiomeModifiers;
import com.mojang.serialization.MapCodec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

public class MobSpawnsBiomeModifier implements BiomeModifier
{
	public static final MapCodec<MobSpawnsBiomeModifier> CODEC = MapCodec.unit(MobSpawnsBiomeModifier::new);

	public MobSpawnsBiomeModifier() {
	}

	public void modify(RegistryEntry<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
		if (phase == Phase.ADD) {
			AddSpawnBiomeModificationsEvent.EVENT.invoke(new AddSpawnBiomeModificationsEvent((tag, spawnGroup, entityType, spawnWeight, minGroupSize, maxGroupSize) -> {
				if (biome.isIn(tag)) {
					builder.getMobSpawnSettings().getSpawner(spawnGroup).add(new SpawnSettings.SpawnEntry(entityType, spawnWeight, minGroupSize, maxGroupSize));
				}
			}));
		}
	}

	public MapCodec<? extends BiomeModifier> codec() {
		return LostLegendsBiomeModifiers.BIOME_MODIFIER.get();
	}
}