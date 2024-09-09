package sabledream.studios.lostlegends.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import sabledream.studios.lostlegends.entity.MelonGolemEntity;
import sabledream.studios.lostlegends.init.LostLegendsBlocks;
import sabledream.studios.lostlegends.init.LostLegendsEntityTypes;

import java.util.function.Predicate;

public class CarvedMelonBlock extends HorizontalFacingBlock
{
	public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
	public static final MapCodec<CarvedMelonBlock> CODEC = createCodec(CarvedMelonBlock::new);
	private static final Predicate<BlockState> IS_MELON = blockState -> blockState != null && (blockState.isOf(LostLegendsBlocks.CARVED_MELON.get()) || blockState.isOf(LostLegendsBlocks.MELON_LANTERN.get()));
	@Nullable
	private BlockPattern melonGolemBase;
	@Nullable
	private BlockPattern melonGolemFull;

	public CarvedMelonBlock(Settings settings) {
		super(settings);
		setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH));
	}

	@Override
	@SuppressWarnings("deprecation")
	protected void onBlockAdded(BlockState state, World level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
		if (oldState.isOf(state.getBlock())) {
			return;
		}
		this.trySpawnGolem(level, pos);
	}

	public boolean canDispense(WorldView level, BlockPos pos) {
		return getOrCreateSnowGolemBase().searchAround(level, pos) != null;
	}

	private void trySpawnGolem(World level, BlockPos pos) {
		BlockPattern.Result Result = getOrCreateSnowGolemFull().searchAround(level, pos);
		if (Result != null) {
			MelonGolemEntity melonGolemEntity = LostLegendsEntityTypes.MELON_GOLEM.get().create(level);

			if (melonGolemEntity != null) {
				spawnGolemInWorld(level, Result, melonGolemEntity, Result.translate(0, 2, 0).getBlockPos());
			}
		}
	}

	public static void spawnGolemInWorld(World level, BlockPattern.Result patternMatch, MelonGolemEntity entity, BlockPos pos) {
		clearPatternBlocks(level, patternMatch);
		entity.refreshPositionAndAngles(pos.getX() + 0.5, pos.getY() + 0.05, pos.getZ() + 0.5, 0.0F, 0.0F);
		level.spawnEntity(entity);
		for (ServerPlayerEntity serverPlayer : level.getNonSpectatingEntities(ServerPlayerEntity.class, entity.getBoundingBox().expand(5.0D))) {
			Criteria.SUMMONED_ENTITY.trigger(serverPlayer, entity);
		}

		updatePatternBlocks(level, patternMatch);
	}

	public static void clearPatternBlocks(World level, BlockPattern.Result patternMatch) {
		for (int i = 0; i < patternMatch.getWidth(); ++i) {
			for (int j = 0; j < patternMatch.getHeight(); ++j) {
				CachedBlockPosition CachedBlockPosition = patternMatch.translate(i, j, 0);
				level.setBlockState(CachedBlockPosition.getBlockPos(), Blocks.AIR.getDefaultState(), 2);
				level.syncWorldEvent(2001, CachedBlockPosition.getBlockPos(), Block.getRawIdFromState(CachedBlockPosition.getBlockState()));
			}
		}
	}

	public static void updatePatternBlocks(World level, BlockPattern.Result patternMatch) {
		for (int i = 0; i < patternMatch.getWidth(); ++i) {
			for (int j = 0; j < patternMatch.getHeight(); ++j) {
				CachedBlockPosition CachedBlockPosition = patternMatch.translate(i, j, 0);
				level.updateNeighbors(CachedBlockPosition.getBlockPos(), Blocks.AIR);
			}
		}
	}

	public MapCodec<? extends HorizontalFacingBlock> getCodec() {
		return CODEC;
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext blockPlaceContext) {
		return this.getDefaultState().with(FACING, blockPlaceContext.getHorizontalPlayerFacing().getOpposite());
	}


	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	private BlockPattern getOrCreateSnowGolemBase() {
		if (melonGolemBase == null) {
			melonGolemBase = BlockPatternBuilder.start().aisle(" ", "#", "#").where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK))).build();
		}
		return melonGolemBase;
	}

	private BlockPattern getOrCreateSnowGolemFull() {
		if (melonGolemFull == null) {
			melonGolemFull = BlockPatternBuilder.start().aisle("^", "#", "#").where('^', CachedBlockPosition.matchesBlockState(IS_MELON)).where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK))).build();
		}
		return melonGolemFull;
	}

}