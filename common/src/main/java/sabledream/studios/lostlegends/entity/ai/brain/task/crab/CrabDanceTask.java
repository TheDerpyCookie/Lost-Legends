package sabledream.studios.lostlegends.entity.ai.brain.task.crab;

import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.server.world.ServerWorld;
import sabledream.studios.lostlegends.client.render.entity.animation.CrabAnimations;
import sabledream.studios.lostlegends.entity.CrabEntity;
import sabledream.studios.lostlegends.entity.pose.CrabEntityPose;
import sabledream.studios.lostlegends.init.LostLegendsMemoryModuleTypes;
import sabledream.studios.lostlegends.util.MovementUtil;

import java.util.Map;

public final class CrabDanceTask extends MultiTickTask<CrabEntity>
{
	private final static int DANCE_DURATION = CrabAnimations.DANCE.getAnimationLengthInTicks() * 60;

	public CrabDanceTask() {
		super(
			Map.of(
				LostLegendsMemoryModuleTypes.CRAB_IS_DANCING.get(), MemoryModuleState.VALUE_PRESENT,
				MemoryModuleType.BREED_TARGET, MemoryModuleState.VALUE_ABSENT,
				MemoryModuleType.TEMPTING_PLAYER, MemoryModuleState.VALUE_ABSENT,
				LostLegendsMemoryModuleTypes.CRAB_HAS_EGG.get(), MemoryModuleState.VALUE_ABSENT,
				LostLegendsMemoryModuleTypes.CRAB_BURROW_POS.get(), MemoryModuleState.VALUE_ABSENT
			), DANCE_DURATION
		);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, CrabEntity crab) {
		return !crab.isClimbing()
			   && crab.isDancing();
	}

	@Override
	protected void run(ServerWorld world, CrabEntity crab, long time) {
		MovementUtil.stopMovement(crab);
		crab.startDanceAnimation();
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, CrabEntity crab, long time) {
		return !crab.isClimbing()
			   && crab.isDancing();
	}

	@Override
	protected void finishRunning(ServerWorld world, CrabEntity crab, long time) {
		crab.setPose(CrabEntityPose.IDLE);
	}
}