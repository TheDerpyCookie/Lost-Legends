package sabledream.studios.lostlegends.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import sabledream.studios.lostlegends.entity.ai.goal.fancychicken.FancyChickenFleeFromPigEntityGoal;
import sabledream.studios.lostlegends.init.LostLegendsSoundEvents;

import java.security.ProtectionDomain;

public class FancyChickenEntity extends LostLegendsBaseChickenEntity
{
	public FancyChickenEntity(EntityType<FancyChickenEntity> type, World world){
		super(type,world);
	}

	@Override
	protected void initGoals(){
		super.initGoals();
		goalSelector.add(3, new FancyChickenFleeFromPigEntityGoal(this, PigEntity.class, 6.0f, 1.0D, 1.2D ));
	}
	protected SoundEvent getAmbientSound() {
		return LostLegendsSoundEvents.FANCY_CHICKEN_AMBIENT.get();
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return LostLegendsSoundEvents.FANCY_CHICKEN_HURT.get();
	}

	protected SoundEvent getDeathSound() {
		return LostLegendsSoundEvents.FANCY_CHICKEN_DEATH.get();
	}
}
