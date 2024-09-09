package sabledream.studios.lostlegends.entity;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.NoWaterTargeting;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;
//import sabledream.studios.lostlegends.block.entity.BurrowBlockEntity;
import sabledream.studios.lostlegends.block.entity.BurrowBlockEntity;
import sabledream.studios.lostlegends.entity.ai.pathing.WallClimbNavigation;
import sabledream.studios.lostlegends.init.LostLegendsBlocks;
import sabledream.studios.lostlegends.init.LostLegendsEntityTypes;
import sabledream.studios.lostlegends.tag.LostLegendsTags;

import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

public class Meerkat extends AnimalEntity {
	private static final TrackedData<Optional<UUID>> DATA_TRUSTED_ID_0 = DataTracker.registerData(Meerkat.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
	private static final TrackedData<Boolean> DATA_STANDING = DataTracker.registerData(Meerkat.class, TrackedDataHandlerRegistry.BOOLEAN);

	public static final EntityDimensions STANDING_DIMENSIONS = EntityDimensions.changing(0.5F, 1.0F);

	public final AnimationState standingAnimationState = new AnimationState();
	public final AnimationState stopStandingAnimationState = new AnimationState();
	public AnimationState diggingAnimationState = new AnimationState();
	public AnimationState digUpAnimationState = new AnimationState();


	BlockPos burrowPos;
	public int stayOutOfBurrowCountdown;

	public Meerkat(EntityType<? extends Meerkat> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(1, new SwimGoal(this));
		this.goalSelector.add(2, new MeerkatEnterBurrowGoal());
		this.goalSelector.add(2, new MeerkatDiggingUpGoal());
		this.goalSelector.add(4, new EscapeDangerGoal(this, 1.35D) {
			@Override
			public boolean canStart() {
				return isBaby() && super.canStart();
			}
		});
		this.goalSelector.add(4, new MeleeAttackGoal(this, 1.275D, true));
		this.goalSelector.add(5, new AnimalMateGoal(this, 0.85D));
		this.goalSelector.add(7, new MeerkatMakeBurrowGoal(this));
		this.goalSelector.add(8, new MeerkatGoToBurrowGoal());
		this.goalSelector.add(9, new StandGoal(this));
		this.goalSelector.add(10, new StopStandGoal(this));
		this.goalSelector.add(11, new WanderAroundFarGoal(this, 1.0D) {
			@Override
			public boolean canStart() {
				return !isStanding() && super.canStart();
			}

			@Override
			public boolean shouldContinue() {
				return !isStanding() && super.shouldContinue();
			}
		});
		this.goalSelector.add(12, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.targetSelector.add(1, (new RevengeGoal(this)).setGroupRevenge());
		this.targetSelector.add(2, new ActiveTargetGoal<>(this, Meerkat.class, true));

	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.26D)
			.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0F)
			.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 24.0F);
	}


	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(DATA_TRUSTED_ID_0, Optional.empty());
		builder.add(DATA_STANDING, false);
	}

	public void onTrackedDataSet(TrackedData<?> p_29615_) {
		if (DATA_STANDING.equals(p_29615_)) {
			this.calculateDimensions();
			if (this.isStanding()) {
				this.standingAnimationState.start(this.age);
				this.stopStandingAnimationState.stop();
			} else {
				this.stopStandingAnimationState.start(this.age);
				this.standingAnimationState.stop();
			}
		}

		if (POSE.equals(p_29615_)) {
			switch (this.getPose()) {
				case DIGGING:
					this.diggingAnimationState.start(this.age);
					break;
				case EMERGING:
					this.digUpAnimationState.start(this.age);
					break;
				default:
					diggingAnimationState.stop();
					digUpAnimationState.stop();
					break;
			}
		}

		super.onTrackedDataSet(p_29615_);
	}

	@Override
	public void tickMovement() {
		super.tickMovement();
		if (!this.getWorld().isClient) {
			if (this.stayOutOfBurrowCountdown > 0) {
				--this.stayOutOfBurrowCountdown;
			}
		}
	}

	public void tick() {
		super.tick();
		if (this.getWorld().isClient()) {

			switch (this.getPose()) {
				case DIGGING:
					this.clientDiggingParticles(this.diggingAnimationState);
				case EMERGING:
					this.clientDiggingParticles(this.diggingAnimationState);
			}
		}

	}

	private void clientDiggingParticles(AnimationState p_219384_) {
		if ((float) p_219384_.getTimeRunning() < 4500.0F) {
			Random randomsource = this.getRandom();
			BlockState blockstate = this.getSteppingBlockState();
			if (blockstate.getRenderType() != BlockRenderType.INVISIBLE) {
				for (int i = 0; i < 5; ++i) {
					double d0 = this.getX() + (double) MathHelper.nextBetween(randomsource, -0.3F, 0.3F);
					double d1 = this.getY();
					double d2 = this.getZ() + (double) MathHelper.nextBetween(randomsource, -0.3F, 0.3F);
					this.getWorld().addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockstate), d0, d1, d2, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}

	private boolean isDiggingOrEmerging() {
		return this.isInPose(EntityPose.DIGGING) || this.isInPose(EntityPose.EMERGING);
	}

	@Override
	public boolean canTarget(LivingEntity p_21171_) {
		return (this.getTrustedLeaderUUID() == null && p_21171_.getType() != LostLegendsEntityTypes.MEERKAT.get()) || (this.getTrustedLeaderUUID() != null && this.getTrustedLeaderUUID() != p_21171_.getUuid()) && super.canTarget(p_21171_);
	}

	public int getMaxSpawnClusterSize() {
		return 8;
	}

	public boolean isStanding() {
		return this.dataTracker.get(DATA_STANDING);
	}

	public void setStanding(boolean standing) {
		this.dataTracker.set(DATA_STANDING, standing);
	}

	@Nullable
	public UUID getTrustedLeaderUUID() {
		return this.dataTracker.get(DATA_TRUSTED_ID_0).orElse((UUID) null);
	}

	public void setTrustedLeaderUUID( UUID p_28516_) {
		this.dataTracker.set(DATA_TRUSTED_ID_0, Optional.ofNullable(p_28516_));
	}

	public void setBurrowPos(BlockPos burrowPos) {
		this.burrowPos = burrowPos;
	}


	public BlockPos getBurrowPos() {
		return burrowPos;
	}

	@VisibleForTesting
	public boolean hasBurrow() {
		return this.burrowPos != null;
	}

	//This thing write save datas(called nbt(CompoundTag) I guess)
	@Override
	public void writeCustomDataToNbt(NbtCompound p_28518_) {
		super.writeCustomDataToNbt(p_28518_);
		NbtList listtag = new NbtList();

		UUID uuid = this.getTrustedLeaderUUID();
		if (uuid != null) {
			listtag.add(NbtHelper.fromUuid(uuid));
		}

		if (this.hasBurrow()) {
			p_28518_.put("BurrowPos", NbtHelper.fromBlockPos(this.getBurrowPos()));
		}

		p_28518_.put("TrustedLeader", listtag);

		p_28518_.putInt("CannotEnterBurrowTicks", this.stayOutOfBurrowCountdown);
		p_28518_.putBoolean("Standing", this.isStanding());
	}

	//This thing read save datas(called nbt(CompoundTag) I guess)
	@Override
	public void readCustomDataFromNbt(NbtCompound p_28493_) {
		super.readCustomDataFromNbt(p_28493_);
		NbtList listtag = p_28493_.getList("Trusted", 11);

		for (int i = 0; i < listtag.size(); ++i) {
			this.setTrustedLeaderUUID(NbtHelper.toUuid(listtag.get(i)));
		}

		this.burrowPos = null;
		if (p_28493_.contains("BurrowPos")) {
			Optional<BlockPos> optionalBlockPos = NbtHelper.toBlockPos(p_28493_, "BurrowPos");
			this.burrowPos = optionalBlockPos.orElseThrow(() -> new IllegalArgumentException("BurrowPos not found in NBT data"));
		}

		this.stayOutOfBurrowCountdown = p_28493_.getInt("CannotEnterBurrowTicks");
		this.setStanding(p_28493_.getBoolean("Standing"));
	}

	@Nullable
	@Override
	public PassiveEntity createChild(ServerWorld p_146743_, PassiveEntity p_146744_) {
		Meerkat meerkat = LostLegendsEntityTypes.MEERKAT.get().create(p_146743_);

		UUID uuid = meerkat.getTrustedLeaderUUID();
		if (uuid != null) {
			meerkat.setTrustedLeaderUUID(uuid);
		}
		return meerkat;
	}

	public static boolean checkMeerkatSpawnRules(EntityType<? extends AnimalEntity> p_218105_, WorldAccess p_218106_, SpawnReason p_218107_, BlockPos p_218108_, Random p_218109_) {
		return p_218106_.getBlockState(p_218108_.down()).isIn(BlockTags.SAND) && isLightLevelValidForNaturalSpawn(p_218106_, p_218108_);
	}

	public EntityData initialize(ServerWorldAccess p_146746_, LocalDifficulty p_146747_, SpawnReason p_146748_, @Nullable EntityData p_146749_, @Nullable NbtCompound p_146750_) {
		if (p_146748_ != SpawnReason.STRUCTURE && p_146748_ != SpawnReason.SPAWN_EGG) {
			boolean flag = false;
			UUID uuid = null;

			if (p_146749_ instanceof MeerkatGroupData meerkat$group) {
				uuid = meerkat$group.uuid;
				//when random success and group size is many. child is spawn
				if (meerkat$group.getSpawnedCount() >= 2 && p_146746_.getRandom().nextFloat() < 0.25F) {
					flag = true;
				}
			} else {
				p_146749_ = (EntityData) new MeerkatGroupData(this.getUuid());
				uuid = this.getUuid();
			}
			//when group found. add leader
			if (uuid != null) {
				this.setTrustedLeaderUUID(uuid);
			}
			if (flag) {
				this.setBreedingAge(-24000);
			}
		} else {
			this.setTrustedLeaderUUID(null);
		}

		return super.initialize(p_146746_, p_146747_, p_146748_, p_146749_);
	}

	boolean closerThan(Vec3i p_27817_, double p_27818_) {
		return p_27817_.isWithinDistance(this.getBlockPos(), (double) p_27818_);
	}

	@Override
	public void takeKnockback(double p_147241_, double p_147242_, double p_147243_) {
		if (!this.isDiggingOrEmerging()) {
			super.takeKnockback(p_147241_, p_147242_, p_147243_);
		}
	}

	@Override
	public boolean isPushable() {
		return !this.isDiggingOrEmerging() && super.isPushable();
	}


	boolean isTooFarAway(BlockPos p_27890_) {
		return !this.closerThan(p_27890_, 128);
	}

	void pathfindRandomlyTowards(BlockPos p_27881_) {
		Vec3d vec3 = Vec3d.ofBottomCenter(p_27881_);
		int i = 0;
		BlockPos blockpos = this.getBlockPos();
		int j = (int) vec3.y - blockpos.getY();
		if (j > 2) {
			i = 4;
		} else if (j < -2) {
			i = -4;
		}

		int k = 6;
		int l = 8;
		int i1 = blockpos.getManhattanDistance(p_27881_);
		if (i1 < 15) {
			k = i1 / 2;
			l = i1 / 2;
		}

		Vec3d vec31 = NoWaterTargeting.find(this, k, l, i, vec3, (double) ((float) Math.PI / 10F));
		if (vec31 != null) {
			this.navigation.setRangeMultiplier(0.5F);
			this.navigation.startMovingTo(vec31.x, vec31.y, vec31.z, 1.0D);
		}
	}

	public void shareTheBurrow(BlockPos burrowPos) {
		for (Meerkat meerkat : this.getWorld().getEntitiesByType(TypeFilter.instanceOf(Meerkat.class), this.getBoundingBox().expand(32D), (meerkat) -> {
			return meerkat.getTrustedLeaderUUID() == this.getUuid();
		})) {
			meerkat.setBurrowPos(burrowPos);
		}
	}

	public boolean wantsToEnterBurrow() {
		if (this.getTarget() == null && this.getPose() != EntityPose.EMERGING && this.getPose() != EntityPose.DIGGING) {
			boolean flag = this.stayOutOfBurrowCountdown <= 0 || this.getWorld().isRaining() || this.getWorld().isNight();
			return flag;
		} else {
			return false;
		}
	}

	public boolean wantsToMakeBurrow() {
		if (this.getTarget() == null && !this.isStanding() && this.getPose() != EntityPose.EMERGING && this.getPose() != EntityPose.DIGGING && Meerkat.this.random.nextInt(240) == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return false;
	}

	public class MeerkatDiggingUpGoal extends Goal {
		public int tick;

		public MeerkatDiggingUpGoal() {
			this.setControls(EnumSet.of(Control.MOVE, Control.JUMP, Control.LOOK));
		}

		@Override
		public boolean canStart() {
			return Meerkat.this.getPose() == EntityPose.EMERGING;
		}
		@Override
		public boolean shouldContinue() {
			return Meerkat.this.getPose() == EntityPose.EMERGING && this.tick < 60;
		}

		public void start() {
			this.tick = 0;
		}

		@Override
		public void tick() {
			super.tick();
			++this.tick;
		}

		@Override
		public void stop() {
			super.stop();
			Meerkat.this.setPose(EntityPose.STANDING);
		}
		
		public boolean shouldRunEveryTick() {
			return true;
		}
	}

	public class MeerkatMakeBurrowGoal extends Goal {

		public boolean burrowMade;

		public int tick;

		public final Meerkat meerkat;

		public MeerkatMakeBurrowGoal(Meerkat meerkat) {
			this.meerkat = meerkat;
			this.setControls(EnumSet.of(Control.MOVE, Control.JUMP, Control.LOOK));
		}

		public boolean canStart() {
			if (this.meerkat.getTrustedLeaderUUID() == this.meerkat.getUuid() || this.meerkat.getTrustedLeaderUUID() == null) {
				if (!this.meerkat.hasBurrow() && wantsToMakeBurrow()) {
					return this.meerkat.getWorld().getBlockState(this.meerkat.getBlockPos().down()).isOf(Blocks.SAND);
				}
			}

			return false;
		}

		public boolean shouldContinue() {
			return this.tick < 120 && !burrowMade;
		}

		public boolean shouldRunEveryTick() {
			return true;
		}

		public void start() {
			this.tick = 0;
			burrowMade = false;

		}

		@Override
		public void tick() {
			super.tick();
			++this.tick;
			if (this.tick % 5 == 0) {
				if (this.meerkat.getWorld().getBlockState(this.meerkat.getBlockPos().down()).isOf(Blocks.SAND)) {
					this.meerkat.getWorld().syncWorldEvent(2001, this.meerkat.getBlockPos().down(), Block.getRawIdFromState(this.meerkat.getWorld().getBlockState(this.meerkat.getBlockPos().down())));
				}
			}
		}

		@Override
		public void stop() {
			super.stop();
			if (this.meerkat.getWorld().getBlockState(this.meerkat.getBlockPos().down()).isOf(Blocks.SAND)) {
				this.meerkat.getWorld().setBlockState(this.meerkat.getBlockPos().down(), LostLegendsBlocks.BURROW.get().getDefaultState(), 2);
				this.meerkat.setBurrowPos(this.meerkat.getBlockPos().down());
				Meerkat.this.shareTheBurrow(this.meerkat.getBlockPos().down());
			}
		}
	}

	public class MeerkatEnterBurrowGoal extends Goal {
		public int tick;

		public MeerkatEnterBurrowGoal() {
			this.setControls(EnumSet.of(Control.MOVE, Control.JUMP, Control.LOOK));
		}

		public boolean canStart() {
			if (Meerkat.this.hasBurrow() && wantsToEnterBurrow() && Meerkat.this.burrowPos.isWithinDistance(Meerkat.this.getPos(), 2.0D)) {
				BlockEntity blockentity = Meerkat.this.getWorld().getBlockEntity(Meerkat.this.burrowPos);
				if (blockentity instanceof BurrowBlockEntity) {
					BurrowBlockEntity burrow = (BurrowBlockEntity) blockentity;
					if (!burrow.isFull()) {
						return true;
					}
				}
			}

			return false;
		}

		public boolean shouldContinue() {
			return this.tick < 100;
		}

		public void start() {
			this.tick = 0;
			Meerkat.this.setPose(EntityPose.DIGGING);
		}

		@Override
		public void tick() {
			super.tick();
			++this.tick;
		}

		@Override
		public void stop() {
			super.stop();
			BlockEntity blockentity = Meerkat.this.getWorld().getBlockEntity(Meerkat.this.burrowPos);
			if (blockentity instanceof BurrowBlockEntity burrow) {
				burrow.addOccupant(Meerkat.this);
			} else {
				Meerkat.this.setPose(EntityPose.EMERGING);
				Meerkat.this.setBurrowPos(null);
				Meerkat.this.shareTheBurrow(null);
			}
		}

		public boolean shouldRunEveryTick() {
			return true;
		}
	}


	@VisibleForTesting
	public class MeerkatGoToBurrowGoal extends Goal {
		int travellingTicks = Meerkat.this.getWorld().random.nextInt(10);
		public MeerkatGoToBurrowGoal() {
			this.setControls(EnumSet.of(Goal.Control.MOVE));
		}

		public boolean canStart() {
			return Meerkat.this.burrowPos != null && Meerkat.this.wantsToEnterBurrow() && !Meerkat.this.hasPositionTarget() && !this.hasReachedTarget(Meerkat.this.burrowPos) && Meerkat.this.getWorld().getBlockState(Meerkat.this.burrowPos).isIn(LostLegendsTags.BURROW);
		}

		public boolean shouldContinue() {
			return this.canStart();
		}

		public void start() {
			this.travellingTicks = 0;
			super.start();
		}

		public void stop() {
			this.travellingTicks = 0;
			Meerkat.this.navigation.stop();
			Meerkat.this.navigation.resetRangeMultiplier();
		}

		public void tick() {
			if (Meerkat.this.burrowPos != null) {
				++this.travellingTicks;
				if (!Meerkat.this.navigation.isFollowingPath()) {
					if (!Meerkat.this.closerThan(Meerkat.this.burrowPos, 16)) {
						Meerkat.this.pathfindRandomlyTowards(Meerkat.this.burrowPos);
					} else {
						boolean flag = this.pathfindDirectlyTowards(Meerkat.this.burrowPos);
						if (!flag) {
							this.dropHive();
						}

					}
				}
			}
		}

		private boolean pathfindDirectlyTowards(BlockPos p_27991_) {
			Meerkat.this.navigation.setRangeMultiplier(10.0F);
			Meerkat.this.navigation.startMovingTo((double) p_27991_.getX(), (double) p_27991_.getY(), (double) p_27991_.getZ(), 1.0D);
			return Meerkat.this.navigation.getCurrentPath() != null && Meerkat.this.navigation.getCurrentPath().reachesTarget();
		}

		private void dropHive() {
			Meerkat.this.burrowPos = null;
		}

		private boolean hasReachedTarget(BlockPos p_28002_) {
			if (Meerkat.this.closerThan(p_28002_, 2)) {
				return true;
			} else {
				Path path = Meerkat.this.navigation.getCurrentPath();
				return path != null && path.getTarget().equals(p_28002_) && path.reachesTarget() && path.isFinished();
			}
		}
	}

	/*
	 *  group data class
	 */
	public static class MeerkatGroupData extends PassiveEntity.PassiveData {
		public final UUID uuid;

		public MeerkatGroupData(UUID p_28703_) {
			super(false);
			this.uuid = p_28703_;
		}
	}

	private class StandGoal extends Goal {
		public final Meerkat meerkat;

		private static final UniformIntProvider TIME_BETWEEN_STANDING = UniformIntProvider.create(300, 1200);


		private int cooldown = -1;

		public StandGoal(Meerkat meerkat) {
			this.meerkat = meerkat;
		}

		@Override
		public boolean canStart() {
			if (this.cooldown <= -1) {
				this.cooldown = TIME_BETWEEN_STANDING.get(this.meerkat.random);
			}

			if (!this.meerkat.isStanding()) {
				if (this.cooldown <= 0) {
					this.cooldown = TIME_BETWEEN_STANDING.get(this.meerkat.random);
					if (this.meerkat.getTarget() == null && this.meerkat.isOnGround() && !this.meerkat.isTouchingWater()) {
						return true;
					}
				} else {
					--this.cooldown;
					return false;
				}
			} else {
				if (this.cooldown <= 0) {
					this.cooldown = TIME_BETWEEN_STANDING.get(this.meerkat.random);
				}
			}

			return false;
		}

		@Override
		public boolean shouldContinue() {
			return false;
		}

		@Override
		public void start() {
			super.start();
			this.meerkat.setStanding(true);
		}
	}

	private class StopStandGoal extends Goal {
		public final Meerkat meerkat;

		private static final UniformIntProvider TIME_STANDING = UniformIntProvider.create(100, 400);


		private int cooldown;

		public StopStandGoal(Meerkat meerkat) {
			this.meerkat = meerkat;
		}

		@Override
		public boolean canStart() {
			if (this.meerkat.isStanding()) {

				if (this.meerkat.getTarget() != null || !this.meerkat.isOnGround() || this.meerkat.isTouchingWater()) {
					return true;
				}
				if (this.cooldown <= 0) {
					this.cooldown = TIME_STANDING.get(this.meerkat.random);
					return true;
				} else {
					--this.cooldown;
					return false;
				}
			} else {
				if (this.cooldown <= 0) {
					this.cooldown = TIME_STANDING.get(this.meerkat.random);
				}
			}

			return false;
		}

		@Override
		public boolean shouldContinue() {
			return false;
		}

		@Override
		public void start() {
			super.start();
			this.meerkat.setStanding(false);
			this.meerkat.getNavigation().stop();
		}
	}
}

