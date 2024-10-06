package sabledream.studios.lostlegends.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.client.gui.screen.ingame.StonecutterScreen;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.init.LostLegendsSoundEvents;
import sabledream.studios.lostlegends.item.api.axe.AxeBehaviors;

public class HollowedLogBlock extends PillarBlock implements Waterloggable
{
	public static final double HOLLOW_PARTICLE_DIRECTION_OFFSET = 0.3375D;
	public static final int HOLLOW_PARTICLES_MIN = 12;
	public static final int HOLLOW_PARTICLES_MAX = 28;
	public static final double ENTRANCE_DIRECTION_STEP_SCALE = 0.475D;
	private static final double HOLLOWED_AMOUNT = 0.71875D;
	private static final double EDGE_AMOUNT = 0.140625D;
	private static final double CRAWL_HEIGHT = EDGE_AMOUNT + HOLLOWED_AMOUNT;
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	public static final MapCodec<HollowedLogBlock> CODEC = createCodec(HollowedLogBlock::new);
	protected static final VoxelShape X_SHAPE = VoxelShapes.union(
		Block.createCuboidShape(0D, 0D, 0D, 16D, 16D, 3D),
		Block.createCuboidShape(0D, 13D, 0D, 16D, 16D, 16D),
		Block.createCuboidShape(0D, 0D, 13D, 16D, 16D, 16D),
		Block.createCuboidShape(0D, 0D, 0D, 16D, 3D, 16D)
	);
	protected static final VoxelShape Y_SHAPE = VoxelShapes.union(
		Block.createCuboidShape(0D, 0D, 0D, 16D, 16D, 3D),
		Block.createCuboidShape(0D, 0D, 0D, 3D, 16D, 16D),
		Block.createCuboidShape(0D, 0D, 13D, 16D, 16D, 16D),
		Block.createCuboidShape(13D, 0D, 0D, 16D, 16D, 16D)
	);
	protected static final VoxelShape Z_SHAPE = VoxelShapes.union(
		Block.createCuboidShape(13D, 0D, 0D, 16D, 16D, 16D),
		Block.createCuboidShape(0D, 0D, 0D, 3D, 16D, 16D),
		Block.createCuboidShape(0D, 13D, 0D, 16D, 16D, 16D),
		Block.createCuboidShape(0D, 0D, 0D, 16D, 3D, 16D)
	);
	protected static final VoxelShape X_COLLISION_SHAPE = VoxelShapes.union(
		Block.createCuboidShape(0D, 0, 0, 16, 16D, 2.25D),
		Block.createCuboidShape(0D, 13.75D, 0, 16D, 16D, 16D),
		Block.createCuboidShape(0D, 0D, 13D, 16D, 16D, 16D),
		Block.createCuboidShape(0D, 0D, 0D, 16D, 2.25D, 16D)
	);
	protected static final VoxelShape Y_COLLISION_SHAPE = VoxelShapes.union(
		Block.createCuboidShape(0D, 0D, 0D, 16D, 16D, 2.25D),
		Block.createCuboidShape(0D, 0D, 0D, 2.25D, 16D, 16),
		Block.createCuboidShape(0D, 0D, 13.75D, 16D, 16D, 16),
		Block.createCuboidShape(13.75D, 0D, 0D, 16D, 16D, 16)
	);
	protected static final VoxelShape Z_COLLISION_SHAPE = VoxelShapes.union(
		Block.createCuboidShape(13.75D, 0D, 0D, 16D, 16D, 16D),
		Block.createCuboidShape(0D, 0D, 0D, 2.25D, 16D, 16),
		Block.createCuboidShape(0D, 13.75D, 0D, 16D, 16D, 16),
		Block.createCuboidShape(0D, 0D, 0D, 16D, 2.25D, 16)
	);
	protected static final VoxelShape RAYCAST_SHAPE = VoxelShapes.fullCube();

	public HollowedLogBlock(@NotNull Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(AXIS, Direction.Axis.Y));
	}

	@Contract(value = "_, _ -> new", pure = true)
	public static AxeBehaviors.@NotNull AxeBehavior createHollowBehavior(
		@NotNull Block result,
		boolean isStem
	) {
		return new AxeBehaviors.AxeBehavior() {
			@Override
			public boolean meetsRequirements(WorldView level, BlockPos pos, Direction direction, BlockState state) {
				return LostLegends.getConfig().logHollowing && state.contains(Properties.AXIS) && direction.getAxis().equals(state.get(Properties.AXIS));
			}

			@Override
			public BlockState getOutputBlockState(BlockState state) {
				return result.getStateWithProperties(state);
			}

			@Override
			public void onSuccess(World level, BlockPos pos, Direction direction, BlockState state, BlockState oldState) {
				hollowEffects(level, direction, oldState, pos, isStem);
			}
		};
	}

	public static void hollowEffects(@NotNull World level, @NotNull Direction face, @NotNull BlockState state, @NotNull BlockPos pos, boolean isStem) {
		if (level instanceof ServerWorld serverLevel) {
			double offsetX = Math.abs(face.getOffsetX()) * HOLLOW_PARTICLE_DIRECTION_OFFSET;
			double offsetY = Math.abs(face.getOffsetY()) * HOLLOW_PARTICLE_DIRECTION_OFFSET;
			double offsetZ = Math.abs(face.getOffsetZ()) * HOLLOW_PARTICLE_DIRECTION_OFFSET;
			serverLevel.spawnParticles(
				new BlockStateParticleEffect(ParticleTypes.BLOCK, state),
				pos.getX() + 0.5D,
				pos.getY() + 0.5D,
				pos.getZ() + 0.5D,
				level.random.nextBetween(HOLLOW_PARTICLES_MIN, HOLLOW_PARTICLES_MAX),
				0.1625D + offsetX,
				0.1625D + offsetY,
				0.1625D + offsetZ,
				0.05D
			);
			SoundEvent hollowedSound = isStem ? LostLegendsSoundEvents.STEM_HOLLOWED.get() : LostLegendsSoundEvents.LOG_HOLLOWED.get();
			level.playSound(null, pos, hollowedSound, SoundCategory.BLOCKS, 0.7F, 0.95F + (level.random.nextFloat() * 0.2F));
		}
	}

	@NotNull
	@Override
	public MapCodec<? extends HollowedLogBlock> getCodec() {
		return CODEC;
	}

	@Override
	@NotNull
	public ActionResult onUse(@NotNull BlockState state, @NotNull World level, @NotNull BlockPos pos, @NotNull PlayerEntity player, @NotNull BlockHitResult hit) {
		Direction direction = player.getMovementDirection();
		Direction hitDirection = hit.getSide();
		Direction.Axis axis = state.get(Properties.AXIS);
		double crawlingHeight = player.getDimensions(EntityPose.SWIMMING).height();
		double playerY = player.getY();

		if (player.isSneaking()
			&& player.getPose() != EntityPose.SWIMMING
			&& !player.hasVehicle()
			&& direction.getAxis() != Direction.Axis.Y
			&& direction.getAxis() == axis
			&& player.getWidth() <= HOLLOWED_AMOUNT
			&& player.getHeight() >= HOLLOWED_AMOUNT
			&& player.getBlockPos().offset(direction).equals(pos)
			&& player.getPos().distanceTo(Vec3d.ofBottomCenter(pos)) <= (0.5D + player.getWidth())
			&& playerY >= pos.getY()
			&& playerY + crawlingHeight <= pos.getY() + CRAWL_HEIGHT
			&& hitDirection.getAxis() == axis
			&& hitDirection.getOpposite() == direction
		) {
			player.setPos(
				pos.getX() + 0.5D - direction.getOffsetX() * ENTRANCE_DIRECTION_STEP_SCALE,
				pos.getY() + EDGE_AMOUNT,
				pos.getZ() + 0.5D - direction.getOffsetZ() * ENTRANCE_DIRECTION_STEP_SCALE
			);
			player.setPose(EntityPose.SWIMMING);
			player.setSwimming(true);
			return ActionResult.SUCCESS;
		}
		return ActionResult.PASS;
	}


	@Override
	@NotNull
	public VoxelShape getOutlineShape(@NotNull BlockState state, @NotNull BlockView level, @NotNull BlockPos pos, @NotNull ShapeContext context) {
		return switch (state.get(AXIS)) {
			default -> X_SHAPE;
			case Y -> Y_SHAPE;
			case Z -> Z_SHAPE;
		};
	}

	@Override
	@NotNull
	public VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockView level, @NotNull BlockPos pos, @NotNull ShapeContext context) {
		return switch (state.get(AXIS)) {
			default -> X_COLLISION_SHAPE;
			case Y -> Y_COLLISION_SHAPE;
			case Z -> Z_COLLISION_SHAPE;
		};
	}

	@Override
	@NotNull
	public VoxelShape getRaycastShape(@NotNull BlockState state, @NotNull BlockView level, @NotNull BlockPos pos) {
		return RAYCAST_SHAPE;
	}

	@Override
	public BlockState getPlacementState(@NotNull ItemPlacementContext ctx) {
		BlockState superState = super.getPlacementState(ctx);
		return superState != null ? superState.with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).isOf(Fluids.WATER)) : null;
	}

	@Override
	@NotNull
	public BlockState getStateForNeighborUpdate(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull WorldAccess level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
		if (state.get(WATERLOGGED)) {
			level.scheduleFluidTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(level));
		}
		level.scheduleBlockTick(currentPos, this, 1);
		return super.getStateForNeighborUpdate(state, direction, neighborState, level, currentPos, neighborPos);
	}

	@Override
	@NotNull
	public FluidState getFluidState(@NotNull BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	@Override
	protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(WATERLOGGED);
	}

	@Override
	public boolean isTransparent(@NotNull BlockState state, @NotNull BlockView level, @NotNull BlockPos pos) {
		return !state.get(WATERLOGGED);
	}

	@Override
	public boolean hasSidedTransparency(@NotNull BlockState state) {
		return true;
	}
}
