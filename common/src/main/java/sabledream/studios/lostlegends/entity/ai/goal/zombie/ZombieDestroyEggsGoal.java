package sabledream.studios.lostlegends.entity.ai.goal.zombie;

import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.StepAndDestroyBlockGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import sabledream.studios.lostlegends.init.LostLegendsBlocks;

public class ZombieDestroyEggsGoal extends StepAndDestroyBlockGoal
{
	public ZombieDestroyEggsGoal(PathAwareEntity mob, double speed, int maxYDifference) {
		super(LostLegendsBlocks.CRAB_EGG.get(), mob, speed, maxYDifference);
	}

	@Override
	public boolean canStart() {
		// Add logic here to determine if the goal can start based on both blocks
		return super.canStart() || mob.getWorld().getBlockState(mob.getBlockPos()).getBlock() == LostLegendsBlocks.CRAB_EGG.get();
	}

	@Override
	public void tickStepping(WorldAccess world, BlockPos pos) {
		// Play sound for turtle egg
		if (world.getBlockState(pos).getBlock() == Blocks.TURTLE_EGG) {
			world.playSound(null, pos, SoundEvents.ENTITY_ZOMBIE_DESTROY_EGG, SoundCategory.HOSTILE, 0.5F, 0.9F + mob.getRandom().nextFloat() * 0.2F);
		}
		// Play sound for crab egg
		else if (world.getBlockState(pos).getBlock() == LostLegendsBlocks.CRAB_EGG.get()) {
			world.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + world.getRandom().nextFloat() * 0.2F);
		}
	}

	@Override
	public void onDestroyBlock(World world, BlockPos pos) {
		// Call super to retain functionality for turtle egg destruction
		super.onDestroyBlock(world, pos);
		// Add any additional logic if needed for crab egg destruction
	}

	@Override
	public double getDesiredDistanceToTarget() {
		return 1.14;
	}
}