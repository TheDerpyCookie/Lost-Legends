package sabledream.studios.lostlegends.entity.ai.goal.melongolem;

import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import sabledream.studios.lostlegends.entity.MelonGolemEntity;
import sabledream.studios.lostlegends.init.LostLegendsSoundEvents;

public class MelonGolemProjectileAttackGoal extends ProjectileAttackGoal
{
	private final MelonGolemEntity melonGolemEntity;

	public MelonGolemProjectileAttackGoal(MelonGolemEntity mob, double mobSpeed, int intervalTicks, float maxShootRange) {
		super(mob, mobSpeed, intervalTicks, maxShootRange);
		melonGolemEntity = mob;
	}

	public MelonGolemProjectileAttackGoal(MelonGolemEntity mob, double mobSpeed, int minIntervalTicks, int maxIntervalTicks, float maxShootRange) {
		super(mob, mobSpeed, minIntervalTicks, maxIntervalTicks, maxShootRange);
		melonGolemEntity = mob;
	}

	@Override
	public void start() {
		super.start();
		melonGolemEntity.playSound(LostLegendsSoundEvents.MELON_GOLEM_AGGRO.get(), 1.0F, 1.0F);
	}

}
