package sabledream.studios.lostlegends.entity.ai.brain.sensor;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.NearestVisibleLivingEntitySensor;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import sabledream.studios.lostlegends.tag.LostLegendsTags;

public final class BarnacleAttackableSensor extends NearestVisibleLivingEntitySensor
{
	@Override
	protected boolean matches(LivingEntity entity, LivingEntity target) {
		return this.isInRange(entity, target) && target.isInsideWaterOrBubbleColumn() && (this.canHuntPrey(entity, target)) && Sensor.testAttackableTargetPredicate(entity, target);
	}

	private boolean canHuntPrey(LivingEntity barnacle, LivingEntity target) {
		return !barnacle.getBrain().hasMemoryModule(MemoryModuleType.HAS_HUNTING_COOLDOWN) && target.getType().isIn(LostLegendsTags.BARNACLE_PREY);
	}

	private boolean isInRange(LivingEntity axolotl, LivingEntity target) {
		return target.squaredDistanceTo(axolotl) <= 64.0;
	}

	@Override
	protected MemoryModuleType<LivingEntity> getOutputMemoryModule() {
		return MemoryModuleType.NEAREST_ATTACKABLE;
	}
}