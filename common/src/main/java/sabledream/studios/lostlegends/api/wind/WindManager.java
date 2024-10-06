package sabledream.studios.lostlegends.api.wind;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.LightType;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class WindManager
{
	private static final long MIN_TIME_VALUE = Long.MIN_VALUE + 1;
	public static final Map<Function<WindManager, WindManagerExtension>, Integer> EXTENSION_PROVIDERS = new Object2ObjectOpenHashMap<>();
	private final List<WindDisturbance<?>> windDisturbancesA = new ArrayList<>();
	private final List<WindDisturbance<?>> windDisturbancesB = new ArrayList<>();
	private boolean isSwitchedServer;
	public final List<WindManagerExtension> attachedExtensions;

	public boolean overrideWind;
	public long time;
	public Vec3d commandWind = Vec3d.ZERO;
	public double windX;
	public double windY;
	public double windZ;
	public double laggedWindX;
	public double laggedWindY;
	public double laggedWindZ;
	public long seed;
	private boolean seedSet = false;

	private final ServerWorld level;
	public PerlinNoiseSampler noise;

	@SuppressWarnings("unchecked")
	public WindManager(@NotNull ServerWorld level) {
		this.level = level;
		this.noise = EasyNoiseSampler.createXoroNoise(this.seed);
		List<WindManagerExtension> extensions = new ObjectArrayList<>();
		Map.Entry<Function<WindManager, WindManagerExtension>, Integer>[] extensionProviders = EXTENSION_PROVIDERS.entrySet().toArray(new Map.Entry[0]);
		Arrays.sort(extensionProviders, Map.Entry.comparingByValue());

		for (Map.Entry<Function<WindManager, WindManagerExtension>, Integer> extensionFunc : extensionProviders) {
			var extension = extensionFunc.getKey().apply(this);
			extensions.add(extension);
		}
		this.attachedExtensions = extensions;
	}

	public static void addExtension(Function<WindManager, WindManagerExtension> extension, int priority) {
		if (extension != null) EXTENSION_PROVIDERS.put(extension, priority);
	}

	public static void addExtension(Function<WindManager, WindManagerExtension> extension) {
		addExtension(extension, 1000);
	}



	private List<WindDisturbance<?>> getWindDisturbances() {
		return !this.isSwitchedServer ? this.windDisturbancesA : this.windDisturbancesB;
	}

	private List<WindDisturbance<?>> getWindDisturbanceStash() {
		return this.isSwitchedServer ? this.windDisturbancesA : this.windDisturbancesB;
	}

	public void clearWindDisturbances() {
		this.getWindDisturbances().clear();
	}

	public void clearAllWindDisturbances() {
		this.getWindDisturbances().clear();
		this.getWindDisturbanceStash().clear();
	}

	public void clearAndSwitchWindDisturbances() {
		this.clearWindDisturbances();
		this.isSwitchedServer = !this.isSwitchedServer;
	}

	@NotNull
	public static WindManager getWindManager(@NotNull ServerWorld level) {
		return ((WindManagerInterface)level).LostLegends$getWindManager();
	}

	@NotNull
	public PersistentState.Type<WindStorage> createData() {
		return new PersistentState.Type<>(
			() -> new WindStorage(this),
			(tag, provider) -> WindStorage.load(tag, this),
			DataFixTypes.SAVED_DATA_RANDOM_SEQUENCES
		);
	}

	public void tick(@NotNull ServerWorld level) {
		if (!this.seedSet) {
			this.seedSet = true;
			this.seed = level.getSeed();
			this.noise = EasyNoiseSampler.createXoroNoise(this.seed);
		}
		if (level.getTickManager().shouldTick()) {
			this.runResetsIfNeeded();

			this.time += 1;
			//WIND
			float thunderLevel = this.level.getThunderGradient(1F) * 0.03F;
			double calcTime = this.time * 0.0005;
			double calcTimeY = this.time * 0.00035;
			Vec3d vec3 = sampleVec3(calcTime, calcTimeY, calcTime);
			this.windX = vec3.x + (vec3.x * thunderLevel);
			this.windY = vec3.y + (vec3.y * thunderLevel);
			this.windZ = vec3.z + (vec3.z * thunderLevel);
			//LAGGED WIND
			double calcLaggedTime = (this.time - 40) * 0.0005;
			double calcLaggedTimeY = (this.time - 60) * 0.00035;
			Vec3d laggedVec = sampleVec3(calcLaggedTime, calcLaggedTimeY, calcLaggedTime);
			this.laggedWindX = laggedVec.x + (laggedVec.x * thunderLevel);
			this.laggedWindY = laggedVec.y + (laggedVec.y * thunderLevel);
			this.laggedWindZ = laggedVec.z + (laggedVec.z * thunderLevel);

			//EXTENSIONS
			for (WindManagerExtension extension : this.attachedExtensions) {
				extension.baseTick(level);
				extension.tick(level);
			}
		}
	}

	//Reset values in case of potential overflow
	private boolean runResetsIfNeeded() {
		boolean needsReset = false;
		if (Math.abs(this.time) == Long.MAX_VALUE) {
			needsReset = true;
			this.time = MIN_TIME_VALUE;
		}
		if (Math.abs(this.windX) == Double.MAX_VALUE) {
			needsReset = true;
			this.windX = 0;
		}
		if (Math.abs(this.windY) == Double.MAX_VALUE) {
			needsReset = true;
			this.windY = 0;
		}
		if (Math.abs(this.windZ) == Double.MAX_VALUE) {
			needsReset = true;
			this.windZ = 0;
		}
		if (Math.abs(this.laggedWindX) == Double.MAX_VALUE) {
			needsReset = true;
			this.laggedWindX = 0;
		}
		if (Math.abs(this.laggedWindY) == Double.MAX_VALUE) {
			needsReset = true;
			this.laggedWindY = 0;
		}
		if (Math.abs(this.laggedWindZ) == Double.MAX_VALUE) {
			needsReset = true;
			this.laggedWindZ = 0;
		}

		//EXTENSIONS
		for (WindManagerExtension extension : this.attachedExtensions) {
			if (extension.runResetsIfNeeded()) {
				needsReset = true;
			}
		}
		return needsReset;
	}

	@NotNull
	public WindSyncPacket createSyncPacket() {
		return new WindSyncPacket(
			this.time,
			this.seed,
			this.overrideWind,
			this.commandWind
		);
	}

	@NotNull
	public Vec3d getWindMovement(@NotNull BlockPos pos) {
		return this.getWindMovement(Vec3d.ofBottomCenter(pos));
	}

	@NotNull
	public Vec3d getWindMovement(@NotNull BlockPos pos, double scale) {
		return this.getWindMovement(Vec3d.ofBottomCenter(pos), scale);
	}

	@NotNull
	public Vec3d getWindMovement(@NotNull BlockPos pos, double scale, double clamp) {
		return this.getWindMovement(Vec3d.ofBottomCenter(pos), scale, clamp);
	}

	@NotNull
	public Vec3d getWindMovement(@NotNull Vec3d pos) {
		return this.getWindMovement(pos, 1D);
	}

	@NotNull
	public Vec3d getWindMovement(@NotNull Vec3d pos, double scale) {
		return this.getWindMovement(pos, scale, Double.MAX_VALUE);
	}

	@NotNull
	public Vec3d getWindMovement(@NotNull Vec3d pos, double scale, double clamp) {
		return this.getWindMovement(pos, scale, clamp, 1D);
	}

	@NotNull
	public Vec3d getWindMovement(@NotNull Vec3d pos, double scale, double clamp, double windDisturbanceScale) {
		double brightness = this.level.getLightLevel(LightType.SKY, BlockPos.ofFloored(pos));
		double windScale = (Math.max((brightness - (Math.max(15 - brightness, 0))), 0) * 0.0667D);
		Pair<Double, Vec3d> disturbance = this.calculateWindDisturbance(level, pos);
		double disturbanceAmount = disturbance.getFirst();
		Vec3d windDisturbance = disturbance.getSecond();
		double windX = MathHelper.lerp(disturbanceAmount, this.windX * windScale, windDisturbance.x * windDisturbanceScale) * scale;
		double windY = MathHelper.lerp(disturbanceAmount, this.windY * windScale, windDisturbance.y * windDisturbanceScale) * scale;
		double windZ = MathHelper.lerp(disturbanceAmount, this.windZ * windScale, windDisturbance.z * windDisturbanceScale) * scale;
		return new Vec3d(
			MathHelper.clamp(windX, -clamp, clamp),
			MathHelper.clamp(windY, -clamp, clamp),
			MathHelper.clamp(windZ, -clamp, clamp)
		);
	}

	@NotNull
	public Vec3d getWindMovement3D(@NotNull BlockPos pos, double stretch) {
		return this.getWindMovement3D(Vec3d.ofBottomCenter(pos), stretch);
	}

	@NotNull
	public Vec3d getWindMovement3D(@NotNull BlockPos pos, double scale, double stretch) {
		return this.getWindMovement3D(Vec3d.ofBottomCenter(pos), scale, stretch);
	}

	@NotNull
	public Vec3d getWindMovement3D(@NotNull BlockPos pos, double scale, double clamp, double stretch) {
		return this.getWindMovement3D(Vec3d.ofBottomCenter(pos), scale, clamp, stretch);
	}

	@NotNull
	public Vec3d getWindMovement3D(@NotNull Vec3d pos, double stretch) {
		return this.getWindMovement3D(pos, 1D, stretch);
	}

	@NotNull
	public Vec3d getWindMovement3D(@NotNull Vec3d pos, double scale, double stretch) {
		return this.getWindMovement3D(pos, scale, Double.MAX_VALUE, stretch);
	}

	@NotNull
	public Vec3d getWindMovement3D(@NotNull Vec3d pos, double scale, double clamp, double stretch) {
		Vec3d wind = this.sample3D(pos, stretch);
		return new Vec3d(MathHelper.clamp((wind.getX()) * scale, -clamp, clamp),
			MathHelper.clamp((wind.getY()) * scale, -clamp, clamp),
			MathHelper.clamp((wind.getZ()) * scale, -clamp, clamp));
	}

	@NotNull
	private Vec3d sampleVec3(double x, double y, double z) {
		if (!this.overrideWind) {
			double windX = this.noise.sample(x, 0D, 0D);
			double windY = this.noise.sample(0D, y, 0D);
			double windZ = this.noise.sample(0D, 0D, z);
			return new Vec3d(windX, windY, windZ);
		}
		return this.commandWind;
	}

	@NotNull
	private Vec3d sample3D(@NotNull Vec3d pos, double stretch) {
		double sampledTime = this.time * 0.1D;
		double xyz = pos.getX() + pos.getY() + pos.getZ();
		double windX = this.noise.sample((xyz + sampledTime) * stretch, 0D, 0D);
		double windY = this.noise.sample(0D, (xyz + sampledTime) * stretch, 0D);
		double windZ = this.noise.sample(0D, 0D, (xyz + sampledTime) * stretch);
		return new Vec3d(windX, windY, windZ);
	}

	@NotNull
	private Pair<Double, Vec3d> calculateWindDisturbance(@NotNull World level, @NotNull Vec3d pos) {
		return calculateWindDisturbance(this.getWindDisturbances(), level, pos);
	}

	@NotNull
	public static Pair<Double, Vec3d> calculateWindDisturbance(@NotNull List<WindDisturbance<?>> windDisturbances, @NotNull World level, @NotNull Vec3d pos) {
		ArrayList<Pair<Double, Vec3d>> winds = new ArrayList<>();
		double strength = 0D;
		for (WindDisturbance<?> windDisturbance : windDisturbances) {
			WindDisturbance.DisturbanceResult disturbanceResult = windDisturbance.calculateDisturbanceResult(level, pos);
			if (disturbanceResult.strength() != 0D && disturbanceResult.weight() != 0D) {
				strength = Math.max(strength, disturbanceResult.strength());
				winds.add(Pair.of(disturbanceResult.weight(), disturbanceResult.wind()));
			}
		}

		double finalX = 0D;
		double finalY = 0D;
		double finalZ = 0D;
		if (!winds.isEmpty()) {
			double x = 0D;
			double y = 0D;
			double z = 0D;
			double sumOfWeights = 0D;
			for (Pair<Double, Vec3d> pair : winds) {
				double weight = pair.getFirst();
				sumOfWeights += weight;
				Vec3d windVec = pair.getSecond();
				x += weight * windVec.x;
				y += weight * windVec.y;
				z += weight * windVec.z;
			}
			finalX = x / sumOfWeights;
			finalY = y / sumOfWeights;
			finalZ = z / sumOfWeights;
		}

		return Pair.of(strength, new Vec3d(finalX, finalY, finalZ));
	}
}