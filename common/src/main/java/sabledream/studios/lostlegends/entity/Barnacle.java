package sabledream.studios.lostlegends.entity;

import com.google.common.collect.ImmutableList;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import sabledream.studios.lostlegends.entity.ai.goal.barnacle.BarnacleAttackGoal;
import sabledream.studios.lostlegends.init.LostLegendsMemorySensorType;

public class Barnacle extends SquidEntity
{
	public final AnimationState impaleAnimationState = new AnimationState();
	public final AnimationState hurtAnimationState = new AnimationState();
	public final AnimationState swimAnimationState = new AnimationState();
	public boolean animating = false;
	public boolean swim = false;
	public int animateTicks = 0;
	public Entity lookTarget = null;
	protected static final ImmutableList<SensorType<? extends Sensor<? super Barnacle>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY, LostLegendsMemorySensorType.BARNACLE_ATTACKABLES_SENSORS.get());
	protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS);
	private static final TrackedData<Integer> LOOK_TARGET = DataTracker.registerData(Barnacle.class, TrackedDataHandlerRegistry.INTEGER);
	public static final float ATTACK_REACH_SQR = 36;

	public Barnacle(EntityType<? extends SquidEntity> entityType, World level) {
		super(entityType, level);
	}

	@NotNull
	public static DefaultAttributeContainer.Builder createAttributes() {
		return SquidEntity.createSquidAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0)
			.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0)
			.add(EntityAttributes.GENERIC_ATTACK_SPEED)
			.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0);
	}

	@NotNull
	@Override
	protected Brain.Profile<Barnacle> createBrainProfile() {
		return Brain.createProfile(MEMORY_TYPES, SENSOR_TYPES);
	}

	@NotNull
	@Override
	@SuppressWarnings("unchecked")
	public Brain<Barnacle> getBrain() {
		return (Brain<Barnacle>) super.getBrain();
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(LOOK_TARGET, -1);
	}

	public int getLookTarget() {
		return dataTracker.get(LOOK_TARGET);
	}

	public void setLookTarget(int lookTarget) {
		dataTracker.set(LOOK_TARGET, lookTarget);
	}

	@Override
	public void handleStatus(byte id) {
		switch (id) {
			case 64 -> impaleAnimationState.start(age);
			case 65 -> hurtAnimationState.start(age);
			case 66 -> swimAnimationState.start(age);
			default -> super.handleStatus(id);
		}
	}

	@Override
	public boolean damage(@NotNull DamageSource damageSource, float damage) {
		if (super.damage(damageSource, damage)) {
			getWorld().sendEntityStatus(this, (byte) 65);
			return true;
		}
		return false;
	}

	@Override
	protected void mobTick() {
		getWorld().getProfiler().push("barnacleBrain");
		getBrain().tick((ServerWorld) getWorld(), this);
		getWorld().getProfiler().pop();
		super.mobTick();
	}

	@Override
	public void tickMovement() {
		super.tickMovement();
		if (getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_ATTACKABLE).isPresent() && Sensor.testAttackableTargetPredicate(this, getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_ATTACKABLE).get()))
			setTarget(getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_ATTACKABLE).get());
		else setTarget(null);
		lookTarget = getWorld().getEntityById(getLookTarget());
		if (lookTarget != null) {
			Vec3d pos = lookTarget.getPos();
			lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, new Vec3d(pos.x, lookTarget.getBoundingBox().getCenter().y, pos.z));
		}
		if (getWorld().isClient) return;
		if (getTarget() == null || (!animating && getTarget().squaredDistanceTo(this) >= ATTACK_REACH_SQR + 10) || getTarget().squaredDistanceTo(this) < 1)
			setLookTarget(-1);
		else if (animating && getTarget() != null && getTarget().squaredDistanceTo(this) >= 1)
			setLookTarget(getTarget().getId());
		if (!animating) return;
		if (getTarget() != null) {
			if (animateTicks == 16 && getTarget().hasVehicle()) getTarget().stopRiding();
			if (animateTicks >= 16 && getTarget().squaredDistanceTo(this) <= ATTACK_REACH_SQR + 10) {
				Vec3d vec = getPos().subtract(getTarget().getPos()).normalize();
				getTarget().setVelocity(vec.multiply(0.2, 0.2, 0.2));
				getTarget().velocityModified = true;
			}
			if (animateTicks == 38 && getTarget().squaredDistanceTo(this) <= 9)
				tryAttack(getTarget());
		}
		if (++animateTicks >= 40) {
			animating = false;
			animateTicks = 0;
		}
	}

	@Override
	public void lookAt(EntityAnchorArgumentType.EntityAnchor anchor, Vec3d target) {
		Vec3d vec3 = anchor.positionAt(this);
		double dx = target.x - vec3.x;
		double dy = target.y - vec3.y;
		double dz = target.z - vec3.z;
		double sqrt = Math.sqrt(dx * dx + dz * dz);
		setPitch(MathHelper.wrapDegrees((float) (MathHelper.atan2(dy, sqrt) * 57.2957763671875D - 90.0F)));
		setYaw(MathHelper.wrapDegrees((float) (MathHelper.atan2(dz, dx) * 57.2957763671875D) - 90.0F));
		setHeadYaw(getYaw());
		tiltAngle = getPitch();
		bodyYaw = headYaw;
	}

	@Override
	protected void initGoals() {
		goalSelector.add(0, new BarnacleAttackGoal(this, 60, ATTACK_REACH_SQR, 1, 2, true, false, entity -> {
			getWorld().sendEntityStatus(this, (byte) 64);
			animating = true;
		}));
		goalSelector.add(1, new OceanDepthsMonsterRandomMovementGoal(this));
		goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
	}

	public static class OceanDepthsMonsterRandomMovementGoal extends Goal
	{
		private final SquidEntity squid;

		public OceanDepthsMonsterRandomMovementGoal(SquidEntity $$0) {
			this.squid = $$0;
		}

		public boolean canStart() {
			return true;
		}

		public void tick() {
			if (squid.getTarget() != null) return;
			int $$0 = squid.getDespawnCounter();
			if ($$0 > 100) {
				squid.setSwimmingVector(0.0F, 0.0F, 0.0F);
			} else if (squid.getRandom().nextInt(toGoalTicks(50)) == 0 || !squid.isSubmergedInWater() || !squid.hasSwimmingVector()) {
				float $$1 = squid.getRandom().nextFloat() * 6.2831855F;
				float $$2 = MathHelper.cos($$1) * 0.2F;
				float $$3 = -0.1F + squid.getRandom().nextFloat() * 0.2F;
				float $$4 = MathHelper.sin($$1) * 0.2F;
				squid.setSwimmingVector($$2, $$3, $$4);
			}
		}
	}
}