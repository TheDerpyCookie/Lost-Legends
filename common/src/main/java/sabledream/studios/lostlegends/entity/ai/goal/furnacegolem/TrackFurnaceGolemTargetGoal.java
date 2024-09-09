package sabledream.studios.lostlegends.entity.ai.goal.furnacegolem;

import net.minecraft.entity.ai.goal.TrackIronGolemTargetGoal;
import sabledream.studios.lostlegends.entity.FurnaceGolemEntity;
import sabledream.studios.lostlegends.init.LostLegendsSoundEvents;

public class TrackFurnaceGolemTargetGoal extends TrackIronGolemTargetGoal
{
	private final FurnaceGolemEntity golem;


	public TrackFurnaceGolemTargetGoal(FurnaceGolemEntity golem) {
		super(golem);
		this.golem = golem;
	}


	@Override
	public void start() {
		golem.playSound(LostLegendsSoundEvents.FURNACE_GOLEM_AGGRO.get(), 1.0F, 1.0F);
		golem.setAngry(true);
		super.start();
	}

	@Override
	public void stop() {
		golem.setAngry(false);
		super.stop();
	}
}