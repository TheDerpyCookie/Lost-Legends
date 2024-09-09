package sabledream.studios.lostlegends.entity;

import sabledream.studios.lostlegends.mixin.IllusionerEntityMixin;
import net.minecraft.entity.mob.IllusionerEntity;

/**
 * @see IllusionerEntityMixin
 */
public interface IllusionerEntityAccess
{
	void LostLegends_setIllusioner(IllusionerEntity illusionerEntity);

	void LostLegends_setIsIllusion(boolean isIllusion);

	void LostLegends_setTicksUntilDespawn(int ticksUntilDespawn);

	boolean LostLegends_tryToTeleport(int x, int y, int z);

	void LostLegends_spawnCloudParticles();
}
