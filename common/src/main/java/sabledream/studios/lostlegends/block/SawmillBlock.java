package sabledream.studios.lostlegends.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.PlaceableOnWaterItem;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.block.AbstractBlock;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import sabledream.studios.lostlegends.screen.SawmillMenu;


public class SawmillBlock extends WaterBlock
{

	private static final Text CONTAINER_TITLE = Text.translatable("container.sawmill.sawmill");
	protected static final VoxelShape SHAPE_Z =
		VoxelShapes.union(Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 7.0, 16.0),
			Block.createCuboidShape(6.0, 7.0, 0.0, 10.0, 16.0, 16.0));
	protected static final VoxelShape SHAPE_X =
		VoxelShapes.union(Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 7.0, 16.0),
			Block.createCuboidShape(0.0, 7.0, 6.0, 16.0, 16.0, 10.0));

	protected static final VoxelShape SHAPE_Z_UP =
		VoxelShapes.union(Block.createCuboidShape(0.0, 9.0, 0.0, 16.0, 16.0, 16.0),
			Block.createCuboidShape(6.0, 0.0, 0.0, 10.0, 9.0, 16.0));
	protected static final VoxelShape SHAPE_X_UP =
		VoxelShapes.union(Block.createCuboidShape(0.0, 9.0, 0.0, 16.0, 16.0, 16.0),
			Block.createCuboidShape(0.0, 0.0, 6.0, 16.0, 9.0, 10.0));

	public static final BooleanProperty BOTTOM = Properties.BOTTOM;
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

	public SawmillBlock() {
		super(AbstractBlock.Settings.create().hardness(1)
			.sounds(BlockSoundGroup.WOOD).mapColor(MapColor.OAK_TAN).instrument(NoteBlockInstrument.BASS));
		this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH)
			.with(WATERLOGGED, false)
			.with(BOTTOM, true));

	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(FACING, BOTTOM);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		BlockState blockState = super.getPlacementState(context)
			.with(FACING, context.getHorizontalPlayerFacing().getOpposite());
		BlockPos blockPos = context.getBlockPos();
		Direction direction = context.getSide();
		return direction != Direction.DOWN && (direction == Direction.UP ||
											   context.getHitPos().y - blockPos.getY() <= 0.5) ? blockState :
			blockState.with(BOTTOM, false);
	}

	@Override
	protected ActionResult onUse(BlockState state, World level, BlockPos pos, PlayerEntity player, BlockHitResult hitResult) {
		if (level.isClient) {
			return ActionResult.SUCCESS;
		} else {
			player.openHandledScreen(state.createScreenHandlerFactory(level, pos));
			//player.awardStat(Stats.INTERACT_WITH_STONECUTTER);
			return ActionResult.CONSUME;
		}
	}

	@Nullable
	public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World level, BlockPos pos) {
		return new SimpleNamedScreenHandlerFactory((i, inventory, player) ->
			new SawmillMenu(i, inventory, ScreenHandlerContext.create(level, pos)), CONTAINER_TITLE);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView level, BlockPos pos, ShapeContext context) {
		if (state.get(BOTTOM)) {
			return state.get(FACING).getAxis() == Direction.Axis.X ? SHAPE_Z : SHAPE_X;
		} else {
			return state.get(FACING).getAxis() == Direction.Axis.X ? SHAPE_Z_UP : SHAPE_X_UP;
		}
	}

	@Override
	public boolean hasSidedTransparency(BlockState state) {
		return true;
	}

}

