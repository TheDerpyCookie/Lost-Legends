package sabledream.studios.lostlegends.entity;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import sabledream.studios.lostlegends.entity.ai.goal.furnacegolem.FurnaceGolemActiveTargetGoal;
import sabledream.studios.lostlegends.entity.ai.goal.furnacegolem.TrackFurnaceGolemTargetGoal;
import sabledream.studios.lostlegends.entity.animation.BlinkManager;
import sabledream.studios.lostlegends.init.LostLegendsSoundEvents;

public class FurnaceGolemEntity extends IronGolemEntity {
	public static final TrackedData<Boolean> IS_ANGRY = DataTracker.registerData(FurnaceGolemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	public final BlinkManager blinkManager;
	private int attackTimer;

	public FurnaceGolemEntity(EntityType<? extends IronGolemEntity> type, World worldIn) {
		super(type, worldIn);
		blinkManager = new BlinkManager();
		experiencePoints = 5;
		setAiDisabled(false);
	}

	@Override
	protected void initGoals() {
		goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, true));
		goalSelector.add(2, new WanderNearTargetGoal(this, 0.9D, 32.0F));
		goalSelector.add(2, new WanderAroundPointOfInterestGoal(this, 0.6D, false));
		goalSelector.add(4, new IronGolemWanderAroundGoal(this, 0.6D));
		goalSelector.add(5, new IronGolemLookGoal(this));
		goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
		goalSelector.add(8, new LookAroundGoal(this));
		targetSelector.add(1, new TrackFurnaceGolemTargetGoal(this));
		targetSelector.add(2, new RevengeGoal(this));
		targetSelector.add(3, new FurnaceGolemActiveTargetGoal(this, MobEntity.class, 5, false, false, entity -> entity instanceof Monster && !(entity instanceof CreeperEntity) && !(entity instanceof TropicalSlimeEntity)));
	}

	@Override
	public boolean tryAttack(Entity entity) {
		attackTimer = 10;
		getWorld().sendEntityStatus(this, (byte) 4);
		float f = (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
		float g = (int) f > 0 ? f / 2.0F + (float) this.random.nextInt((int) f) : f;
		DamageSource damageSource = this.getDamageSources().onFire();
		boolean hurt = entity.damage(damageSource, g);
		if (hurt) {
			double knockBackResistance;
			if (entity instanceof LivingEntity livingEntity) {
				knockBackResistance = livingEntity.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
			} else {
				knockBackResistance = 0.0;
			}

			double d = knockBackResistance;
			double e = Math.max(0.0, 1.0 - d);
			entity.setVelocity(entity.getVelocity().add(0.0, 0.4000000059604645 * e, 0.0));
			World level = this.getWorld();
			if (level instanceof ServerWorld serverLevel) {
				EnchantmentHelper.onTargetDamaged(serverLevel, entity, damageSource);
			}
		}
		playSound(LostLegendsSoundEvents.FURNACE_GOLEM_ATTACK.get(), 1.0F, 1.0F);
		return hurt;
	}

	@Override
	public void tickMovement() {
		super.tickMovement();
		if (isAngry()) {
			float rand = random.nextFloat();
			if (rand > 0.80F && rand <= 0.83F) {
				int x = MathHelper.floor(getX());
				int y = MathHelper.floor(getY());
				int z = MathHelper.floor(getZ());
				BlockPos ground = new BlockPos(x, y - 1, z);
				BlockPos around = ground.add(random.nextInt(3) - 1, 0, random.nextInt(3) - 1);
				BlockPos above = around.up();
				boolean solidFace = getWorld().getBlockState(around).isSideSolidFullSquare(getWorld(), around, Direction.UP);
				boolean aboveIsAir = getWorld().getBlockState(above).isAir();

				if (solidFace && aboveIsAir) {
					getWorld().setBlockState(above, Blocks.FIRE.getDefaultState(), Block.NOTIFY_ALL);
				}
			}
		}



		if (isInsideWaterOrBubbleColumn()) {
			damage(getDamageSources().drown(), 5.0F);
		}

		blinkManager.tickBlink();
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(IS_ANGRY, false);
	}

	public boolean isAngry() {
		return dataTracker.get(IS_ANGRY);
	}

	public void setAngry(boolean angry) {
		dataTracker.set(IS_ANGRY, angry);
	}

}
