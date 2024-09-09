package sabledream.studios.lostlegends.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class TinyCactusBlock extends PlantBlock
{
	public static final MapCodec<TinyCactusBlock> CODEC = createCodec(TinyCactusBlock::new);
	protected static final VoxelShape NORMAL = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 13.0D, 13.0D);

	public TinyCactusBlock(Settings properties)
	{
		super(properties);
	}

	@Override
	public MapCodec<TinyCactusBlock> getCodec()
	{
		return CODEC;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView worldIn, BlockPos pos, ShapeContext selectionContext)
	{
		return NORMAL;
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView worldIn, BlockPos pos)
	{
		BlockState groundState = worldIn.getBlockState(pos.down());

		return groundState.isIn(BlockTags.SAND);
	}

	@Override
	public void onEntityCollision(BlockState p_51148_, World p_51149_, BlockPos p_51150_, Entity p_51151_)
	{
		if (p_51151_ instanceof PlayerEntity)
		{
			PlayerEntity playerEntity = (PlayerEntity) p_51151_;
			playerEntity.damage(p_51149_.getDamageSources().cactus(), 1.0F);
		}
	}

}