package sabledream.studios.lostlegends.fluid;


import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

public abstract class ModFlowingFluid extends FlowableFluid
{

	@Nullable
	private final Supplier<? extends FluidBlock> block;
	private final boolean convertsToSource;

	protected ModFlowingFluid(Properties properties, Supplier<? extends FluidBlock> block) {
		this.block = block;
		this.convertsToSource = properties.canConvertToSource;
		this.afterInit(properties);
	}

	private void afterInit(ModFlowingFluid.Properties properties) {
	}

	public static Properties properties() {
		return new Properties();
	}

	protected boolean canConvertToSource(World level) {
		return convertsToSource;
	}


	protected void beforeDestroyingBlock(WorldAccess worldIn, BlockPos pos, BlockState state) {
		BlockEntity blockEntity = state.hasBlockEntity() ? worldIn.getBlockEntity(pos) : null;
		Block.dropStacks(state, worldIn, pos, blockEntity);
	}

	@Override
	protected boolean canBeReplacedWith(FluidState state, BlockView level, BlockPos pos, Fluid fluidIn, Direction direction) {
		// Based on the water implementation, may need to be overriden for mod fluids that shouldn't behave like water.
		return direction == Direction.DOWN && !isSame(fluidIn);
	}


	protected BlockState createLegacyBlock(FluidState state) {
		if (block != null)
			return block.get().getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
		return Blocks.AIR.getDefaultState();
	}

	public boolean isSame(Fluid fluidIn) {
		return fluidIn == getSource() || fluidIn == getFlowing();
	}


	public abstract Fluid getSource();

	@Override
	public abstract Fluid getFlowing();

	@Environment(EnvType.CLIENT)
	public abstract ModFluidRenderProperties createRenderProperties();

	/**
	 * This is for normal fluids. Just wraps forge stuff. I'll figure out fabric implementation
	 */
	@SuppressWarnings("All") //dont care here as this object wont even be used
	public static final class Properties {
		public String descriptionId;
		public double motionScale = 0.014D;
		public boolean canPushEntity = true;
		public boolean canSwim = true;
		public boolean canDrown = true;
		public float fallDistanceModifier = 0.5F;
		public boolean canExtinguish = false;
		public boolean supportsBoating = false;
		public boolean canConvertToSource = false;
		@Nullable
		public PathNodeType pathType = PathNodeType.WATER,
			adjacentPathType = PathNodeType.WATER_BORDER;
		public boolean canHydrate = false;
		public int lightLevel = 0,
			density = 1000,
			temperature = 300,
			viscosity = 1000;
		public Rarity rarity = Rarity.COMMON;
		public Map<String, SoundEvent> sounds;

		/**
		 * Sets the identifier representing the name of the fluid type.
		 */
		public Properties descriptionId(String descriptionId) {
			this.descriptionId = descriptionId;
			return this;
		}

		/**
		 * Sets how much the velocity of the fluid should be scaled by.
		 */
		public Properties motionScale(double motionScale) {
			this.motionScale = motionScale;
			return this;
		}

		public Properties setCanConvertToSource(boolean canConvertToSource) {
			this.canConvertToSource = canConvertToSource;
			return this;
		}

		/**
		 * Sets whether the fluid can push an entity.
		 */
		public Properties canPushEntity(boolean canPushEntity) {
			this.canPushEntity = canPushEntity;
			return this;
		}

		/**
		 * Sets whether the fluid can be swum in.
		 */
		public Properties canSwim(boolean canSwim) {
			this.canSwim = canSwim;
			return this;
		}

		/**
		 * Sets whether the fluid can drown something.
		 */
		public Properties canDrown(boolean canDrown) {
			this.canDrown = canDrown;
			return this;
		}

		/**
		 * Sets how much the fluid should scale the damage done when hitting
		 * the ground per tick.
		 */
		public Properties fallDistanceModifier(float fallDistanceModifier) {
			this.fallDistanceModifier = fallDistanceModifier;
			return this;
		}

		/**
		 * Sets whether the fluid can extinguish.
		 */
		public Properties canExtinguish(boolean canExtinguish) {
			this.canExtinguish = canExtinguish;
			return this;
		}

		/**
		 * Sets whether the fluid supports boating.
		 */
		public Properties supportsBoating(boolean supportsBoating) {
			this.supportsBoating = supportsBoating;
			return this;
		}

		/**
		 * Sets the path type of this fluid.
		 *
		 * @param pathType the path type of this fluid
		 * @return the property holder instance
		 */
		public Properties pathType(@Nullable PathNodeType pathType) {
			this.pathType = pathType;
			return this;
		}

		/**
		 * Sets the path type of the adjacent fluid. Path types with a negative
		 * malus are not traversable. Pathfinding will favor paths consisting of
		 * a lower malus.
		 *
		 * @param adjacentPathType the path type of this fluid
		 * @return the property holder instance
		 */
		public Properties adjacentPathType(@Nullable PathNodeType adjacentPathType) {
			this.adjacentPathType = adjacentPathType;
			return this;
		}

		/**
		 * Sets a sound to play when a certain action is performed. Actions id have to match forge ones. I.e: "bucket_fill"
		 */
		public Properties sound(String soundActionId, SoundEvent sound) {
			this.sounds.put(soundActionId, sound);
			return this;
		}

		/**
		 * Sets whether the fluid can hydrate.
		 *
		 * <p>Hydration is an arbitrary word which depends on the implementation.
		 */
		public Properties canHydrate(boolean canHydrate) {
			this.canHydrate = canHydrate;
			return this;
		}

		/**
		 * Sets the light level emitted by the fluid.
		 */
		public Properties lightLevel(int lightLevel) {
			this.lightLevel = lightLevel;
			return this;
		}

		/**
		 * Sets the density of the fluid.
		 */
		public Properties density(int density) {
			this.density = density;
			return this;
		}

		/**
		 * Sets the temperature of the fluid.
		 */
		public Properties temperature(int temperature) {
			this.temperature = temperature;
			return this;
		}

		/**
		 * Sets the viscosity, or thickness, of the fluid.
		 */
		public Properties viscosity(int viscosity) {
			this.viscosity = viscosity;
			return this;
		}

		/**
		 * Sets the rarity of the fluid.
		 */
		public Properties rarity(Rarity rarity) {
			this.rarity = rarity;
			return this;
		}
	}

}