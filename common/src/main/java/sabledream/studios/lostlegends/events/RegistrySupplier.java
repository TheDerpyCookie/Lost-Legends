package sabledream.studios.lostlegends.events;

import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class RegistrySupplier<T> implements Supplier<T>
{

	private final Identifier id;
	private Supplier<T> supplier;
	private T raw;

	public RegistrySupplier(Identifier id, Supplier<T> supplier) {
		this.id = id;
		this.supplier = supplier;
	}

	public RegistrySupplier(Identifier id, T raw) {
		this.id = id;
		this.raw = raw;
	}

	public Identifier getId() {
		return this.id;
	}

	@Override
	public T get() {
		return this.raw != null ? this.raw : this.supplier.get();
	}
}