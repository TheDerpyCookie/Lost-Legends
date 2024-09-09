package sabledream.studios.lostlegends.entity;

import com.jcraft.jorbis.Block;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Blocks;
import net.minecraft.command.EntityDataObject;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import sabledream.studios.lostlegends.entity.ai.brain.GlareBrain;
import sabledream.studios.lostlegends.entity.ai.brain.OstrichBrain;
import sabledream.studios.lostlegends.init.LostLegendsBlocks;
import sabledream.studios.lostlegends.init.LostLegendsEntityTypes;

import java.util.EnumSet;
import java.util.Optional;
import java.util.UUID;

public class Ostrich extends AnimalEntity implements Angerable
{

    private static final TrackedData<Integer> DATA_REMAINING_ANGER_TIME = DataTracker.registerData(Ostrich.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> HAS_EGG = DataTracker.registerData(Ostrich.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> DIP = DataTracker.registerData(Ostrich.class, TrackedDataHandlerRegistry.BOOLEAN);

    public AnimationState idlingState = new AnimationState();
    public AnimationState dippingState = new AnimationState();
    public AnimationState kickingState = new AnimationState();

    private float runningScale;

    private static final UniformIntProvider PERSISTENT_ANGER_TIME = TimeHelper.betweenSeconds(20, 39);
    private UUID persistentAngerTarget;

    @Nullable
    private BlockPos homeTarget;

    public int featherTime = this.random.nextInt(6000) + 6000;

	private float maxUpStep;

	@Deprecated
	public float maxUpStep() {
		return this.maxUpStep;
	}

	public void setMaxUpStep(float p_275672_) {
		this.maxUpStep = p_275672_;
	}

    public Ostrich(EntityType<? extends Ostrich> p_27557_, World p_27558_) {
        super(p_27557_, p_27558_);
        this.setMaxUpStep(1.5F);
        MobNavigation groundpathnavigation = (MobNavigation) this.getNavigation();
        groundpathnavigation.setCanSwim(true);
        groundpathnavigation.setCanWalkOverFences(true);
    }

    public static DefaultAttributeContainer.Builder createOstrichAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 24.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0F).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 20.0F);
    }

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(DATA_REMAINING_ANGER_TIME, 0);
		builder.add(HAS_EGG, false);
		builder.add(DIP, false);
	}
		public void onTrackedDataSet(TrackedData<?> p_29615_) {
        if (DIP.equals(p_29615_)) {
            if (this.isDip()) {
                idlingState.stop();
                this.dippingState.start(this.age);
            } else {
                this.dippingState.stop();
            }
        }

        super.onTrackedDataSet(p_29615_);
    }

    @Override
    public void tick() {
        if ((this.isDip() || this.isMoving()) && getWorld().isClient()) {
            if (isDashing()) {
                idlingState.stop();
                runningScale = MathHelper.clamp(runningScale + 0.1F, 0, 1);
            } else {
                idlingState.stop();
                runningScale = MathHelper.clamp(runningScale - 0.1F, 0, 1);
            }
        } else if (getWorld().isClient()) {
            runningScale = MathHelper.clamp(runningScale - 0.1F, 0, 1);
            idlingState.startIfNotRunning(this.age);
        }
        super.tick();
    }

    public float getRunningScale() {
        return runningScale;
    }

    public void setHomeTarget(@Nullable BlockPos pos) {
        this.homeTarget = pos;
    }

    @Nullable
    private BlockPos getHomeTarget() {
        return this.homeTarget;
    }

    public boolean isDip() {
        return this.dataTracker.get(DIP);
    }

    public void setDip(boolean dip) {
        this.dataTracker.set(DIP, dip);
    }

    private boolean isDashing() {
        return this.getVelocity().horizontalLengthSquared() > 0.0075D;
    }

    private boolean isMoving() {
        return this.getVelocity().horizontalLengthSquared() > 1.0E-6D;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new OstrichDipGoal());
        this.goalSelector.add(4, new MeleeAttackGoal(this, 1.5D, true));
        this.goalSelector.add(5, new TemptGoal(this, 1.25D, Ingredient.ofItems(Items.WHEAT), false));

        this.goalSelector.add(6, new LayEggGoal(this, 0.85D));

        this.goalSelector.add(8, new OstrichGoHomeGoal(this, 0.85D));
        this.goalSelector.add(9, new AnimalMateGoal(this, 0.85D) {
            @Override
            public boolean canStart() {
                return !hasEgg() && super.canStart();
            }
        });
        this.goalSelector.add(10, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(11, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.targetSelector.add(1, (new RevengeGoal(this)).setGroupRevenge());
        this.targetSelector.add(2, new OstrichAttackEnemyGoal());
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        this.targetSelector.add(4, new UniversalAngerGoal<>(this, true));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound compoundTag) {
        super.writeCustomDataToNbt(compoundTag);
        if (this.homeTarget != null) {
            compoundTag.put("HomeTarget", NbtHelper.fromBlockPos(this.homeTarget));
        }
        compoundTag.putInt("FeatherTime", this.featherTime);
        compoundTag.putBoolean("HasEgg", this.hasEgg());
        this.writeAngerToNbt(compoundTag);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound compoundTag) {
        super.readCustomDataFromNbt(compoundTag);
		this.homeTarget = null;
        if (compoundTag.contains("HomeTarget")) {
            Optional<BlockPos> optionalBlockPos = NbtHelper.toBlockPos(compoundTag,"HomeTarget");
			this.homeTarget = optionalBlockPos.orElseThrow(() -> new IllegalArgumentException("HomeTarget not found in NBT data"));
        }
        if (compoundTag.contains("FeatherTime")) {
            this.featherTime = compoundTag.getInt("FeatherTime");
        }
        this.setHasEgg(compoundTag.getBoolean("HasEgg"));
        this.readAngerFromNbt(this.getWorld(), compoundTag);
    }

	public boolean isBreedingItem(ItemStack itemStack) {
		return OstrichBrain.getTemptItems().test(itemStack);
	}


	public boolean hasEgg() {
        return this.dataTracker.get(HAS_EGG);
    }

    public void setHasEgg(boolean hasEgg) {
        this.dataTracker.set(HAS_EGG, hasEgg);
    }

    public int getAngerTime() {
        return this.dataTracker.get(DATA_REMAINING_ANGER_TIME);
    }

    public void setAngerTime(int p_30404_) {
        this.dataTracker.set(DATA_REMAINING_ANGER_TIME, p_30404_);
    }

    public void chooseRandomAngerTime() {
        this.setAngerTime(PERSISTENT_ANGER_TIME.get(this.random));
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.homeTarget != null) {
            if (!this.getWorld().getBlockState(this.homeTarget).isOf(LostLegendsBlocks.OSTRICH_EGG.get())) {
                this.homeTarget = null;
            }
        }

        if (!this.getWorld().isClient) {
            this.tickAngerLogic((ServerWorld) this.getWorld(), true);
        }

        if (!this.getWorld().isClient && this.isAlive() && !this.isBaby() && --this.featherTime <= 0) {
            this.playSound(SoundEvents.ENTITY_BAT_TAKEOFF, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.dropItem(Items.FEATHER);
            this.emitGameEvent(GameEvent.ENTITY_INTERACT);
            this.featherTime = this.random.nextInt(6000) + 6000;
        }
    }

    @Override
    public boolean tryAttack(Entity p_21372_) {
        this.getWorld().sendEntityStatus(this, (byte) 4);
        return super.tryAttack(p_21372_);
    }

    @Override
    public void handleStatus(byte p_219360_) {
        if (p_219360_ == 4) {
            this.kickingState.start(this.age);
        } else {
            super.handleStatus(p_219360_);
        }
    }

	
	public UUID getAngryAt() {
		return this.persistentAngerTarget;
	}

	public void setAngryAt( UUID p_30400_) {
		this.persistentAngerTarget = p_30400_;
	}

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld p_146743_, PassiveEntity p_146744_) {
        Ostrich ostrich = LostLegendsEntityTypes.OSTRICH.get().create(p_146743_);
        return ostrich;
    }

    public void breed(ServerWorld p_218479_, AnimalEntity p_218480_) {
        ServerPlayerEntity serverplayer = this.getLovingPlayer();
        if (serverplayer == null) {
            serverplayer = p_218480_.getLovingPlayer();
        }

        if (serverplayer != null) {
            serverplayer.incrementStat(Stats.ANIMALS_BRED);
			Criteria.BRED_ANIMALS.trigger(serverplayer, this, p_218480_, (PassiveEntity) null);
        }

        this.setBreedingAge(6000);
        p_218480_.setBreedingAge(6000);
        this.resetLoveTicks();
        p_218480_.resetLoveTicks();
        this.setHasEgg(true);
        p_218479_.sendEntityStatus(this, (byte) 18);
        if (p_218479_.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
            p_218479_.spawnEntity(new ExperienceOrbEntity(p_218479_, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
        }
    }

    @Override
    protected float getJumpVelocity() {
        return 0.56F * this.getJumpVelocityMultiplier();
    }

	@Override
	protected void applyDamage(DamageSource source, float amount) {
		this.setDip(false);
		super.applyDamage(source, amount);
	}

	class OstrichDipGoal extends Goal {

        public int tick;

        OstrichDipGoal() {
            this.setControls(EnumSet.of(Control.MOVE, Control.JUMP, Control.LOOK));
        }

        @Override
        public boolean canStart() {
            if (Ostrich.this.getHomeTarget() != null) {
                if (!Ostrich.this.isDip() && Ostrich.this.getTarget() == null && Ostrich.this.homeTarget.getSquaredDistance(Ostrich.this.getBlockPos()) < 64) {
                    if (Ostrich.this.isOnGround() && Ostrich.this.getWorld().getBlockState(Ostrich.this.getBlockPos().down()).isIn(BlockTags.SAND) && Ostrich.this.random.nextInt(240) == 0) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public boolean shouldContinue() {
            return this.tick < 100;
        }

        @Override
        public void start() {
            super.start();
            setDip(true);
            this.tick = 0;

        }

        @Override
        public void tick() {
            super.tick();
            this.tick++;
        }

        @Override
        public void stop() {
            super.stop();
            setDip(false);
            this.tick = 0;
        }
    }

    static class OstrichGoHomeGoal extends Goal
	{
        private final Ostrich ostrich;
        private final double speedModifier;
        private boolean stuck;
        private int closeToHomeTryTicks;

        OstrichGoHomeGoal(Ostrich p_30253_, double p_30254_) {
            this.ostrich = p_30253_;
            this.speedModifier = p_30254_;
            this.setControls(EnumSet.of(Control.MOVE));
        }

        public boolean canStart() {
            if (this.ostrich.isBaby()) {
                return false;
            } else if (this.ostrich.hasEgg()) {
                return false;
            } else {
                return this.ostrich.getHomeTarget() != null && !this.ostrich.getHomeTarget().isWithinDistance(this.ostrich.getPos(), 32.0D);
            }
        }

        public void start() {
            this.stuck = false;
            this.closeToHomeTryTicks = 0;
        }

        public void stop() {
        }

        public boolean shouldContinue() {
            return this.ostrich.getHomeTarget() != null && !this.ostrich.getHomeTarget().isWithinDistance(this.ostrich.getPos(), 7.0D) && !this.stuck && this.closeToHomeTryTicks <= this.getTickCount(600);
        }

        public void tick() {
            if (this.ostrich.getHomeTarget() != null) {
                BlockPos blockpos = this.ostrich.getHomeTarget();
                boolean flag = blockpos.isWithinDistance(this.ostrich.getPos(), 16.0D);
                if (flag) {
                    ++this.closeToHomeTryTicks;
                }

                if (this.ostrich.getNavigation().isIdle()) {
                    Vec3d vec3 = Vec3d.ofBottomCenter(blockpos);
                    Vec3d vec31 = NoPenaltyTargeting.findTo(this.ostrich, 16, 3, vec3, (double) ((float) Math.PI / 10F));
                    if (vec31 == null) {
                        vec31 = NoPenaltyTargeting.findTo(this.ostrich, 8, 7, vec3, (double) ((float) Math.PI / 2F));
                    }

                    if (vec31 != null && !flag && !this.ostrich.getWorld().getBlockState(BlockPos.ofFloored(vec31)).isOf(Blocks.WATER)) {
                        vec31 = NoPenaltyTargeting.findTo(this.ostrich, 16, 5, vec3, (double) ((float) Math.PI / 2F));
                    }

                    if (vec31 == null) {
                        this.stuck = true;
                        return;
                    }

                    this.ostrich.getNavigation().startMovingTo(vec31.x, vec31.y, vec31.z, this.speedModifier);
                }

            }
        }
    }

    static class LayEggGoal extends MoveToTargetPosGoal
	{
        private final Ostrich ostrich;

        LayEggGoal(Ostrich p_30276_, double p_30277_) {
            super(p_30276_, p_30277_, 16);
            this.ostrich = p_30276_;
        }

        public boolean canStart() {
            return this.ostrich.hasEgg() ? super.canStart() : false;
        }

        public boolean shouldContinue() {
            return super.shouldContinue() && this.ostrich.hasEgg();
        }

        public void tick() {
            super.tick();
            if (!this.ostrich.isTouchingWater() && this.hasReached()) {
                World level = this.ostrich.getWorld();
                level.playSound((PlayerEntity) null, this.targetPos.up(), SoundEvents.ENTITY_TURTLE_LAY_EGG, SoundCategory.BLOCKS, 0.3F, 0.9F + level.random.nextFloat() * 0.2F);
                level.setBlockState(this.targetPos.up(), LostLegendsBlocks.OSTRICH_EGG.get().getDefaultState(), 3);
                this.ostrich.setHasEgg(false);
                this.ostrich.setLoveTicks(600);
                this.ostrich.setHomeTarget(this.targetPos.up());
            }

        }

		protected boolean isTargetPos(WorldView p_30280_, BlockPos p_30281_) {
            return (p_30280_.getBlockState(p_30281_).isIn(BlockTags.DIRT) || p_30280_.getBlockState(p_30281_).isIn(BlockTags.SAND)) && p_30280_.isAir(p_30281_.up());
        }
    }

    private class OstrichAttackEnemyGoal extends ActiveTargetGoal<LivingEntity>
	{
        public OstrichAttackEnemyGoal() {
            super(Ostrich.this, LivingEntity.class, 140, true, true, (livingEntity -> {
                return livingEntity instanceof Monster;
            }));
        }

        public boolean canStart() {
            if (Ostrich.this.isBaby()) {
                return false;
            } else {
                if (super.canStart()) {
                    if (canFindEgg()) {
                        return true;
                    }
                }

                return false;
            }
        }

        private boolean canFindEgg() {
            BlockPos blockpos = Ostrich.this.getBlockPos();
            BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

            for (int i = 0; i < 8; ++i) {
                for (int j = 0; j < 8; ++j) {
                    for (int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
                        for (int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
                            blockpos$mutable.set(blockpos, k, i, l);
                            if (getWorld().getBlockState(blockpos$mutable).isOf(LostLegendsBlocks.OSTRICH_EGG.get())) {
                                return true;
                            }
                        }
                    }
                }
            }

            return false;
        }

        protected double getFollowRange() {
            return super.getFollowRange() * 0.5D;
        }
    }
}
