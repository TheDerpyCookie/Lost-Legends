package sabledream.studios.lostlegends.mixin;

import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.client.render.entity.animation.KeyframeAnimation;
import sabledream.studios.lostlegends.entity.CopperGolemEntity;
import sabledream.studios.lostlegends.entity.ai.brain.CopperGolemBrain;
import sabledream.studios.lostlegends.entity.pose.CopperGolemEntityPose;
import sabledream.studios.lostlegends.init.LostLegendsBlocks;
import sabledream.studios.lostlegends.init.LostLegendsEntityTypes;
import sabledream.studios.lostlegends.util.CopperGolemBuildPatternPredicates;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(LightningRodBlock.class)
public abstract class LightningRodBlockMixin extends LightningRodBlockBlockMixin
{
	@Nullable
	private BlockPattern LostLegends_copperGolemPattern;

	@Inject(method = "onBlockAdded", at = @At("HEAD"))
	private void LostLegends_onBlockAdded(
		BlockState state,
		World world,
		BlockPos pos,
		BlockState oldState,
		boolean notify,
		CallbackInfo ci
	) {
		if (!oldState.isOf(state.getBlock())) {
			this.LostLegends_tryToSpawnCopperGolem(
				world,
				pos
			);
		}
	}

	private void LostLegends_tryToSpawnCopperGolem(
		World world,
		BlockPos pos
	) {
		if (!LostLegends.getConfig().enableCopperGolem) {
			return;
		}

		BlockPattern.Result patternSearchResult = this.LostLegends_getCopperGolemPattern().searchAround(world, pos);

		if (patternSearchResult == null) {
			return;
		}

		BlockState lightningRodBlockState = patternSearchResult.translate(0, 0, 0).getBlockState();
		BlockState headBlockState = patternSearchResult.translate(0, 1, 0).getBlockState();
		BlockState bodyBlockState = patternSearchResult.translate(0, 2, 0).getBlockState();

		Oxidizable.OxidationLevel lightningRodOxidationLevel;

		if (lightningRodBlockState.isOf(LostLegendsBlocks.WAXED_LIGHTNING_ROD.get())) {
			lightningRodOxidationLevel = Oxidizable.OxidationLevel.UNAFFECTED;
		} else if (lightningRodBlockState.isOf(LostLegendsBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get())) {
			lightningRodOxidationLevel = Oxidizable.OxidationLevel.WEATHERED;
		} else if (lightningRodBlockState.isOf(LostLegendsBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get())) {
			lightningRodOxidationLevel = Oxidizable.OxidationLevel.EXPOSED;
		} else if (lightningRodBlockState.isOf(LostLegendsBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get())) {
			lightningRodOxidationLevel = Oxidizable.OxidationLevel.OXIDIZED;
		} else {
			lightningRodOxidationLevel = ((Oxidizable) lightningRodBlockState.getBlock()).getDegradationLevel();
		}

		Oxidizable.OxidationLevel bodyOxidationLevel;

		if (bodyBlockState.isOf(Blocks.WAXED_COPPER_BLOCK)) {
			bodyOxidationLevel = Oxidizable.OxidationLevel.UNAFFECTED;
		} else if (bodyBlockState.isOf(Blocks.WAXED_WEATHERED_COPPER)) {
			bodyOxidationLevel = Oxidizable.OxidationLevel.WEATHERED;
		} else if (bodyBlockState.isOf(Blocks.WAXED_EXPOSED_COPPER)) {
			bodyOxidationLevel = Oxidizable.OxidationLevel.EXPOSED;
		} else if (bodyBlockState.isOf(Blocks.WAXED_OXIDIZED_COPPER)) {
			bodyOxidationLevel = Oxidizable.OxidationLevel.OXIDIZED;
		} else {
			bodyOxidationLevel = ((OxidizableBlock) bodyBlockState.getBlock()).getDegradationLevel();
		}

		if (lightningRodOxidationLevel != bodyOxidationLevel) {
			return;
		}

		for (int i = 0; i < this.LostLegends_getCopperGolemPattern().getHeight(); ++i) {
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
		float copperGolemYaw = headBlockState.get(CarvedPumpkinBlock.FACING).asRotation();

		CopperGolemEntity copperGolem = LostLegendsEntityTypes.COPPER_GOLEM.get().create(world);

		copperGolem.setPosition(
			(double) cachedBlockPosition.getX() + 0.5D,
			(double) cachedBlockPosition.getY() + 0.05D,
			(double) cachedBlockPosition.getZ() + 0.5D
		);
		copperGolem.setSpawnYaw(copperGolemYaw);
		copperGolem.setOxidationLevel(bodyOxidationLevel);

		if (lightningRodOxidationLevel == Oxidizable.OxidationLevel.OXIDIZED) {
			ArrayList<CopperGolemEntityPose> possiblePoses = new ArrayList<>()
			{{
				add(CopperGolemEntityPose.SPIN_HEAD);
				add(CopperGolemEntityPose.PRESS_BUTTON_UP);
				add(CopperGolemEntityPose.PRESS_BUTTON_DOWN);
			}};
			int randomPoseIndex = copperGolem.getRandom().nextInt(possiblePoses.size());
			CopperGolemEntityPose randomPose = possiblePoses.get(randomPoseIndex);
			copperGolem.setPose(randomPose);
			KeyframeAnimation keyframeAnimation = copperGolem.getKeyframeAnimationByPose();

			if (keyframeAnimation != null) {
				int keyFrameAnimationLengthInTicks = keyframeAnimation.getAnimationLengthInTicks();
				int randomKeyframeAnimationTick = copperGolem.getRandom().nextBetween(keyFrameAnimationLengthInTicks / 6, keyFrameAnimationLengthInTicks - (keyFrameAnimationLengthInTicks / 6));
				copperGolem.setKeyframeAnimationTicks(randomKeyframeAnimationTick);
			}
		} else {
			boolean isHeadBlockWaxed = this.LostLegends_isCopperBlockWaxed(headBlockState);
			boolean isBodyBlockWaxed = this.LostLegends_isCopperBlockWaxed(bodyBlockState);
			boolean isWaxed = isHeadBlockWaxed && isBodyBlockWaxed;
			copperGolem.setIsWaxed(isWaxed);
		}

		CopperGolemBrain.setSpinHeadCooldown(copperGolem);
		CopperGolemBrain.setPressButtonCooldown(copperGolem);

		world.spawnEntity(copperGolem);

		for (ServerPlayerEntity serverPlayerEntity : world.getNonSpectatingEntities(
			ServerPlayerEntity.class,
			copperGolem.getBoundingBox().expand(5.0D)
		)) {
			Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity, copperGolem);
		}

		for (int j = 0; j < this.LostLegends_getCopperGolemPattern().getHeight(); ++j) {
			CachedBlockPosition cachedBlockPosition2 = patternSearchResult.translate(0, j, 0);
			world.updateNeighbors(cachedBlockPosition2.getBlockPos(), Blocks.AIR);
		}
	}

	private BlockPattern LostLegends_getCopperGolemPattern() {
		if (this.LostLegends_copperGolemPattern == null) {
			this.LostLegends_copperGolemPattern = BlockPatternBuilder.start()
				.aisle("|", "^", "#")
				.where('|', CachedBlockPosition.matchesBlockState(CopperGolemBuildPatternPredicates.IS_COPPER_GOLEM_LIGHTNING_ROD_PREDICATE))
				.where('^', CachedBlockPosition.matchesBlockState(CopperGolemBuildPatternPredicates.IS_GOLEM_HEAD_PREDICATE))
				.where('#', CachedBlockPosition.matchesBlockState(CopperGolemBuildPatternPredicates.IS_COPPER_GOLEM_BODY_PREDICATE))
				.build();
		}

		return this.LostLegends_copperGolemPattern;
	}

	private boolean LostLegends_isCopperBlockWaxed(
		BlockState blockState
	) {
		return blockState.isOf(Blocks.WAXED_COPPER_BLOCK)
			   || blockState.isOf(Blocks.WAXED_WEATHERED_COPPER)
			   || blockState.isOf(Blocks.WAXED_EXPOSED_COPPER)
			   || blockState.isOf(Blocks.WAXED_OXIDIZED_COPPER)
			   || blockState.isOf(LostLegendsBlocks.WAXED_LIGHTNING_ROD.get())
			   || blockState.isOf(LostLegendsBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get())
			   || blockState.isOf(LostLegendsBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get())
			   || blockState.isOf(LostLegendsBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get());
	}

	@Override
	public void LostLegends_hasRandomTicks(
		BlockState state, CallbackInfoReturnable<Boolean> cir
	) {
		cir.setReturnValue(Oxidizable.getIncreasedOxidationBlock(state.getBlock()).isPresent());
	}

	@Override
	public void LostLegends_randomTick(
		BlockState state,
		ServerWorld world,
		BlockPos pos,
		Random random,
		CallbackInfo ci
	) {
		((Degradable) this).tickDegradation(state, world, pos, random);
	}
}
