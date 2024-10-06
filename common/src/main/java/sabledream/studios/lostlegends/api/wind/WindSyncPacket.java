package sabledream.studios.lostlegends.api.wind;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import sabledream.studios.lostlegends.LostLegends;

public record WindSyncPacket(
	long windTime,
	long seed,
	boolean override,
	Vec3d commandWind
) implements CustomPayload
{

	public static final Id<WindSyncPacket> PACKET_TYPE = new Id<>(
		LostLegends.makeID("wind_sync_packet")
	);
	public static final PacketCodec<PacketByteBuf, WindSyncPacket> CODEC = PacketCodec.of(WindSyncPacket::write, WindSyncPacket::create);

	public static WindSyncPacket create(@NotNull PacketByteBuf buf) {
		return new WindSyncPacket(
			buf.readLong(),
			buf.readLong(),
			buf.readBoolean(),
			buf.readVec3d()
		);
	}

	public void write(@NotNull PacketByteBuf buf) {
		buf.writeLong(this.windTime());
		buf.writeLong(this.seed());
		buf.writeBoolean(this.override());
		buf.writeVec3d(this.commandWind());
	}

	@Override
	public Id<? extends CustomPayload> getId() {
		return PACKET_TYPE;
	}
}