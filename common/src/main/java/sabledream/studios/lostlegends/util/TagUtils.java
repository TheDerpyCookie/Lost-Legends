package sabledream.studios.lostlegends.util;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import sabledream.studios.lostlegends.api.AdvancedMath;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class TagUtils
{
	@Nullable
	public static <T> T getRandomEntry(TagKey<T> tag) {
		return getRandomEntry(AdvancedMath.random(), tag);
	}

	@SuppressWarnings("unchecked")
	@Nullable
	public static <T> T getRandomEntry(Random random, TagKey<T> tag) {
		Optional<? extends Registry<?>> maybeRegistry = Registries.REGISTRIES.getOrEmpty(tag.registry().getRegistry());
		Objects.requireNonNull(random);
		Objects.requireNonNull(tag);

		if (maybeRegistry.isPresent()) {
			Registry<T> registry = (Registry<T>) maybeRegistry.get();
			if (tag.isOf(registry.getKey())) {
				ArrayList<T> entries = new ArrayList<>();
				for (RegistryEntry<T> entry : registry.iterateEntries(tag)) {
					var optionalKey = entry.getKey();
					if (optionalKey.isPresent()) {
						var key = optionalKey.get();
						registry.getOrEmpty(key).ifPresent(entries::add);
					}
				}
				if (!entries.isEmpty()) {
					return entries.get(random.nextInt(entries.size()));
				}
			}
		}
		return null;
	}

}
