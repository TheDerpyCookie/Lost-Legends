package sabledream.studios.lostlegends.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import sabledream.studios.lostlegends.block.impl.SnowLoggingUtils;
import sabledream.studios.lostlegends.entity.TumbleweedEntity;

public class TumbleweedBlock extends PlantBlock
{
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	public static final MapCodec<TumbleweedBlock> CODEC = createCodec(TumbleweedBlock::new);
	protected static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(1D, 0D, 1D, 15D, 14D, 15D);
	protected static final VoxelShape OUTLINE_SHAPE = Block.createCuboidShape(1D, 0D, 1D, 15D, 14D, 15D);


	public TumbleweedBlock(Settings settings) {
		super(settings);
		this.getStateManager().getDefaultState().with(WATERLOGGED, true);
	}

	@Override
	protected MapCodec<? extends TumbleweedBlock> getCodec() {
		return null;
	}


	@Override
	protected ItemActionResult onUseWithItem(
		ItemStack stack,
		BlockState state,
		World world,
		BlockPos pos,
		PlayerEntity player,
		Hand hand,
		BlockHitResult hit
	) {
		if (stack.isOf(Items.SHEARS)) {
			shear(world, pos, player);
			stack.damage(1, player, LivingEntity.getSlotForHand(hand));
			return ItemActionResult.success(world.isClient);
		} else {
			return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
		}
	}
	public static boolean shear(World world, BlockPos pos, Entity entity){
		if (!world.isClient){
			TumbleweedEntity.spawnFromShears(world, pos);
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
			world.emitGameEvent(entity, GameEvent.SHEAR, pos);
		}
		return true;
	}

	@Override
	public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState state = SnowLoggingUtils.getSnowPlacementState(super.getPlacementState(ctx), ctx);
		if(state != null) {
			FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
			boolean waterlogged = fluidState.getFluid() == Fluids.WATER;
			return state.with(WATERLOGGED, waterlogged);
		}
		return null;
	}

	@Override
	protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return state.isIn(BlockTags.DEAD_BUSH_MAY_PLACE_ON);
	}

	@Override
	protected BlockState getStateForNeighborUpdate(
		BlockState state,
		Direction direction,
		BlockState neighborState,
		WorldAccess world,
		BlockPos pos,
		BlockPos neighborPos
	) {
		if(state.get(WATERLOGGED)) {
			world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}
		return super.getStateForNeighborUpdate(state,direction,neighborState,world,pos,neighborPos);
	}

	public  VoxelShape getCollisionShape(BlockState state, BlockView level,BlockPos pos, ShapeContext context)  {
		return COLLISION_SHAPE;
	}

	public  VoxelShape getOutlineShape(BlockState state, BlockView level,BlockPos pos, ShapeContext context)  {
		return OUTLINE_SHAPE;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(WATERLOGGED);
	}
}