package sabledream.studios.lostlegends.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Unique;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.entity.FurnaceGolemEntity;
import sabledream.studios.lostlegends.entity.TuffGolemEntity;
import sabledream.studios.lostlegends.init.LostLegendsEntityTypes;
import sabledream.studios.lostlegends.util.CopperGolemBuildPatternPredicates;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.SpawnReason;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(CarvedPumpkinBlock.class)
public abstract class CarvedPumpkinBlockMixin extends HorizontalFacingBlock
{
	@Nullable
	private BlockPattern LostLegends_copperGolemDispenserPattern;

	@Nullable
	private BlockPattern LostLegends_tuffGolemDispenserPattern;

	@Nullable
	private BlockPattern LostLegends_tuffGolemPattern;

	@Final
	private static Predicate<BlockState> PUMPKINS_PREDICATE;

	@Unique
	private BlockPattern LostLegends_furnaceGolemPattern;
	@Unique
	private BlockPattern LostLegends_furnaceGolemDispenserPattern;


	private static final Predicate<BlockState> IS_TUFF_GOLEM_HEAD_PREDICATE = state -> state != null && (
		state.isOf(Blocks.CARVED_PUMPKIN)
		|| state.isOf(Blocks.JACK_O_LANTERN)
	);

	private static final Predicate<BlockState> IS_TUFF_GOLEM_WOOL_PREDICATE = state -> state != null && (
		state.isIn(BlockTags.WOOL)
	);

	protected CarvedPumpkinBlockMixin(Settings settings) {
		super(settings);
	}

	@Inject(
		method = "canDispense",
		at = @At("HEAD"),
		cancellable = true
	)
	public void LostLegends_canDispense(WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (this.getTuffGolemDispenserPattern().searchAround(world, pos) != null || this.getCopperGolemDispenserPattern().searchAround(world, pos) != null) {
			cir.setReturnValue(true);
		}
	}

	@Inject(
		method = "onBlockAdded",
		at = @At("HEAD")
	)
	private void LostLegends_customOnBlockAdded(
		BlockState state,
		World world,
		BlockPos pos,
		BlockState oldState,
		boolean notify,
		CallbackInfo ci
	) {
		if (!oldState.isOf(state.getBlock())) {
			this.LostLegends_tryToSpawnTuffGolem(
				world,
				pos
			);
		}
	}

	private void LostLegends_tryToSpawnTuffGolem(
		World world,
		BlockPos pos
	) {
		if (!LostLegends.getConfig().enableTuffGolem) {
			return;
		}

		BlockPattern.Result patternSearchResult = this.LostLegends_getTuffGolemPattern().searchAround(world, pos);

		if (patternSearchResult == null) {
			return;
		}

		BlockState headBlockState = patternSearchResult.translate(0, 0, 0).getBlockState();
		BlockState woolBlockState = patternSearchResult.translate(0, 1, 0).getBlockState();

		for (int i = 0; i < this.LostLegends_getTuffGolemPattern().getHeight(); ++i) {
			CachedBlockPosition cachedBlockPosition = patternSearchResult.translate(0, i, 0);
			world.setBlockState(
				cachedBlockPosition.getBlockPos(),
				Blocks.AIR.getDefaultState(),
				Block.NOTIFY_LISTENERS
			);
			world.syncWorldEvent(
				WorldEvents.BLOCK_BROKEN,
				cachedBlockPosition.getBlockPos(),
				Block.getRawIdFromState(cachedBlockPosition.getBlockState())
			);
		}

		BlockPos cachedBlockPosition = patternSearchResult.translate(0, 2, 0).getBlockPos();
		float tuffGolemYaw = headBlockState.get(CarvedPumpkinBlock.FACING).asRotation();

		TuffGolemEntity tuffGolem = LostLegendsEntityTypes.TUFF_GOLEM.get().create(world);

		tuffGolem.setPosition(
			(double) cachedBlockPosition.getX() + 0.5D,
			(double) cachedBlockPosition.getY() + 0.05D,
			(double) cachedBlockPosition.getZ() + 0.5D
		);
		tuffGolem.setSpawnYaw(tuffGolemYaw);
		tuffGolem.setColor(TuffGolemEntity.Color.fromWool(woolBlockState.getBlock()));
		tuffGolem.initialize((ServerWorldAccess) world, world.getLocalDifficulty(cachedBlockPosition), SpawnReason.TRIGGERED, null);
		world.spawnEntity(tuffGolem);

		for (ServerPlayerEntity serverPlayerEntity : world.getNonSpectatingEntities(
			ServerPlayerEntity.class,
			tuffGolem.getBoundingBox().expand(5.0D)
		)) {
			Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity, tuffGolem);
		}

		for (int j = 0; j < this.LostLegends_getTuffGolemPattern().getHeight(); ++j) {
			CachedBlockPosition cachedBlockPosition2 = patternSearchResult.translate(0, j, 0);
			world.updateNeighbors(cachedBlockPosition2.getBlockPos(), Blocks.AIR);
		}
	}

	private BlockPattern getCopperGolemDispenserPattern() {
		if (this.LostLegends_copperGolemDispenserPattern == null) {
			this.LostLegends_copperGolemDispenserPattern = BlockPatternBuilder.start()
				.aisle("|", " ", "#")
				.where('|', CachedBlockPosition.matchesBlockState(CopperGolemBuildPatternPredicates.IS_COPPER_GOLEM_LIGHTNING_ROD_PREDICATE))
				.where('#', CachedBlockPosition.matchesBlockState(CopperGolemBuildPatternPredicates.IS_COPPER_GOLEM_BODY_PREDICATE))
				.build();
		}

		return this.LostLegends_copperGolemDispenserPattern;
	}

	private BlockPattern getTuffGolemDispenserPattern() {
		if (this.LostLegends_tuffGolemDispenserPattern == null) {
			this.LostLegends_tuffGolemDispenserPattern = BlockPatternBuilder.start()
				.aisle(" ", "|", "#")
				.where('|', CachedBlockPosition.matchesBlockState(IS_TUFF_GOLEM_WOOL_PREDICATE))
				.where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.TUFF)))
				.build();
		}

		return this.LostLegends_tuffGolemDispenserPattern;
	}

	private BlockPattern LostLegends_getTuffGolemPattern() {
		if (this.LostLegends_tuffGolemPattern == null) {
			this.LostLegends_tuffGolemPattern = BlockPatternBuilder.start()
				.aisle("^", "|", "#")
				.where('^', CachedBlockPosition.matchesBlockState(IS_TUFF_GOLEM_HEAD_PREDICATE))
				.where('|', CachedBlockPosition.matchesBlockState(IS_TUFF_GOLEM_WOOL_PREDICATE))
				.where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.TUFF)))
				.build();
		}

		return this.LostLegends_tuffGolemPattern;
	}
	@Unique
	private BlockPattern LostLegends_getFurnaceGolemPattern() {
		if (LostLegends_furnaceGolemPattern == null) {
			LostLegends_furnaceGolemPattern = BlockPatternBuilder.start().aisle("~^~", "#@#", "~#~").where('@', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.BLAST_FURNACE))).where('^', CachedBlockPosition.matchesBlockState(PUMPKINS_PREDICATE)).where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.IRON_BLOCK))).where('~', pos -> pos.getBlockState().isAir()).build();
		}
		return LostLegends_furnaceGolemPattern;
	}

	@Unique
	private BlockPattern LostLegends_getFurnaceGolemDispenserPattern() {
		if (LostLegends_furnaceGolemDispenserPattern == null) {
			LostLegends_furnaceGolemDispenserPattern = BlockPatternBuilder.start().aisle("~ ~", "#@#", "~#~").where('@', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.BLAST_FURNACE))).where('#', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.IRON_BLOCK))).where('~', pos -> pos.getBlockState().isAir()).build();
		}
		return LostLegends_furnaceGolemDispenserPattern;
	}

	@Inject(method = "canDispense", at = @At("RETURN"), cancellable = true)
	public void LostLegends_canSpawnGolem(WorldView level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (!cir.getReturnValue()) {
			cir.setReturnValue(LostLegends_getFurnaceGolemDispenserPattern().searchAround(level, pos) != null);
		}
	}

	@Inject(at = @At("HEAD"), method = "trySpawnEntity")
	public void LostLegends_spawnFurnaceGolem(World level, BlockPos blockPos, CallbackInfo ci) {
		BlockPattern.Result result = LostLegends_getFurnaceGolemPattern().searchAround(level, blockPos);
		if (result == null) return;

		FurnaceGolemEntity furnaceGolemEntity = LostLegendsEntityTypes.FURNACE_GOLEM.get().create(level);
		if (furnaceGolemEntity == null) return;

		furnaceGolemEntity.setPlayerCreated(true);
		CarvedPumpkinBlock.spawnEntity(level, result, furnaceGolemEntity, result.translate(1,2,0).getBlockPos());
	}
}
