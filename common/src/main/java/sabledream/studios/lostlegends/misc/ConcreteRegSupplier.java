package sabledream.studios.lostlegends.misc;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ConcreteRegSupplier<T> implements RegSupplier<T> {

	private final Identifier id;
	private final T instance;
	private final RegistryKey<T> registryKey;
	private final RegistryEntry<T> holder;

	public ConcreteRegSupplier(Identifier id, T instance, RegistryKey<T> registryKey, RegistryEntry<T> holder) {
		this.id = id;
		this.instance = instance;
		this.registryKey = registryKey;
		this.holder = holder;
	}

	@Override
	public T get() {
		return instance;
	}

	@Override
	public Identifier getId() {
		return this.id;
	}

	@Override
	public RegistryKey<T> getKey() {
		return this.registryKey;
	}

	@Override
	public RegistryEntry<T> getHolder() {
		return this.holder;
	}
}