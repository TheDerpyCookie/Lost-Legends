package sabledream.studios.lostlegends.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import sabledream.studios.lostlegends.entity.IceologerIceChunkEntity;
import sabledream.studios.lostlegends.init.LostLegendsEntityTypes;
import sabledream.studios.lostlegends.init.LostLegendsItems;

public class CustomSnowballEntity extends ThrownItemEntity
{

	public CustomSnowballEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
		super(entityType, world);
	}

	public CustomSnowballEntity(World world, LivingEntity owner) {
		super(LostLegendsEntityTypes.CUSTOM_SNOWBALL_ENTITY.get(), owner, world);  // You can replace this with a custom entity type if needed.
	}

	@Override
	public void tick() {
		super.tick();

		// Disable gravity
		this.setNoGravity(true);

		// Keep the snowball moving in a straight line
		if (!this.getWorld().isClient) {
			this.setVelocity(this.getVelocity().normalize().multiply(1.5)); // Constant speed
		}
	}

	@Override
	protected Item getDefaultItem() {
		return LostLegendsItems.ICE_SHARD.get();
	}

	@Override
	protected void onCollision(HitResult hitResult) {
		super.onCollision(hitResult);
		if (!this.getWorld().isClient) {
			// If we hit an entity, check for the collision type
			if (hitResult instanceof EntityHitResult entityHitResult) {
				// Check if we hit a LivingEntity
				if (entityHitResult.getEntity() instanceof LivingEntity target) {
					// Summon the IceologerIceChunkEntity at the target's position
					IceologerIceChunkEntity iceChunk = IceologerIceChunkEntity.createWithOwnerAndTarget(
						this.getWorld(),
						(PlayerEntity) this.getOwner(), // Cast owner to PlayerEntity
						target
					);
					iceChunk.setPosition(target.getX(), target.getY() + 1, target.getZ());
					this.getWorld().spawnEntity(iceChunk);
				}
			}
			// Remove the snowball from the world after impact
			this.discard();
		}
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		// Override to ensure snowball does not deal damage
		// No action here ensures no damage is applied
	}
}
