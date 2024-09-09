package sabledream.studios.lostlegends.entity.ai.goal.fancychicken;

import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.PigEntity;
import sabledream.studios.lostlegends.entity.FancyChickenEntity;
import sabledream.studios.lostlegends.init.LostLegendsSoundEvents;

public class FancyChickenFleeFromPigEntityGoal extends FleeEntityGoal<PigEntity>

{
	public FancyChickenFleeFromPigEntityGoal(PathAwareEntity mob, Class<PigEntity> fleefromType, float distance, double slowspeed, double fastspeed){
		super(mob,fleefromType,distance,slowspeed,fastspeed);
	}

	@Override
	public void start() {
		mob.playSound(LostLegendsSoundEvents.FANCY_CHICKEN_FLEE.get(), 1.0F, 1.0F);
		super.start();
	}
}
