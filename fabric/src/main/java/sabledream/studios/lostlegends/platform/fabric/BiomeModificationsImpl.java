package sabledream.studios.lostlegends.platform.fabric;

import sabledream.studios.lostlegends.LostLegends;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;

public final class BiomeModificationsImpl
{
	public static void addMobSpawn(
		TagKey<Biome> tag,
		EntityType<?> entityType,
		SpawnGroup spawnGroup,
		int weight,
		int minGroupSize,
		int maxGroupSize
	) {
		BiomeModifications.addSpawn(biomeSelector -> biomeSelector.hasTag(tag) && biomeSelector.hasTag(BiomeTags.IS_OVERWORLD), spawnGroup, entityType, weight, minGroupSize, maxGroupSize);
	}

	public static void addButtercupFeature() {
		TagKey<Biome> flowerForestTag = TagKey.of(RegistryKeys.BIOME, LostLegends.makeID("has_buttercup_patch"));
		BiomeModifications.create(LostLegends.makeID("add_buttercup_patch"))
			.add(ModificationPhase.ADDITIONS,
				(context) -> context.hasTag(flowerForestTag),
				context -> context.getGenerationSettings().addFeature(
					GenerationStep.Feature.VEGETAL_DECORATION,
					RegistryKey.of(
						RegistryKeys.PLACED_FEATURE,
						LostLegends.makeID("buttercup_patch")
					)
				)
			);
	}
	public static void addTinycactusFeature() {
		TagKey<Biome> desertcactusTag = TagKey.of(RegistryKeys.BIOME, LostLegends.makeID("has_tiny_cactus_patch"));
		BiomeModifications.create(LostLegends.makeID("add_tiny_cactus_patch"))
			.add(ModificationPhase.ADDITIONS,
				(context) -> context.hasTag(desertcactusTag),
				context -> context.getGenerationSettings().addFeature(
					GenerationStep.Feature.VEGETAL_DECORATION,
					RegistryKey.of(
						RegistryKeys.PLACED_FEATURE,
						LostLegends.makeID("tiny_cactus_patch")
					)
				)
			);
	}

}
