package sabledream.studios.lostlegends.client;

import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.passive.GlowSquidEntity;

import java.util.HashSet;
import java.util.Set;

public class RenderedHypnoEntities {
	public static Set<GlowSquidEntity> GLOWSQUIDS = new HashSet();
	public static Set<AxolotlEntity> AXOLOTLS = new HashSet();
	public static double lookIntensity = 0.0;
	public static double lookIntensityGoal = 0.0;
	public static int lockedIntensityTimer = 0;
	public RenderedHypnoEntities() {
	}
}