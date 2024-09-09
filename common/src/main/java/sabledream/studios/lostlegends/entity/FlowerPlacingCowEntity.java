package sabledream.studios.lostlegends.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class FlowerPlacingCowEntity extends LostLegendsBaseCowEntity implements Shearable
{
public FlowerPlacingCowEntity(EntityType<? extends FlowerPlacingCowEntity> type, World world) {
	super(type, world);
}

@Override
public ActionResult interactMob(PlayerEntity player, Hand hand) {
	ItemStack itemStack = player.getStackInHand(hand);
	if (itemStack.getItem() instanceof ShearsItem && isShearable()) {
		sheared(SoundCategory.PLAYERS);
		if (!getWorld().isClient) {
			itemStack.damage(1, player,  getSlotForHand(hand));
		}
		return ActionResult.success(getWorld().isClient);
	}
	return super.interactMob(player, hand);
}

public void sheared(SoundCategory soundSource) {
	getWorld().playSound( this, getBlockPos(), SoundEvents.ENTITY_MOOSHROOM_SHEAR, soundSource, 1.0f, 1.0F);
	if (getWorld().isClient()) return;
	CowEntity cowEntity = EntityType.COW.create(getWorld());
	if (cowEntity == null) return;

	((ServerWorld) getWorld()).spawnParticles(ParticleTypes.EXPLOSION, getX(), getY(), getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
	discard();
	cowEntity.refreshPositionAndAngles(getX(), getY(), getZ(), getYaw(), getPitch());
	cowEntity.setHealth(getHealth());
	cowEntity.bodyYaw = bodyYaw;
	if (hasCustomName()) {
		cowEntity.setCustomName(getCustomName());
		cowEntity.setCustomNameVisible(isCustomNameVisible());
	}
	if (isPersistent()) {
		cowEntity.isPersistent();
	}
	cowEntity.setInvulnerable(isInvulnerable());
	getWorld().spawnEntity(cowEntity);

	dropCustomItems();
}
	public void dropCustomItems() {
    }
	@Override
	public boolean isShearable() {
		return isAlive() && !isBaby();
	}
}
