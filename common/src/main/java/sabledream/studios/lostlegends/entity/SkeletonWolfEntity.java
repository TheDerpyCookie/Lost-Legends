package sabledream.studios.lostlegends.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.command.EntityDataObject;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import sabledream.studios.lostlegends.init.LostLegendsSoundEvents;

import java.util.List;
import java.util.UUID;

public class SkeletonWolfEntity extends HostileEntity implements Angerable
{

protected static final TrackedData<Integer> ANGER_TIME = DataTracker.registerData(SkeletonWolfEntity.class, TrackedDataHandlerRegistry.INTEGER);
private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20,39);
private float headRotationCourse;
private float headRotationCourseOld;
private UUID targetUuid;


public SkeletonWolfEntity(EntityType<SkeletonWolfEntity> type, World world){
	super(type, world);
}


public static DefaultAttributeContainer.Builder createSkeletonWolfAttributes(){
	return MobEntity.createMobAttributes()
		.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D)
		.add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
		.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D);
}


	@Override
	protected void initGoals() {
		goalSelector.add(2, new AvoidSunlightGoal(this));
		goalSelector.add(3, new EscapeSunlightGoal( this, 1.0D));
		goalSelector.add(3, new FleeEntityGoal<>(this, WolfEntity.class, 6.0F, 1.0F,1.2D));
		goalSelector.add(4, new MeleeAttackGoal(this, 1.2D, false));
		goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
		goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		goalSelector.add(6, new LookAroundGoal(this));
		goalSelector.add(1, new RevengeGoal(this));
		goalSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		if ((hasAngerTime())){
			return LostLegendsSoundEvents.SKELETON_WOLF_GROWL.get();
		}
		if (random.nextInt(3) == 0 && getHealth() < 10.0F){
			return LostLegendsSoundEvents.SKELETON_WOLF_WHINE.get();
		}
		return LostLegendsSoundEvents.SKELETON_WOLF_AMBIENT.get();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return LostLegendsSoundEvents.SKELETON_WOLF_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return LostLegendsSoundEvents.SKELETON_WOLF_DEATH.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		playSound(LostLegendsSoundEvents.SKELETON_WOLF_AMBIENT.get(), 0.33F,1.0F);
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(ANGER_TIME, 0);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		readAngerFromNbt(getWorld(), nbt);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		writeAngerToNbt(nbt);
	}

	@Environment(EnvType.CLIENT)
	public float getTailAngle() {
		if (hasAngerTime()) {
			return 1.5393804F;
		}
		return ((float) Math.PI / 5F);
	}

	public int getAngerTime() {
		return dataTracker.get(ANGER_TIME);
	}

	public void setAngerTime(int ticks) {
		dataTracker.set(ANGER_TIME, ticks);
	}

	public void chooseRandomAngerTime() {
		setAngerTime(ANGER_TIME_RANGE.get(random));
	}

	@Nullable
	public UUID getAngryAt() {
		return targetUuid;
	}

	public void setAngryAt(@Nullable UUID uuid) {
		targetUuid = uuid;
	}

	@Override
	public void tickMovement() {
		super.tickMovement();
		if (isAlive() && isAffectedByDaylight()) {
			setOnFireFor(8);
		}
		if (!getWorld().isClient) {
			tickAngerLogic((ServerWorld) getWorld(), true);
		}
	}

	private boolean hasHowled = false;

	@Override
	public void tick() {
		super.tick();
		if (isAlive()) {
			headRotationCourseOld = headRotationCourse;
			headRotationCourse += (0.0F - headRotationCourse) * 0.4F;

			long time = getWorld().getTimeOfDay();
			if (time >= 13000 && time <= 23000 && !hasHowled) {
				if (random.nextInt(100) < 5) {
					playSound(LostLegendsSoundEvents.SKELETON_WOLF_HOWL.get(), 1.0F, 1.0F);
					hasHowled = true;
					applyDamageBoostToNearbySkeletonMobs();
				}
			} else if (time < 13000 || time > 23000) {
				hasHowled = false;
			}
		}
	}

	private void applyDamageBoostToNearbySkeletonMobs() {
		List<SkeletonEntity> nearbySkeletons = getWorld().getEntitiesByClass(SkeletonEntity.class, getBoundingBox().expand(10.0D), entity -> true);
		for (SkeletonEntity skeleton : nearbySkeletons) {
			skeleton.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 200, 1));
		}
	}


}