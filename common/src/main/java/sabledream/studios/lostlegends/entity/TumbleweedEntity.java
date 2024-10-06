package sabledream.studios.lostlegends.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Arm;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.api.wind.WindManager;
import sabledream.studios.lostlegends.entity.impl.EntityStepOnBlockInterface;
import sabledream.studios.lostlegends.init.LostLegendsBlocks;
import sabledream.studios.lostlegends.init.LostLegendsEntityTypes;
import sabledream.studios.lostlegends.init.LostLegendsSoundEvents;
import sabledream.studios.lostlegends.tag.LostLegendsTags;
import sabledream.studios.lostlegends.util.TagUtils;

import java.util.List;

public class TumbleweedEntity extends MobEntity implements EntityStepOnBlockInterface
{
	public static final int SPAWN_CHANCE = 60;
	private static final double WIND_MULTIPLIER = 1.4D;
	private static final double WIND_CLAMP = 0.2D;
	private static final float ROTATION_AMOUNT = 55F;
	private static final float MAX_ITEM_OFFSET = 0.25F;
	public static final double INACTIVE_PLAYER_DISTANCE_FROM = 24D;
	public static final int MAX_INACTIVE_TICKS = 200;
	public static final int TUMBLEWEED_PLANT_ITEM_CHANCE = 15;
	private static final TrackedData<ItemStack> ITEM_STACK = DataTracker.registerData(TumbleweedEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
	private static final TrackedData<Float> ITEM_X = DataTracker.registerData(TumbleweedEntity.class, TrackedDataHandlerRegistry.FLOAT);
	private static final TrackedData<Float> ITEM_Z = DataTracker.registerData(TumbleweedEntity.class, TrackedDataHandlerRegistry.FLOAT);
	public DefaultedList<ItemStack> inventory;
	public boolean spawnedFromShears;
	public int ticksSinceActive;
	public boolean isItemNatural;
	public boolean isTouchingStickingBlock;
	public boolean isTouchingStoppingBlock;
	public float prevPitch;
	public float prevRoll;
	public float pitch;
	public float roll;
	public float prevTumble;
	public float tumble;
	public float itemX;
	public float itemZ;
	private float lookRot;

	public TumbleweedEntity(EntityType<TumbleweedEntity> entityType, World world){
		super(entityType, world);
		this.intersectionChecked = true;
		this.inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
	}


	@Override
	public void LostLegends$onSteppedOnBlock(World level, BlockPos pos, BlockState state) {
		if(state.isIn(LostLegendsTags.STOPS_TUMBLEWEED)){
			this.isTouchingStoppingBlock = true;
		}

	}


	public static boolean checkTumbleweedSpawnRules(EntityType<TumbleweedEntity> type, @NotNull ServerWorldAccess level, SpawnReason spawnType, @NotNull BlockPos pos, @NotNull Random random) {
		if (!SpawnReason.isAnySpawner(spawnType) && !LostLegends.getConfig().spawnTumbleweed) return false;
		return level.getBrightness(pos) > 7 && random.nextInt(SPAWN_CHANCE) == 0 && pos.getY() > level.getSeaLevel();
	}

	@NotNull
	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 1D);
	}

	public static boolean isSilkTouchOrShears(@NotNull DamageSource damageSource) {
		if (damageSource.getSource() instanceof LivingEntity livingEntity) {
			ItemStack stack = livingEntity.getMainHandStack();
			var silkTouch = livingEntity.getRegistryManager().getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH);
			return EnchantmentHelper.getLevel(silkTouch, stack) > 0 || stack.isOf(Items.SHEARS);
		}
		return false;
	}

	@Nullable
	@Override
	public EntityData initialize(@NotNull ServerWorldAccess level, @NotNull LocalDifficulty difficulty, @NotNull SpawnReason reason, @Nullable EntityData spawnData) {
		if (this.inventory.get(0).isEmpty() && reason == SpawnReason.NATURAL) {
			int diff = difficulty.getGlobalDifficulty().getId();
			if (this.random.nextBetweenExclusive(0, diff == 0 ? 32 : (27 / diff)) == 0) {
				int tagSelector = this.random.nextBetweenExclusive(1, 6);
				TagKey<Item> itemTag = tagSelector <= 1 ? LostLegendsTags.TUMBLEWEED_RARE :tagSelector <= 3 ? LostLegendsTags.TUMBLEWEED_MEDIUM : LostLegendsTags.TUMBLEWEED_COMMON;
				ItemConvertible itemLike = TagUtils.getRandomEntry(this.random, itemTag);
				if (itemLike != null) {
					this.setItem(new ItemStack(itemLike), true);
				}
			} else if (this.random.nextInt(TUMBLEWEED_PLANT_ITEM_CHANCE) == 0) {
				this.setItem(new ItemStack(LostLegendsBlocks.TUMBLEWEED_PLANT.get()), true);
			}
		}
		return super.initialize(level, difficulty, reason, spawnData);
	}

	public static void spawnFromShears(@NotNull World level, BlockPos pos) {
		level.playSound(null, pos, LostLegendsSoundEvents.BLOCK_TUMBLEWEED_SHEAR.get(), SoundCategory.BLOCKS, 1F, 1F);
		TumbleweedEntity weed = new TumbleweedEntity(LostLegendsEntityTypes.TUMBLEWEED.get(), level);
		level.spawnEntity(weed);
		weed.setPosition(Vec3d.ofBottomCenter(pos));
		weed.spawnedFromShears = true;
	}

	@Override
	protected void pushAway(@NotNull Entity entity) {
		if (entity.getType().isIn(LostLegendsTags.TUMBLEWEED_PASSES_THROUGH)) return;
		boolean isSmall = entity.getBoundingBox().getAverageSideLength() < this.getBoundingBox().getAverageSideLength() * 0.9D;
		if (entity instanceof TumbleweedEntity) {
			super.pushAway(entity);
		}
		if (this.getDeltaPos().length() > (isSmall ? 0.2D : 0.3D) && this.isMovingTowards(entity) && !(entity instanceof TumbleweedEntity)) {
//			boolean hurt = entity.damage(this.getDamageSources().create(RegisterDamageTypes.TUMBLEWEED, this), 2F);
			isSmall = isSmall || !entity.isAlive();
			if (!isSmall) {
				this.destroy(false);
			}
		}
	}


	@Override
	protected void drop(ServerWorld level, DamageSource source) {
		if (!isSilkTouchOrShears(source)) {
			super.drop(level, source);
		}
	}

	@Override
	protected void onBlockCollision(@NotNull BlockState state) {
		if (state.getBlock() instanceof LeavesBlock) {
			this.isTouchingStickingBlock = true;
		}
	}

	@Override
	public void tick() {
		if (this.isTouchingStickingBlock) {
			this.setVelocity(Vec3d.ZERO);
			this.isTouchingStickingBlock = false;
		}
		this.isTouchingStoppingBlock = false;
		if (!this.getWorld().isClient && this.getSteppingBlockState().isIn(BlockTags.CROPS) && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) && !this.isOnGround()) {
			if (LostLegends.getConfig().tumbleweedDestroysCrops) {
				this.getWorld().breakBlock(this.getBlockPos(), true, this);
			}
		}
		super.tick();
		Vec3d deltaPos = this.getDeltaPos();
		this.setAngles(deltaPos);
		if (this.getWorld().isClient) {
			this.itemX = this.getItemX();
			this.itemZ = this.getItemZ();
		} else if (!this.isRemoved() && this.getWorld() instanceof ServerWorld serverLevel) {
			this.heal(1F);
			double brightness = this.getWorld().getBrightness(BlockPos.ofFloored(this.getEyePos()));
			this.checkActive(brightness);
			this.moveWithWind(serverLevel, brightness, deltaPos);
			this.tickAfterWindLeash();
			this.pickupItem();
		}
	}

	public void setAngles(@NotNull Vec3d deltaPos) {
		if (deltaPos.horizontalLength() > 0.01) {
			this.lookRot = -((float) MathHelper.atan2(deltaPos.x, deltaPos.z)) * MathHelper.DEGREES_PER_RADIAN;
		}
		float yRot = this.getYaw();
		this.setYaw(yRot += ((this.lookRot - yRot) * 0.25F));
		this.bodyYaw = yRot;
		this.prevPitch = this.pitch;
		this.prevRoll = this.roll;
		this.prevTumble = this.tumble;
		float yRotAmount = (float) ((Math.abs(deltaPos.y) * 0.5F) * ROTATION_AMOUNT);
		this.pitch -= (float) (deltaPos.z * ROTATION_AMOUNT);
		this.roll -= (float) (deltaPos.x * ROTATION_AMOUNT);
		this.pitch += yRotAmount;
		this.roll += yRotAmount;
		this.tumble += (float) (((Math.abs(deltaPos.z) + Math.abs(deltaPos.x) + (Math.abs(deltaPos.x) * 0.5F)) * 0.5F) * ROTATION_AMOUNT);
		if (this.pitch > 360F) {
			this.pitch -= 360F;
			this.prevPitch -= 360F;
		} else if (this.pitch < 0F) {
			this.pitch += 360F;
			this.prevPitch += 360F;
		}
		if (this.roll > 360F) {
			this.roll -= 360F;
			this.prevRoll -= 360F;
		} else if (this.roll < 0F) {
			this.roll += 360F;
			this.prevRoll += 360F;
		}
		if (this.tumble > 360F) {
			this.tumble -= 360F;
			this.prevTumble -= 360F;
		} else if (this.tumble < 0F) {
			this.tumble += 360F;
			this.prevTumble += 360F;
		}
	}

	private void checkActive(double brightness) {
		PlayerEntity entity = this.getWorld().getClosestPlayer(this, -1D);
		if (!this.cannotDespawn() && ((brightness < 7 && (entity == null || entity.distanceTo(this) > INACTIVE_PLAYER_DISTANCE_FROM)) || this.isTouchingStoppingBlock || this.isTouchingStickingBlock || (this.touchingWater))) {
			++this.ticksSinceActive;
			if (this.ticksSinceActive >= MAX_INACTIVE_TICKS) {
				this.destroy(false);
			}
		} else {
			this.ticksSinceActive = 0;
		}
	}

	private void moveWithWind(@NotNull ServerWorld serverLevel, double brightness, @NotNull Vec3d deltaPos) {
		if (!(this.isTouchingStoppingBlock || this.isTouchingStickingBlock)) {
			Vec3d deltaMovement = this.getVelocity();
			WindManager windManager = WindManager.getWindManager(serverLevel);
			Vec3d windVec = windManager.getWindMovement(this.getPos(), WIND_MULTIPLIER, WIND_CLAMP).multiply(this.touchingWater ? 0.16777216D : 1D);
			double multiplier = (Math.max((brightness - (Math.max(15 - brightness, 0))), 0) * 0.0667D) * (this.touchingWater ? 0.16777216D : 1D);
			deltaMovement = deltaMovement.add((windVec.x * 0.2D), 0D, (windVec.z * 0.2D));
			deltaMovement = new Vec3d(deltaMovement.x, deltaMovement.y < 0 ? deltaMovement.y * 0.88D : deltaMovement.y, deltaMovement.z);
			if (deltaPos.y <= 0D && this.isOnGround()) {
				deltaMovement = deltaMovement.add(0D, Math.min(0.65D, ((deltaPos.horizontalLength() * 1.2D))) * multiplier, 0D);
			}
			if (deltaPos.x == 0D) {
				double nonNegX = deltaMovement.x < 0D ? -deltaMovement.x : deltaMovement.x;
				deltaMovement = deltaMovement.add(0D, (nonNegX * 1.8D) * multiplier, 0D);
			}
			if (deltaPos.z == 0D) {
				double nonNegZ = deltaMovement.z < 0D ? -deltaMovement.z : deltaMovement.z;
				deltaMovement = deltaMovement.add(0D, (nonNegZ * 1.8D) * multiplier, 0D);
			}
			if (this.submergedInWater) {
				deltaMovement = deltaMovement.add(0D, 0.01D, 0D);
			}
			this.setVelocity(deltaMovement);
		}
	}

	protected void tickAfterWindLeash() {
		Entity entity = this.getLeashHolder();
		if (entity != null && entity.getWorld() == this.getWorld()) {
			this.setPositionTarget(entity.getBlockPos(), 5);
			float f = this.distanceTo(entity);
			if (f > 10.0f) {
				this.detachLeash(true, true);
			} else if (f > 6.0f) {
				double d = (entity.getX() - this.getX()) / (double) f;
				double e = (entity.getY() - this.getY()) / (double) f;
				double g = (entity.getZ() - this.getZ()) / (double) f;
				this.setVelocity(this.getVelocity().add(Math.copySign(d * d * 0.4D, d), Math.copySign(e * e * 0.4D, e), Math.copySign(g * g * 0.4D, g)));
			}
		}
	}

	public void pickupItem() {
		ItemStack inventoryStack = this.inventory.get(0);
		if (inventoryStack.getCount() > 1) {
			this.getWorld().spawnEntity(new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), inventoryStack.split(inventoryStack.getCount() - 1)));
		}
		if (!this.getWorld().isClient && inventoryStack.isEmpty() && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) && !this.isRemoved()) {
			List<ItemEntity> list = this.getWorld().getNonSpectatingEntities(ItemEntity.class, this.getBoundingBox().expand(0.15D));
			for (ItemEntity item : list) {
				if (this.isMovingTowards(item)) {
					ItemStack stack = item.getStack();
					if (!stack.isEmpty()) {
						this.inventory.set(0, stack.split(1));
						this.isItemNatural = false;
						this.randomizeItemOffsets();
						break;
					}
				}
			}
		}
		this.setVisibleItem(this.inventory.get(0));
	}

	public void dropItem(boolean killed) {
		if (!this.isItemNatural || killed) {
			this.getWorld().spawnEntity(new ItemEntity(this.getWorld(), this.getX(), this.getEyeY(), this.getZ(), this.inventory.get(0).split(1)));
		}
	}

	public void destroy(boolean killed) {
		if (this.isAlive()) {
			this.playSound(LostLegendsSoundEvents.ENTITY_TUMBLEWEED_BREAK.get(), this.getSoundVolume(), this.getPitch());
		}
		this.dropItem(killed);
		this.spawnBreakParticles();
		this.remove(RemovalReason.KILLED);
	}

	public void setItem(@NotNull ItemStack stack, boolean natural) {
		this.inventory.set(0, stack);
		this.isItemNatural = natural;
		this.randomizeItemOffsets();
	}

	public boolean isMovingTowards(@NotNull Entity entity) {
		return entity.getPos().distanceTo(this.getPos()) > entity.getPos().distanceTo(this.getPos());
	}

	@NotNull
	public Vec3d getDeltaPos() {
		return this.getPos().subtract(this.getPos());
	}


	@Override
	public boolean isInvulnerableTo(@NotNull DamageSource source) {
		return source.isIn(DamageTypeTags.WITCH_RESISTANT_TO) || source.isOf(DamageTypes.CACTUS) || super.isInvulnerableTo(source);
	}

	@Override
	public boolean canFreeze() {
		return false;
	}

	@Override
	public boolean canBeLeashed() {
		return LostLegends.getConfig().leashedTumbleweed;
	}

	@Override
	public boolean canHaveStatusEffect(@NotNull StatusEffectInstance effectInstance) {
		return false;
	}

	@Override
	public boolean canTakeDamage() {
		return false;
	}

	@Override
	protected SoundEvent getHurtSound(@NotNull DamageSource damageSource) {
		return LostLegendsSoundEvents.ENTITY_TUMBLEWEED_DAMAGE.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return LostLegendsSoundEvents.ENTITY_TUMBLEWEED_BREAK.get();
	}

	@Override
	protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
		this.playSound(LostLegendsSoundEvents.ENTITY_TUMBLEWEED_BOUNCE.get(), 0.2F, 1F);
	}

	@Override
	public boolean handleFallDamage(float fallDistance, float multiplier, @NotNull DamageSource source) {
		return false;
	}

	@NotNull
	public Packet<ClientPlayPacketListener> getAddEntityPacket(EntityTrackerEntry serverEntity) {
		return new EntitySpawnS2CPacket(
			this.getId(),
			this.getUuid(),
			this.getX(),
			this.getY(),
			this.getZ(),
			this.pitch,
			this.roll,
			this.getType(),
			0,
			this.getVelocity(),
			this.tumble
		);
	}

	@Override
	public void onSpawnPacket(@NotNull EntitySpawnS2CPacket packet) {
		double d = packet.getX();
		double e = packet.getY();
		double f = packet.getZ();
		this.roll = packet.getYaw();
		this.pitch = packet.getPitch();
		this.updateTrackedPosition(d, e, f);
		this.bodyYaw = packet.getHeadYaw();
		this.tumble = packet.getHeadYaw();
		this.prevBodyYaw = this.bodyYaw;
		this.prevHeadYaw = this.headYaw;
		this.setId(packet.getEntityId());
		this.setUuid(packet.getUuid());
		this.updatePositionAndAngles(d, e, f, 0, 0);
		this.setVelocity(packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ());
	}

	@Override
	public void readCustomDataFromNbt(@NotNull NbtCompound compound) {
		super.readCustomDataFromNbt(compound);
		this.spawnedFromShears = compound.getBoolean("SpawnedFromShears");
		this.ticksSinceActive = compound.getInt("TicksSinceActive");
		this.isItemNatural = compound.getBoolean("IsTumbleweedItemNatural");
		this.setItemX(compound.getFloat("ItemX"));
		this.setItemZ(compound.getFloat("ItemZ"));
		this.pitch = compound.getFloat("TumblePitch");
		this.roll = compound.getFloat("TumbleRoll");
		this.isTouchingStickingBlock = compound.getBoolean("isTouchingStickingBlock");
		this.isTouchingStoppingBlock = compound.getBoolean("IsTouchingStoppingBlock");
		this.lookRot = compound.getFloat("LookRot");
		this.inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
		Inventories.readNbt(compound, this.inventory, this.getRegistryManager());
	}

	@Override
	public void writeCustomDataToNbt(@NotNull NbtCompound compound) {
		super.writeCustomDataToNbt(compound);
		compound.putBoolean("SpawnedFromShears", this.spawnedFromShears);
		compound.putInt("TicksSinceActive", this.ticksSinceActive);
		compound.putBoolean("IsTumbleweedItemNatural", this.isItemNatural);
		compound.putFloat("ItemX", this.getItemX());
		compound.putFloat("ItemZ", this.getItemZ());
		compound.putFloat("TumblePitch", this.pitch);
		compound.putFloat("TumbleRoll", this.roll);
		compound.putBoolean("isTouchingStickingBlock", this.isTouchingStickingBlock);
		compound.putBoolean("IsTouchingStoppingBlock", this.isTouchingStoppingBlock);
		compound.putFloat("LookRot", this.lookRot);
		Inventories.writeNbt(compound, this.inventory,this.getRegistryManager());
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(ITEM_STACK, ItemStack.EMPTY);
		builder.add(ITEM_X, 0F);
		builder.add(ITEM_Z, 0F);
	}

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return new ItemStack(LostLegendsBlocks.TUMBLEWEED.get());
	}

	@Override
	public void onDeath(@NotNull DamageSource damageSource) {
		super.onDeath(damageSource);
		if (!this.getWorld().isClient && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && !damageSource.isSourceCreativePlayer()) {
			if (isSilkTouchOrShears(damageSource)) {
				this.getWorld().spawnEntity(new ItemEntity(this.getWorld(), this.getX(), this.getY(), this.getZ(), new ItemStack(LostLegendsBlocks.TUMBLEWEED.get())));
			}
		}
		this.destroy(true);
	}

	public void spawnBreakParticles() {
		if (this.getWorld() instanceof ServerWorld level) {
			level.spawnParticles(
				new BlockStateParticleEffect(ParticleTypes.BLOCK, LostLegendsBlocks.TUMBLEWEED.get().getDefaultState()),
				this.getX(),
				this.getBodyY(0.6666666666666666D),
				this.getZ(),
				20,
				this.getWidth() / 4F,
				this.getHeight() / 4F,
				this.getWidth() / 4F,
				0.05D
			);
		}
	}

	@Override
	public boolean cannotDespawn() {
		return super.cannotDespawn() || this.spawnedFromShears || this.isLeashed() || this.hasCustomName();
	}

	@Override
	public boolean canImmediatelyDespawn(double distanceToClosestPlayer) {
		return !this.spawnedFromShears;
	}

	@Override
	public ItemStack getActiveItem() {
		return this.dataTracker.get(ITEM_STACK);
	}

	public void setVisibleItem(ItemStack itemStack) {
		this.getDataTracker().set(ITEM_STACK, itemStack);
	}

	public void randomizeItemOffsets() {
		this.setItemX((this.random.nextFloat() * (this.random.nextBoolean() ? 1F : -1F)) * MAX_ITEM_OFFSET);
		this.setItemZ((this.random.nextFloat() * (this.random.nextBoolean() ? 1F : -1F)) * MAX_ITEM_OFFSET);
	}

	public float getItemX() {
		return this.dataTracker.get(ITEM_X);
	}

	public void setItemX(float f) {
		this.getDataTracker().set(ITEM_X, f);
	}

	public float getItemZ() {
		return this.dataTracker.get(ITEM_Z);
	}

	public void setItemZ(float f) {
		this.getDataTracker().set(ITEM_Z, f);
	}

	@Override
	protected void onKilledBy(@Nullable LivingEntity entitySource) {
	}

	@Override
	protected void playSwimSound(float volume) {
	}

	@Override
	@NotNull
	public Iterable<ItemStack> getArmorItems() {
		return DefaultedList.ofSize(1, ItemStack.EMPTY);
	}

	@Override
	@NotNull
	public ItemStack getEquippedStack(@NotNull EquipmentSlot slot) {
		return ItemStack.EMPTY;
	}

	@Override
	public void equipStack(@NotNull EquipmentSlot slot, @NotNull ItemStack stack) {
	}

	@Override
	@NotNull
	public Arm getMainArm() {
		return Arm.LEFT;
	}


}
