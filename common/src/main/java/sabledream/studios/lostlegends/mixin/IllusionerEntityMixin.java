package sabledream.studios.lostlegends.mixin;

import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.entity.IllusionerEntityAccess;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.IllusionerEntity;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IllusionerEntity.class)
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class IllusionerEntityMixin extends IllusionerSpellcastingIllagerEntityMixin implements RangedAttackMob, IllusionerEntityAccess
{
	private static final int MAX_ILLUSIONS_COUNT = 9;
	private static final int ILLUSION_LIFETIME_TICKS = 600;
	private static final int INVISIBILITY_TICKS = 60;

	private static final String IS_ILLUSION_NBT_NAME = "IsIllusion";
	private static final String WAS_ATTACKED_NBT_NAME = "WasAttacked";
	private static final String TICKS_UNTIL_DESPAWN_NBT_NAME = "TicksUntilDespawn";
	private static final String TICKS_UNTIL_CAN_CREATE_ILLUSIONS_NBT_NAME = "TicksUntilCanCreateIllusions";

	private IllusionerEntity LostLegends_illusioner = null;
	private boolean LostLegends_isIllusion = false;
	private boolean LostLegends_wasAttacked = false;
	private int LostLegends_ticksUntilDespawn = 0;
	private int LostLegends_ticksUntilCanCreateIllusion = 0;

	protected IllusionerEntityMixin(
		EntityType<? extends SpellcastingIllagerEntity> entityType,
		World world
	) {
		super(entityType, world);
		this.LostLegends_illusioner = null;
		this.LostLegends_isIllusion = false;
		this.LostLegends_wasAttacked = false;
		this.LostLegends_ticksUntilDespawn = 0;
		this.LostLegends_ticksUntilCanCreateIllusion = 0;
	}

	@Override
	public void LostLegends_writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
		if (LostLegends.getConfig().enableIllusioner) {
			nbt.putBoolean(IS_ILLUSION_NBT_NAME, this.LostLegends_isIllusion());
			nbt.putBoolean(WAS_ATTACKED_NBT_NAME, this.LostLegends_wasAttacked());
			nbt.putInt(TICKS_UNTIL_DESPAWN_NBT_NAME, this.LostLegends_getTicksUntilDespawn());
			nbt.putInt(TICKS_UNTIL_CAN_CREATE_ILLUSIONS_NBT_NAME, this.LostLegends_getTicksUntilCanCreateIllusions());

		}
	}

	@Override
	public void LostLegends_readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
		if (LostLegends.getConfig().enableIllusioner) {
			this.LostLegends_setIsIllusion(nbt.getBoolean(IS_ILLUSION_NBT_NAME));
			this.LostLegends_setWasAttacked(nbt.getBoolean(WAS_ATTACKED_NBT_NAME));
			this.LostLegends_setTicksUntilDespawn(nbt.getInt(TICKS_UNTIL_DESPAWN_NBT_NAME));
			this.LostLegends_setTicksUntilCanCreateIllusions(nbt.getInt(TICKS_UNTIL_CAN_CREATE_ILLUSIONS_NBT_NAME));
		}
	}

	@ModifyArg(
		method = "initGoals",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V",
			ordinal = 1
		),
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/entity/mob/IllusionerEntity$GiveInvisibilityGoal;<init>(Lnet/minecraft/entity/mob/IllusionerEntity;)V"
			)
		),
		index = 1
	)
	private Goal replaceBlindTargetGoal(Goal original) {
		return BlindTargetGoalFactory.newBlindTargetGoal((IllusionerEntity) (Object) this);
	}

	@WrapWithCondition(
		method = "initGoals",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V",
			ordinal = 1
		),
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/entity/mob/IllusionerEntity$GiveInvisibilityGoal;<init>(Lnet/minecraft/entity/mob/IllusionerEntity;)V"
			)
		)
	)
	private boolean shouldReplaceBlindTargetGoal(GoalSelector instance, int priority, Goal goal) {
		return !LostLegends.getConfig().enableIllusioner || !this.LostLegends_isIllusion();
	}

	@ModifyArg(
		method = "initGoals",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V",
			ordinal = 2
		),
		slice = @Slice(
			from = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/entity/ai/goal/SwimGoal;<init>(Lnet/minecraft/entity/mob/MobEntity;)V"
			)
		),
		index = 1
	)
	private Goal replaceWithFleeGoal(Goal original) {
		return new FleeEntityGoal<>((IllusionerEntity) (Object) this, IronGolemEntity.class, 8.0F, 0.6, 1.0);
	}

	@Inject(
		at = @At("TAIL"),
		method = "tickMovement"
	)
	public void tickMovement(CallbackInfo ci) {
		if (!LostLegends.getConfig().enableIllusioner) {
			return;
		}

		if (this.getWorld().isClient()) {
			return;
		}

		if (this.LostLegends_getTicksUntilCanCreateIllusions() > 0) {
			this.LostLegends_setTicksUntilCanCreateIllusions(this.LostLegends_getTicksUntilCanCreateIllusions() - 1);
		}

		if (
			(
				this.getTarget() instanceof PlayerEntity
				|| this.getTarget() instanceof IronGolemEntity
			)
			&& this.LostLegends_wasAttacked()
			&& this.LostLegends_getTicksUntilCanCreateIllusions() == 0
		) {
			this.LostLegends_createIllusions();
		}

		if (
			this.LostLegends_wasAttacked()
			&& this.getTarget() == null
			&& this.LostLegends_getTicksUntilCanCreateIllusions() < ILLUSION_LIFETIME_TICKS / 3
		) {
			this.LostLegends_setWasAttacked(false);
			this.LostLegends_setTicksUntilCanCreateIllusions(0);
		}

		if (!this.LostLegends_isIllusion()) {
			return;
		}

		if (this.LostLegends_getTicksUntilDespawn() > 0) {
			this.LostLegends_setTicksUntilDespawn(this.LostLegends_getTicksUntilDespawn() - 1);
		}

		boolean isIllusionerNonExistingOrDead = this.LostLegends_getIllusioner() != null && !this.LostLegends_getIllusioner().isAlive();

		if (
			this.LostLegends_getTicksUntilDespawn() == 0
			|| isIllusionerNonExistingOrDead
		) {
			this.LostLegends_discardIllusion();
		}
	}

	@Override
	protected void LostLegends_shouldDropXp(CallbackInfoReturnable<Boolean> cir) {
		if (LostLegends.getConfig().enableIllusioner) {
			cir.setReturnValue(!this.LostLegends_isIllusion());
		}
	}

	@Override
	protected void LostLegends_shouldDropLoot(CallbackInfoReturnable<Boolean> cir) {
		if (LostLegends.getConfig().enableIllusioner) {
			cir.setReturnValue(!this.LostLegends_isIllusion());
		}
	}

	@Override
	public void LostLegends_damage(
		DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir
	) {
		if (LostLegends.getConfig().enableIllusioner) {
			Entity attacker = source.getAttacker();

			if (
				attacker instanceof IllusionerEntity
				|| (
					this.LostLegends_isIllusion()
					&& !(attacker instanceof LivingEntity)
				)
			) {
				cir.setReturnValue(false);
				return;
			}

			if (!this.getWorld().isClient()) {
				if (attacker instanceof PlayerEntity || attacker instanceof IronGolemEntity) {
					if (this.LostLegends_isIllusion()) {
						this.LostLegends_discardIllusion();
						cir.setReturnValue(false);
						return;
					}

					if (
						this.LostLegends_getTicksUntilCanCreateIllusions() == 0
						&& (
							attacker instanceof PlayerEntity
							&& !((PlayerEntity) source.getAttacker()).getAbilities().creativeMode
						)
					) {
						this.LostLegends_createIllusions();
					}
				}
			}
		}
	}

	private void LostLegends_discardIllusion() {
		this.LostLegends_playMirrorSound();
		this.LostLegends_spawnCloudParticles();
		this.discard();
	}

	private void LostLegends_createIllusions() {
		this.LostLegends_setWasAttacked(true);
		this.LostLegends_setTicksUntilCanCreateIllusions(ILLUSION_LIFETIME_TICKS);
		this.LostLegends_playMirrorSound();

		Vec3d illusionerPosition = this.getPos();
		float slice = 2.0F * (float) Math.PI / MAX_ILLUSIONS_COUNT;
		int radius = 9;
		int randomPoint = this.getRandom().nextBetween(0, MAX_ILLUSIONS_COUNT - 1);

		for (int point = 0; point < MAX_ILLUSIONS_COUNT; ++point) {
			float angle = slice * point;
			int x = (int) (illusionerPosition.getX() + radius * MathHelper.cos(angle));
			int y = (int) illusionerPosition.getY();
			int z = (int) (illusionerPosition.getZ() + radius * MathHelper.sin(angle));

			if (randomPoint == point) {
				boolean teleportResult = this.LostLegends_tryToTeleport(x, y, z);

				if (teleportResult) {
					this.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, INVISIBILITY_TICKS));
					this.LostLegends_spawnCloudParticles();
				}
			} else {
				this.LostLegends_createIllusion(x, y, z);
			}
		}
	}

	private void LostLegends_createIllusion(int x, int y, int z) {
		IllusionerEntity illusioner = (IllusionerEntity) (Object) this;
		IllusionerEntity illusion = EntityType.ILLUSIONER.create(this.getWorld());

		illusion.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
		IllusionerEntityAccess illusionerAccess = (IllusionerEntityAccess) illusion;
		illusionerAccess.LostLegends_setIsIllusion(true);
		illusionerAccess.LostLegends_setIllusioner(illusioner);
		illusionerAccess.LostLegends_setTicksUntilDespawn(ILLUSION_LIFETIME_TICKS);

		illusion.setHealth(this.getMaxHealth());
		illusion.copyPositionAndRotation(illusioner);
		illusion.setTarget(illusioner.getTarget());

		boolean teleportResult = illusionerAccess.LostLegends_tryToTeleport(x, y, z);

		if (teleportResult) {
			this.getEntityWorld().spawnEntity(illusion);
			illusionerAccess.LostLegends_spawnCloudParticles();
		}
	}

	public boolean LostLegends_tryToTeleport(int x, int y, int z) {
		y -= 8;
		double bottomY = Math.max(y, getWorld().getBottomY());
		double topY = Math.min(bottomY + 16, ((ServerWorld) this.getWorld()).getLogicalHeight() - 1);

		for (int i = 0; i < 16; ++i) {
			y = (int) MathHelper.clamp(y + 1, bottomY, topY);
			boolean teleportResult = this.teleport(x, y, z, false);

			if (teleportResult) {
				return true;
			}
		}

		return false;
	}

	private void LostLegends_playMirrorSound() {
		this.playSound(
			SoundEvents.ENTITY_ILLUSIONER_MIRROR_MOVE,
			this.getSoundVolume(),
			this.getSoundPitch()
		);
	}

	public void LostLegends_spawnCloudParticles() {
		this.LostLegends_spawnParticles(ParticleTypes.CLOUD, 16);
	}

	private <T extends ParticleEffect> void LostLegends_spawnParticles(
		T particleType,
		int amount
	) {
		if (this.getWorld().isClient()) {
			return;
		}

		for (int i = 0; i < amount; i++) {
			((ServerWorld) this.getEntityWorld()).spawnParticles(
				particleType,
				this.getParticleX(0.5D),
				this.getRandomBodyY() + 0.5D,
				this.getParticleZ(0.5D),
				1,
				0.0D,
				0.0D,
				0.0D,
				0.0D
			);
		}
	}

	public boolean LostLegends_isIllusion() {
		return this.LostLegends_isIllusion;
	}

	public void LostLegends_setIsIllusion(boolean isIllusion) {
		this.LostLegends_isIllusion = isIllusion;
	}

	public boolean LostLegends_wasAttacked() {
		return this.LostLegends_wasAttacked;
	}

	public void LostLegends_setWasAttacked(boolean wasAttacked) {
		this.LostLegends_wasAttacked = wasAttacked;
	}

	@Nullable
	public IllusionerEntity LostLegends_getIllusioner() {
		return this.LostLegends_illusioner;
	}

	public void LostLegends_setIllusioner(IllusionerEntity illusioner) {
		this.LostLegends_illusioner = illusioner;
	}

	public int LostLegends_getTicksUntilDespawn() {
		return this.LostLegends_ticksUntilDespawn;
	}

	public void LostLegends_setTicksUntilDespawn(int ticksUntilDespawn) {
		this.LostLegends_ticksUntilDespawn = ticksUntilDespawn;
	}

	public int LostLegends_getTicksUntilCanCreateIllusions() {
		return this.LostLegends_ticksUntilCanCreateIllusion;
	}

	public void LostLegends_setTicksUntilCanCreateIllusions(int ticksUntilCanCreateIllusions) {
		this.LostLegends_ticksUntilCanCreateIllusion = ticksUntilCanCreateIllusions;
	}
}
