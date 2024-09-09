package sabledream.studios.lostlegends.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
//import sabledream.studios.lostlegends.block.entity.BurrowBlockEntity;
import sabledream.studios.lostlegends.block.entity.BurrowBlockEntity;
import sabledream.studios.lostlegends.entity.Meerkat;
//import sabledream.studios.lostlegends.init.LostLegendsBlockEntity;
import sabledream.studios.lostlegends.init.LostLegendsBlockEntityTypes;
import sabledream.studios.lostlegends.init.LostLegendsBlockSetTypes;
import sabledream.studios.lostlegends.init.LostLegendsBlockEntity;


import java.util.List;
import java.util.function.Predicate;

public class BurrowBlock extends BlockWithEntity
{
	public BurrowBlock(Settings settings){
		super(settings);
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return null;
	}


	public BlockRenderType getRenderType(BlockState state){
		return BlockRenderType.MODEL;
	}

	@Override
	public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
		return super.getPickStack(world, pos, state);
	}

	@Override
	public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		return super.onBreak(world, pos, state, player);
	}

	public void onStateReplaced(BlockState p_48713_, World p_48714_, BlockPos p_48715_, BlockState p_48716_, boolean p_48717_) {
		if (!p_48713_.isOf(p_48716_.getBlock())) {
			BlockEntity blockentity = p_48714_.getBlockEntity(p_48715_);
			if (blockentity instanceof BurrowBlockEntity) {
				if (p_48714_ instanceof ServerWorld) {
					((BurrowBlockEntity) blockentity).emptyAllLivingFromBurrow(null, BurrowBlockEntity.BurrowReleaseStatus.EMERGENCY);
					p_48714_.updateComparators(p_48715_, this);
				}
			}

			super.onStateReplaced(p_48713_, p_48714_, p_48715_, p_48716_, p_48717_);
		}
	}
	public void afterBreak(World p_49584_, PlayerEntity p_49585_, BlockPos p_49586_, BlockState p_49587_, BlockEntity p_49588_, ItemStack p_49589_) {
		super.afterBreak(p_49584_, p_49585_, p_49586_, p_49587_, p_49588_, p_49589_);
		if (!p_49584_.isClient && p_49588_ instanceof BurrowBlockEntity burrow) {

			burrow.emptyAllLivingFromBurrow(p_49585_, BurrowBlockEntity.BurrowReleaseStatus.EMERGENCY);
			p_49584_.updateComparators(p_49586_, this);
			this.angerNearbyMeerkats(p_49584_, p_49586_);
		}

	}

	private void angerNearbyMeerkats(World world, BlockPos position) {
		List<Meerkat> meerkats = world.getEntitiesByClass(Meerkat.class, new Box(position).expand(8.0D, 6.0D, 8.0D),
			meerkat -> meerkat.getTarget() == null);

		if (meerkats.isEmpty()) {
			return; // No Meerkats nearby, nothing to do
		}

		Predicate<PlayerEntity> playerPredicate = player -> player.isSneaking() && player.getHealth() > 0; // Example predicate logic

		List<PlayerEntity> players = world.getEntitiesByClass(PlayerEntity.class, new Box(position).expand(8.0D, 6.0D, 8.0D), playerPredicate);

		if (players.isEmpty()) {
			return; // Prevent Error when no players meeting the conditions are around
		}

		for (Meerkat meerkat : meerkats) {
			if (meerkat.getTarget() == null) {
				PlayerEntity randomPlayer = players.get(world.random.nextInt(players.size()));
				meerkat.setTarget(randomPlayer);
			}
		}
	}
	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
		return new BurrowBlockEntity(p_153215_, p_153216_);
	}
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World p_152180_, BlockState p_152181_, BlockEntityType<T> p_152182_) {
		return p_152180_.isClient ? null : validateTicker(p_152182_, LostLegendsBlockEntity.BURROW.get(), BurrowBlockEntity::serverTick);
	}
}
