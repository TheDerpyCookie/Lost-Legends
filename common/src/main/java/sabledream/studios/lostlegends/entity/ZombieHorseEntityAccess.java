package sabledream.studios.lostlegends.entity;

import sabledream.studios.lostlegends.mixin.ZombieHorseEntityMixin;

/**
 * @see ZombieHorseEntityMixin
 */
public interface ZombieHorseEntityAccess
{
	boolean LostLegends_isTrapped();

	void LostLegends_setTrapped(boolean isTrapped);
}
