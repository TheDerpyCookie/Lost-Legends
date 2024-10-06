package sabledream.studios.lostlegends.init;

import sabledream.studios.lostlegends.entity.ai.brain.CopperGolemBrain;
import sabledream.studios.lostlegends.entity.ai.brain.CrabBrain;
import sabledream.studios.lostlegends.entity.ai.brain.GlareBrain;
import sabledream.studios.lostlegends.entity.ai.brain.sensor.*;
import sabledream.studios.lostlegends.platform.RegistryHelper;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.sensor.TemptationsSensor;
import sabledream.studios.lostlegends.tag.LostLegendsTags;

import java.util.function.Supplier;

/**
 * @see net.minecraft.entity.ai.brain.sensor.SensorType
 */
public final class LostLegendsMemorySensorType
{
	public static final Supplier<SensorType<TemptationsSensor>> COPPER_GOLEM_TEMPTATIONS;
	public static final Supplier<SensorType<CopperGolemSpecificSensor>> COPPER_GOLEM_SPECIFIC_SENSOR;
	public static final Supplier<SensorType<TemptationsSensor>> GLARE_TEMPTATIONS;
	public static final Supplier<SensorType<GlareSpecificSensor>> GLARE_SPECIFIC_SENSOR;
	public static final Supplier<SensorType<TemptationsSensor>> CRAB_TEMPTATIONS;
	public static final Supplier<SensorType<BarnacleSpecificSensor>> BARNACLE_SPECIFIC_SENSOR;
	public static final Supplier<SensorType<BarnacleAttackableSensor>> BARNACLE_ATTACKABLE_SENSOR;
	public static final Supplier<SensorType<AttackablesSensor>> BARNACLE_ATTACKABLES_SENSORS;




	static {
		BARNACLE_ATTACKABLES_SENSORS = RegistryHelper.registerSensorType("barnacle_attackables_sensors", () -> new SensorType<>(() -> {
			return new AttackablesSensor(LostLegendsTags.BARNACLE_ALWAYS_HOSTILES, e -> true, 10);
		}));

		COPPER_GOLEM_TEMPTATIONS = RegistryHelper.registerSensorType("copper_golem_temptations", () -> new SensorType<>(() -> {
			return new TemptationsSensor(CopperGolemBrain.getTemptItems());
		}));
		COPPER_GOLEM_SPECIFIC_SENSOR = RegistryHelper.registerSensorType("copper_golem_specific_sensor", () -> new SensorType<>(() -> {
			return new CopperGolemSpecificSensor();
		}));
		GLARE_TEMPTATIONS = RegistryHelper.registerSensorType("glare_temptations", () -> new SensorType<>(() -> {
			return new TemptationsSensor(GlareBrain.getTemptItems());
		}));
		GLARE_SPECIFIC_SENSOR = RegistryHelper.registerSensorType("glare_specific_sensor", () -> new SensorType<>(() -> {
			return new GlareSpecificSensor();
		}));
		CRAB_TEMPTATIONS = RegistryHelper.registerSensorType("crab_temptations", () -> new SensorType<>(() -> {
			return new TemptationsSensor(CrabBrain.getTemptItems());
		}));

		BARNACLE_ATTACKABLE_SENSOR = RegistryHelper.registerSensorType("barnacle_attackable_sensor", () -> new SensorType<>(() -> {
			return new BarnacleAttackableSensor();
		}));

		BARNACLE_SPECIFIC_SENSOR = RegistryHelper.registerSensorType("barnacle_specific_sensor", () -> new SensorType<>(() -> {
			return new BarnacleSpecificSensor();
		}));


	}

	public static void init() {
	}

	private LostLegendsMemorySensorType() {
	}
}