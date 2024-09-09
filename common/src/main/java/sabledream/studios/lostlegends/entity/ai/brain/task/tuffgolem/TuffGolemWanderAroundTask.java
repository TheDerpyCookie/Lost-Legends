package sabledream.studios.lostlegends.entity.ai.brain.task.tuffgolem;

import sabledream.studios.lostlegends.entity.TuffGolemEntity;
import net.minecraft.entity.ai.brain.task.WanderAroundTask;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;

public final class TuffGolemWanderAroundTask extends WanderAroundTask
{
	@Override
	protected boolean shouldRun(ServerWorld world, MobEntity entity) {
		TuffGolemEntity tuffGolem = (TuffGolemEntity) entity;

		return tuffGolem.isNotImmobilized() && super.shouldRun(world, tuffGolem);
	}
}
