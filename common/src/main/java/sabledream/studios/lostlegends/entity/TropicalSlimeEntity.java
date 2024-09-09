package sabledream.studios.lostlegends.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import sabledream.studios.lostlegends.entity.ai.goal.tslime.*;

public class TropicalSlimeEntity extends HostileEntity implements Monster
{
	private final int size;
	public float targetStretch;
	public float stretch;
	public float lastStretch;
	private boolean onGroundLastTick;


	public TropicalSlimeEntity(EntityType<TropicalSlimeEntity> type, World world) {
		super(type, world);
		size = 4;
		experiencePoints = size;
		setAiDisabled(false);
		moveControl = new TropicalSlimeMoveControl(this);
		setAttributes();
	}

	@Override
	protected void initGoals() {
		super.initGoals();

		goalSelector.add(1, new TropicalSlimeSwimGoal(this));
		goalSelector.add(1, new TropicalSlimeFloatGoal(this));
		goalSelector.add(2, new TropicalSlimeAttackGoal(this));
		goalSelector.add(3, new TropicalSlimeFaceRandomGoal(this));
		goalSelector.add(5, new TropicalSlimeHopGoal(this));
		targetSelector.add(3, new RevengeGoal(this));
	}

	protected void setAttributes() {
		getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(16.0D);
		getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.6D);
		getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(4.0D);
	}


	@Override
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		if (isBaby()) {
			return super.interactMob(player, hand);
		}
		if (player.getAbilities().creativeMode) {
			return super.interactMob(player, hand);
		}

		ItemStack itemstack = player.getStackInHand(hand);
		if (itemstack.getItem() != Items.BUCKET) return super.interactMob(player, hand);

		if (getWorld().isClient) {
			return super.interactMob(player, hand);
		}

		remove(RemovalReason.KILLED);
		getWorld().addParticle(ParticleTypes.EXPLOSION, getX(), getBodyY(0.5D), getZ(), 0.0D, 0.0D, 0.0D);
		player.playSound(SoundEvents.ENTITY_SLIME_SQUISH, 1.0F, 1.0F);
		spawnWater();
		giveTropicalFishBucket(player, itemstack);
		return ActionResult.success(getWorld().isClient);
	}

	private void giveTropicalFishBucket(PlayerEntity player, ItemStack itemstack) {
		itemstack.decrement(1);
		if (!player.getInventory().insertStack(new ItemStack(Items.TROPICAL_FISH_BUCKET))) {
			player.dropItem(new ItemStack(Items.TROPICAL_FISH_BUCKET), false);
		}
	}

	private void spawnWater() {
		int x = MathHelper.floor(getX());
		int y = MathHelper.floor(getY());
		int z = MathHelper.floor(getZ());
		BlockPos blockPos = new BlockPos(x, y, z);
		BlockState waterState = Blocks.WATER.getDefaultState();
		getWorld().removeBlock(blockPos, false);
		getWorld().setBlockState(blockPos, waterState);
	}

	protected ParticleEffect getSquishParticle() {
		return ParticleTypes.DRIPPING_WATER;
	}

	@Override
	public void tick() {
		stretch += (targetStretch - stretch) * 0.5F;
		lastStretch = stretch;
		super.tick();

		if (isOnGround() && !onGroundLastTick) {
			int i = size;
			if (spawnCustomParticles()) ;
			for (int j = 0; j < i * 8; ++j) {
				float f = random.nextFloat() * ((float) Math.PI * 2f);
				float f1 = random.nextFloat() * 0.5F + 0.5F;
				float f2 = MathHelper.sin(f) * i * 0.5F * f1;
				float f3 = MathHelper.cos(f) * i * 0.5F * f1;
				getWorld().addParticle(getSquishParticle(), getX() + f2, getY(), getZ() + f3, 0.0D, 0.0D, 0.0D);
			}
			playSound(getSquishSound(), getSoundVolume(), ((random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
			targetStretch = -0.5F;
		} else if (!isOnGround() && onGroundLastTick) {
			targetStretch = 1.0F;
		}
		onGroundLastTick = isOnGround();
		updateStretch();
	}

	protected void updateStretch() {
		targetStretch *= 0.6f;
	}

	public int getJumpDelay() {
		return random.nextInt(20) + 10;
	}

	@Override
	public void pushAwayFrom(Entity entity) {
		if (!this.isSleeping()) {
			super.pushAwayFrom(entity);
		}
	}

	@Override
	public void onPlayerCollision(PlayerEntity player) {
		if (canAttack()) {
			damage(player);
		}
	}

	protected float getAttackDamage() {
		return (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
	}

	protected void damage(LivingEntity livingEntity) {
		if (!isAlive()) {
			return;
		}
		if (!isInAttackRange(livingEntity)) {
			return;
		}
		if (!canSee(livingEntity)) {
			return;
		}

		DamageSource damageSource = this.getDamageSources().mobAttack(this);
		if (livingEntity.damage(damageSource, this.getAttackDamage())) {
			this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
			World level = this.getWorld();
			if (level instanceof ServerWorld serverLevel) {
				EnchantmentHelper.onTargetDamaged(serverLevel, livingEntity, damageSource);
			}
		}
	}

	public boolean canAttack() {
		return canMoveVoluntarily();
	}


	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_SLIME_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_SLIME_DEATH;
	}

	protected SoundEvent getSquishSound() {
		return SoundEvents.ENTITY_SLIME_SQUISH;
	}


	public boolean doPlayJumpSound() {
		return true;
	}

	public float getJumpSoundPitch() {
		return ((random.nextFloat() - random.nextFloat()) * 0.2f + 1.0f) * 0.8f;
	}

	@Override
	public void jump() {
		Vec3d vec3d = this.getVelocity();
		this.setVelocity(vec3d.x, (double) this.getJumpVelocity(), vec3d.z);
		this.velocityDirty = true;
	}

	public SoundEvent getJumpSound() {
		return SoundEvents.ENTITY_SLIME_JUMP;
	}

	protected boolean spawnCustomParticles() {
		return false;
	}
}
