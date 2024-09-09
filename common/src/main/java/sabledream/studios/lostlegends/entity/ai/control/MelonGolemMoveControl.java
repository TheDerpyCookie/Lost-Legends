package sabledream.studios.lostlegends.entity.ai.control;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.EntityAttributes;
import sabledream.studios.lostlegends.entity.MelonGolemEntity;

public class MelonGolemMoveControl extends MoveControl{
private final MelonGolemEntity melonGolem;
private float yRot;
private int jumpDelay;
private boolean isAggressive;

public MelonGolemMoveControl(MelonGolemEntity entity) {
	super(entity);
	melonGolem = entity;
	yRot = 180.0F * entity.getYaw() / (float) Math.PI;
}

public void setDirection(float yRot, boolean isAggressive) {
	this.yRot = yRot;
	this.isAggressive = isAggressive;
}

public void move(double speed) {
	this.speed = speed;
	this.state = state.MOVE_TO;
}

@Override
public void tick() {
	this.entity.setYaw(this.wrapDegrees(this.entity.getYaw(), this.yRot, 90.0F));
	this.entity.headYaw = this.entity.getYaw();
	this.entity.bodyYaw = this.entity.getYaw();
	if (this.state != state.MOVE_TO) {
		this.entity.setForwardSpeed(0.0F);
	} else {
		this.state = state.WAIT;
		if (this.entity.isOnGround()) {
			this.entity.setMovementSpeed((float)(this.getSpeed() * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
			if (this.jumpDelay-- <= 0) {
				this.jumpDelay = this.melonGolem.getJumpDelay();
				if (this.isAggressive) {
					this.jumpDelay /= 3;
				}

				this.melonGolem.getJumpControl().setActive();
			} else {
				this.melonGolem.sidewaysSpeed = 0.0F;
				this.melonGolem.forwardSpeed = 0.0F;
				this.entity.setMovementSpeed(0.0F);
			}
		} else {
			this.entity.setMovementSpeed((float)(this.getSpeed() * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
		}

	}
}
}

