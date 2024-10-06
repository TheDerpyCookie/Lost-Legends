package sabledream.studios.lostlegends.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sabledream.studios.lostlegends.api.TermiteManager;
import sabledream.studios.lostlegends.block.entity.BurrowBlockEntity;
import sabledream.studios.lostlegends.block.entity.TermiteMoundBlockEntity;
import sabledream.studios.lostlegends.init.LostLegendsBlockEntity;
import sabledream.studios.lostlegends.init.LostLegendsBlockEntityTypes;
import sabledream.studios.lostlegends.init.LostLegendsRegisterProperties;

public class TermiteMoundBlock extends BlockWithEntity
{
	public static final MapCodec<TermiteMoundBlock> CODEC =createCodec(TermiteMoundBlock::new);
	public static final int MIN_PLACEMENT_TICK_DELAY = 40;
	public static final int MAX_PLACEMENT_TICK_DELAY = 200;
	public static final int MIN_TICK_DELAY = 90;
	public static final int MAX_TICK_DELAY = 150;
	public static final int MIN_AWAKE_LIGHT_LEVEL = 7;


	public TermiteMoundBlock(@NotNull Settings settings) {
		super(settings);
		this.setDefaultState(
			this.getStateManager().getDefaultState()
				.with(LostLegendsRegisterProperties.NATURAL, false)
				.with(LostLegendsRegisterProperties.TERMITES_AWAKE, false)
				.with(LostLegendsRegisterProperties.CAN_SPAWN_TERMITE, false)
		);
	}

	public static boolean canTermitesWaken(@NotNull World level, @NotNull BlockPos pos) {
		return !shouldTermitesSleep(level, getLightLevel(level, pos));
	}

	public static boolean shouldTermitesSleep(@NotNull World level, int light) {
		return level.isNight() && light < MIN_AWAKE_LIGHT_LEVEL;
	}

	public static int getLightLevel(@NotNull World level, @NotNull BlockPos blockPos) {
		BlockPos.Mutable mutableBlockPos = blockPos.mutableCopy();
		int finalLight = 0;
		for (Direction direction : Direction.values()) {
			mutableBlockPos.move(direction);
			int newLight = !level.isRaining() ? level.getLightLevel(mutableBlockPos) : level.getLightLevel(LightType.BLOCK, mutableBlockPos);
			finalLight = Math.max(finalLight, newLight);
			mutableBlockPos.move(direction, -1);
		}
		return finalLight;
	}

	@Override
	protected MapCodec<? extends TermiteMoundBlock> getCodec() {
		return CODEC;
	}


	@Nullable
	@Override
	public BlockEntity createBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new TermiteMoundBlockEntity(pos, state);
	}


	@Override
	protected void appendProperties(@NotNull StateManager.Builder<Block, BlockState> builder) {
		builder.add(LostLegendsRegisterProperties.NATURAL, LostLegendsRegisterProperties.TERMITES_AWAKE, LostLegendsRegisterProperties.CAN_SPAWN_TERMITE);
	}

	@Override
	@NotNull
	public BlockState getStateForNeighborUpdate(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull WorldAccess level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
		boolean isSafe = TermiteManager.isPosSafeForTermites(level, neighborPos, neighborState);
		if (isSafe != state.get(LostLegendsRegisterProperties.TERMITES_AWAKE)) {
			state = state.with(LostLegendsRegisterProperties.TERMITES_AWAKE, isSafe);
		}
		if (isSafe != state.get(LostLegendsRegisterProperties.CAN_SPAWN_TERMITE)) {
			state = state.with(LostLegendsRegisterProperties.CAN_SPAWN_TERMITE, isSafe);
		}
		return state;
	}

	@Override
	public void onBlockAdded(@NotNull BlockState state, @NotNull World level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean movedByPiston) {
		level.scheduleBlockTick(pos, this, level.random.nextBetween(MIN_PLACEMENT_TICK_DELAY, MAX_PLACEMENT_TICK_DELAY));
	}


	@Override
	public void onStateReplaced(@NotNull BlockState state, @NotNull World level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean movedByPiston) {
		if (!state.isOf(newState.getBlock())) {
			if (level.getBlockEntity(pos) instanceof TermiteMoundBlockEntity termiteMoundBlockEntity) {
				termiteMoundBlockEntity.termiteManager.clearTermites(level);
			}
		}
		super.onStateReplaced(state, level, pos, newState, movedByPiston);
	}

	public void tick(@NotNull BlockState state, @NotNull ServerWorld level, @NotNull BlockPos pos, @NotNull Random random) {
		boolean areTermitesSafe = TermiteManager.areTermitesSafe(level, pos);
		boolean canAwaken = canTermitesWaken(level, pos) && areTermitesSafe;
		if (canAwaken != state.get(LostLegendsRegisterProperties.TERMITES_AWAKE)) {
			level.setBlockState(pos, state.with(LostLegendsRegisterProperties.TERMITES_AWAKE, canAwaken), NOTIFY_ALL);
		}
		if (areTermitesSafe != state.get(LostLegendsRegisterProperties.CAN_SPAWN_TERMITE)) {
			level.setBlockState(pos, state.with(LostLegendsRegisterProperties.CAN_SPAWN_TERMITE, areTermitesSafe), NOTIFY_ALL);
		}
		level.scheduleBlockTick(pos, this, random.nextBetween(MIN_TICK_DELAY, MAX_TICK_DELAY));
	}

	@Override
	@NotNull
	public BlockRenderType getRenderType(@NotNull BlockState blockState) {
		return BlockRenderType.MODEL;
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull World level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
		return !level.isClient ?
			validateTicker(type, LostLegendsBlockEntity.TERMITE_MOUND.get(), (worldx, pos, statex, blockEntity) ->
				blockEntity.tickServer(
					worldx,
					pos,
					statex.get(LostLegendsRegisterProperties.NATURAL),
					statex.get(LostLegendsRegisterProperties.TERMITES_AWAKE),
					statex.get(LostLegendsRegisterProperties.CAN_SPAWN_TERMITE)
				)
			)
			: validateTicker(type, LostLegendsBlockEntity.TERMITE_MOUND.get(), (worldx, pos, statex, blockEntity) -> blockEntity.tickClient());
	}
}

