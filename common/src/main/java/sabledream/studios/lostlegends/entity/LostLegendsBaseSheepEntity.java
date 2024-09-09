package sabledream.studios.lostlegends.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import sabledream.studios.lostlegends.entity.animation.BlinkManager;
import sabledream.studios.lostlegends.entity.animation.EntityVariantManager;

public class LostLegendsBaseSheepEntity extends SheepEntity
{

	public final BlinkManager blinkManager;
	private final EntityVariantManager<LostLegendsBaseSheepEntity> variantManager;

	public LostLegendsBaseSheepEntity(EntityType<? extends SheepEntity> type, World worldIn) {
		super(type, worldIn);
		blinkManager = new BlinkManager();
		variantManager = new EntityVariantManager<>();
	}

	public static DefaultAttributeContainer.Builder createSheepAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23000000417232513D);
	}

	@Override
	public void tickMovement() {
		super.tickMovement();
		blinkManager.tickBlink();
	}

	@Override
	public LostLegendsBaseSheepEntity createChild(ServerWorld serverWorld, PassiveEntity other) {
		return variantManager.getChild(this, other).create(serverWorld);
	}

}

