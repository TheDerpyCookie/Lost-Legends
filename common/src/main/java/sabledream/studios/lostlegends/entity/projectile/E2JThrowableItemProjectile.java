package sabledream.studios.lostlegends.entity.projectile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public abstract class E2JThrowableItemProjectile extends ThrownItemEntity
{
	public E2JThrowableItemProjectile(EntityType<? extends E2JThrowableItemProjectile> entityType, World world) {
		super(entityType, world);
	}

	public E2JThrowableItemProjectile(EntityType<? extends E2JThrowableItemProjectile> entityType, LivingEntity owner, World world) {
		super(entityType, owner, world);
	}

	public E2JThrowableItemProjectile(EntityType<? extends E2JThrowableItemProjectile> entityType, double x, double y, double z, World world) {
		super(entityType, x, y, z, world);
	}

	@Override
	protected abstract Item getDefaultItem();

	@Environment(EnvType.CLIENT)
	private ParticleEffect getParticle() {
		ItemStack itemStack = this.getStack();
		return !itemStack.isEmpty() && !itemStack.isOf(this.getDefaultItem()) ? new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack) : ParticleTypes.SPIT;
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
			ParticleEffect particleOptions = getParticle();
			for (int i = 0; i < 8; ++i) {
				getWorld().addParticle(particleOptions, getX(), getY(), getZ(), 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		playSound(getHitSound(), 1.0F, 1.0F);
		Entity entity = entityHitResult.getEntity();
		entity.damage(entity.getDamageSources().thrown(this, getOwner()), 5.0F);
	}

	@Override
	protected void onCollision(HitResult hitResult) {
		super.onCollision(hitResult);
		if (!getWorld().isClient) {
			getWorld().sendEntityStatus(this, (byte) 3);
			discard();
		}
	}

	protected abstract SoundEvent getHitSound();
}
