package sabledream.studios.lostlegends.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import sabledream.studios.lostlegends.entity.Ostrich;
import sabledream.studios.lostlegends.init.LostLegendsEntityTypes;
import sabledream.studios.lostlegends.util.RandomSource;

import java.util.List;
import java.util.Random;

public class OstrichEggBlock extends Block {

	public static final int MAX_HATCH_LEVEL = 2;
	private static final VoxelShape SHAPE = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 12.0D, 13.0D);
	public static final IntProperty HATCH = Properties.HATCH;

	public OstrichEggBlock(Settings p_57759_) {
		super(p_57759_);
		this.setDefaultState(this.stateManager.getDefaultState().with(HATCH, 0));
	}

	@Override
	public BlockState onBreak(World p_49852_, BlockPos p_49853_, BlockState p_49854_, PlayerEntity p_49855_) {
		super.onBreak(p_49852_, p_49853_, p_49854_, p_49855_);
		angerNearbyOstrichs(p_49855_);
		return p_49854_;
	}

	public static void angerNearbyOstrichs(PlayerEntity p_34874_) {
		List<Ostrich> list = p_34874_.getWorld().getNonSpectatingEntities(Ostrich.class, p_34874_.getBoundingBox().expand(16.0D));
		list.stream().filter((p_34881_) -> {
			return p_34881_.canSee(p_34874_) && !p_34874_.isCreative();
		}).forEach((p_34872_) -> {
			p_34872_.setAngryAt(p_34874_.getUuid());
			p_34872_.chooseRandomAngerTime();
		});
	}

	public VoxelShape getOutlineShape(BlockState p_57809_, BlockView p_57810_, BlockPos p_57811_, ShapeContext p_57812_) {
		return SHAPE;
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> p_57799_) {
		p_57799_.add(HATCH);
	}

	public void randomTick(BlockState p_222644_, ServerWorld p_222645_, BlockPos p_222646_, Random p_222647_) {
		if (p_222647_.nextFloat() < 0.45F) {
			int i = p_222644_.get(HATCH);
			if (i < 2) {
				p_222645_.playSound((PlayerEntity) null, p_222646_, SoundEvents.ENTITY_TURTLE_EGG_CRACK, SoundCategory.BLOCKS, 0.7F, 0.9F + p_222647_.nextFloat() * 0.2F);
				p_222645_.setBlockState(p_222646_, p_222644_.with(HATCH, Integer.valueOf(i + 1)), 2);
			} else {
				p_222645_.playSound((PlayerEntity) null, p_222646_, SoundEvents.ENTITY_TURTLE_EGG_HATCH, SoundCategory.BLOCKS, 0.7F, 0.9F + p_222647_.nextFloat() * 0.2F);
				p_222645_.removeBlock(p_222646_, false);

				p_222645_.syncWorldEvent(2001, p_222646_, Block.getRawIdFromState(p_222644_));
				Ostrich ostrich = LostLegendsEntityTypes.OSTRICH.get().create(p_222645_);
				ostrich.setBreedingAge(-24000);
				ostrich.refreshPositionAndAngles((double) p_222646_.getX() + 0.5D, (double) p_222646_.getY(), (double) p_222646_.getZ() + 0.5D, 0.0F, 0.0F);
				p_222645_.spawnEntity(ostrich);
			}
		}

	}
}
