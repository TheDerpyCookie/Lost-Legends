package sabledream.studios.lostlegends.entity.ai.goal.melongolem;

import net.minecraft.entity.ai.goal.Goal;
import sabledream.studios.lostlegends.entity.MelonGolemEntity;
import sabledream.studios.lostlegends.entity.ai.control.MelonGolemMoveControl;

import java.util.EnumSet;

public class MelonGolemHopGoal extends Goal
{
	private final MelonGolemEntity melonGolem;

	public MelonGolemHopGoal(MelonGolemEntity entity) {
		melonGolem = entity;
		this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
	}

	@Override
	public boolean canStart() {
		return !melonGolem.hasVehicle();
	}

	@Override
	public void tick() {
		((MelonGolemMoveControl) melonGolem.getMoveControl()).move(1.0D);
	}
}
