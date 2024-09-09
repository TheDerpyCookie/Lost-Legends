package sabledream.studios.lostlegends.mixin;

import sabledream.studios.lostlegends.entity.ZombieHorseEntityAccess;
import sabledream.studios.lostlegends.entity.ai.goal.zombiehorse.ZombieHorseTrapTriggerGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ZombieHorseEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieHorseEntity.class)
public abstract class ZombieHorseEntityMixin extends ZombieHorseAbstractHorseEntityMixin implements ZombieHorseEntityAccess
{
	@Unique
	private final ZombieHorseTrapTriggerGoal LostLegends_trapTriggerGoal = new ZombieHorseTrapTriggerGoal((ZombieHorseEntity) (Object) this);

	@Unique
	private boolean LostLegends_isTrapped;

	@Unique
	private int LostLegends_trapTime;

	protected ZombieHorseEntityMixin(EntityType<? extends AbstractHorseEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void LostLegends_writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
		nbt.putBoolean("ZombieTrap", this.LostLegends_isTrapped());
		nbt.putInt("ZombieTrapTime", this.LostLegends_trapTime);
	}

	@Override
	public void LostLegends_readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
		this.LostLegends_setTrapped(nbt.getBoolean("ZombieTrap"));
		this.LostLegends_trapTime = nbt.getInt("ZombieTrapTime");
	}

	@Override
	protected void LostLegends_tickMovement(CallbackInfo ci) {
		if (this.LostLegends_isTrapped() && this.LostLegends_trapTime++ >= 18000) {
			this.discard();
		}
	}

	public boolean LostLegends_isTrapped() {
		return this.LostLegends_isTrapped;
	}

	public void LostLegends_setTrapped(boolean isTrapped) {
		if (isTrapped == this.LostLegends_isTrapped) {
			return;
		}

		this.LostLegends_isTrapped = isTrapped;

		if (isTrapped) {
			this.goalSelector.add(1, this.LostLegends_trapTriggerGoal);
		} else {
			this.goalSelector.remove(this.LostLegends_trapTriggerGoal);
		}
	}
}
