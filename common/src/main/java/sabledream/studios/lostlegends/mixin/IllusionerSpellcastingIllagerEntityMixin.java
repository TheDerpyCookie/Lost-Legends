package sabledream.studios.lostlegends.mixin;


import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpellcastingIllagerEntity.class)
public abstract class IllusionerSpellcastingIllagerEntityMixin extends IllusionerRaiderEntityMixin
{
	protected IllusionerSpellcastingIllagerEntityMixin(EntityType<? extends IllagerEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(
		method = "writeCustomDataToNbt",
		at = @At("TAIL")
	)
	public void LostLegends_writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
	}

	@Inject(
		method = "readCustomDataFromNbt",
		at = @At("TAIL")
	)
	public void LostLegends_readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
	}
}
