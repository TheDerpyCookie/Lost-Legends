package sabledream.studios.lostlegends.entity.ai.goal.furnacegolem;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import org.jetbrains.annotations.Nullable;
import sabledream.studios.lostlegends.entity.FurnaceGolemEntity;
import sabledream.studios.lostlegends.init.LostLegendsSoundEvents;

import java.util.function.Predicate;

public final class FurnaceGolemActiveTargetGoal extends ActiveTargetGoal<MobEntity>
{
	final FurnaceGolemEntity golem;

	public FurnaceGolemActiveTargetGoal(FurnaceGolemEntity entity, Class<MobEntity> targetClass, int reciprocalChance, boolean checkVisibility, boolean checkCanNavigate, @Nullable Predicate<LivingEntity> targetPredicate) {
		super(entity, targetClass, reciprocalChance, checkVisibility, checkCanNavigate, targetPredicate);
		golem = entity;
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
