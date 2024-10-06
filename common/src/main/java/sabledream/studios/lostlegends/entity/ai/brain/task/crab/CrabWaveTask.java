package sabledream.studios.lostlegends.entity.ai.brain.task.crab;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.server.world.ServerWorld;
import sabledream.studios.lostlegends.client.render.entity.animation.CrabAnimations;
import sabledream.studios.lostlegends.entity.CrabEntity;
import sabledream.studios.lostlegends.entity.ai.brain.CrabBrain;
import sabledream.studios.lostlegends.entity.pose.CrabEntityPose;
import sabledream.studios.lostlegends.init.LostLegendsMemoryModuleTypes;
import sabledream.studios.lostlegends.util.MovementUtil;

import java.util.Map;

public final class CrabWaveTask extends MultiTickTask<CrabEntity>
{
	private final static int WAVE_DURATION = CrabAnimations.WAVE.getAnimationLengthInTicks();

	private LivingEntity nearestTarget;
	private int waveTicks = 0;
	private int maxWaveTicks = 0;

	public CrabWaveTask() {
		super(
			Map.of(
				MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleState.VALUE_PRESENT,
				MemoryModuleType.BREED_TARGET, MemoryModuleState.VALUE_ABSENT,
				MemoryModuleType.TEMPTING_PLAYER, MemoryModuleState.VALUE_ABSENT,
				LostLegendsMemoryModuleTypes.CRAB_WAVE_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT,
				LostLegendsMemoryModuleTypes.CRAB_HAS_EGG.get(), MemoryModuleState.VALUE_ABSENT,
				LostLegendsMemoryModuleTypes.CRAB_BURROW_POS.get(), MemoryModuleState.VALUE_ABSENT
			), WAVE_DURATION
		);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, CrabEntity crab) {
		if (crab.getNavigation().isFollowingPath()) {
			return false;
		}

		LivingEntity nearestTarget = crab.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER).orElse(null);

		if (nearestTarget == null) {
			return false;
		}

		this.nearestTarget = nearestTarget;

		return true;
	}

	@Override
	protected void run(ServerWorld world, CrabEntity crab, long time) {
		MovementUtil.stopMovement(crab);
		LookTargetUtil.lookAt(crab, this.nearestTarget);
		crab.getLookControl().lookAt(this.nearestTarget);
		crab.getLookControl().tick();

		this.waveTicks = 0;
		this.maxWaveTicks = WAVE_DURATION;
		crab.startWaveAnimation();
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, CrabEntity crab, long time) {
		return this.waveTicks <= this.maxWaveTicks;
	}

	protected void keepRunning(ServerWorld world, CrabEntity crab, long time) {
		this.waveTicks++;
	}

	@Override
	protected void finishRunning(ServerWorld world, CrabEntity crab, long time) {
		crab.setPose(CrabEntityPose.IDLE);
		CrabBrain.setWaveCooldown(crab);
	}
}