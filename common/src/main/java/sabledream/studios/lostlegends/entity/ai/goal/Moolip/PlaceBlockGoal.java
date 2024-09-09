package sabledream.studios.lostlegends.entity.ai.goal.Moolip;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public abstract class PlaceBlockGoal extends Goal
{
	private final Entity entity;

	public PlaceBlockGoal(Entity entity) {
		this.entity = entity;
	}

	public boolean canStart() {
		return entity.getRandom().nextInt(2000) == 0;
	}

	public boolean canPlace(WorldView world, BlockState target, BlockPos targetPos, BlockState downTarget, BlockPos downTargetPos) {
		return !downTarget.isAir() && downTarget.isFullCube(world, downTargetPos) && target.isAir() && target.canPlaceAt(world, targetPos);
	}

	@Override
	public void tick() {
		World world = entity.getWorld();
		int i = MathHelper.floor(entity.getX());
		int j = MathHelper.floor(entity.getY());
		int k = MathHelper.floor(entity.getZ());
		Block flower = getRandomFlower();
		BlockPos blockPos = new BlockPos(i, j, k);
		BlockState blockState = flower.getDefaultState();
		BlockPos blockDownPos = blockPos.down();
		BlockState blockDownState = world.getBlockState(blockDownPos);
		if (canPlace(world, blockState, blockPos, blockDownState, blockDownPos)) {
			entity.playSound(getPlantSound(), 1.0f, 1.0f);
			world.removeBlock(blockPos, false);
			world.setBlockState(blockPos, blockState, 3);
		}
	}

	protected abstract Block getRandomFlower();

	protected abstract SoundEvent getPlantSound();
}