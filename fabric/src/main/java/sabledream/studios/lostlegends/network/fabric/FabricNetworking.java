package sabledream.studios.lostlegends.network.fabric;

import sabledream.studios.lostlegends.network.Packet;
import sabledream.studios.lostlegends.network.base.ClientboundPacketType;
import sabledream.studios.lostlegends.network.base.Networking;
import sabledream.studios.lostlegends.network.base.PacketType;
import sabledream.studios.lostlegends.network.base.ServerboundPacketType;
import sabledream.studios.lostlegends.network.internal.NetworkPacketPayload;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

/**
 * Network related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public class FabricNetworking implements Networking
{
	private static final boolean IS_CLIENT = FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT);

	private final Identifier channel;

	public FabricNetworking(Identifier channel, int protocolVersion) {
		this.channel = channel.withSuffixedPath("/v" + protocolVersion);
	}

	@Override
	public <T extends Packet<T>> void register(ClientboundPacketType<T> type) {
		final var payloadType = type.type(this.channel);
		PayloadTypeRegistry.playS2C().register(payloadType, type.codec(payloadType));
		if (!IS_CLIENT) return;
		FabricClientNetworkHandler.register(payloadType, type);
	}

	@Override
	public <T extends Packet<T>> void register(ServerboundPacketType<T> type) {
		final var payloadType = type.type(this.channel);
		PayloadTypeRegistry.playC2S().register(payloadType, type.codec(payloadType));
		ServerPlayNetworking.registerGlobalReceiver(
			payloadType,
			(payload, context) -> type.handle(payload.packet()).accept(context.player())
		);
	}

	@Override
	public <T extends Packet<T>> void sendToServer(T message) {
		if (!IS_CLIENT) return;
		FabricClientNetworkHandler.send(this.channel, message);
	}

	@Override
	public <T extends Packet<T>> void sendToPlayer(T message, ServerPlayerEntity player) {
		ServerPlayNetworking.send(player, new NetworkPacketPayload<>(message, this.channel));
	}

	@Override
	public boolean canSendToPlayer(ServerPlayerEntity player, PacketType<?> type) {
		return ServerPlayNetworking.canSend(player, type.type(this.channel));
	}
}
