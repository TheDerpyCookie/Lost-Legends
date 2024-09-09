package sabledream.studios.lostlegends.entity.ai.goal.muddypig;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import sabledream.studios.lostlegends.entity.MuddyPigEntity;

public class MuddyPigMoveToTargetGoal extends MoveToTargetPosGoal
{
	private final MuddyPigEntity muddyPig;

	public MuddyPigMoveToTargetGoal(MuddyPigEntity entity, double speed) {
		super(entity, speed, 16, 3);
		muddyPig = entity;
	}

	@Override
	public boolean canStart() {
		return !muddyPig.isInMuddyState() && super.canStart();
	}


	@Override
	public boolean shouldContinue() {
		if (muddyPig.isInMuddyState()) return false;
		if (tryingTime > 600) return false;

		return isTargetPos(muddyPig.getWorld(), getTargetPos());
	}

	@Override
	public boolean shouldResetPath() {
		return tryingTime % 100 == 0;
	}

	@Override
	protected boolean isTargetPos(WorldView worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos).getBlock();
		BlockState blockState = block.getDefaultState();

		return blockState.isOf(Blocks.MUD);
	}
}