package sabledream.studios.lostlegends.entity;

import com.mojang.serialization.Dynamic;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.task.BreedTask;
import net.minecraft.entity.ai.pathing.AmphibiousSwimNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;

import org.jetbrains.annotations.Nullable;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.client.render.entity.animation.BarnacleAnimations;
import sabledream.studios.lostlegends.client.render.entity.animation.CrabAnimations;
import sabledream.studios.lostlegends.client.render.entity.animation.KeyframeAnimation;
import sabledream.studios.lostlegends.client.render.entity.animation.PenguinAnimations;
import sabledream.studios.lostlegends.client.render.entity.animation.animator.context.AnimationContextTracker;
import sabledream.studios.lostlegends.entity.ai.brain.PenguinBrain;
import sabledream.studios.lostlegends.entity.ai.pathing.WallClimbNavigation;
import sabledream.studios.lostlegends.entity.animation.AnimatedEntity;
import sabledream.studios.lostlegends.entity.pose.CrabEntityPose;
import sabledream.studios.lostlegends.entity.pose.PenguinEntityPose;
import sabledream.studios.lostlegends.init.LostLegendsEntityTypes;
import sabledream.studios.lostlegends.init.LostLegendsSoundEvents;
import sabledream.studios.lostlegends.tag.LostLegendsTags;

import java.util.ArrayList;

public class PenguinEntity extends AnimalEntity implements AnimatedEntity
{
	private static final float MOVEMENT_SPEED = 0.2F;

	private static final EntityDimensions BABY_BASE_DIMENSIONS;
	private AnimationContextTracker animationContextTracker;
	private static final TrackedData<Integer> POSE_TICKS = DataTracker.registerData(PenguinEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public PenguinEntity(EntityType<? extends PenguinEntity> entityType, World world) {
		super(entityType, world);
		this.setPose(CrabEntityPose.IDLE.get());
		this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DOOR_IRON_CLOSED, -1.0F);
		this.setPathfindingPenalty(PathNodeType.DOOR_WOOD_CLOSED, -1.0F);
		this.setPathfindingPenalty(PathNodeType.DOOR_OPEN, -1.0F);
	}

	@Override
	public EntityData initialize(
		ServerWorldAccess world,
		LocalDifficulty difficulty,
		SpawnReason spawnReason,
		@Nullable EntityData entityData
	) {
		EntityData superEntityData = super.initialize(world, difficulty, spawnReason, entityData);

		this.setPose(PenguinEntityPose.IDLE);

		return superEntityData;
	}

	@Nullable
	@Override
	public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
		PenguinEntity babypenguin = LostLegendsEntityTypes.PENGUIN.get().create(world);

		if (babypenguin instanceof PenguinEntity) {
			PenguinEntity babypenguinEntity = (PenguinEntity) babypenguin;
			if(babypenguinEntity.isBaby()){
				babypenguinEntity.getDimensions().scaled(0.5F);
			}
		}

		return babypenguin;
	}

	private EntityDimensions getDimensions() {
		return BABY_BASE_DIMENSIONS;
	}

	public EntityDimensions getBaseDimensions(EntityPose pose){
		return this.isBaby() ? BABY_BASE_DIMENSIONS : super.getBaseDimensions(pose);
	}

	static{
		BABY_BASE_DIMENSIONS = LostLegendsEntityTypes.PENGUIN.get().getDimensions().scaled(0.5F).withEyeHeight(0.665F);
	}


	@Override
	public AnimationContextTracker getAnimationContextTracker() {
		if (this.animationContextTracker == null) {
			this.animationContextTracker = new AnimationContextTracker();

			for (KeyframeAnimation keyframeAnimation : this.getAnimations()) {
				this.animationContextTracker.add(keyframeAnimation);
			}

			this.animationContextTracker.add(this.getMovementAnimation());
		}

		return this.animationContextTracker;
	}

	@Override
	public ArrayList<KeyframeAnimation> getAnimations() {
		return PenguinAnimations.ANIMATIONS;
	}

	@Override
	public KeyframeAnimation getMovementAnimation() {
		if (isTouchingWater()) {
			return PenguinAnimations.SWIM;
		} else {
			return PenguinAnimations.WALK;
		}
	}

	@Override
	public void travel(Vec3d movementInput) {
		if (this.canMoveVoluntarily() && this.isTouchingWater()) {
			this.updateVelocity(this.getMovementSpeed(), movementInput);
			this.move(MovementType.SELF, this.getVelocity());
			this.setVelocity(this.getVelocity().multiply(0.9));
		} else {
			super.travel(movementInput);
		}
	}


	@Override
	protected EntityNavigation createNavigation(World world) {
		return new AmphibiousSwimNavigation(this, world);
	}

	@Override
	public boolean isPushedByFluids() {
		return false;
	}
	@Override
	public float getMovementSpeed() {
		if (this.isBaby()) {
			return MOVEMENT_SPEED / 2.0F;
		}

		return MOVEMENT_SPEED;
	}

	@Override
	public int getKeyframeAnimationTicks() {
		return this.dataTracker.get(POSE_TICKS);
	}

	public void setKeyframeAnimationTicks(int keyframeAnimationTicks) {
		this.dataTracker.set(POSE_TICKS, keyframeAnimationTicks);
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);

		builder.add(POSE_TICKS, 0);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return PenguinBrain.getTemptItems().test(stack);
	}

	@Override
	public void breed(ServerWorld world, AnimalEntity other) {
		ServerPlayerEntity serverPlayerEntity = this.getLovingPlayer();

		if(serverPlayerEntity == null && other.getLovingPlayer()!= null){
			serverPlayerEntity = other.getLovingPlayer();
		}
		if (serverPlayerEntity != null){
			serverPlayerEntity.incrementStat(Stats.ANIMALS_BRED);
			Criteria.BRED_ANIMALS.trigger(serverPlayerEntity, this,other,this);
		}
		this.resetLoveTicks();
		other.resetLoveTicks();
		Random random = this.getRandom();

		if(this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT)){
			this.getWorld().spawnEntity(new ExperienceOrbEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), random.nextInt(7) +1));
		}

	}

	@Override
	protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
		return PenguinBrain.create(dynamic);
	}

	@Override
	@SuppressWarnings("all")
	public Brain<PenguinEntity> getBrain() {
		return (Brain<PenguinEntity>) super.getBrain();
	}


	public static DefaultAttributeContainer.Builder createPenguinAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 15.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, MOVEMENT_SPEED).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0);
	}

	@Override
	protected void playStepSound(
		BlockPos pos,
		BlockState state
	) {
		if (state.isLiquid()) {
			return;
		}

		BlockState blockState = this.getWorld().getBlockState(pos.up());
		BlockSoundGroup blockSoundGroup = blockState.isIn(BlockTags.INSIDE_STEP_SOUND_BLOCKS) ? blockState.getSoundGroup():state.getSoundGroup();
		this.playSound(LostLegendsSoundEvents.ENTITY_PENGUIN_STEP.get(), blockSoundGroup.getVolume() * 0.15F, 0.75F + this.getRandom().nextFloat() * 0.15F);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return LostLegendsSoundEvents.ENTITY_PENGUIN_AMBIENT.get();
	}

	@Override
	public void tick() {

		if (this.getWorld().isClient() == false && LostLegends.getConfig().enablePenguin == false) {
			this.discard();
		}

		this.updateKeyframeAnimations();
		super.tick();
	}

	private void updateKeyframeAnimations() {
		if (this.getWorld().isClient() == false) {
			this.updateKeyframeAnimationTicks();
		}

		KeyframeAnimation keyframeAnimationToStart = this.getKeyframeAnimationByPose();

		if (keyframeAnimationToStart != null) {
			this.tryToStartKeyframeAnimation(keyframeAnimationToStart);
		}
	}

	@Nullable
	private KeyframeAnimation getKeyframeAnimationByPose() {
		KeyframeAnimation keyframeAnimation = null;

		if (this.isInPose(PenguinEntityPose.IDLE) && this.isMoving() == false) {
			keyframeAnimation = CrabAnimations.IDLE;
		}

		return keyframeAnimation;
	}

	private void tryToStartKeyframeAnimation(KeyframeAnimation keyframeAnimationToStart) {
		if (this.isKeyframeAnimationRunning(keyframeAnimationToStart)) {
			return;
		}

		if (this.getWorld().isClient() == false) {
			this.setKeyframeAnimationTicks(keyframeAnimationToStart.getAnimationLengthInTicks());
		}

		this.startKeyframeAnimation(keyframeAnimationToStart);
	}

	private void startKeyframeAnimation(KeyframeAnimation keyframeAnimationToStart) {
		for (KeyframeAnimation keyframeAnimation : this.getAnimations()) {
			if (keyframeAnimation == keyframeAnimationToStart) {
				continue;
			}

			this.stopKeyframeAnimation(keyframeAnimation);
		}

		this.startKeyframeAnimation(keyframeAnimationToStart, this.age);
	}

	@Override
	public void setPose(EntityPose pose) {
		if (this.getWorld().isClient()) {
			return;
		}

		super.setPose(pose);
	}

	public void setPose(PenguinEntityPose pose) {
		if (this.getWorld().isClient()) {
			return;
		}

		super.setPose(pose.get());
	}

	public boolean isInPose(PenguinEntityPose pose) {
		return this.getPose() == pose.get();
	}

	public boolean isMoving() {
		return (this.isOnGround() || this.isClimbing()) && this.getVelocity().lengthSquared() >= 0.0001;
	}

	@Override
	protected void mobTick() {
		this.getWorld().getProfiler().push("penguinBrain");
		this.getBrain().tick((ServerWorld) this.getWorld(), this);

		this.getWorld().getProfiler().pop();
		this.getWorld().getProfiler().push("penguinMemoryUpdate");
		PenguinBrain.updateMemories(this);
		this.getWorld().getProfiler().pop();

		this.getWorld().getProfiler().push("penguinActivityUpdate");
		PenguinBrain.updateActivities(this);
		this.getWorld().getProfiler().pop();

		super.mobTick();
	}

	public static boolean canSpawn(
		EntityType<? extends AnimalEntity> type,
		WorldAccess world,
		SpawnReason reason,
		BlockPos pos,
		Random random
	) {
		return world.getBlockState(pos.down()).isIn(LostLegendsTags.PENGUIN_SPAWNABLE_ON) && isLightLevelValidForNaturalSpawn(world, pos);
	}
}
