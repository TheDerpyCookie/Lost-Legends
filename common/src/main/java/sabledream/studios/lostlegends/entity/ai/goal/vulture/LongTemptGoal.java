package sabledream.studios.lostlegends.entity.ai.goal.vulture;

import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.recipe.Ingredient;
import sabledream.studios.lostlegends.entity.Vulture;

import java.util.EnumSet;

public class LongTemptGoal extends Goal
{
	private static final TargetPredicate TEMP_TARGETING = TargetPredicate.createNonAttackable().setBaseMaxDistance(64.0D).ignoreVisibility();
	private final TargetPredicate targetingConditions;
	protected final Vulture mob;
	private double px;
	private double py;
	private double pz;
	private double pRotX;
	private double pRotY;
	@Nullable
	protected PlayerEntity player;
	private int calmDown;
	private boolean isRunning;
	private final Ingredient items;
	private final boolean canScare;

	public LongTemptGoal(Vulture p_25939_, Ingredient p_25941_, boolean p_25942_) {
		this.mob = p_25939_;
		this.items = p_25941_;
		this.canScare = p_25942_;
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		this.targetingConditions = TEMP_TARGETING.copy().setPredicate(this::shouldFollow);
	}

	public boolean canStart() {
		if (this.calmDown > 0) {
			--this.calmDown;
			return false;
		} else {
			this.player = this.mob.getWorld().getClosestPlayer(this.targetingConditions, this.mob);
			return this.player != null;
		}
	}

	private boolean shouldFollow(LivingEntity p_148139_) {
		return this.items.test(p_148139_.getMainHandStack()) || this.items.test(p_148139_.getOffHandStack());
	}

	public boolean shouldContinue() {
		if (this.canScare()) {
			if (this.mob.squaredDistanceTo(this.player) < 36.0D) {
				if (this.player.squaredDistanceTo(this.px, this.py, this.pz) > 0.010000000000000002D) {
					return false;
				}

				if (Math.abs((double) this.player.getPitch() - this.pRotX) > 5.0D || Math.abs((double) this.player.getYaw() - this.pRotY) > 5.0D) {
					return false;
				}
			} else {
				this.px = this.player.getX();
				this.py = this.player.getY();
				this.pz = this.player.getZ();
			}

			this.pRotX = (double) this.player.getPitch();
			this.pRotY = (double) this.player.getYaw();
		}

		return this.canStart();
	}

	protected boolean canScare() {
		return this.canScare;
	}

	public void start() {
		this.px = this.player.getX();
		this.py = this.player.getY();
		this.pz = this.player.getZ();
		this.isRunning = true;
	}

	public void stop() {
		this.player = null;
		this.mob.getNavigation().stop();
		this.calmDown = toGoalTicks(100);
		this.isRunning = false;
	}

	public void tick() {
		this.mob.getLookControl().lookAt(this.player, (float) (this.mob.getMaxHeadRotation() + 20), (float) this.mob.getMaxLookPitchChange());
		this.mob.moveTargetPoint = player.getPos();
	}

	public boolean isRunning() {
		return this.isRunning;
	}
}