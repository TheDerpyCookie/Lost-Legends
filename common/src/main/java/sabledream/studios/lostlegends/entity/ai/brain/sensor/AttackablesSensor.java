package sabledream.studios.lostlegends.entity.ai.brain.sensor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.NearestVisibleLivingEntitySensor;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.tag.TagKey;

import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.NotNull;
import sabledream.studios.lostlegends.mixin.SensorAccessor;

import java.util.function.Predicate;

public class AttackablesSensor extends NearestVisibleLivingEntitySensor
{
	public final TagKey<EntityType<?>> alwaysHostiles;
	public final Predicate<LivingEntity> predicate;

	public AttackablesSensor(TagKey<EntityType<?>> alwaysHostiles, Predicate<LivingEntity> predicate, int interval) {
		((SensorAccessor) this).setScanRate(interval);
		((SensorAccessor) this).setTimeToTick(SensorAccessor.getRandom().nextInt(interval));
		this.alwaysHostiles = alwaysHostiles;
		this.predicate = predicate;
	}

	protected boolean matches(@NotNull LivingEntity entity, @NotNull LivingEntity otherEntity) {
		return this.isClose(entity, otherEntity) && isHostileTarget(otherEntity) && Sensor.testAttackableTargetPredicate(entity, otherEntity);
	}

	private boolean isHostileTarget(LivingEntity entity) {
		return entity.getType().isIn(alwaysHostiles);
	}

	private boolean isClose(LivingEntity entity, LivingEntity otherEntity) {
		double followRange = entity.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
		return otherEntity.squaredDistanceTo(entity) <= followRange * followRange;
	}


	protected void sense(@NotNull ServerWorld level, @NotNull LivingEntity entity) {
		if (predicate.test(entity)) super.sense(level, entity);
	}

	@NotNull
	protected MemoryModuleType<LivingEntity> getOutputMemoryModule() {
		return MemoryModuleType.NEAREST_ATTACKABLE;
	}
}