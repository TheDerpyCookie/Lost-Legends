package sabledream.studios.lostlegends.entity.ai.brain.task.crab;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.task.BreedTask;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.server.world.ServerWorld;
import sabledream.studios.lostlegends.entity.CrabEntity;

public class CrabBreedTask extends BreedTask
{
	public CrabBreedTask(EntityType<? extends AnimalEntity> targetType) {
		super(targetType);
	}

	@Override
	protected void finishRunning(ServerWorld serverWorld, AnimalEntity animalEntity, long time) {
		super.finishRunning(serverWorld, animalEntity, time);
		((CrabEntity) animalEntity).setHasEgg(true);
	}
}
