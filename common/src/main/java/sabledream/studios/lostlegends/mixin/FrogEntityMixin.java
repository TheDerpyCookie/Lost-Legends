package sabledream.studios.lostlegends.mixin;

import net.minecraft.block.TurtleEggBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FrogEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sabledream.studios.lostlegends.entity.FireflyEntity;

@Mixin(FrogEntity.class)
public abstract class FrogEntityMixin {
	@Inject(method = "isValidFrogFood", at = @At("HEAD"), cancellable = true)
	private static void isValidFrogFood(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
		if (entity instanceof FireflyEntity) cir.setReturnValue(true);
	}
}