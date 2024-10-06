package sabledream.studios.lostlegends.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.block.HollowedLogBlock;
import sabledream.studios.lostlegends.config.LostLegendsConfig;
import sabledream.studios.lostlegends.init.LostLegendsBlocks;
import net.minecraft.state.property.Properties;
import sabledream.studios.lostlegends.init.LostLegendsParticleTypes;
import sabledream.studios.lostlegends.init.LostLegendsProperties;
import sabledream.studios.lostlegends.init.LostLegendsSoundEvents;
import sabledream.studios.lostlegends.tag.LostLegendsTags;

import java.util.*;


public class TermiteManager {
	public static final int TERMITE_COUNT_ASLEEP = 1;
	public static final int TERMITE_COUNT_ASLEEP_NATURAL = 0;
	public static final int TERMITE_COUNT = 5;
	public static final int TERMITE_COUNT_NATURAL = 3;
	public static final int PARTICLE_COUNT_WHILE_EATING = 4;
	public static final int PARTICLE_COUNT = 6;
	public static final float BLOCK_SOUND_VOLUME = 0.6F;
	private final ArrayList<Termite> termites = new ArrayList<>();
	public int ticksToNextTermite;
	public int highestID;

	public TermiteManager() {
	}

	public static int maxTermites(boolean natural, boolean awake, boolean canSpawn) {
		if (!canSpawn) {
			return 0;
		}
		if (!awake) {
			return natural ? TERMITE_COUNT_ASLEEP_NATURAL : TERMITE_COUNT_ASLEEP;
		}
		return natural ? TERMITE_COUNT_NATURAL : TERMITE_COUNT;
	}

	public static boolean areTermitesSafe(@NotNull World level, @NotNull BlockPos pos) {
		BlockPos.Mutable mutableBlockPos = pos.mutableCopy();
		for (Direction direction : Direction.values()) {
			if (!isPosSafeForTermites(level, mutableBlockPos.move(direction))) {
				return false;
			}
			mutableBlockPos.move(direction, -1);
		}
		return true;
	}

	public static boolean isPosSafeForTermites(@NotNull WorldAccess level, @NotNull BlockPos pos) {
		return isStateSafeForTermites(level.getBlockState(pos)) && level.getFluidState(pos).isEmpty();
	}

	public static boolean isPosSafeForTermites(@NotNull WorldAccess level, @NotNull BlockPos pos, @NotNull BlockState state) {
		return isStateSafeForTermites(state) && level.getFluidState(pos).isEmpty();
	}

	public static boolean isStateSafeForTermites(@NotNull BlockState state) {
		return !state.isIn(LostLegendsTags.KILLS_TERMITE) && (!state.contains(Properties.WATERLOGGED) || !state.get(Properties.WATERLOGGED));
	}

	public void addTermite(@NotNull BlockPos pos) {
		Termite termite = new Termite(pos, pos, 0, 0, 0, false, this.highestID += 1);
		this.termites.add(termite);
	}

	public void tick(@NotNull World level, @NotNull BlockPos pos, boolean natural, boolean awake, boolean canSpawn) {
		int maxTermites = maxTermites(natural, awake, canSpawn);
		ArrayList<Termite> termitesToRemove = new ArrayList<>();
		Random random = level.getRandom();
		for (Termite termite : this.termites) {
			if (termite.tick(level, natural, random)) {
				if (level instanceof ServerWorld serverLevel) {
					BlockPos termitePos = termite.getPos();
					serverLevel.spawnParticles(
						LostLegendsParticleTypes.TERMITE,
						termitePos.getX() + 0.5D,
						termitePos.getY() + 0.5D,
						termitePos.getZ() + 0.5D,
						termite.eating ? PARTICLE_COUNT_WHILE_EATING : PARTICLE_COUNT,
						0D,
						0D,
						0D,
						0D
					);
				}
			} else {
				level.playSound(null, termite.pos, LostLegendsSoundEvents.BLOCK_TERMITE_MOUND_ENTER.get(), SoundCategory.NEUTRAL, BLOCK_SOUND_VOLUME, 1F);
				termitesToRemove.add(termite);
			}
		}
		for (Termite termite : termitesToRemove) {
			level.emitGameEvent(null, GameEvent.ENTITY_DIE, Vec3d.ofCenter(termite.pos));
			this.termites.remove(termite);
			level.emitGameEvent(null, GameEvent.BLOCK_CHANGE, Vec3d.ofCenter(pos));
		}
		if (this.termites.size() < maxTermites) {
			if (this.ticksToNextTermite > 0) {
				--this.ticksToNextTermite;
			} else {
				this.addTermite(pos);
				level.emitGameEvent(null, GameEvent.BLOCK_CHANGE, Vec3d.ofCenter(pos));
				level.playSound(null, pos, LostLegendsSoundEvents.BLOCK_TERMITE_MOUND_EXIT.get(), SoundCategory.NEUTRAL, BLOCK_SOUND_VOLUME, 1F);
				this.ticksToNextTermite = natural ? TERMITE_RELEASE_COUNTDOWN_NATURAL : TERMITE_RELEASE_COUNTDOWN;
			}
		}
		while (this.termites.size() > maxTermites) {
			Termite termite = this.termites.get(random.nextInt(this.termites.size()));
			level.playSound(null, termite.pos, LostLegendsSoundEvents.BLOCK_TERMITE_MOUND_ENTER.get(), SoundCategory.NEUTRAL, BLOCK_SOUND_VOLUME, 1F);
			level.emitGameEvent(null, GameEvent.TELEPORT, Vec3d.ofCenter(termite.pos));
			this.termites.remove(termite);
			level.emitGameEvent(null, GameEvent.BLOCK_CHANGE, Vec3d.ofCenter(pos));
		}
		termitesToRemove.clear();
	}
	public static final int TERMITE_RELEASE_COUNTDOWN = 200;
	public static final int TERMITE_RELEASE_COUNTDOWN_NATURAL = 320;

	public void clearTermites(@NotNull World level) {
		for (Termite termite : this.termites) {
			level.emitGameEvent(null, GameEvent.ENTITY_DIE, Vec3d.ofCenter(termite.pos));
			level.playSound(null, termite.pos, LostLegendsSoundEvents.BLOCK_TERMITE_MOUND_ENTER.get(), SoundCategory.NEUTRAL, BLOCK_SOUND_VOLUME, 1F);
		}
		this.termites.clear();
	}

	public ArrayList<Termite> termites() {
		return this.termites;
	}

	public void saveAdditional(@NotNull NbtCompound tag) {
		tag.putInt("ticksToNextTermite", this.ticksToNextTermite);
		tag.putInt("highestID", this.highestID);
		Logger logger = LostLegends.getLogger();
		DataResult<NbtElement> var10000 = Termite.CODEC.listOf().encodeStart(NbtOps.INSTANCE, this.termites);
		Objects.requireNonNull(logger);
		var10000.resultOrPartial(logger::error).ifPresent((nbt) -> tag.put("termites", nbt));
	}

	public void load(@NotNull NbtCompound tag) {
		this.ticksToNextTermite = tag.getInt("ticksToNextTermite");
		this.highestID = tag.getInt("highestID");
		if (tag.contains("termites", 9)) {
			this.termites.clear();
			DataResult<List<Termite>> var10000 = Termite.CODEC.listOf().parse(new Dynamic<>(NbtOps.INSTANCE, tag.getList("termites", 10)));
			Logger logger = LostLegends.getLogger();
			Objects.requireNonNull(logger);
			Optional<List<Termite>> list = var10000.resultOrPartial(logger::error);
			if (list.isPresent()) {
				List<Termite> termiteList = list.get();
				this.termites.addAll(termiteList);
			}
		}
	}

	public static class Termite {
		public static final int DESTROY_POWER_BEFORE_BLOCK_BREAKS = 200;
		public static final int DESTROY_POWER_LEAVES = 4;
		public static final int DESTROY_POWER_BREAKABLE = 2;
		public static final int DESTROY_POWER = 1;
		public static final int MAX_IDLE_TICKS = 2000;
		public static final int MAX_IDLE_TICKS_NATURAL = 1200;
		public static final int UPDATE_DELAY_IN_TICKS = 1;
		public static final int GNAW_PARTICLE_CHANCE = 4;
		public static final int MIN_GNAW_PARTICLES = 0;
		public static final int MAX_GNAW_PARTICLES = 3;
		public static final int MIN_EAT_PARTICLES = 18;
		public static final int MAX_EAT_PARTICLES = 25;
		public static final Codec<Termite> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			BlockPos.CODEC.fieldOf("mound").forGetter(Termite::getMoundPos),
			BlockPos.CODEC.fieldOf("pos").forGetter(Termite::getPos),
			Codec.intRange(0, DESTROY_POWER_BEFORE_BLOCK_BREAKS + 1).fieldOf("block_destroy_power").orElse(0).forGetter(Termite::getBlockDestroyPower),
			Codec.INT.fieldOf("idle_ticks").orElse(0).forGetter(Termite::getIdleTicks),
			Codec.INT.fieldOf("update_delay").orElse(0).forGetter(Termite::getUpdateTicks),
			Codec.BOOL.fieldOf("eating").orElse(true).forGetter(Termite::getEating),
			Codec.INT.fieldOf("id").orElse(0).forGetter(Termite::getID)
		).apply(instance, Termite::new));

		public static final Map<Block, Block> DEGRADABLE_BLOCKS = new Object2ObjectOpenHashMap<>();
		public static final Map<Block, Block> NATURAL_DEGRADABLE_BLOCKS = new Object2ObjectOpenHashMap<>();

		public BlockPos mound;
		public BlockPos pos;
		public int blockDestroyPower;
		public int idleTicks;
		public int update;
		public boolean eating;
		public int id;

		public Termite(@NotNull BlockPos mound, @NotNull BlockPos pos, int blockDestroyPower, int aliveTicks, int update) {
			this.mound = mound;
			this.pos = pos;
			this.blockDestroyPower = blockDestroyPower;
			this.idleTicks = aliveTicks;
			this.update = update;
		}

		public Termite(@NotNull BlockPos mound, @NotNull BlockPos pos, int blockDestroyPower, int aliveTicks, int update, boolean eating, int id) {
			this.mound = mound;
			this.pos = pos;
			this.blockDestroyPower = blockDestroyPower;
			this.idleTicks = aliveTicks;
			this.update = update;
			this.eating = eating;
			this.id = id;
		}

		public boolean tick(@NotNull World level, boolean natural, Random random) {
			boolean exit = false;
			++this.idleTicks;
			if (this.idleTicks > (natural ? MAX_IDLE_TICKS_NATURAL : MAX_IDLE_TICKS) || isTooFar(natural, this.mound, this.pos)) {
				return false;
			}
			if (!areTermitesSafe(level, this.pos)) {
				return false;
			}
			if (canMove(level, this.pos)) {
				BlockState blockState = level.getBlockState(this.pos);
				Block block = blockState.getBlock();
				boolean degradable = !natural ? DEGRADABLE_BLOCKS.containsKey(block) : NATURAL_DEGRADABLE_BLOCKS.containsKey(block);
				boolean breakable = blockState.isIn(LostLegendsTags.TERMITE_BREAKABLE);
				boolean leaves = blockState.isIn(BlockTags.LEAVES);
				if ((degradable || breakable) && isEdibleProperty(blockState)) {
					this.eating = true;
					exit = true;
					int additionalPower = breakable ? leaves ? DESTROY_POWER_LEAVES : DESTROY_POWER_BREAKABLE : DESTROY_POWER;
					this.blockDestroyPower += additionalPower;
					spawnGnawParticles(level, blockState, this.pos, random);
					if (this.blockDestroyPower > DESTROY_POWER_BEFORE_BLOCK_BREAKS) {
						this.blockDestroyPower = 0;
						this.idleTicks = natural ? Math.max(0, this.idleTicks - (DESTROY_POWER_BEFORE_BLOCK_BREAKS / additionalPower)) : 0;
						if (breakable) {
							level.breakBlock(this.pos, true);
						} else {
							level.addBlockBreakParticles(this.pos, blockState);
							Block setBlock = !natural ? DEGRADABLE_BLOCKS.get(block) : NATURAL_DEGRADABLE_BLOCKS.get(block);
							BlockState setState = setBlock.getStateWithProperties(blockState);
							level.setBlockState(this.pos, setState);
							if (setBlock instanceof HollowedLogBlock) {
								boolean nether = new ItemStack(setBlock.asItem()).isIn(ItemTags.NON_FLAMMABLE_WOOD);
								level.playSound(null, pos, nether ? LostLegendsSoundEvents.STEM_HOLLOWED.get() : LostLegendsSoundEvents.LOG_HOLLOWED.get(), SoundCategory.BLOCKS, BLOCK_SOUND_VOLUME, 0.95F + (random.nextFloat() * 0.2F));
							}
						}
						spawnEatParticles(level, blockState, this.pos, random);
						level.playSound(null, pos, LostLegendsSoundEvents.BLOCK_TERMITE_MOUND_TERMITE_GNAW_FINISH.get(), SoundCategory.BLOCKS, BLOCK_SOUND_VOLUME, 0.9F + (random.nextFloat() * 0.25F));
					}
				} else {
					this.eating = false;
					this.blockDestroyPower = 0;
					Direction direction = Direction.random(random);
					if (blockState.isAir()) {
						direction = Direction.DOWN;
					}
					BlockPos offset = this.pos.offset(direction);
					BlockState state = level.getBlockState(offset);
					if (!isStateSafeForTermites(state)) {
						return false;
					}

					if (this.update > 0 && !blockState.isAir()) {
						--this.update;
						return true;
					} else {
						this.update = UPDATE_DELAY_IN_TICKS;
						BlockPos priority = degradableBreakablePos(level, this.pos, natural, random);
						if (priority != null) {
							this.pos = priority;
							exit = true;
						} else {
							BlockPos ledge = ledgePos(level, offset, natural);
							BlockPos posUp = this.pos.up();
							BlockState stateUp = level.getBlockState(posUp);
							if (exposedToAir(level, offset, natural)
								&& isBlockMovable(state, direction)
								&& !(direction != Direction.DOWN && state.isAir() && (!this.mound.isWithinDistance(this.pos, 1.5D)) && ledge == null)
							) {
								this.pos = offset;
								if (ledge != null) {
									this.pos = ledge;
								}
								exit = true;
							} else if (ledge != null && exposedToAir(level, ledge, natural)) {
								this.pos = ledge;
								exit = true;
							} else if (!stateUp.isAir() && isBlockMovable(stateUp, Direction.UP) && exposedToAir(level, posUp, natural)) {
								this.pos = posUp;
								exit = true;
							}
						}
					}
				}
			}
			return exit || (exposedToAir(level, this.pos, natural));
		}

		@Nullable
		public static BlockPos ledgePos(@NotNull World level, @NotNull BlockPos pos, boolean natural) {
			BlockPos.Mutable mutableBlockPos = pos.mutableCopy();
			BlockState state = level.getBlockState(mutableBlockPos);
			if (DEGRADABLE_BLOCKS.containsKey(state.getBlock()) || state.isIn(LostLegendsTags.TERMITE_BREAKABLE)) {
				return mutableBlockPos;
			}
			mutableBlockPos.move(Direction.DOWN);
			state = level.getBlockState(mutableBlockPos);
			if (!state.isAir() && isBlockMovable(state, Direction.DOWN) && exposedToAir(level, mutableBlockPos, natural)) {
				return mutableBlockPos;
			}
			mutableBlockPos.move(Direction.UP, 2);
			state = level.getBlockState(mutableBlockPos);
			if (!state.isAir() && isBlockMovable(state, Direction.UP) && exposedToAir(level, mutableBlockPos, natural)) {
				return mutableBlockPos;
			}
			return null;
		}

		@Nullable
		public static BlockPos degradableBreakablePos(@NotNull World level, @NotNull BlockPos pos, boolean natural, Random random) {
			BlockPos.Mutable mutableBlockPos = pos.mutableCopy();
			List<Direction> directions = Util.copyShuffled(Direction.values(), random);
			BlockState upState = level.getBlockState(mutableBlockPos.move(Direction.UP));
			if (canEatBlock(natural, mutableBlockPos, upState)) return mutableBlockPos;
			mutableBlockPos.move(Direction.DOWN);
			for (Direction direction : directions) {
				BlockState state = level.getBlockState(mutableBlockPos.move(direction));
				if (canEatBlock(natural, mutableBlockPos, state)) return mutableBlockPos;
				mutableBlockPos.move(direction, -1);
			}
			return null;
		}

		private static boolean canEatBlock(boolean natural, @NotNull BlockPos.Mutable mutableBlockPos, @NotNull BlockState state) {
			if (((!natural ? DEGRADABLE_BLOCKS.containsKey(state.getBlock()) : NATURAL_DEGRADABLE_BLOCKS.containsKey(state.getBlock())) || state.isIn(LostLegendsTags.TERMITE_BREAKABLE)) && isEdibleProperty(state)) {
				if (state.contains(Properties.DOUBLE_BLOCK_HALF) && state.get(Properties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER) {
					mutableBlockPos.move(Direction.DOWN);
				}
				return true;
			}
			return false;
		}

		public static boolean isEdibleProperty(@NotNull BlockState state) {
			return !LostLegends.getConfig().onlyEatNaturalBlocks || (state.contains(LostLegendsProperties.TERMITE_EDIBLE) ? state.get(LostLegendsProperties.TERMITE_EDIBLE) :!state.isIn(BlockTags.LEAVES) || !state.contains(Properties.PERSISTENT) || !state.get(Properties.PERSISTENT));
		}

		public static boolean exposedToAir(@NotNull World level, @NotNull BlockPos pos, boolean natural) {
			BlockPos.Mutable mutableBlockPos = pos.mutableCopy();
			for (Direction direction : Direction.values()) {
				BlockState state = level.getBlockState(mutableBlockPos.move(direction));
				if (state.isAir() || (!state.isSolidBlock(level, mutableBlockPos) && !state.isIn(LostLegendsTags.BLOCKS_TERMITE)) || ((!natural && DEGRADABLE_BLOCKS.containsKey(state.getBlock())) || (natural && NATURAL_DEGRADABLE_BLOCKS.containsKey(state.getBlock())) || state.isIn(LostLegendsTags.TERMITE_BREAKABLE)) && isEdibleProperty(state)) {
					return true;
				}
				mutableBlockPos.move(direction, -1);
			}
			return false;
		}

		public static boolean canMove(@NotNull WorldAccess level, @NotNull BlockPos pos) {
			if (level instanceof ServerWorld serverLevel) {
				return serverLevel.shouldTickBlockPos(pos);
			}
			return false;
		}

		public static boolean isBlockMovable(@NotNull BlockState state, @NotNull Direction direction) {
			if (state.isIn(LostLegendsTags.BLOCKS_TERMITE)) {
				return false;
			}
			boolean moveableUp = !(direction == Direction.UP && (state.isIn(BlockTags.INSIDE_STEP_SOUND_BLOCKS) || state.isIn(BlockTags.REPLACEABLE_BY_TREES) || state.isIn(BlockTags.FLOWERS)));
			boolean moveableDown = !(direction == Direction.DOWN && (state.isOf(Blocks.WATER) || state.isOf(Blocks.LAVA) || (state.contains(Properties.WATERLOGGED) && state.get(Properties.WATERLOGGED))));
			return moveableUp && moveableDown;
		}

		public static boolean isTooFar(boolean natural, @NotNull BlockPos mound, @NotNull BlockPos pos) {
			return !mound.isWithinDistance(pos, natural ? LostLegends.getConfig().maxNaturalDistance : LostLegends.getConfig().maxDistance);
		}

		public static void spawnGnawParticles(@NotNull World level, @NotNull BlockState eatState, @NotNull BlockPos pos, Random random) {
			if (level instanceof ServerWorld serverLevel && random.nextInt(GNAW_PARTICLE_CHANCE) == 0) {
				int count = random.nextBetween(MIN_GNAW_PARTICLES, MAX_GNAW_PARTICLES);
				if (count > 0) {
					serverLevel.spawnParticles(
						new BlockStateParticleEffect(ParticleTypes.BLOCK, eatState),
						pos.getX() + 0.5D,
						pos.getY() + 0.5D,
						pos.getZ() + 0.5D,
						count,
						0.3F,
						0.3F,
						0.3F,
						0.05D
					);
				}
			}
		}

		public static void spawnEatParticles(@NotNull World level, @NotNull BlockState eatState, @NotNull BlockPos pos, Random random) {
			if (level instanceof ServerWorld serverLevel) {
				serverLevel.spawnParticles(
					new BlockStateParticleEffect(ParticleTypes.BLOCK, eatState),
					pos.getX() + 0.5D,
					pos.getY() + 0.5D,
					pos.getZ() + 0.5D,
					random.nextBetween(MIN_EAT_PARTICLES, MAX_EAT_PARTICLES),
					0.3F,
					0.3F,
					0.3F,
					0.05D
				);
			}
		}

		@NotNull
		public BlockPos getMoundPos() {
			return this.mound;
		}

		@NotNull
		public BlockPos getPos() {
			return this.pos;
		}

		public int getBlockDestroyPower() {
			return this.blockDestroyPower;
		}

		public int getIdleTicks() {
			return this.idleTicks;
		}

		public int getUpdateTicks() {
			return this.update;
		}

		public boolean getEating() {
			return this.eating;
		}

		public int getID() {
			return this.id;
		}

		public static void addDegradableBlocks() {
			addDegradable(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG);
			addDegradable(Blocks.STRIPPED_ACACIA_LOG, LostLegendsBlocks.STRIPPED_HOLLOWED_ACACIA_LOG.get());
			addDegradable(LostLegendsBlocks.HOLLOWED_ACACIA_LOG.get(), LostLegendsBlocks.STRIPPED_HOLLOWED_ACACIA_LOG.get());
			addDegradable(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD);

			addDegradable(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);
			addDegradable(Blocks.STRIPPED_BIRCH_LOG, LostLegendsBlocks.STRIPPED_HOLLOWED_BIRCH_LOG.get());
			addDegradable(LostLegendsBlocks.HOLLOWED_BIRCH_LOG.get(), LostLegendsBlocks.STRIPPED_HOLLOWED_BIRCH_LOG.get());
			addDegradable(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD);

			addDegradable(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
			addDegradable(Blocks.STRIPPED_OAK_LOG, LostLegendsBlocks.STRIPPED_HOLLOWED_OAK_LOG.get());
			addDegradable(LostLegendsBlocks.HOLLOWED_OAK_LOG.get(), LostLegendsBlocks.STRIPPED_HOLLOWED_OAK_LOG.get());
			addDegradable(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD);

			addDegradable(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG);
			addDegradable(Blocks.STRIPPED_DARK_OAK_LOG, LostLegendsBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG.get());
			addDegradable(LostLegendsBlocks.HOLLOWED_DARK_OAK_LOG.get(), LostLegendsBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG.get());
			addDegradable(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD);

			addDegradable(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG);
			addDegradable(Blocks.STRIPPED_JUNGLE_LOG, LostLegendsBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG.get());
			addDegradable(LostLegendsBlocks.HOLLOWED_JUNGLE_LOG.get(), LostLegendsBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG.get());
			addDegradable(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD);

			addDegradable(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG);
			addDegradable(Blocks.STRIPPED_SPRUCE_LOG, LostLegendsBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG.get());
			addDegradable(LostLegendsBlocks.HOLLOWED_SPRUCE_LOG.get(), LostLegendsBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG.get());
			addDegradable(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD);

			addDegradable(Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG);
			addDegradable(Blocks.STRIPPED_MANGROVE_LOG, LostLegendsBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG.get());
			addDegradable(LostLegendsBlocks.HOLLOWED_MANGROVE_LOG.get(), LostLegendsBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG.get());
			addDegradable(Blocks.MANGROVE_WOOD, Blocks.STRIPPED_MANGROVE_WOOD);

			addDegradable(Blocks.CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG);
			addDegradable(Blocks.STRIPPED_CHERRY_LOG, LostLegendsBlocks.STRIPPED_HOLLOWED_CHERRY_LOG.get());
			addDegradable(LostLegendsBlocks.HOLLOWED_CHERRY_LOG.get(),LostLegendsBlocks.STRIPPED_HOLLOWED_CHERRY_LOG.get());
			addDegradable(Blocks.CHERRY_WOOD, Blocks.STRIPPED_CHERRY_WOOD);
		}

		public static void addDegradable(Block degradable, Block result) {
			DEGRADABLE_BLOCKS.put(degradable, result);
		}

		public static void addNaturalDegradableBlocks() {
			addNaturalDegradable(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG);
			addNaturalDegradable(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
			addNaturalDegradable(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG);
			addNaturalDegradable(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG);
			addNaturalDegradable(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG);
			addNaturalDegradable(Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG);
			addNaturalDegradable(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG);
			addNaturalDegradable(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD);
			addNaturalDegradable(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD);
			addNaturalDegradable(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD);
			addNaturalDegradable(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD);
			addNaturalDegradable(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD);
			addNaturalDegradable(Blocks.MANGROVE_WOOD, Blocks.STRIPPED_MANGROVE_WOOD);
			addNaturalDegradable(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD);
			addNaturalDegradable(Blocks.CHERRY_WOOD, Blocks.STRIPPED_CHERRY_WOOD );
			addNaturalDegradable(Blocks.CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG);
		}

		public static void addNaturalDegradable(@NotNull Block degradable, @NotNull Block result) {
			NATURAL_DEGRADABLE_BLOCKS.put(degradable, result);
		}
	}
}