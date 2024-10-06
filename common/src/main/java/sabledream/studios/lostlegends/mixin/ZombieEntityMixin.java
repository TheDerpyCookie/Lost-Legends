package sabledream.studios.lostlegends.mixin;

import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sabledream.studios.lostlegends.entity.ai.goal.zombie.ZombieDestroyEggsGoal;
import java.lang.reflect.Field;


@Mixin(ZombieEntity.class)
public abstract class ZombieEntityMixin {

	// Inject into the initGoals method to add the modified DestroyEggGoal
	@Inject(method = "initGoals", at = @At("TAIL"))
	private void addModifiedDestroyEggGoal(CallbackInfo ci) {
		ZombieEntity entity = (ZombieEntity) (Object) this;

		// Access the goalSelector directly
		GoalSelector goalSelector = ((MobEntity) entity).goalSelector;

		// Add your custom goal
		goalSelector.add(1, new ZombieDestroyEggsGoal(entity, 1.0, 3));
	}
}