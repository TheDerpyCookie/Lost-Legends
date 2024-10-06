package sabledream.studios.lostlegends.api.wind;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

public interface WindManagerExtension {

	Identifier extensionID();

	void tick(ServerWorld level);

	void baseTick(ServerWorld level);

	boolean runResetsIfNeeded();

	CustomPayload syncPacket(WindSyncPacket packet);

	void load(NbtCompound compoundTag);

	void save(NbtCompound compoundTag);
}