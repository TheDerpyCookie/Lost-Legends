package sabledream.studios.lostlegends.block.entity;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.VisibleForTesting;
import sabledream.studios.lostlegends.entity.Meerkat;
import sabledream.studios.lostlegends.init.LostLegendsBlockEntity;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class BurrowBlockEntity extends BlockEntity
{

	private static final List<String> IGNORED_TAGS = Arrays.asList("Air", "ArmorDropChances", "ArmorItems", "Brain", "CanPickUpLoot", "DeathTime", "FallDistance", "FallFlying", "Fire", "HandDropChances", "HandItems", "HurtByTimestamp", "HurtTime", "LeftHanded", "Motion", "PortalCooldown", "Pos", "Rotation", "Passengers", "Leash", "CannotEnterBurrowTicks");
	private final List<BurrowData> stored = Lists.newArrayList();

	public BurrowBlockEntity(BlockPos pos, BlockState state) {
		super(LostLegendsBlockEntity.BURROW.get(), pos, state);
	}


	public void readNbt(NbtCompound p_155156_, RegistryWrapper.WrapperLookup wrapperLookup) {
		super.readNbt(p_155156_,wrapperLookup);
		this.stored.clear();
		NbtList listtag = p_155156_.getList("Meerkats", 10);

		for (int i = 0; i < listtag.size(); ++i) {
			NbtCompound compoundtag = listtag.getCompound(i);
			BurrowBlockEntity.BurrowData beehiveblockentity$beedata = new BurrowBlockEntity.BurrowData(compoundtag.getCompound("EntityData"), compoundtag.getInt("TicksInBurrow"), compoundtag.getInt("MinOccupationTicks"));
			this.stored.add(beehiveblockentity$beedata);
		}
	}

	protected void writeNbt(NbtCompound p_187467_,RegistryWrapper.WrapperLookup registryLookup) {
		super.writeNbt(p_187467_,registryLookup);
		p_187467_.put("Meerkats", this.writeMeerkats());
	}

	public NbtList writeMeerkats() {
		NbtList listtag = new NbtList();

		for (BurrowBlockEntity.BurrowData burrowData : this.stored) {
			NbtCompound compoundtag = burrowData.entityData.copy();
			NbtCompound compoundtag1 = new NbtCompound();
			compoundtag1.put("EntityData", compoundtag);
			compoundtag1.putInt("TicksInBurrow", burrowData.ticksInBurrow);
			compoundtag1.putInt("MinOccupationTicks", burrowData.minOccupationTicks);
			listtag.add(compoundtag1);
		}

		return listtag;
	}

	private static void tickOccupants(World p_155150_, BlockPos p_155151_, BlockState p_155152_, List<BurrowBlockEntity.BurrowData> p_155153_) {
		boolean flag = false;

		BurrowBlockEntity.BurrowData burrowMeerkat;
		for (Iterator<BurrowData> iterator = p_155153_.iterator(); iterator.hasNext(); ++burrowMeerkat.ticksInBurrow) {
			burrowMeerkat = iterator.next();
			if (burrowMeerkat.ticksInBurrow > burrowMeerkat.minOccupationTicks) {
				BurrowBlockEntity.BurrowReleaseStatus burrowReleaseStatus = BurrowReleaseStatus.BURROW_RELEASED;
				if (releaseOccupant(p_155150_, p_155151_, burrowMeerkat, (List<Entity>) null, burrowReleaseStatus)) {
					flag = true;
					iterator.remove();
				}
			}
		}

		if (flag) {
			markDirty(p_155150_, p_155151_, p_155152_);
		}

	}

	public static void serverTick(World p_155145_, BlockPos p_155146_, BlockState p_155147_, BurrowBlockEntity p_155148_) {
		tickOccupants(p_155145_, p_155146_, p_155147_, p_155148_.stored);
		if (!p_155148_.stored.isEmpty() && p_155145_.getRandom().nextDouble() < 0.005D) {
			p_155145_.syncWorldEvent(2001, p_155146_, Block.getRawIdFromState(Blocks.SAND.getDefaultState()));
			//p_155145_.gameEvent(GameEvent.BLOCK_ACTIVATE, p_155146_, GameEvent.Context.of(p_155147_));
		}
	}

	public void addOccupant(Entity p_58742_) {
		this.addOccupantWithPresetTicks(p_58742_, 0);
	}

	@VisibleForTesting
	public int getOccupantCount() {
		return this.stored.size();
	}

	public void addOccupantWithPresetTicks(Entity p_58745_, int p_58747_) {
		if (this.stored.size() < 3) {
			p_58745_.stopRiding();
			p_58745_.removeAllPassengers();
			NbtCompound compoundtag = new NbtCompound();
			p_58745_.saveNbt(compoundtag);
			this.storeMeerkat(compoundtag, p_58747_);
			if (this.getWorld() != null) {

				BlockPos blockpos = this.getPos();
				this.getWorld().playSound((PlayerEntity) null, (double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ(), SoundEvents.BLOCK_SAND_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
				this.getWorld().emitGameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Emitter.of(p_58745_, this.getCachedState()));
			}

			p_58745_.discard();
			super.markDirty();
		} else {
			p_58745_.setPose(EntityPose.EMERGING);
		}
	}

	public void storeMeerkat(NbtCompound p_155158_, int p_155159_) {
		this.stored.add(new BurrowData(p_155158_, p_155159_, 2400));
	}

	public boolean isEmpty() {
		return this.stored.isEmpty();
	}

	public boolean isFull() {
		return this.stored.size() == 3;
	}

	public void emptyAllLivingFromBurrow(PlayerEntity p_58749_, BurrowBlockEntity.BurrowReleaseStatus p_58751_) {
		List<Entity> list = this.releaseAllOccupants(p_58751_);
		if (p_58749_ != null) {
			for (Entity entity : list) {
				if (entity instanceof Meerkat) {
					Meerkat meerkat = (Meerkat) entity;
					if (p_58749_.getPos().squaredDistanceTo(entity.getPos()) <= 16.0D) {
						meerkat.setTarget(p_58749_);
					}
					meerkat.stayOutOfBurrowCountdown = 400;
				}
			}
		}

	}

	private List<Entity> releaseAllOccupants(BurrowReleaseStatus p_58761_) {
		List<Entity> list = Lists.newArrayList();
		this.stored.removeIf((p_58766_) -> {
			return releaseOccupant(this.getWorld(), this.pos, p_58766_, list, p_58761_);
		});
		if (!list.isEmpty()) {
			super.markDirty();
		}

		return list;
	}

	private static boolean releaseOccupant(World p_155137_, BlockPos p_155138_, BurrowBlockEntity.BurrowData p_155140_,List<Entity> p_155141_, BurrowBlockEntity.BurrowReleaseStatus p_155142_) {
		if ((p_155137_.isNight() || p_155137_.isRaining()) && p_155142_ != BurrowBlockEntity.BurrowReleaseStatus.EMERGENCY) {
			return false;
		} else {
			NbtCompound compoundtag = p_155140_.entityData.copy();
			removeIgnoredTags(compoundtag);
			Direction direction = Direction.UP;
			BlockPos blockpos = p_155138_.offset(direction);
			boolean flag = !p_155137_.getBlockState(blockpos).getCollisionShape(p_155137_, blockpos).isEmpty();
			if (flag && p_155142_ != BurrowBlockEntity.BurrowReleaseStatus.EMERGENCY) {
				return false;
			} else {
				Entity entity = EntityType.loadEntityWithPassengers(compoundtag, p_155137_, (p_58740_) -> {
					return p_58740_;
				});
				if (entity != null) {

					if (entity instanceof Meerkat) {
						Meerkat meerkat = (Meerkat) entity;

						meerkat.stayOutOfBurrowCountdown = 400;
						meerkat.setPose(EntityPose.EMERGING);
						setReleaseData(p_155140_.ticksInBurrow, meerkat);
						if (p_155141_ != null) {
							p_155141_.add(meerkat);
						}

						float f = entity.getWidth();
						double d0 = (double) p_155138_.getX() + 0.5D + (double) direction.getOffsetX();
						double d1 = (double) p_155138_.getY() + 0.5D + (double) direction.getOffsetY();
						double d2 = (double) p_155138_.getZ() + 0.5D + (double) direction.getOffsetZ();
						entity.refreshPositionAndAngles(d0, d1, d2, entity.getYaw(), entity.getPitch());
					}

					p_155137_.playSound((PlayerEntity) null, p_155138_, SoundEvents.BLOCK_SAND_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
					p_155137_.emitGameEvent(GameEvent.BLOCK_CHANGE, p_155138_, GameEvent.Emitter.of(entity, p_155137_.getBlockState(p_155138_)));
					p_155137_.spawnEntity(entity);
					return true;
				}
			}
		}
		return false;
	}

	private static void setReleaseData(int p_58737_, Meerkat p_58738_) {
		int i = p_58738_.getBreedingAge();
		if (i < 0) {
			p_58738_.setBreedingAge(Math.min(0, i + p_58737_));
		} else if (i > 0) {
			p_58738_.setBreedingAge(Math.max(0, i - p_58737_));
		}

		p_58738_.setLoveTicks(Math.max(0, p_58738_.getLoveTicks() - p_58737_));

	}

	static void removeIgnoredTags(NbtCompound p_155162_) {
		for (String s : IGNORED_TAGS) {
			p_155162_.remove(s);
		}
	}

	static class BurrowData {
		final NbtCompound entityData;
		int ticksInBurrow;
		final int minOccupationTicks;

		BurrowData(NbtCompound p_58786_, int p_58787_, int p_58788_) {
			BurrowBlockEntity.removeIgnoredTags(p_58786_);
			this.entityData = p_58786_;
			this.ticksInBurrow = p_58787_;
			this.minOccupationTicks = p_58788_;
		}
	}

	public static enum BurrowReleaseStatus {
		BURROW_RELEASED,
		EMERGENCY;
	}
}
