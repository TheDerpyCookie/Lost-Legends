package sabledream.studios.lostlegends.block.entity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.PlayerAssociatedNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerChunkLoadingManager;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.ChunkManager;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

public class LostLegendsPlayerLookup
{


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
}
