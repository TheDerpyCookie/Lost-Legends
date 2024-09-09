package sabledream.studios.lostlegends.network.defaults;

import sabledream.studios.lostlegends.network.Packet;
import sabledream.studios.lostlegends.network.base.PacketType;
import net.minecraft.util.Identifier;

public abstract class AbstractPacketType<T extends Packet<T>> implements PacketType<T>
{

	protected final Class<T> clazz;
	protected final Identifier id;

	public AbstractPacketType(Class<T> clazz, Identifier id) {
		this.clazz = clazz;
		this.id = id;
	}

	@Override
	public Class<T> type() {
		return clazz;
	}

	@Override
	public Identifier id() {
		return id;
	}
}
