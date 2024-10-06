package sabledream.studios.lostlegends.entity.ai.brain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import sabledream.studios.lostlegends.entity.CrabEntity;
import sabledream.studios.lostlegends.entity.ai.brain.task.crab.*;
import sabledream.studios.lostlegends.init.LostLegendsActivities;
import sabledream.studios.lostlegends.init.LostLegendsEntityTypes;
import sabledream.studios.lostlegends.init.LostLegendsMemoryModuleTypes;
import sabledream.studios.lostlegends.init.LostLegendsMemorySensorType;
import sabledream.studios.lostlegends.tag.LostLegendsTags;

import java.util.List;

public final class CrabBrain
{
	public static final List<MemoryModuleType<?>> MEMORY_MODULES;
	public static final List<SensorType<? extends Sensor<? super CrabEntity>>> SENSORS;
	private static final UniformIntProvider WAVE_COOLDOWN_PROVIDER;

	public static Brain<?> create(Dynamic<?> dynamic) {
		Brain.Profile<CrabEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
		Brain<CrabEntity> brain = profile.deserialize(dynamic);

		addCoreActivities(brain);
		addIdleActivities(brain);
		addLayEggActivities(brain);
		addDanceActivities(brain);
		addWaveActivities(brain);

		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.resetPossibleActivities();

		return brain;
	}

	private static void addCoreActivities(Brain<CrabEntity> brain) {
		brain.setTaskList(
			Activity.CORE,
			0,
			ImmutableList.of(
				// TODO check
				//new FleeTask(2.0f),
				new LookAroundTask(45, 90),
//				new MoveToTargetTask(),
				new TemptationCooldownTask(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
				new TemptationCooldownTask(LostLegendsMemoryModuleTypes.CRAB_WAVE_COOLDOWN.get())
			)
		);
	}

	private static void addLayEggActivities(Brain<CrabEntity> brain) {
		brain.setTaskList(
			LostLegendsActivities.CRAB_LAY_EGG.get(),
			ImmutableList.of(
				Pair.of(0, new CrabGoToHomePositionTask()),
				Pair.of(1, new CrabLocateBurrowSpotTask()),
				Pair.of(2, new CrabTravelToBurrowSpotTask()),
				Pair.of(3, new CrabLayEggTask())
			),
			ImmutableSet.of(
				Pair.of(LostLegendsMemoryModuleTypes.CRAB_HAS_EGG.get(), MemoryModuleState.VALUE_PRESENT)
			)
		);
	}

	private static void addDanceActivities(Brain<CrabEntity> brain) {
		brain.setTaskList(
			LostLegendsActivities.CRAB_DANCE.get(),
			ImmutableList.of(
				Pair.of(0, new CrabDanceTask())
			),
			ImmutableSet.of(
				Pair.of(LostLegendsMemoryModuleTypes.CRAB_IS_DANCING.get(), MemoryModuleState.VALUE_PRESENT),
				Pair.of(MemoryModuleType.BREED_TARGET, MemoryModuleState.VALUE_ABSENT),
				Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryModuleState.VALUE_ABSENT),
				Pair.of(LostLegendsMemoryModuleTypes.CRAB_HAS_EGG.get(), MemoryModuleState.VALUE_ABSENT),
				Pair.of(LostLegendsMemoryModuleTypes.CRAB_BURROW_POS.get(), MemoryModuleState.VALUE_ABSENT)
			)
		);
	}

	private static void addWaveActivities(Brain<CrabEntity> brain) {
		brain.setTaskList(
			LostLegendsActivities.CRAB_WAVE.get(),
			ImmutableList.of(
				Pair.of(0, new CrabWaveTask())
			),
			ImmutableSet.of(
				Pair.of(MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleState.VALUE_PRESENT),
				Pair.of(MemoryModuleType.BREED_TARGET, MemoryModuleState.VALUE_ABSENT),
				Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryModuleState.VALUE_ABSENT),
				Pair.of(LostLegendsMemoryModuleTypes.CRAB_WAVE_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT),
				Pair.of(LostLegendsMemoryModuleTypes.CRAB_HAS_EGG.get(), MemoryModuleState.VALUE_ABSENT),
				Pair.of(LostLegendsMemoryModuleTypes.CRAB_IS_DANCING.get(), MemoryModuleState.VALUE_ABSENT),
				Pair.of(LostLegendsMemoryModuleTypes.CRAB_BURROW_POS.get(), MemoryModuleState.VALUE_ABSENT)
			)
		);
	}

	private static void addIdleActivities(Brain<CrabEntity> brain) {
		brain.setTaskList(
			Activity.IDLE,
			ImmutableList.of(
				Pair.of(0, new TemptTask(crab -> 1.25f)),
				Pair.of(1, new CrabBreedTask(LostLegendsEntityTypes.CRAB.get())),
				Pair.of(2, WalkTowardClosestAdultTask.create(UniformIntProvider.create(5, 16), 1.25f)),
				Pair.of(3, new RandomTask(
					ImmutableList.of(
						Pair.of(StrollTask.create(1.0f), 2),
						Pair.of(GoTowardsLookTargetTask.create(1.0f, 3), 2),
						Pair.of(new WaitTask(30, 60), 1)))
				)
			),
			ImmutableSet.of(
				Pair.of(LostLegendsMemoryModuleTypes.CRAB_WAVE_COOLDOWN.get(), MemoryModuleState.VALUE_PRESENT),
				Pair.of(LostLegendsMemoryModuleTypes.CRAB_HAS_EGG.get(), MemoryModuleState.VALUE_ABSENT),
				Pair.of(LostLegendsMemoryModuleTypes.CRAB_IS_DANCING.get(), MemoryModuleState.VALUE_ABSENT),
				Pair.of(LostLegendsMemoryModuleTypes.CRAB_BURROW_POS.get(), MemoryModuleState.VALUE_ABSENT)
			));
	}

	public static void updateActivities(CrabEntity crab) {
		crab.getBrain().resetPossibleActivities(
			ImmutableList.of(
				LostLegendsActivities.CRAB_LAY_EGG.get(),
				LostLegendsActivities.CRAB_DANCE.get(),
				LostLegendsActivities.CRAB_WAVE.get(),
				Activity.IDLE
			)
		);
	}

	public static void updateMemories(CrabEntity crab) {
		if (crab.hasEgg()) {
			crab.getBrain().remember(LostLegendsMemoryModuleTypes.CRAB_HAS_EGG.get(), true);
		} else {
			crab.getBrain().forget(LostLegendsMemoryModuleTypes.CRAB_HAS_EGG.get());
		}

		if (crab.isDancing() && !crab.isClimbing()) {
			crab.getBrain().remember(LostLegendsMemoryModuleTypes.CRAB_IS_DANCING.get(), true);
		} else {
			crab.getBrain().forget(LostLegendsMemoryModuleTypes.CRAB_IS_DANCING.get());
		}
	}

	public static void setWaveCooldown(CrabEntity crab) {
		crab.getBrain().remember(LostLegendsMemoryModuleTypes.CRAB_WAVE_COOLDOWN.get(), WAVE_COOLDOWN_PROVIDER.get(crab.getRandom()));
	}

	public static Ingredient getTemptItems() {
		return Ingredient.fromTag(LostLegendsTags.CRAB_TEMPT_ITEMS);
	}

	static {
		SENSORS = List.of(
			SensorType.NEAREST_LIVING_ENTITIES,
			SensorType.NEAREST_PLAYERS,
			SensorType.NEAREST_ADULT,
			LostLegendsMemorySensorType.CRAB_TEMPTATIONS.get()
		);
		MEMORY_MODULES = List.of(
			MemoryModuleType.TEMPTING_PLAYER,
			MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
			MemoryModuleType.IS_TEMPTED,
			MemoryModuleType.VISIBLE_MOBS,
			MemoryModuleType.PATH,
			MemoryModuleType.LOOK_TARGET,
			MemoryModuleType.WALK_TARGET,
			MemoryModuleType.BREED_TARGET,
			MemoryModuleType.IS_PANICKING,
			MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
			MemoryModuleType.NEAREST_VISIBLE_PLAYER,
			MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM,
			LostLegendsMemoryModuleTypes.CRAB_HAS_EGG.get(),
			LostLegendsMemoryModuleTypes.CRAB_IS_DANCING.get(),
			LostLegendsMemoryModuleTypes.CRAB_BURROW_POS.get(),
			LostLegendsMemoryModuleTypes.CRAB_WAVE_COOLDOWN.get()
		);
		WAVE_COOLDOWN_PROVIDER = TimeHelper.betweenSeconds(20, 40);
	}
}