package sabledream.studios.lostlegends.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.control.BodyControl;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;

import org.jetbrains.annotations.Nullable;
import sabledream.studios.lostlegends.entity.ai.goal.vulture.LongTemptGoal;
import sabledream.studios.lostlegends.init.LostLegendsEntityTypes;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

public class Vulture extends TameableEntity
{
	private static final TrackedData<Boolean> DATA_CIRCLE = DataTracker.registerData(Vulture.class, TrackedDataHandlerRegistry.BOOLEAN);


	public static final float FLAP_DEGREES_PER_TICK = 7.448451F;
	public static final int TICKS_PER_FLAP = MathHelper.ceil(24.166098F);
	public Vec3d moveTargetPoint = Vec3d.ZERO;
	Vec3i anchorPoint = BlockPos.ZERO;
	Vulture.AttackPhase attackPhase = Vulture.AttackPhase.CIRCLE;

	public Vulture(EntityType<? extends Vulture> p_27557_, World p_27558_) {
		super(p_27557_, p_27558_);
		this.moveControl = new VultureMoveControl(this);
		this.lookControl = new VultureLookControl(this);
	}

	public boolean isFlapping() {
		return (this.getUniqueFlapTickOffset() + this.age) % TICKS_PER_FLAP == 0;
	}

	protected BodyControl createBodyControl() {
		return new VultureBodyRotationControl(this);
	}


	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(DATA_CIRCLE, false);
	}

	protected void initGoals() {
		this.goalSelector.add(0, new Vulture.VultureCircleAroundDeathPointGoal());
		this.goalSelector.add(1, new LongTemptGoal(this, Ingredient.ofItems(Items.ROTTEN_FLESH), false));
		this.goalSelector.add(2, new Vulture.VultureAttackStrategyGoal());
		this.goalSelector.add(3, new Vulture.VultureSweepAttackGoal());
		this.goalSelector.add(4, new Vulture.VultureCircleAroundAnchorGoal());
		this.targetSelector.add(1, new VultureAttackZombieEntityTargetGoal());
	}


	public boolean hasCircle() {
		return this.dataTracker.get(DATA_CIRCLE);
	}

	public void setCircle(boolean p_28516_) {
		this.dataTracker.set(DATA_CIRCLE, p_28516_);
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 22.0D)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.28D)
			.add(EntityAttributes.GENERIC_FLYING_SPEED, 0.28D)
			.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0F)
			.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0F);
	}

	public int getUniqueFlapTickOffset() {
		return this.getId() * 3;
	}

	protected boolean isDisallowedInPeaceful() {
		return false;
	}
	public void tick() {
		super.tick();
		if (this.getWorld().isClient) {
			float f = MathHelper.cos((float) (this.getUniqueFlapTickOffset() + this.age) * 12.448451F * ((float) Math.PI / 180F) + (float) Math.PI);
			float f1 = MathHelper.cos((float) (this.getUniqueFlapTickOffset() + this.age + 1) * 12.448451F * ((float) Math.PI / 180F) + (float) Math.PI);
			if (f > 0.0F && f1 <= 0.0F) {
				this.getWorld().playSoundAtBlockCenter(this.getBlockPos(), SoundEvents.ENTITY_PHANTOM_FLAP, this.getSoundCategory(), 0.95F + this.random.nextFloat() * 0.05F, 0.95F + this.random.nextFloat() * 0.05F, false);
			}
		}

	}

	public void travel(Vec3d p_20818_) {
		if (this.canMoveVoluntarily() || this.isLogicalSideForUpdatingMovement()) {
			if (this.isTouchingWater()) {
				this.updateVelocity(0.02F, p_20818_);
				this.move(MovementType.SELF, this.getVelocity());
				this.setVelocity(this.getVelocity().multiply((double) 0.8F));
			} else if (this.isInLava()) {
				this.updateVelocity(0.02F, p_20818_);
				this.move(MovementType.SELF, this.getVelocity());
				this.setVelocity(this.getVelocity().multiply(0.5D));
			} else {
				BlockPos ground = BlockPos.ofFloored(this.getX(), this.getY() - 1.0D, this.getZ());
				float f = 0.91F;
				if (this.isOnGround()) {
					f = this.getWorld().getBlockState(ground).getOpacity(this.getWorld(), ground) * 0.91F;
				}


				float f1 = 0.16277137F / (f * f * f);
				f = 0.91F;
				if (this.isOnGround()) {
					f = this.getWorld().getBlockState(ground).getOpacity(this.getWorld(), ground) * 0.91F;
				}

				this.updateVelocity(this.isOnGround() ? 0.1F * f1 : 0.02F, p_20818_);
				this.move(MovementType.SELF, this.getVelocity());
				this.setVelocity(this.getVelocity().multiply((double) f));
			}
		}

		this.updateLimbs(false);
	}

	public boolean isClimbing() {
		return false;
	}

	public boolean handleFallDamage(float p_148989_, float p_148990_, DamageSource p_148991_) {
		return false;
	}

	protected void fall(double p_29370_, boolean p_29371_, BlockState p_29372_, BlockPos p_29373_) {
	}

	@Override
	public EntityData initialize(ServerWorldAccess p_146746_, LocalDifficulty p_146747_, SpawnReason p_146748_, @Nullable EntityData p_146749_) {
		this.anchorPoint = this.getBlockPos().up(5);
		return super.initialize(p_146746_, p_146747_, p_146748_, p_146749_);
	}

	public void readCustomDataFromNbt(NbtCompound p_33132_) {
		super.readCustomDataFromNbt(p_33132_);
		if (p_33132_.contains("AX")) {
			this.anchorPoint = new BlockPos(p_33132_.getInt("AX"), p_33132_.getInt("AY"), p_33132_.getInt("AZ"));
		}
		this.setCircle(p_33132_.getBoolean("Circle"));
	}

	public void writeCustomDataToNbt(NbtCompound p_33141_) {
		super.writeCustomDataToNbt(p_33141_);
		p_33141_.putInt("AX", this.anchorPoint.getX());
		p_33141_.putInt("AY", this.anchorPoint.getY());
		p_33141_.putInt("AZ", this.anchorPoint.getZ());
		p_33141_.putBoolean("Circle", this.hasCircle());
	}

	@Nullable
	@Override
	public Vulture createChild(ServerWorld p_146743_, PassiveEntity p_146744_) {
		Vulture vulture = LostLegendsEntityTypes.VULTURE.get().create(p_146743_);
		UUID uuid = this.getOwnerUuid();
		if (uuid != null) {
			vulture.setOwnerUuid(uuid);
			vulture.setTamed(true,true);
		}

		return vulture;
	}

	@Override
	public boolean isBreedingItem(ItemStack p_27600_) {
		return false;
	}

	public ActionResult interactMob(PlayerEntity p_30412_, Hand p_30413_) {
		ItemStack itemstack = p_30412_.getStackInHand(p_30413_);
		Item item = itemstack.getItem();
		boolean flag = this.isTamed() && this.isOwner(p_30412_) && !this.hasCircle() || itemstack.isOf(Items.ROTTEN_FLESH) && !this.isTamed() && !this.isOwner(p_30412_);

		if (this.getWorld().isClient) {
			return flag ? ActionResult.CONSUME : ActionResult.PASS;
		} else {
			if (flag) {
				if (!p_30412_.getAbilities().creativeMode) {
					itemstack.decrement(1);
				}

				this.setCircle(true);
				this.setOwner(p_30412_);
				this.navigation.stop();
				this.setTarget((LivingEntity) null);
				this.getWorld().sendEntityStatus(this, (byte) 7);

				return ActionResult.SUCCESS;
			}

			return super.interactMob(p_30412_, p_30413_);
		}
	}

	public boolean canAttackWithOwner(LivingEntity p_30389_, LivingEntity p_30390_) {
		if (!(p_30389_ instanceof CreeperEntity) && !(p_30389_ instanceof GhastEntity)) {
			if (p_30389_ instanceof Vulture) {
				Vulture vulture = (Vulture) p_30389_;
				return !vulture.isTamed() || vulture.getOwner() != p_30390_;
			} else if (p_30389_ instanceof PlayerEntity && p_30390_ instanceof PlayerEntity && !((PlayerEntity) p_30390_).shouldDamagePlayer((PlayerEntity) p_30389_)) {
				return false;
			} else {
				return !(p_30389_ instanceof TameableEntity) || !((TameableEntity) p_30389_).isTamed();
			}
		} else {
			return false;
		}
	}

	public static boolean checkVultureSpawnRules(EntityType<? extends AnimalEntity> p_218105_, WorldAccess p_218106_, SpawnReason p_218107_, BlockPos p_218108_, Random p_218109_) {
		return (p_218106_.getBlockState(p_218108_.down()).isIn(BlockTags.SAND) || p_218106_.getBlockState(p_218108_.down()).isIn(BlockTags.TERRACOTTA) || p_218106_.getBlockState(p_218108_.down()).isIn(BlockTags.DIRT) || p_218106_.getBlockState(p_218108_.down()).isOf(Blocks.GRASS_BLOCK)) && isLightLevelValidForNaturalSpawn(p_218106_, p_218108_);
	}


	static enum AttackPhase {
		CIRCLE,
		SWOOP;
	}

	class VultureAttackZombieEntityTargetGoal extends Goal {
		private final TargetPredicate attackTargeting = TargetPredicate.createAttackable().setBaseMaxDistance(64.0D);
		private int nextScanTick = toGoalTicks(20);

		public boolean canStart() {
			if (this.nextScanTick > 0) {
				--this.nextScanTick;
				return false;
			} else {
				this.nextScanTick = toGoalTicks(60);
				List<ZombieEntity> list = Vulture.this.getWorld().getTargets(ZombieEntity.class, this.attackTargeting, Vulture.this, Vulture.this.getBoundingBox().expand(16.0D, 64.0D, 16.0D));
				if (!list.isEmpty()) {
					list.sort(Comparator.<Entity, Double>comparing(Entity::getY).reversed());

					for (ZombieEntity ZombieEntity : list) {
						if (Vulture.this.isTarget(ZombieEntity, TargetPredicate.DEFAULT)) {
							Vulture.this.setTarget(ZombieEntity);
							return true;
						}
					}
				}

				return false;
			}
		}

		public boolean shouldContinue() {
			LivingEntity livingentity = Vulture.this.getTarget();
			return livingentity != null ? Vulture.this.isTarget(livingentity, TargetPredicate.DEFAULT) : false;
		}
	}

	class VultureTemptGoal extends Goal {
		private final TargetPredicate attackTargeting = TargetPredicate.createNonAttackable().ignoreVisibility().setBaseMaxDistance(64.0D);
		private int nextScanTick = toGoalTicks(20);

		public boolean canStart() {
			if (this.nextScanTick > 0) {
				--this.nextScanTick;
				return false;
			} else {
				this.nextScanTick = toGoalTicks(60);
				List<ZombieEntity> list = Vulture.this.getWorld().getTargets(ZombieEntity.class, this.attackTargeting, Vulture.this, Vulture.this.getBoundingBox().expand(16.0D, 64.0D, 16.0D));
				if (!list.isEmpty()) {
					list.sort(Comparator.<Entity, Double>comparing(Entity::getY).reversed());

					for (ZombieEntity ZombieEntity : list) {
						if (Vulture.this.isTarget(ZombieEntity, TargetPredicate.DEFAULT)) {
							Vulture.this.setTarget(ZombieEntity);
							return true;
						}
					}
				}

				return false;
			}
		}

		public boolean shouldContinue() {
			LivingEntity livingentity = Vulture.this.getTarget();
			return livingentity != null ? Vulture.this.isTarget(livingentity, TargetPredicate.DEFAULT) : false;
		}
	}

	class VultureAttackStrategyGoal extends Goal {
		private int nextSweepTick;

		public boolean canStart() {
			LivingEntity livingentity = Vulture.this.getTarget();
			return livingentity != null ? Vulture.this.isTarget(livingentity, TargetPredicate.DEFAULT) : false;
		}

		public void start() {
			this.nextSweepTick = this.getTickCount(10);
			Vulture.this.attackPhase = Vulture.AttackPhase.CIRCLE;
			this.setAnchorupTarget();
		}

		public void stop() {
			Vulture.this.anchorPoint = Vulture.this.getWorld().getTopPosition(Heightmap.Type.MOTION_BLOCKING, (BlockPos) Vulture.this.anchorPoint).up(10 + Vulture.this.random.nextInt(20));
		}

		public void tick() {
			if (Vulture.this.attackPhase == Vulture.AttackPhase.CIRCLE) {
				--this.nextSweepTick;
				if (this.nextSweepTick <= 0) {
					Vulture.this.attackPhase = Vulture.AttackPhase.SWOOP;
					this.setAnchorupTarget();
					this.nextSweepTick = this.getTickCount((8 + Vulture.this.random.nextInt(4)) * 20);
					//Vulture.this.playSound(SoundEvents.Vulture_SWOOP, 10.0F, 0.95F + Vulture.this.random.nextFloat() * 0.1F);
				}
			}

		}

		private void setAnchorupTarget() {
			Vulture.this.anchorPoint = Vulture.this.getTarget().getBlockPos().up(20 + Vulture.this.random.nextInt(20));
			if (Vulture.this.anchorPoint.getY() < Vulture.this.getWorld().getSeaLevel()) {
				Vulture.this.anchorPoint = new BlockPos(Vulture.this.anchorPoint.getX(), Vulture.this.getWorld().getSeaLevel() + 1, Vulture.this.anchorPoint.getZ());
			}

		}
	}

	class VultureBodyRotationControl extends BodyControl {
		public VultureBodyRotationControl(MobEntity p_33216_) {
			super(p_33216_);
		}

		public void tick() {
			Vulture.this.headYaw = Vulture.this.bodyYaw;
			Vulture.this.bodyYaw = Vulture.this.getYaw();
		}
	}

	class VultureCircleAroundDeathPointGoal extends Vulture.VultureMoveTargetGoal {
		private float angle;
		private float distance;
		private float height;
		private float clockwise;

		public boolean canStart() {
			return Vulture.this.getTarget() == null && Vulture.this.getOwner() != null && Vulture.this.getOwner() instanceof PlayerEntity && ((PlayerEntity) Vulture.this.getOwner()).getLastDeathPos().isPresent() && Vulture.this.squaredDistanceTo(Vulture.this.getOwner()) < 120F && Vulture.this.attackPhase == Vulture.AttackPhase.CIRCLE;
		}

		public void start() {
			this.distance = 5.0F + Vulture.this.random.nextFloat() * 10.0F;
			this.height = -4.0F + Vulture.this.random.nextFloat() * 9.0F;
			this.clockwise = Vulture.this.random.nextBoolean() ? 1.0F : -1.0F;
			this.selectNext();
		}

		public void tick() {
			if (Vulture.this.random.nextInt(this.getTickCount(350)) == 0) {
				this.height = -4.0F + Vulture.this.random.nextFloat() * 9.0F;
			}

			/*if (Vulture.this.random.nextInt(this.getTickCount(250)) == 0) {
				++this.distance;
				if (this.distance > 15.0F) {
					this.distance = 5.0F;
					this.clockwise = -this.clockwise;
				}
			}*/

			/*if (Vulture.this.random.nextInt(this.getTickCount(450)) == 0) {
				this.angle = Vulture.this.random.nextFloat() * 2.0F * (float)Math.PI;
				this.selectNext();
			}*/

			if (this.touchingTarget()) {
				this.selectNext();
			}

			if (Vulture.this.moveTargetPoint.y < Vulture.this.getY() && !Vulture.this.getWorld().isAir(Vulture.this.getBlockPos().down(1))) {
				this.height = Math.max(1.0F, this.height);
				this.selectNext();
			}

			if (Vulture.this.moveTargetPoint.y > Vulture.this.getY() && !Vulture.this.getWorld().isAir(Vulture.this.getBlockPos().up(1))) {
				this.height = Math.min(-1.0F, this.height);
				this.selectNext();
			}

		}

		private void selectNext() {
			if (Vulture.this.getOwner() instanceof PlayerEntity) {
				if (((PlayerEntity) Vulture.this.getOwner()).getLastDeathPos().isPresent() && ((PlayerEntity) Vulture.this.getOwner()).getLastDeathPos().get().dimension() == Vulture.this.getWorld().getDimensionEntry()) {
					Vulture.this.anchorPoint = ((PlayerEntity) Vulture.this.getOwner()).getLastDeathPos().get().pos().up(10);
				}
			}

			this.angle += this.clockwise * 15.0F * ((float) Math.PI / 180F);
			Vulture.this.moveTargetPoint = Vec3d.of(Vulture.this.anchorPoint).add((double) (this.distance * MathHelper.cos(this.angle)), (double) (-4.0F + this.height), (double) (this.distance * MathHelper.sin(this.angle)));
		}
	}

	class VultureCircleAroundAnchorGoal extends Vulture.VultureMoveTargetGoal {
		private float angle;
		private float distance;
		private float height;
		private float clockwise;

		public boolean canStart() {
			return Vulture.this.getTarget() == null || Vulture.this.attackPhase == Vulture.AttackPhase.CIRCLE;
		}

		public void start() {
			this.distance = 5.0F + Vulture.this.random.nextFloat() * 10.0F;
			this.height = -4.0F + Vulture.this.random.nextFloat() * 9.0F;
			this.clockwise = Vulture.this.random.nextBoolean() ? 1.0F : -1.0F;
			this.selectNext();
		}

		public void tick() {
			if (Vulture.this.random.nextInt(this.getTickCount(350)) == 0) {
				this.height = -4.0F + Vulture.this.random.nextFloat() * 9.0F;
			}

			if (Vulture.this.random.nextInt(this.getTickCount(250)) == 0) {
				++this.distance;
				if (this.distance > 15.0F) {
					this.distance = 5.0F;
					this.clockwise = -this.clockwise;
				}
			}

			if (Vulture.this.random.nextInt(this.getTickCount(450)) == 0) {
				this.angle = Vulture.this.random.nextFloat() * 2.0F * (float) Math.PI;
				this.selectNext();
			}

			if (this.touchingTarget()) {
				this.selectNext();
			}

			if (Vulture.this.moveTargetPoint.y < Vulture.this.getY() && !Vulture.this.getWorld().isAir(Vulture.this.getBlockPos().down(1))) {
				this.height = Math.max(1.0F, this.height);
				this.selectNext();
			}

			if (Vulture.this.moveTargetPoint.y > Vulture.this.getY() && !Vulture.this.getWorld().isAir(Vulture.this.getBlockPos().up(1))) {
				this.height = Math.min(-1.0F, this.height);
				this.selectNext();
			}

		}

		private void selectNext() {
			if (BlockPos.ZERO.equals(Vulture.this.anchorPoint)) {
				Vulture.this.anchorPoint = Vulture.this.getBlockPos();
			}

			this.angle += this.clockwise * 15.0F * ((float) Math.PI / 180F);
			Vulture.this.moveTargetPoint = Vec3d.of(Vulture.this.anchorPoint).add((double) (this.distance * MathHelper.cos(this.angle)), (double) (-4.0F + this.height), (double) (this.distance * MathHelper.sin(this.angle)));
		}
	}

	class VultureLookControl extends LookControl
	{
		public VultureLookControl(MobEntity p_33235_) {
			super(p_33235_);
		}

		public void tick() {
		}
	}

	class VultureMoveControl extends MoveControl
	{
		private float speed = 0.1F;

		public VultureMoveControl(MobEntity p_33241_) {
			super(p_33241_);
		}

		public void tick() {
			if (Vulture.this.horizontalCollision) {
				Vulture.this.setYaw(Vulture.this.getYaw() + 180.0F);
				this.speed = 0.1F;
			}

			double d0 = Vulture.this.moveTargetPoint.x - Vulture.this.getX();
			double d1 = Vulture.this.moveTargetPoint.y - Vulture.this.getY();
			double d2 = Vulture.this.moveTargetPoint.z - Vulture.this.getZ();
			double d3 = Math.sqrt(d0 * d0 + d2 * d2);
			if (Math.abs(d3) > (double) 1.0E-5F) {
				double d4 = 1.0D - Math.abs(d1 * (double) 0.7F) / d3;
				d0 *= d4;
				d2 *= d4;
				d3 = Math.sqrt(d0 * d0 + d2 * d2);
				double d5 = Math.sqrt(d0 * d0 + d2 * d2 + d1 * d1);
				float f = Vulture.this.getYaw();
				float f1 = (float) MathHelper.atan2(d2, d0);
				float f2 = MathHelper.wrapDegrees(Vulture.this.getYaw() + 90.0F);
				float f3 = MathHelper.wrapDegrees(f1 * (180F / (float) Math.PI));
				Vulture.this.setYaw(MathHelper.stepUnwrappedAngleTowards(f2, f3, 4.0F) - 90.0F);
				Vulture.this.bodyYaw = Vulture.this.getYaw();
				if (MathHelper.angleBetween(f, Vulture.this.getYaw()) < 3.0F) {
					this.speed = MathHelper.stepTowards(this.speed, 1.8F, 0.005F * (1.8F / this.speed));
				} else {
					this.speed = MathHelper.stepTowards(this.speed, 0.2F, 0.025F);
				}

				float f4 = (float) (-(MathHelper.atan2(-d1, d3) * (double) (180F / (float) Math.PI)));
				Vulture.this.setPitch(f4);
				float f5 = Vulture.this.getYaw() + 90.0F;
				double d6 = (double) (this.speed * MathHelper.cos(f5 * ((float) Math.PI / 180F))) * Math.abs(d0 / d5);
				double d7 = (double) (this.speed * MathHelper.sin(f5 * ((float) Math.PI / 180F))) * Math.abs(d2 / d5);
				double d8 = (double) (this.speed * MathHelper.sin(f4 * ((float) Math.PI / 180F))) * Math.abs(d1 / d5);
				Vec3d Vec3d = Vulture.this.getVelocity();
				Vulture.this.setVelocity(Vec3d.add((new Vec3d(d6, d8, d7)).subtract(Vec3d).multiply(0.2D)));
			}

		}
	}

	abstract class VultureMoveTargetGoal extends Goal
	{
		public VultureMoveTargetGoal() {
			this.setControls(EnumSet.of(Goal.Control.MOVE));
		}

		protected boolean touchingTarget() {
			return Vulture.this.moveTargetPoint.squaredDistanceTo(Vulture.this.getX(), Vulture.this.getY(), Vulture.this.getZ()) < 4.0D;
		}
	}

	class VultureSweepAttackGoal extends Vulture.VultureMoveTargetGoal {
		private static final int CAT_SEARCH_TICK_DELAY = 20;
		private boolean isScaredOfCat;
		private int catSearchTick;

		public boolean canStart() {
			return Vulture.this.getTarget() != null && Vulture.this.attackPhase == Vulture.AttackPhase.SWOOP;
		}

		
		public boolean shouldContinue() {
			LivingEntity livingentity = Vulture.this.getTarget();
			if (livingentity == null) {
				return false;
			} else if (!livingentity.isAlive()) {
				return false;
			} else {
				if (livingentity instanceof PlayerEntity) {
					PlayerEntity PlayerEntity = (PlayerEntity) livingentity;
					if (livingentity.isSpectator() || PlayerEntity.isCreative()) {
						return false;
					}
				}

				if (!this.canStart()) {
					return false;
				} else {
					return true;
				}
			}
		}

		public void start() {
			Vulture.this.setAttacking(true);
		}

		public void stop() {
			Vulture.this.setAttacking(false);
			Vulture.this.setTarget((LivingEntity) null);
			Vulture.this.attackPhase = Vulture.AttackPhase.CIRCLE;
		}

		public void tick() {
			LivingEntity livingentity = Vulture.this.getTarget();
			if (livingentity != null) {
				Vulture.this.moveTargetPoint = new Vec3d(livingentity.getX(), livingentity.getY(), livingentity.getZ());
				if (Vulture.this.getBoundingBox().expand((double) 0.2F).intersects(livingentity.getBoundingBox())) {
					Vulture.this.tryAttack(livingentity);
					Vulture.this.attackPhase = Vulture.AttackPhase.CIRCLE;
					if (!Vulture.this.isSilent()) {
						Vulture.this.getWorld().syncWorldEvent(1039, Vulture.this.getBlockPos(), 0);
					}
				} else if (Vulture.this.horizontalCollision || Vulture.this.hurtTime > 0) {
					Vulture.this.attackPhase = Vulture.AttackPhase.CIRCLE;
				}

			}
		}
	}
}
