package sabledream.studios.lostlegends.events.neoforge;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.registries.DeferredRegister;
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

	@SuppressWarnings("unchecked")
	public static class Impl<T> extends DeferredRegistry<T>
	{

		private final DeferredRegister<T> register;
		private final List<RegistrySupplier<T>> entries;

		public Impl(String modid, RegistryKey<? extends Registry<T>> resourceKey) {
			this.register = DeferredRegister.create(resourceKey, modid);
			this.entries = new ArrayList<>();
		}

		@Override
		public void register() {
			this.register.register(ModLoadingContext.get().getActiveContainer().getEventBus());
		}


		@Override
		public <R extends T> RegistrySupplier<R> register(String id, Supplier<R> supplier) {
			var orig = this.register.register(id, supplier);
			var RegistrySupplier = new RegistrySupplier<>(orig.getId(), orig);
			this.entries.add((RegistrySupplier<T>) RegistrySupplier);
			return RegistrySupplier;
		}

		@Override
		public Collection<RegistrySupplier<T>> getEntries() {
			return this.entries;
		}
	}


}