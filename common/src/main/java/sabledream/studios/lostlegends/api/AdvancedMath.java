package sabledream.studios.lostlegends.api;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;

public class AdvancedMath
{
	public static Random random() {
		return Random.create();
	}


	public static double getAngleBetweenXZ(@NotNull Vec3d posA, @NotNull Vec3d posB) {
		double angle = Math.atan2(posA.x - posB.x, posA.z - posB.z);
		return (360D + (angle * MathHelper.DEGREES_PER_RADIAN)) % 360D;
	}


}
