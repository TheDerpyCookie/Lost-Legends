package sabledream.studios.lostlegends.api;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.PlayerAssociatedNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerChunkLoadingManager;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.chunk.ChunkManager;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

public final class LostLegendsPlayer {
	public static Collection<ServerPlayerEntity> all(MinecraftServer server) {
		Objects.requireNonNull(server, "The server cannot be null");
		return (Collection)(server.getPlayerManager() != null ? Collections.unmodifiableCollection(server.getPlayerManager().getPlayerList()) : Collections.emptyList());
	}

	public static Collection<ServerPlayerEntity> world(ServerWorld world) {
		Objects.requireNonNull(world, "The world cannot be null");
		return Collections.unmodifiableCollection(world.getPlayers());
	}

	public static Collection<ServerPlayerEntity> tracking(ServerWorld world, ChunkPos pos) {
		Objects.requireNonNull(world, "The world cannot be null");
		Objects.requireNonNull(pos, "The chunk pos cannot be null");
		return world.getChunkManager().chunkLoadingManager.getPlayersWatchingChunk(pos, false);
	}

	public static Collection<ServerPlayerEntity> tracking(BlockEntity blockEntity) {
		Objects.requireNonNull(blockEntity, "BlockEntity cannot be null");
		if (blockEntity.hasWorld() && !blockEntity.getWorld().isClient()) {
			return tracking((ServerWorld)blockEntity.getWorld(), blockEntity.getPos());
		} else {
			throw new IllegalArgumentException("Only supported on server worlds!");
		}
	}

	public static Collection<ServerPlayerEntity> tracking(ServerWorld world, BlockPos pos) {
		Objects.requireNonNull(pos, "BlockPos cannot be null");
		return tracking(world, new ChunkPos(pos));
	}

	public static Collection<ServerPlayerEntity> around(ServerWorld world, Vec3d pos, double radius) {
		double radiusSq = radius * radius;
		return (Collection)world(world).stream().filter((p) -> {
			return p.squaredDistanceTo(pos) <= radiusSq;
		}).collect(Collectors.toList());
	}

	public static Collection<ServerPlayerEntity> around(ServerWorld world, Vec3i pos, double radius) {
		double radiusSq = radius * radius;
		return (Collection)world(world).stream().filter((p) -> {
			return p.squaredDistanceTo((double)pos.getX(), (double)pos.getY(), (double)pos.getZ()) <= radiusSq;
		}).collect(Collectors.toList());
	}

	private LostLegendsPlayer() {
	}
}

