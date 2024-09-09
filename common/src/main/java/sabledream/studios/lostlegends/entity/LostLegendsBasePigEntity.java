package sabledream.studios.lostlegends.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import sabledream.studios.lostlegends.entity.animation.BlinkManager;
import sabledream.studios.lostlegends.entity.animation.EntityVariantManager;

public class LostLegendsBasePigEntity extends PigEntity
{
	public BlinkManager blinkManager;
	private EntityVariantManager<LostLegendsBasePigEntity> variantManager;

	public LostLegendsBasePigEntity(EntityType<? extends PigEntity> type, World world){
		super(type, world);
		blinkManager = new BlinkManager();
		variantManager = new EntityVariantManager<>();
	}

	@Override
	public void tickMovement() {
		super.tickMovement();
		blinkManager.tickBlink();;
	}

	@Nullable
	@Override
	public PigEntity createChild(ServerWorld world, PassiveEntity other) {
		return variantManager.getChild(this, other).create(world);
	}
}
