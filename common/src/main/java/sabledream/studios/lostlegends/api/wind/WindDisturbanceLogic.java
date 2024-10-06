package sabledream.studios.lostlegends.api.wind;

import net.minecraft.entity.mob.BreezeEntity;
import net.minecraft.entity.projectile.AbstractWindChargeEntity;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.api.AdvancedMath;

import java.util.Optional;

public final class WindDisturbanceLogic<T> {
	public static final Identifier DEFAULT_ID = LostLegends.makeID("default");
	public static final Identifier BREEZE = LostLegends.makeID("breeze");
	public static final Identifier WIND_CHARGE = LostLegends.makeID("wind_charge");
	public static final WindDisturbanceLogic DUMMY_LOGIC = new WindDisturbanceLogic((source, level, windOrigin, affectedArea, windTarget) -> WindDisturbance.DUMMY_RESULT);
	private final DisturbanceLogic<T> disturbanceLogic;

	public WindDisturbanceLogic(DisturbanceLogic<T> disturbanceLogic) {
		this.disturbanceLogic = disturbanceLogic;
	}

	public DisturbanceLogic getLogic() {
		return this.disturbanceLogic;
	}

	@FunctionalInterface
	public interface DisturbanceLogic<T> {
		WindDisturbance.DisturbanceResult calculateDisturbanceResult(Optional<T> source, World level, Vec3d windOrigin, Box affectedArea, Vec3d windTarget);
	}
//
//	public static Optional<WindDisturbanceLogic> getWindDisturbanceLogic(Identifier id) {
//		if (id != null) {
//			if (FrozenRegistry.WIND_DISTURBANCE_LOGIC.containsKey(id)) {
//				WindDisturbanceLogic<?> disturbanceLogic = FrozenRegistry.WIND_DISTURBANCE_LOGIC.get(id);
//				if (disturbanceLogic != null) {
//					return Optional.of(disturbanceLogic);
//				}
//			} else if (FrozenRegistry.WIND_DISTURBANCE_LOGIC_UNSYNCED.containsKey(id)) {
//				WindDisturbanceLogic<?> disturbanceLogic = FrozenRegistry.WIND_DISTURBANCE_LOGIC_UNSYNCED.get(id);
//				if (disturbanceLogic != null) {
//					return Optional.of(disturbanceLogic);
//				}
//			}
//			LostLegends.getLogger().error("Unable to find wind disturbance logic " + id + "!");
//		}
//		return Optional.empty();
//	}

	@NotNull
	@Contract(pure = true)
	public static DisturbanceLogic<?> defaultPredicate() {
		return (source, level, windOrigin, affectedArea, windTarget) -> WindDisturbance.DUMMY_RESULT;
	}
//
//	public static void init() {
//		register(DEFAULT_ID, defaultPredicate());
//		register(BREEZE, breeze());
//		register(WIND_CHARGE, windCharge());
//	}


//	public static <T> void register(Identifier id, DisturbanceLogic<T> predicate) {
//		Registry.register(FrozenRegistry.WIND_DISTURBANCE_LOGIC, id, new WindDisturbanceLogic<>(predicate));
//	}
//
//	public static <T> void registerUnsynced(Identifier id, DisturbanceLogic<T> predicate) {
//		Registry.register(FrozenRegistry.WIND_DISTURBANCE_LOGIC_UNSYNCED, id, new WindDisturbanceLogic<>(predicate));
//	}

	public enum SourceType {
		ENTITY,
		BLOCK_ENTITY,
		NONE
	}

	private static final double WIND_RANGE_BREEZE = 6D;
	private static final double WIND_RANGE_WIND_CHARGE = 5D;

	@NotNull
	@Contract(pure = true)
	private static DisturbanceLogic<BreezeEntity> breeze() {
		return (source, level, windOrigin, affectedArea, windTarget) -> {
			if (source.isPresent()) {
				double distance = windOrigin.distanceTo(windTarget);
				if (distance <= WIND_RANGE_BREEZE) {
					Vec3d breezeLookVec = source.get().getRotationVecClient();
					Vec3d differenceInPoses = windOrigin.subtract(windTarget);
					double scaledDistance = (WIND_RANGE_BREEZE - distance) / WIND_RANGE_BREEZE;
					double strengthFromDistance = MathHelper.clamp((WIND_RANGE_BREEZE - distance) / (WIND_RANGE_BREEZE * 0.75D), 0D, 1D);
					double angleBetween = AdvancedMath.getAngleBetweenXZ(breezeLookVec, differenceInPoses);

					double x = Math.cos((angleBetween * Math.PI) / 180D);
					double z = -Math.sin((angleBetween * Math.PI) / 180D);
					x = -MathHelper.lerp(scaledDistance, (x - (differenceInPoses.x * 0.45D)) * 0.5D, x);
					z = -MathHelper.lerp(scaledDistance, (z - (differenceInPoses.z * 0.45D)) * 0.5D, z);

					Vec3d windVec = new Vec3d(x, strengthFromDistance, z).multiply(1D);
					return new WindDisturbance.DisturbanceResult(
						strengthFromDistance,
						WIND_RANGE_BREEZE - distance,
						windVec
					);
				}
			}
			return null;
		};
	}

	@NotNull
	@Contract(pure = true)
	private static DisturbanceLogic<AbstractWindChargeEntity> windCharge() {
		return (source, level, windOrigin, affectedArea, windTarget) -> {
			if (source.isPresent()) {
				double distance = windOrigin.distanceTo(windTarget);
				if (distance <= WIND_RANGE_WIND_CHARGE) {
					Vec3d chargeMovement = source.get().getVelocity();
					double strengthFromDistance = MathHelper.clamp((WIND_RANGE_WIND_CHARGE - distance) / (WIND_RANGE_WIND_CHARGE * 0.5D), 0D, 1D);
					Vec3d windVec = new Vec3d(chargeMovement.x, chargeMovement.y, chargeMovement.z).multiply(3D * strengthFromDistance);
					return new WindDisturbance.DisturbanceResult(
						strengthFromDistance,
						(WIND_RANGE_WIND_CHARGE - distance) * 2D,
						windVec
					);
				}
			}
			return null;
		};
	}
}