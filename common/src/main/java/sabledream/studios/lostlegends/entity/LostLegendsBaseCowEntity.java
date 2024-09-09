package sabledream.studios.lostlegends.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import sabledream.studios.lostlegends.entity.animation.BlinkManager;
import sabledream.studios.lostlegends.entity.animation.EntityVariantManager;

public abstract class LostLegendsBaseCowEntity extends CowEntity
{
	public  final BlinkManager blinkManager;

	private final EntityVariantManager<LostLegendsBaseCowEntity> variantManager;



	public LostLegendsBaseCowEntity(EntityType<? extends  CowEntity> type, World world){
		super(type,world);
		blinkManager = new BlinkManager();
		variantManager = new EntityVariantManager<>();
	}



	public static DefaultAttributeContainer.Builder createCowAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.20000000298023224D);
	}
	@Override
	public void tickMovement(){
		super.tickMovement();
		blinkManager.tickBlink();
	}
	@Override
	public LostLegendsBaseCowEntity createChild(ServerWorld world, PassiveEntity other){
		return variantManager.getChild(this, other).create(world);
	}


}
