package sabledream.studios.lostlegends.mixin;

import sabledream.studios.lostlegends.entity.PlayerIllusionEntity;
import sabledream.studios.lostlegends.init.LostLegendsEntityTypes;
import sabledream.studios.lostlegends.init.LostLegendsItems;
import sabledream.studios.lostlegends.init.LostLegendsSoundEvents;
import sabledream.studios.lostlegends.modcompat.ModChecker;
import sabledream.studios.lostlegends.modcompat.ModCompat;
import sabledream.studios.lostlegends.packets.TotemEffectPacket;
import sabledream.studios.lostlegends.tag.LostLegendsTags;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity
{
	private static final int MAX_ILLUSIONS_COUNT = 9;
	private static final int ILLUSION_LIFETIME_TICKS = 600;
	private static final int NEGATIVE_EFFECT_TICKS = 400;
	private static final int POSITIVE_EFFECT_TICKS = 200;
	private static final Predicate<LivingEntity> FREEZE_FILTER = (entity) -> {
		return !(entity instanceof PlayerEntity) || !((PlayerEntity) entity).isCreative();
	};
	private static final TargetPredicate FREEZE_TARGET_PREDICATE = TargetPredicate.createNonAttackable().ignoreDistanceScalingFactor().ignoreVisibility().setPredicate(FREEZE_FILTER);
	private static final TargetPredicate ATTACK_TARGET_PREDICATE = TargetPredicate.createNonAttackable().ignoreDistanceScalingFactor().ignoreVisibility();

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Shadow
	public abstract ItemStack getEquippedStack(EquipmentSlot slot);

	@Shadow
	public abstract Iterable<ItemStack> getArmorItems();

	@Shadow
	public double prevCapeX;

	@Shadow
	public double prevCapeZ;

	@Shadow
	public double prevCapeY;

	@Shadow
	public double capeX;

	@Shadow
	public double capeY;

	@Shadow
	public double capeZ;

	@Shadow
	public float prevStrideDistance;

	@Shadow
	public float strideDistance;

	@Inject(
		at = @At("TAIL"),
		method = "tick"
	)
	private void LostLegends_addToTick(CallbackInfo ci) {
		this.LostLegends_updateWildfireCrown();
	}

	private void LostLegends_updateWildfireCrown() {
		ItemStack itemStack = this.getEquippedStack(EquipmentSlot.HEAD);

		if (itemStack.isOf(LostLegendsItems.WILDFIRE_CROWN.get()) && (!this.isSubmergedIn(FluidTags.LAVA) && !this.isOnFire())) {
			this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 160, 0, false, false, true));
		}
	}

	@Inject(
		at = @At("HEAD"),
		method = "damage",
		cancellable = true
	)
	public void LostLegends_tryUseTotems(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		PlayerEntityMixin entity = this;
		PlayerEntity player = (PlayerEntity) (Object) this;

		if (
			player.isAlive()
			&& source.isOf(DamageTypes.IN_FIRE) == false
			&& source.isOf(DamageTypes.ON_FIRE) == false
			&& source.isOf(DamageTypes.FALL) == false
			&& source.isOf(DamageTypes.FALLING_BLOCK) == false
			&& source.getAttacker() != null
			&& !player.isDead()
			&& this.getHealth() <= this.getMaxHealth() / 2.0F
		) {
			ItemStack totemItemStack = LostLegends_getTotem(
				LostLegends_getTotemFromHands(player),
				LostLegends_getTotemFromCustomEquipmentSlots(player)
			);

			if (totemItemStack != null) {
				if ((Object) this instanceof ServerPlayerEntity) {
					ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) (Entity) this;

					if (totemItemStack.getItem() == LostLegendsItems.TOTEM_OF_FREEZING.get()) {
						serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(LostLegendsItems.TOTEM_OF_FREEZING.get()));
					} else if (totemItemStack.getItem() == LostLegendsItems.TOTEM_OF_ILLUSION.get()) {
						serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(LostLegendsItems.TOTEM_OF_ILLUSION.get()));
					}

					Criteria.USED_TOTEM.trigger(serverPlayerEntity, totemItemStack);
				}

				Item totemItem = totemItemStack.getItem();
				this.clearStatusEffects();
				TotemEffectPacket.sendToClient(((PlayerEntity) (Object) entity), totemItem);
				totemItemStack.decrement(1);

				if (totemItem == LostLegendsItems.TOTEM_OF_FREEZING.get()) {
					this.LostLegends_freezeEntities();
					this.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, POSITIVE_EFFECT_TICKS, 1));
				} else if (totemItem == LostLegendsItems.TOTEM_OF_ILLUSION.get()) {
					this.LostLegends_createIllusions();
				}
			}
		}
	}

	@Nullable
	private static ItemStack LostLegends_getTotem(ItemStack... itemStacks) {
		return Arrays.stream(itemStacks).filter(Objects::nonNull).toList().stream().findFirst().orElse(null);
	}

	@Nullable
	private static ItemStack LostLegends_getTotemFromHands(PlayerEntity player) {
		for (Hand hand : Hand.values()) {
			ItemStack itemStack = player.getStackInHand(hand);

			if (LostLegends_isTotem(itemStack)) {
				return itemStack;
			}
		}

		return null;
	}

	@Nullable
	private static ItemStack LostLegends_getTotemFromCustomEquipmentSlots(PlayerEntity player) {
		for (ModCompat compat : ModChecker.CUSTOM_EQUIPMENT_SLOTS_COMPATS) {
			ItemStack itemStack = compat.getEquippedItemFromCustomSlots(player, PlayerEntityMixin::LostLegends_isTotem);

			if (itemStack != null) {
				return itemStack;
			}
		}

		return null;
	}

	private static boolean LostLegends_isTotem(ItemStack itemStack) {
		return itemStack.isIn(LostLegendsTags.TOTEMS);
	}

	private void LostLegends_freezeEntities() {
		List<LivingEntity> nearbyEntities = this.getWorld().getEntitiesByClass(LivingEntity.class, this.getBoundingBox().expand(9.0), (livingEntity) -> {
			return FREEZE_TARGET_PREDICATE.test(this, livingEntity);
		});

		nearbyEntities.forEach(nearbyEntity -> {
			nearbyEntity.setFrozenTicks(NEGATIVE_EFFECT_TICKS);
			nearbyEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, NEGATIVE_EFFECT_TICKS, 1));
		});
	}

	private void LostLegends_createIllusions() {
		this.playSound(
			LostLegendsSoundEvents.ENTITY_PLAYER_MIRROR_MOVE.get(),
			this.getSoundVolume(),
			this.getSoundPitch()
		);

		Vec3d illusionerPosition = this.getPos();
		float slice = 2.0F * (float) Math.PI / MAX_ILLUSIONS_COUNT;
		int radius = 9;

		ArrayList<PlayerIllusionEntity> createdPlayerIllusions = new ArrayList<>();

		for (int point = 0; point < MAX_ILLUSIONS_COUNT; ++point) {
			float angle = slice * point;
			int x = (int) (illusionerPosition.getX() + radius * MathHelper.cos(angle));
			int y = (int) illusionerPosition.getY();
			int z = (int) (illusionerPosition.getZ() + radius * MathHelper.sin(angle));

			PlayerIllusionEntity createdPlayerIllusion = this.LostLegends_createIllusion(x, y, z);

			if (createdPlayerIllusion != null) {
				createdPlayerIllusions.add(createdPlayerIllusion);
			}
		}

		List<MobEntity> nearbyEntities = this.getWorld().getEntitiesByClass(MobEntity.class, this.getBoundingBox().expand(18.0), (mobEntity) -> {
			return ATTACK_TARGET_PREDICATE.test(this, mobEntity);
		});

		nearbyEntities.forEach(nearbyEntity -> {
			if (nearbyEntity.getTarget() == this) {
				if (!createdPlayerIllusions.isEmpty()) {
					nearbyEntity.setAttacking(true);
					nearbyEntity.setAttacker(createdPlayerIllusions.get(this.getRandom().nextInt(createdPlayerIllusions.size())));
					nearbyEntity.onAttacking(createdPlayerIllusions.get(this.getRandom().nextInt(createdPlayerIllusions.size())));
				}

				nearbyEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, NEGATIVE_EFFECT_TICKS, 1));
			}
		});

		if (!createdPlayerIllusions.isEmpty()) {
			var illusionToReplace = createdPlayerIllusions.get(this.getRandom().nextInt(createdPlayerIllusions.size()));
			boolean teleportResult = this.LostLegends_tryToTeleport(illusionToReplace.getBlockX(), illusionToReplace.getBlockY(), illusionToReplace.getBlockZ());

			if (teleportResult) {
				this.LostLegends_spawnCloudParticles();
			}

			var attacker = illusionToReplace.getAttacker();

			if (attacker != null) {
				illusionToReplace.setAttacking(null);
				illusionToReplace.setAttacker(null);
				illusionToReplace.onAttacking(null);
			}

			illusionToReplace.discard();
		}

		this.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, POSITIVE_EFFECT_TICKS));
	}

	@Nullable
	private PlayerIllusionEntity LostLegends_createIllusion(int x, int y, int z) {
		PlayerIllusionEntity playerIllusion = LostLegendsEntityTypes.PLAYER_ILLUSION.get().create(this.getWorld());

		if (playerIllusion == null) {
			return null;
		}

		playerIllusion.prevCapeX = this.prevCapeX;
		playerIllusion.prevCapeY = this.prevCapeY;
		playerIllusion.prevCapeZ = this.prevCapeZ;
		playerIllusion.capeX = this.capeX;
		playerIllusion.capeY = this.capeY;
		playerIllusion.capeZ = this.capeZ;
		playerIllusion.prevStrideDistance = this.prevStrideDistance;
		playerIllusion.strideDistance = this.strideDistance;

		playerIllusion.equipStack(EquipmentSlot.MAINHAND, getMainHandStack().copy());
		playerIllusion.equipStack(EquipmentSlot.OFFHAND, getOffHandStack().copy());
		this.getArmorItems().forEach((item) -> playerIllusion.tryEquip(item.copy()));

		playerIllusion.setHealth(this.getMaxHealth());
		playerIllusion.copyPositionAndRotation(this);
		float randomYaw = 360.F * this.getRandom().nextFloat();
		playerIllusion.prevYaw = randomYaw;
		playerIllusion.setYaw(randomYaw);
		playerIllusion.prevBodyYaw = randomYaw;
		playerIllusion.setBodyYaw(randomYaw);
		playerIllusion.prevHeadYaw = randomYaw;
		playerIllusion.setHeadYaw(randomYaw);
		playerIllusion.setPlayerUuid(this.getUuid());
		playerIllusion.setPlayer((PlayerEntity) (Object) this);
		playerIllusion.setTicksUntilDespawn(ILLUSION_LIFETIME_TICKS);

		boolean teleportResult = playerIllusion.tryToTeleport(x, y, z);

		if (teleportResult) {
			getWorld().spawnEntity(playerIllusion);
			playerIllusion.spawnCloudParticles();
		}

		return playerIllusion;
	}

	private boolean LostLegends_tryToTeleport(int x, int y, int z) {
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

	private void LostLegends_spawnCloudParticles() {
		this.LostLegends_spawnParticles(ParticleTypes.CLOUD, 16);
	}

	private void LostLegends_spawnParticles(
		SimpleParticleType particleType,
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
}
