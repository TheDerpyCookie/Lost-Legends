package sabledream.studios.lostlegends.mixin;

import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.entity.IceologerEntity;
import net.minecraft.entity.mob.IllusionerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.raid.Raid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Raid.class)
public final class RaidMixin
{
	@Inject(
		method = "addRaider",
		at = @At("HEAD"),
		cancellable = true
	)
	public void LostLegends_addRaider(
		int wave,
		RaiderEntity raider,
		BlockPos pos,
		boolean existing,
		CallbackInfo ci
	) {
		if (
			(
				raider instanceof IllusionerEntity
				&& (
					!LostLegends.getConfig().enableIllusioner
					|| !LostLegends.getConfig().enableIllusionerInRaids
				)
			) || (
				raider instanceof IceologerEntity
				&& (
					!LostLegends.getConfig().enableIceologer
					|| !LostLegends.getConfig().enableIceologerInRaids
				)
			)
		) {
			ci.cancel();
		}
	}
}
