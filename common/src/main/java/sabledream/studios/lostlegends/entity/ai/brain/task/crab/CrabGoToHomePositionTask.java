package sabledream.studios.lostlegends.entity.ai.brain.task.crab;

import net.minecraft.entity.ai.brain.task.MultiTickTask;
import sabledream.studios.lostlegends.entity.CrabEntity;
import sabledream.studios.lostlegends.init.LostLegendsMemoryModuleTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

//public final class CrabGoToHomePositionTask extends MultiTickTask<CrabEntity>
//{
//	private final static int GO_TO_HOME_POSITION_DURATION = 2400;
//
//	public CrabGoToHomePositionTask() {
//		super(ImmutableMap.of(
//			MemoryModuleType.LOOK_TARGET, MemoryModuleState.VALUE_ABSENT,
//			MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT,
//			LostLegendsMemoryModuleTypes.CRAB_HAS_EGG.get(), MemoryModuleState.VALUE_PRESENT
//		), GO_TO_HOME_POSITION_DURATION);
//	}
//
//	@Override
//	protected boolean shouldRun(
//		ServerWorld world,
//		CrabEntity crab
//	) {
//		if (
//			crab.isCloseToHomePos(3.0F)
//			|| crab.isLeashed()
//			|| crab.hasVehicle()
//		) {
//			return false;
//		}
//
//		return true;
//	}
//
//	@Override
//	protected void run(
//		ServerWorld serverWorld,
//		CrabEntity crab,
//		long l
//	) {
//		this.walkTowardsHomePos(crab);
//	}
//
//	@Override
//	protected boolean shouldKeepRunning(
//		ServerWorld world,
//		CrabEntity crab,
//		long time
//	) {
//		return !crab.isAtHomePos()
//			   && !crab.isLeashed()
//			   && !crab.hasVehicle();
//	}
//
//	@Override
//	protected void keepRunning(
//		ServerWorld world,
//		CrabEntity crab,
//		long time
//	) {
//		if (crab.getNavigation().isFollowingPath()) {
//			return;
//		}
//
//		this.walkTowardsHomePos(crab);
//	}
//
//	@Override
//	protected void finishRunning(
//		ServerWorld world,
//		CrabEntity crab,
//		long time
//	) {
//
//		crab.getBrain().forget(MemoryModuleType.LOOK_TARGET);
//		crab.getBrain().forget(MemoryModuleType.WALK_TARGET);
//	}
//
//	private void walkTowardsHomePos(
//		CrabEntity crab
//	) {
//		LookTargetUtil.walkTowards(
//			crab,
//			new BlockPos(crab.getHomePos()),
//			1.0F,
//			0
//		);
//	}
//}
