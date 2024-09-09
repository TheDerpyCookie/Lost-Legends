package sabledream.studios.lostlegends.entity.ai.brain.task.tuffgolem;

import sabledream.studios.lostlegends.entity.TuffGolemEntity;
import sabledream.studios.lostlegends.entity.ai.brain.TuffGolemBrain;
import sabledream.studios.lostlegends.entity.pose.TuffGolemEntityPose;
import sabledream.studios.lostlegends.init.LostLegendsMemoryModuleTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.server.world.ServerWorld;

public final class TuffGolemSleepTask extends MultiTickTask<TuffGolemEntity>
{
	private final static int MIN_TICKS_TO_SLEEP = 6000;
	private final static int MAX_TICKS_TO_SLEEP = 12000;

	public TuffGolemSleepTask() {
		super(ImmutableMap.of(
			LostLegendsMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT
		), MIN_TICKS_TO_SLEEP, MAX_TICKS_TO_SLEEP);
	}

	@Override
	protected boolean shouldRun(
		ServerWorld world,
		TuffGolemEntity tuffGolem
	) {
		return tuffGolem.isAtHome()
			   && tuffGolem.getKeyframeAnimationTicks() == 0
			   && tuffGolem.getBrain().getOptionalMemory(LostLegendsMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get()).isEmpty();
	}

	@Override
	protected void run(
		ServerWorld world,
		TuffGolemEntity tuffGolem,
		long time
	) {
		tuffGolem.setPosition(tuffGolem.getHomePos());
		tuffGolem.setSpawnYaw(tuffGolem.getHomeYaw());

		if (tuffGolem.isInPose(TuffGolemEntityPose.STANDING.get())) {
			tuffGolem.startSleeping();
		} else if (tuffGolem.isInPose(TuffGolemEntityPose.STANDING_WITH_ITEM.get())) {
			tuffGolem.startSleepingWithItem();
		}
	}

	@Override
	protected boolean shouldKeepRunning(
		ServerWorld world,
		TuffGolemEntity tuffGolem,
		long time
	) {
		return tuffGolem.isInSleepingPose() && tuffGolem.getBrain().getOptionalMemory(LostLegendsMemoryModuleTypes.TUFF_GOLEM_SLEEP_COOLDOWN.get()).isEmpty();
	}

	@Override
	protected void finishRunning(
		ServerWorld world,
		TuffGolemEntity tuffGolem,
		long time
	) {
		TuffGolemBrain.setSleepCooldown(tuffGolem);
		tuffGolem.stopMovement();

		if (tuffGolem.isInPose(TuffGolemEntityPose.SLEEPING.get())) {
			tuffGolem.startStanding();
		} else if (tuffGolem.isInPose(TuffGolemEntityPose.SLEEPING_WITH_ITEM.get())) {
			tuffGolem.startStandingWithItem();
		}
	}
}
