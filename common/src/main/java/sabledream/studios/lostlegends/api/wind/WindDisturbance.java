package sabledream.studios.lostlegends.api.wind;


import com.google.common.collect.ImmutableList;
import java.util.Optional;
//import net.frozenblock.lib.registry.api.FrozenRegistry;
//import net.frozenblock.lib.wind.impl.networking.WindDisturbancePacket;
//import net.minecraft.core.BlockPos;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.server.level.ChunkTrackingView;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.level.ChunkPos;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ChunkFilter;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class WindDisturbance<T> {
	public static final DisturbanceResult DUMMY_RESULT = new DisturbanceResult(0D, 0D, Vec3d.ZERO);

	private final Optional<T> source;
	public final Vec3d origin;
	public final Box affectedArea;
	private final WindDisturbanceLogic<T> disturbanceLogic;

	public WindDisturbance(Optional<T> source, Vec3d origin, Box affectedArea, WindDisturbanceLogic<T> disturbanceLogic) {
		this.source = source;
		this.origin = origin;
		this.affectedArea = affectedArea;
		this.disturbanceLogic = disturbanceLogic;
	}

	public DisturbanceResult calculateDisturbanceResult(World level, Vec3d windTarget) {
		if (this.affectedArea.contains(windTarget)) {
			DisturbanceResult disturbanceResult = this.disturbanceLogic.getLogic().calculateDisturbanceResult(
				this.source,
				level,
				this.origin,
				this.affectedArea,
				windTarget
			);
			if (disturbanceResult != null) {
				return disturbanceResult;
			}
		}
		return DUMMY_RESULT;
	}

	public boolean isWithinViewDistance(@NotNull ChunkFilter chunkTrackingView) {
		for (double xCorner : ImmutableList.of(this.affectedArea.minX, this.affectedArea.maxX)) {
			for (double zCorner : ImmutableList.of(this.affectedArea.minZ, this.affectedArea.maxZ)) {
				ChunkPos chunkPos = new ChunkPos(BlockPos.ofFloored(xCorner, 0, zCorner));
				if (chunkTrackingView.isWithinDistanceExcludingEdge(chunkPos.x, chunkPos.z)) {
					return true;
				}
			}
		}
		return false;
	}
//
//	public Optional<WindDisturbancePacket> toPacket() {
//		Identifier resourceLocation = Optional.ofNullable(FrozenRegistry.WIND_DISTURBANCE_LOGIC.getKey(this.disturbanceLogic))
//			.orElseGet(() -> FrozenRegistry.WIND_DISTURBANCE_LOGIC_UNSYNCED.getKey(this.disturbanceLogic));
//
//		if (resourceLocation != null) {
//			return Optional.of(
//				new WindDisturbancePacket(
//					this.affectedArea,
//					this.origin,
//					this.getSourceTypeFromSource(),
//					resourceLocation,
//					this.encodePosOrID()
//				)
//			);
//		}
//
//		return Optional.empty();
//	}

	private WindDisturbanceLogic.SourceType getSourceTypeFromSource() {
		if (this.source.isPresent()) {
			if (this.source.get() instanceof Entity) {
				return WindDisturbanceLogic.SourceType.ENTITY;
			} else if (this.source.get() instanceof BlockEntity) {
				return WindDisturbanceLogic.SourceType.BLOCK_ENTITY;
			}
		}
		return WindDisturbanceLogic.SourceType.NONE;
	}

	private long encodePosOrID() {
		if (this.source.isPresent()) {
			if (this.source.get() instanceof Entity entity) {
				return entity.getId();
			} else if (this.source.get() instanceof BlockEntity blockEntity) {
				return blockEntity.getPos().asLong();
			}
		}
		return 0L;
	}

	public record DisturbanceResult(double strength, double weight, Vec3d wind) {
	}
}