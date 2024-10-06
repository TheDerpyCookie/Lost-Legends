package sabledream.studios.lostlegends.mixin;

import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Sensor.class)
public interface SensorAccessor {
	@Accessor("RANDOM")
	static Random getRandom() {
		return null;
	}

	@Mutable
	@Accessor("senseInterval")
	void setScanRate(int scanRate);

	@Accessor("lastSenseTime")
	void setTimeToTick(long timeToTick);
}