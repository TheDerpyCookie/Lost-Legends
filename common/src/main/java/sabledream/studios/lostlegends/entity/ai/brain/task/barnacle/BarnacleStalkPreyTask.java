package sabledream.studios.lostlegends.entity.ai.brain.task.barnacle;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import sabledream.studios.lostlegends.entity.BarnacleEntity;

public final class BarnacleStalkPreyTask extends MultiTickTask<BarnacleEntity>
{
	private LivingEntity prey;

	public BarnacleStalkPreyTask() {
		super(ImmutableMap.of());
	}

	/*
	@Override
	protected boolean shouldRun(ServerWorld world, GlareEntity glare) {
		//LivingEntity prey = barnacle.getOwner();

		return true;
	}

	@Override
	protected void run(ServerWorld world, GlareEntity glare, long time) {
		this.owner = glare.getOwner();
		this.tryTeleport(glare);
	} */
}
