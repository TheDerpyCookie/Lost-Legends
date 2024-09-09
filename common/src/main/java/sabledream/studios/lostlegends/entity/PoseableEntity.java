package sabledream.studios.lostlegends.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface PoseableEntity<T extends CustomEntityPose>
{
	void setPose(EntityPose pose);

	EntityPose getPose();

	World getWorld();

	default void setPose(T pose) {
		if (this.getWorld().isClient()) {
			return;
		}

		setPose(pose.get());
	}

	default boolean isInPose(EntityPose pose){
		return this.getPose() == pose;}

	default boolean isInPose(T pose) {
		return this.getPose() == pose.get();
	}

	EntityData initialize(
		ServerWorldAccess world,
		LocalDifficulty difficulty,
		SpawnReason spawnReason,
		@Nullable EntityData entityData,
		@Nullable NbtCompound entityNbt
	);
}
