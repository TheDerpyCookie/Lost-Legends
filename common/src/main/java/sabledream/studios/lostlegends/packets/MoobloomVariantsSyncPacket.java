package sabledream.studios.lostlegends.packets;

import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.api.MoobloomVariant;
import sabledream.studios.lostlegends.api.MoobloomVariantManager;
import sabledream.studios.lostlegends.events.lifecycle.DatapackSyncEvent;
import sabledream.studios.lostlegends.network.Packet;
import sabledream.studios.lostlegends.network.base.ClientboundPacketType;
import sabledream.studios.lostlegends.network.base.PacketType;
import com.mojang.serialization.DataResult;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public record MoobloomVariantsSyncPacket(
	List<MoobloomVariant> moobloomVariants) implements Packet<MoobloomVariantsSyncPacket>
{
	public static final Identifier ID = LostLegends.makeID("moobloom_variants_sync_packet");
	public static final ClientboundPacketType<MoobloomVariantsSyncPacket> TYPE = new MoobloomVariantsSyncPacket.Handler();

	public static void sendToClient(DatapackSyncEvent event) {
		MessageHandler.DEFAULT_CHANNEL.sendToPlayer(new MoobloomVariantsSyncPacket(MoobloomVariantManager.MOOBLOOM_VARIANT_MANAGER.getMoobloomVariants()), event.player());
	}

	@Override
	public PacketType<MoobloomVariantsSyncPacket> type() {
		return TYPE;
	}

	public static class Handler implements ClientboundPacketType<MoobloomVariantsSyncPacket>
	{
		@Override
		public Class<MoobloomVariantsSyncPacket> type() {
			return MoobloomVariantsSyncPacket.class;
		}

		@Override
		public Identifier id() {
			return ID;
		}

		@Override
		public Runnable handle(final MoobloomVariantsSyncPacket packet) {
			return () -> MoobloomVariantManager.MOOBLOOM_VARIANT_MANAGER.setMoobloomVariants(packet.moobloomVariants());
		}

		public MoobloomVariantsSyncPacket decode(final RegistryByteBuf buf) {
			List<MoobloomVariant> parsedMoobloomVariants = new ArrayList<>();

			NbtCompound data = buf.readNbt();

			if (data == null) {
				LostLegends.getLogger().error("Moobloom Variant packet is empty");
				return new MoobloomVariantsSyncPacket(parsedMoobloomVariants);
			}

			NbtList moobloomVariants = data.getList("moobloom_variants", NbtElement.COMPOUND_TYPE);

			for (NbtElement moobloomVariant : moobloomVariants) {
				DataResult<MoobloomVariant> parsedMoobloomVariant = MoobloomVariant.CODEC.parse(NbtOps.INSTANCE, moobloomVariant);
				parsedMoobloomVariant.error().ifPresent(error -> LostLegends.getLogger().error("Failed to parse Moobloom Variant packet entry: {}", error));
				parsedMoobloomVariant.result().ifPresent(parsedMoobloomVariants::add);
			}

			return new MoobloomVariantsSyncPacket(parsedMoobloomVariants);
		}

		public void encode(final MoobloomVariantsSyncPacket packet, final RegistryByteBuf buf) {
			NbtCompound data = new NbtCompound();
			NbtList parsedMoobloomVariants = new NbtList();

			for (MoobloomVariant moobloomVariant : packet.moobloomVariants()) {
				DataResult<NbtElement> parsedMoobloomVariant = MoobloomVariant.CODEC.encodeStart(NbtOps.INSTANCE, moobloomVariant);
				parsedMoobloomVariant.error().ifPresent(error -> LostLegends.getLogger().error("Failed to encode Moobloom Variant packet entry: {}", error));
				parsedMoobloomVariant.result().ifPresent(parsedMoobloomVariants::add);
			}

			data.put("moobloom_variants", parsedMoobloomVariants);
			buf.writeNbt(data);
		}
	}
}
