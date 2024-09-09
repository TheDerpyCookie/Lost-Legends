package sabledream.studios.lostlegends.entity.ai.goal.tslime;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.EntityAttributes;
import sabledream.studios.lostlegends.entity.TropicalSlimeEntity;

public class TropicalSlimeMoveControl extends MoveControl
{
	private final TropicalSlimeEntity slime;
	private float yRot;
	private int ticksUntilJump;
	private boolean isAggressive;

	public TropicalSlimeMoveControl(TropicalSlimeEntity slime) {
		super(slime);
		this.slime = slime;
		this.yRot = 180.0F * slime.getYaw() / 3.1415927F;
	}

	public void look(float targetYaw, boolean isAggressive) {
		this.yRot = targetYaw;
		this.isAggressive = isAggressive;
	}

	public void move(double speed) {
		this.speed = speed;
		this.state = State.MOVE_TO;
	}

	@Override
	public void tick() {
		this.entity.setYaw(this.wrapDegrees(this.entity.getYaw(), this.yRot, 90.0F));
		this.entity.headYaw = this.entity.getYaw();
		this.entity.bodyYaw = this.entity.getYaw();
		if (state != State.MOVE_TO) {
			entity.setForwardSpeed(0.0F);
			return;
		}
		state = State.WAIT;
		if (entity.isOnGround()) {
			entity.setMovementSpeed((float) (speed * entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
			if (ticksUntilJump-- <= 0) {
				ticksUntilJump = slime.getJumpDelay();
				if (isAggressive) {
					ticksUntilJump /= 3;
				}
				slime.getJumpControl().setActive();
				if (slime.doPlayJumpSound()) {
					slime.playSound(slime.getJumpSound(), 1.0F, slime.getJumpSoundPitch());
				}
			} else {
				slime.sidewaysSpeed = 0.0F;
				slime.forwardSpeed = 0.0F;
				entity.setMovementSpeed(0.0F);
			}
		} else {
			entity.setMovementSpeed((float) (speed * entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
		}

	}

}
