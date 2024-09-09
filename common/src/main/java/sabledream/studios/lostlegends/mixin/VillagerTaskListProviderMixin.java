package sabledream.studios.lostlegends.mixin;

import sabledream.studios.lostlegends.entity.ai.brain.task.beekeeper.BeekeeperWorkTask;
import sabledream.studios.lostlegends.init.LostLegendsVillagerProfessions;
import net.minecraft.entity.ai.brain.task.VillagerTaskListProvider;
import net.minecraft.entity.ai.brain.task.VillagerWorkTask;
import net.minecraft.village.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(VillagerTaskListProvider.class)
public final class VillagerTaskListProviderMixin
{
	@ModifyVariable(
		method = "createWorkTasks(Lnet/minecraft/village/VillagerProfession;F)Lcom/google/common/collect/ImmutableList;",
		at = @At("STORE"),
		ordinal = 0
	)
	private static VillagerWorkTask LostLegends_setSecondVillagerWorkTask(
		VillagerWorkTask originalTask,
		VillagerProfession profession,
		float f
	) {
		if (
			profession == LostLegendsVillagerProfessions.BEEKEEPER.get()
		) {
			return new BeekeeperWorkTask();
		}

		return originalTask;
	}
}
