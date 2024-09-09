package sabledream.studios.lostlegends.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import sabledream.studios.lostlegends.entity.animation.BlinkManager;
import sabledream.studios.lostlegends.entity.animation.EntityVariantManager;

public class LostLegendsBaseChickenEntity extends ChickenEntity
{
	public static BlinkManager blinkManager;

	private final EntityVariantManager<LostLegendsBaseChickenEntity> variantManager;

	public LostLegendsBaseChickenEntity(EntityType<? extends ChickenEntity> type, World worldIn) {
		super(type,worldIn);
		blinkManager = new BlinkManager();
		variantManager = new EntityVariantManager<>();
	}

	public static DefaultAttributeContainer.Builder createChickenAttributes(){
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 4.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D).add(EntityAttributes.GENERIC_SAFE_FALL_DISTANCE);
	}

	@Override
	public void tickMovement(){
		super.tickMovement();
		blinkManager.tickBlink();
	}

	@Override
	public LostLegendsBaseChickenEntity createChild(ServerWorld world, PassiveEntity other){
		return variantManager.getChild(this, other).create(world);
	}


}
