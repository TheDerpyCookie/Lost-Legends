package sabledream.studios.lostlegends.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import sabledream.studios.lostlegends.entity.animation.BlinkManager;
import sabledream.studios.lostlegends.init.LostLegendsSoundEvents;

public class VilerWitchEntity extends WitchEntity
{
	public final BlinkManager blinkManager;

	private int castingTicks = 0;

	public VilerWitchEntity(EntityType<? extends WitchEntity> entityType, World world) {
		super(entityType, world);
		blinkManager = new BlinkManager();
	}


	@Override
	public void tickMovement() {
		super.tickMovement();
		blinkManager.tickBlink();
		if(castingTicks > 0) {
			--castingTicks;
		}
	}

	@Environment(EnvType.CLIENT)
	public int getCastingTicks() {
		return castingTicks;
	}

	@Override
	public void shootAt(LivingEntity target, float pullProgress) {
		if (isDrinking()) return;

		castingTicks = random.nextInt(20) + 40;
		Vec3d vec3d = target.getVelocity();
		double dX = target.getX() + vec3d.x - getX();
		double dY = target.getEyeY() - 1.1d - getY();
		double dZ = target.getZ() + vec3d.z - getZ();
		double distance = Math.sqrt(dX * dX + dZ * dZ);
		RegistryEntry<Potion> potion = Potions.HARMING;
		if (target instanceof RaiderEntity) {
			if (target.getHealth() <= 4.0F) {
				potion = Potions.HEALING;
			} else {
				potion = Potions.REGENERATION;
			}
			setTarget(null);
		} else if (distance >= 8.0D && !target.hasStatusEffect(StatusEffects.SLOWNESS)) {
			potion = Potions.SLOWNESS;
		} else if (target.getHealth() >= 8.0F && !target.hasStatusEffect(StatusEffects.POISON)) {
			potion = Potions.POISON;
		} else if (distance <= 3.0D && !target.hasStatusEffect(StatusEffects.WEAKNESS) && random.nextFloat() < 0.25F) {
			potion = Potions.WEAKNESS;
		}

		PotionEntity thrownPotion = new PotionEntity(this.getWorld(), this);
		if (potion == Potions.HARMING || potion == Potions.POISON) {
			thrownPotion.setItem(PotionContentsComponent.createStack(Items.LINGERING_POTION, potion));
		} else {
			thrownPotion.setItem(PotionContentsComponent.createStack(Items.SPLASH_POTION, potion));
		}

		thrownPotion.setPitch(thrownPotion.getPitch() - -20.0F);
		thrownPotion.setVelocity(dX, dY + (distance * 0.2D), dZ, 0.75F, 8.0F);

		if (!isSilent()) {
			getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), LostLegendsSoundEvents.VILER_WITCH_CASTING.get(), this.getSoundCategory(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
		}

		getWorld().spawnEntity(thrownPotion);
	}

}
