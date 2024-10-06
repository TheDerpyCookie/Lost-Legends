package sabledream.studios.lostlegends.misc;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public interface RegSupplier<T> extends Supplier<T>
{

	@Override
	T get();

	Identifier getId();

	RegistryKey<T> getKey();

	RegistryEntry<T> getHolder();

	default boolean is(TagKey<T> tag) {
		return this.getHolder().isIn(tag);
	}
}