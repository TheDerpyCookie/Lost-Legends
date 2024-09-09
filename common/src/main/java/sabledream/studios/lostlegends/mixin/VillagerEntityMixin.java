package sabledream.studios.lostlegends.mixin;

import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.init.LostLegendsVillagerProfessions;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin
{
	@Shadow
	public abstract void setVillagerData(VillagerData villagerData);

	@Shadow
	public abstract VillagerData getVillagerData();

	@Inject(
		at = @At("HEAD"),
		method = "tick"
	)
	private void LostLegends_tick(CallbackInfo ci) {
		if (
			!LostLegends.getConfig().enableBeekeeperVillagerProfession
			&& this.getVillagerData().getProfession() == LostLegendsVillagerProfessions.BEEKEEPER.get()
		) {
			this.setVillagerData(this.getVillagerData().withProfession(VillagerProfession.NONE));
		}
	}
}
