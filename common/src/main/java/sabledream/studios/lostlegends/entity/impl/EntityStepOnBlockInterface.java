package sabledream.studios.lostlegends.entity.impl;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface EntityStepOnBlockInterface
{
	void LostLegends$onSteppedOnBlock(World level, BlockPos pos, BlockState state);

}
