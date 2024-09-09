package sabledream.studios.lostlegends.world.server;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.UUID;


public class DeathTrackRequest {
	private String entityType;
	private UUID vultureUUID;
	private UUID ownerUUID;

	private BlockPos chunkPosition;

	public DeathTrackRequest(UUID vultureUUID, String entityType, UUID ownerUUID, BlockPos chunkPosition) {
		this.vultureUUID = vultureUUID;
		this.entityType = entityType;
		this.chunkPosition = chunkPosition;
		this.ownerUUID = ownerUUID;
	}

	public UUID getVultureUUID() {
		return vultureUUID;
	}

	public String getEntityTypeLoc() {
		return this.entityType;
	}

	public EntityType getEntityType() {
		return Registries.ENTITY_TYPE.get(Identifier.of(this.entityType));
	}

	public UUID getOwnerUUID() {
		return ownerUUID;
	}

	public BlockPos getChunkPosition() {
		return chunkPosition;
	}
}