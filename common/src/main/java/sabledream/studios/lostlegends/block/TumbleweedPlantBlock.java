package sabledream.studios.lostlegends.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sabledream.studios.lostlegends.entity.TumbleweedEntity;
import sabledream.studios.lostlegends.init.LostLegendsBlocks;
import sabledream.studios.lostlegends.init.LostLegendsEntityTypes;
import sabledream.studios.lostlegends.init.LostLegendsSoundEvents;
import sabledream.studios.lostlegends.tag.LostLegendsTags;

public class TumbleweedPlantBlock extends PlantBlock implements Fertilizable
{
	public static final MapCodec<TumbleweedPlantBlock> CODEC = createCodec(TumbleweedPlantBlock::new);
	public static final int MAX_AGE = 3;
	public static final int AGE_FOR_SOLID_COLLISION = 2;
	public static final int RANDOM_TICK_CHANCE = 2;
	public static final int SNAP_CHANCE = 3;
	public static final int REPRODUCTION_CHANCE_PEACEFUL = 20;
	public static final int REPRODUCTION_CHANCE_DIVIDER_BY_DIFFICULTY = 15;
	public static final IntProperty AGE = Properties.AGE_3;
	private static final VoxelShape[] SHAPES = new VoxelShape[]{
		Block.createCuboidShape(3D, 0D, 3D, 12D, 9D, 12D),
		Block.createCuboidShape(2D, 0D, 2D, 14D, 12D, 14D),
		Block.createCuboidShape(1D, 0D, 1D, 15D, 14D, 15D),
		Block.createCuboidShape(1D, 0D, 1D, 15D, 14D, 15D)
	};

	public TumbleweedPlantBlock(@NotNull AbstractBlock.Settings properties) {
		super(properties);
	}

	public static boolean isFullyGrown(@NotNull BlockState state) {
		return state.get(AGE) == MAX_AGE;
	}

	@NotNull
	@Override
	protected MapCodec<? extends TumbleweedPlantBlock> getCodec() {
		return CODEC;
	}

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerWorld level, @NotNull BlockPos pos, @NotNull Random random) {
		if (random.nextInt(RANDOM_TICK_CHANCE) == 0) {
			if (isFullyGrown(state)) {
				if (random.nextInt(SNAP_CHANCE) == 0) {
					level.setBlockState(pos, state.cycle(AGE), NOTIFY_LISTENERS);
					TumbleweedEntity weed = new TumbleweedEntity(LostLegendsEntityTypes.TUMBLEWEED.get(), level);
					level.spawnEntity(weed);
					weed.setPosition(Vec3d.ofBottomCenter(pos));
					int diff = level.getDifficulty().getId();
					if (level.getRandom().nextInt(diff == 0 ? REPRODUCTION_CHANCE_PEACEFUL : (REPRODUCTION_CHANCE_DIVIDER_BY_DIFFICULTY / diff)) == 0) {
						weed.setItem(new ItemStack(LostLegendsBlocks.TUMBLEWEED_PLANT.get()), true);
					}
					level.playSound(null, pos, LostLegendsSoundEvents.ENTITY_TUMBLEWEED_DAMAGE.get(), SoundCategory.BLOCKS, 1F, 1F);
					level.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
					level.emitGameEvent(null, GameEvent.BLOCK_CHANGE, pos);
				}
			} else {
				level.setBlockState(pos, state.cycle(AGE), NOTIFY_LISTENERS);
			}
		}
	}

	@NotNull
	@Override
	public VoxelShape getCollisionShape(@NotNull BlockState blockState, @NotNull BlockView blockGetter, @NotNull BlockPos blockPos, @NotNull ShapeContext collisionContext) {
		return blockState.get(AGE) < AGE_FOR_SOLID_COLLISION ? VoxelShapes.empty() : super.getCollisionShape(blockState, blockGetter, blockPos, collisionContext);
	}

	@NotNull
	@Override
	public VoxelShape getOutlineShape(@NotNull BlockState state, @NotNull BlockView level, @NotNull BlockPos pos, @NotNull ShapeContext context) {
		return SHAPES[state.get(AGE)];
	}

	@Override
	protected boolean canPlantOnTop(@NotNull BlockState state, @NotNull BlockView level, @NotNull BlockPos pos) {
		return state.isIn(BlockTags.DEAD_BUSH_MAY_PLACE_ON) || state.isIn(LostLegendsTags.BUSH_MAY_PLACE_ON) || super.canPlantOnTop(state, level, pos);
	}

	@Override
	public boolean isFertilizable(@NotNull WorldView level, @NotNull BlockPos pos, @NotNull BlockState state) {
		return !isFullyGrown(state);
	}

	@Override
	public boolean canGrow(@NotNull World level, @NotNull Random random, @NotNull BlockPos pos, @NotNull BlockState state) {
		return true;
	}

	@Override
	public void grow(@NotNull ServerWorld level, @NotNull Random random, @NotNull BlockPos pos, @NotNull BlockState state) {
		level.setBlockState(pos, state.with(AGE, Math.min(MAX_AGE, state.get(AGE) + random.nextBetween(1, 2))));
	}

	@NotNull
	@Override
	public ItemActionResult onUseWithItem(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull World level, @NotNull BlockPos pos, @NotNull PlayerEntity player, @NotNull Hand hand, @NotNull BlockHitResult hit) {
		if (stack.isOf(Items.SHEARS) && shear(level, pos, state, player)) {
			stack.damage(1, player, LivingEntity.getSlotForHand(hand));
			return ItemActionResult.success(level.isClient);
		} else {
			return super.onUseWithItem(stack, state, level, pos, player, hand, hit);
		}
	}

	public static boolean shear(World level, BlockPos pos, @NotNull BlockState state, @Nullable Entity entity) {
		if (isFullyGrown(state)) {
			if (!level.isClient) {
				TumbleweedEntity.spawnFromShears(level, pos);
				level.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
				level.setBlockState(pos, state.with(AGE, 0));
				level.emitGameEvent(entity, GameEvent.SHEAR, pos);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void appendProperties(@NotNull StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(AGE);
	}
}