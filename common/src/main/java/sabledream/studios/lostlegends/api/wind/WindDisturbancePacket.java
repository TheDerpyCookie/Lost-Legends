package sabledream.studios.lostlegends.api.wind;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import sabledream.studios.lostlegends.LostLegends;


public record WindDisturbancePacket(
	Box affectedArea,
	Vec3d origin,
	WindDisturbanceLogic.SourceType disturbanceSourceType,
	Identifier id,
	long posOrID

) implements CustomPayload
{
	public static final Id<WindDisturbancePacket> PACKET_TYPE = new Id<>(
		LostLegends.makeID("wind_disturbance_packet")
	);
	public static final PacketCodec<RegistryByteBuf, WindDisturbancePacket> CODEC = PacketCodec.of(WindDisturbancePacket::write, WindDisturbancePacket::new);

	public WindDisturbancePacket(@NotNull RegistryByteBuf buf) {
		this(
			new Box(buf.readVec3d(), buf.readVec3d()),
			buf.readVec3d(),
			buf.readEnumConstant(WindDisturbanceLogic.SourceType.class),
			buf.readIdentifier(),
			buf.readLong()
		);
	}

	public void write(@NotNull RegistryByteBuf buf) {
		Box affectedArea = this.affectedArea();
		buf.writeVec3d(new Vec3d(affectedArea.minX, affectedArea.minY, affectedArea.minZ));
		buf.writeVec3d(new Vec3d(affectedArea.maxX, affectedArea.maxY, affectedArea.maxZ));
		buf.writeVec3d(this.origin());
		buf.writeEnumConstant(this.disturbanceSourceType());
		buf.writeIdentifier(this.id());
		buf.writeLong(this.posOrID());
	}

	@Override
	@NotNull
	public Id<?> getId() {
		return PACKET_TYPE;
	}
}