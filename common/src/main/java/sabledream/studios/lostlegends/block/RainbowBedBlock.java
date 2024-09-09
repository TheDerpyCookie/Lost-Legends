package sabledream.studios.lostlegends.block;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import sabledream.studios.lostlegends.init.LostLegendsBlockEntity;
public class RainbowBedBlock extends BedBlock
{

	public RainbowBedBlock(DyeColor colorIn, Settings properties) {
		super(colorIn, properties);
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return LostLegendsBlockEntity.RAINBOW_BED.get().instantiate(pos, state);
	}
}

