package sabledream.studios.lostlegends.entity.ai.goal.barnacle;

import net.minecraft.command.EntitySelector;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import sabledream.studios.lostlegends.entity.Barnacle;

import java.util.EnumSet;
import java.util.function.Consumer;

public class BarnacleAttackGoal extends Goal
{
	protected final Barnacle mob;
	protected final double speedModifier;
	protected double extraReach;
	protected final boolean followingTargetEvenIfNotSeen;
	protected double pathedTargetX;
	protected double pathedTargetY;
	protected double pathedTargetZ;
	protected double minDistanceSqr;
	protected int ticksUntilNextPathRecalculation;
	protected int ticksUntilNextAttack;
	protected int attackInterval;
	protected long lastCanUseCheck;
	protected boolean wallCheck;
	protected Consumer<LivingEntity> onDamage;

	public BarnacleAttackGoal(Barnacle mob, int attackInterval, double extraReach, double speedModifier, double minDistanceSqr, boolean wallCheck, boolean followTargetEvenIfNotSeen, @Nullable Consumer<LivingEntity> onDamage) {
		this.mob = mob;
		this.attackInterval = attackInterval;
		this.extraReach = extraReach;
		this.speedModifier = speedModifier;
		this.minDistanceSqr = minDistanceSqr;
		this.wallCheck = wallCheck;
		this.followingTargetEvenIfNotSeen = followTargetEvenIfNotSeen;
		this.onDamage = onDamage;
		this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
	}

	public boolean canStart() {
		long gameTime = mob.getWorld().getTime();
		if (gameTime - lastCanUseCheck < attackInterval) return false;
		else {
			this.lastCanUseCheck = gameTime;
			LivingEntity target = mob.getTarget();
			if (target == null) return false;
			else if (!target.isAlive()) return false;
			else
				return this.mob.squaredDistanceTo(target.getX(), target.getY(), target.getZ()) <= mob.getAttributeInstance(EntityAttributes.GENERIC_FOLLOW_RANGE).getValue();
		}
	}

	public boolean shouldContinue() {
		LivingEntity target = this.mob.getTarget();
		if (target == null) return false;
		else if (!target.isAlive()) return false;
		else if (!this.mob.isInWalkTargetRange(target.getBlockPos())) return false;
		else return !(target instanceof PlayerEntity) || !target.isSpectator() && !((PlayerEntity) target).isCreative();
	}

	public void start() {
		this.mob.setAttacking(true);
		this.ticksUntilNextPathRecalculation = 0;
		this.ticksUntilNextAttack = 0;
	}

	public void stop() {
		LivingEntity target = this.mob.getTarget();
		if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(target)) this.mob.setTarget(null);
		this.mob.setAttacking(false);
	}

	public boolean shouldRunEveryTick() {
		return true;
	}

	public void tick() {
		LivingEntity target = this.mob.getTarget();
		if (target != null) {
			this.mob.getLookControl().lookAt(target, 30.0F, 30.0F);
			double distSqr = this.mob.squaredDistanceTo(target.getX(), target.getY(), target.getZ());
			this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
			if ((this.followingTargetEvenIfNotSeen || this.mob.getVisibilityCache().canSee(target)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == 0.0D && this.pathedTargetY == 0.0D && this.pathedTargetZ == 0.0D || target.squaredDistanceTo(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= minDistanceSqr || this.mob.getRandom().nextFloat() < 0.05F)) {
				int $$0 = this.mob.getDespawnCounter();
				if ($$0 > 100) this.mob.setSwimmingVector(0.0F, 0.0F, 0.0F);
				else if (this.mob.getRandom().nextInt(toGoalTicks(20)) == 0 || !this.mob.isSubmergedInWater() || !this.mob.hasSwimmingVector()) {
					Vec3d v = mob.getTarget().getPos().subtract(mob.getPos()).normalize().multiply(0.2, 0.2, 0.2);
					this.mob.setSwimmingVector((float) v.x, (float) v.y, (float) v.z);
				}
			}
			this.ticksUntilNextAttack = Math.max(getTicksUntilNextAttack() - 1, 0);
			this.checkAndPerformAttack(target, distSqr);
		}
	}

	protected void checkAndPerformAttack(LivingEntity entity, double distSqr) {
		double attackReachSqr = this.getAttackReachSqr(entity);
		if (distSqr <= attackReachSqr + extraReach && isTimeToAttack() && (!wallCheck || mob.canSee(entity))) {
			this.resetAttackCooldown();
			if (onDamage != null) onDamage.accept(entity);
		}
	}

	protected void resetAttackCooldown() {
		this.ticksUntilNextAttack = getAttackInterval();
	}

	protected boolean isTimeToAttack() {
		return getTicksUntilNextAttack() <= 0;
	}

	protected int getTicksUntilNextAttack() {
		return this.ticksUntilNextAttack;
	}

	protected int getAttackInterval() {
		return this.getTickCount(attackInterval);
	}

	protected double getAttackReachSqr(LivingEntity entity) {
		return this.mob.getWidth() * 2.0F * this.mob.getWidth() * 2.0F + entity.getWidth();
	}
}
