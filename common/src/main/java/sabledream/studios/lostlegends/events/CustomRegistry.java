package sabledream.studios.lostlegends.events;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.Set;

public abstract class CustomRegistry<T> {

	@ExpectPlatform
	public static <T> CustomRegistry<T> create(Class<T> clazz, Identifier id) {
		throw new AssertionError();
	}

	public abstract RegistryKey<? extends Registry<T>> getRegistryKey();

	public abstract T get(Identifier key);

	public abstract Identifier getKey(T object);

	public abstract Set<Identifier> getKeys();

	public abstract boolean containsKey(Identifier key);

	public abstract Collection<T> getValues();

}