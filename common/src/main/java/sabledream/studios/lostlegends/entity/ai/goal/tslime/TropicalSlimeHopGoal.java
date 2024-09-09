package sabledream.studios.lostlegends.entity.ai.goal.tslime;

import net.minecraft.entity.ai.goal.Goal;
import sabledream.studios.lostlegends.entity.TropicalSlimeEntity;

import java.util.EnumSet;

public class TropicalSlimeHopGoal extends Goal
{
	private final TropicalSlimeEntity slime;

	public TropicalSlimeHopGoal(TropicalSlimeEntity slime) {
		this.slime = slime;
		setControls(EnumSet.of(Control.JUMP, Control.MOVE));
	}

	public boolean canStart() {
		return !slime.hasVehicle();
	}

	@Override
	public void tick() {
		((TropicalSlimeMoveControl) slime.getMoveControl()).move(1.0D);
	}
}
