package sabledream.studios.lostlegends.entity.ai.goal.mauler;

import sabledream.studios.lostlegends.entity.MaulerEntity;
import net.minecraft.entity.ai.goal.LookAroundGoal;

public final class MaulerLookAroundGoal extends LookAroundGoal
{
	private final MaulerEntity mauler;

	public MaulerLookAroundGoal(MaulerEntity mauler) {
		super(mauler);
		this.mauler = mauler;
	}

	@Override
	public boolean canStart() {
		if (this.mauler.isBurrowedDown()) {
			return false;
		}

		return super.canStart();
	}
}
