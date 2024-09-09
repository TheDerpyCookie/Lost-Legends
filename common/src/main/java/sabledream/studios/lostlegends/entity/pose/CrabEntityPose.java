package sabledream.studios.lostlegends.entity.pose;


import net.minecraft.entity.EntityPose;


public enum CrabEntityPose
{
	IDLE,
	WAVE,
	RAVE;

	public String getName() {
		return "CRAB_" + this.name();
	}

	public EntityPose get() {
		return EntityPose.valueOf(this.getName());
	}

	static {
		EntityPose.values();
	}
}
