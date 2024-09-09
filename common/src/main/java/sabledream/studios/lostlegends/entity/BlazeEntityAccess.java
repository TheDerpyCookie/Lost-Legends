package sabledream.studios.lostlegends.entity;

import sabledream.studios.lostlegends.mixin.BlazeEntityMixin;
import org.jetbrains.annotations.Nullable;

/**
 * @see BlazeEntityMixin
 */
public interface BlazeEntityAccess
{
	void LostLegends_setWildfire(WildfireEntity wildfire);

	@Nullable
	WildfireEntity LostLegends_getWildfire();
}
