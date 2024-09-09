package sabledream.studios.lostlegends.events.fabric;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import sabledream.studios.lostlegends.events.DeferredRegistry;
import sabledream.studios.lostlegends.events.RegistrySupplier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class DeferredRegistryImpl {

	public static <T> DeferredRegistry<T> create(String modid, RegistryKey<? extends Registry<T>> resourceKey) {
		return new Impl<>(modid, resourceKey);
	}

	@SuppressWarnings({"unchecked", "ConstantConditions"})
	public static class Impl<T> extends DeferredRegistry<T>
	{

		private final String modid;
		private final Registry<T> registry;
		private final List<RegistrySupplier<T>> entries;

		public Impl(String modid, RegistryKey<? extends Registry<T>> resourceKey) {
			this.modid = modid;
			this.registry = (Registry<T>) Registries.REGISTRIES.get(resourceKey.getValue());
			this.entries = new ArrayList<>();
		}

		@Override
		public void register() {

		}

		@Override
		public <R extends T> RegistrySupplier<R> register(String id, Supplier<R> supplier) {
			Identifier registeredId =  Identifier.of(this.modid, id);
			RegistrySupplier<R> registrySupplier = new RegistrySupplier<>(registeredId, Registry.register(this.registry, registeredId, supplier.get()));
			this.entries.add((RegistrySupplier<T>) registrySupplier);
			return registrySupplier;
		}

		@Override
		public Collection<RegistrySupplier<T>> getEntries() {
			return this.entries;
		}
	}

}