package sabledream.studios.lostlegends.fabric;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.passive.GlowSquidEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import org.ladysnake.satin.api.event.ShaderEffectRenderCallback;
import org.ladysnake.satin.api.managed.ManagedShaderEffect;
import org.ladysnake.satin.api.managed.ShaderEffectManager;
import org.ladysnake.satin.api.managed.uniform.Uniform1f;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.client.RenderedHypnoEntities;
import sabledream.studios.lostlegends.events.fabric.FabricReloadListener;
import sabledream.studios.lostlegends.events.lifecycle.AddSpawnBiomeModificationsEvent;
import sabledream.studios.lostlegends.events.lifecycle.DatapackSyncEvent;
import sabledream.studios.lostlegends.events.lifecycle.RegisterReloadListenerEvent;
import sabledream.studios.lostlegends.events.lifecycle.SetupEvent;
import sabledream.studios.lostlegends.init.LostLegendsPointOfInterestTypes;
import sabledream.studios.lostlegends.init.LostLegendsStructurePoolElements;
import sabledream.studios.lostlegends.util.ServerWorldSpawnersUtil;
import sabledream.studios.lostlegends.world.spawner.IceologerSpawner;
import sabledream.studios.lostlegends.world.spawner.IllusionerSpawner;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.resource.ResourceType;
import net.minecraft.world.dimension.DimensionTypes;

import java.util.Iterator;

public final class LostLegendsFabric implements ModInitializer
{
	private static final ManagedShaderEffect HYPNO_SHADER = ShaderEffectManager.getInstance().manage(Identifier.of("lostlegends", "shaders/post/hypnotize.json"));
	private static final Uniform1f intensityHypno;
	private static final Uniform1f sTimeHypno;
	private static final Uniform1f rainbowHypno;

	@Override
	public void onInitialize() {
		LostLegends.init();
		LostLegends.postInit();
		LostLegendsPointOfInterestTypes.postInit();

		addCustomStructurePoolElements();
		initEvents();

		ShaderEffectRenderCallback.EVENT.register((tickDelta) -> {
			if (!RenderedHypnoEntities.GLOWSQUIDS.isEmpty() || RenderedHypnoEntities.lockedIntensityTimer > 0 || RenderedHypnoEntities.lookIntensity > 0.0) {
				double bestLookIntensity = 0.0;
				GlowSquidEntity bestSquid = null;
				Iterator var4 = RenderedHypnoEntities.GLOWSQUIDS.iterator();

				Vec3d vec3d;
				double d;
				while (var4.hasNext()) {
					GlowSquidEntity glowsquid = (GlowSquidEntity) var4.next();
					vec3d = glowsquid.getPos().subtract(MinecraftClient.getInstance().player.getPos()).normalize();
					d = vec3d.dotProduct(MinecraftClient.getInstance().player.getRotationVec(tickDelta));
					if (d > bestLookIntensity) {
						bestLookIntensity = d;
						bestSquid = glowsquid;
					}
				}

				RenderedHypnoEntities.lookIntensityGoal = bestLookIntensity;
				if (!MinecraftClient.getInstance().isPaused() && RenderedHypnoEntities.lockedIntensityTimer >= 0) {
					RenderedHypnoEntities.lookIntensityGoal = 1.0;
					RenderedHypnoEntities.lookIntensity += 0.0010000000474974513;
					--RenderedHypnoEntities.lockedIntensityTimer;
				}

				if (RenderedHypnoEntities.lookIntensity < RenderedHypnoEntities.lookIntensityGoal) {
					RenderedHypnoEntities.lookIntensity += 5.000000237487257E-4;
				} else {
					RenderedHypnoEntities.lookIntensity -= 0.0010000000474974513;
				}

				RenderedHypnoEntities.lookIntensity = MathHelper.clamp(RenderedHypnoEntities.lookIntensity, 0.0, 0.5);
				if (bestSquid != null) {
					if (bestSquid.hasCustomName() && bestSquid.getCustomName().getString().equals("jeb_")) {
						rainbowHypno.set(1.0F);
					} else {
						rainbowHypno.set(0.0F);
					}
				}

				intensityHypno.set((float) Math.max(0.0, RenderedHypnoEntities.lookIntensity));
				sTimeHypno.set(((float) MinecraftClient.getInstance().world.getTime() + tickDelta) / 20.0F);
				HYPNO_SHADER.render(tickDelta);
				if (MinecraftClient.getInstance().player.age % 20 == 0) {
					float var10002 = (float) RenderedHypnoEntities.lookIntensity;
					MinecraftClient.getInstance().player.playSound(SoundEvents.ENTITY_GLOW_SQUID_AMBIENT, var10002, (float) RenderedHypnoEntities.lookIntensity);
				}

				if (bestSquid != null && !MinecraftClient.getInstance().isPaused()) {
					ClientPlayerEntity player = MinecraftClient.getInstance().player;
					Vec3d target = bestSquid.getPos();
					vec3d = player.getPos();
					d = target.x - vec3d.x;
					double e = target.y - vec3d.y - 1.0;
					double f = target.z - vec3d.z;
					double g = Math.sqrt(d * d + f * f);
					float currentPitch = MathHelper.wrapDegrees(player.getPitch(tickDelta));
					float currentYaw = MathHelper.wrapDegrees(player.getYaw(tickDelta));
					float desiredPitch = MathHelper.wrapDegrees((float) (-(MathHelper.atan2(e, g) * 57.2957763671875)));
					float desiredYaw = MathHelper.wrapDegrees((float) (MathHelper.atan2(f, d) * 57.2957763671875) - 90.0F);
					Vec2f rotationChange = new Vec2f(MathHelper.wrapDegrees(desiredPitch - currentPitch), MathHelper.wrapDegrees(desiredYaw - currentYaw));
					Vec2f rotationStep = rotationChange.normalize().multiply((float) RenderedHypnoEntities.lookIntensity * 10.0F * (MathHelper.clamp(rotationChange.length(), 0.0F, 10.0F) / 10.0F));
					player.setPitch(player.getPitch(tickDelta) + rotationStep.x);
					player.setYaw(player.getYaw(tickDelta) + rotationStep.y);
				}

				RenderedHypnoEntities.GLOWSQUIDS.clear();
			}

		});
	}
	static {
		intensityHypno = HYPNO_SHADER.findUniform1f("Intensity");
		sTimeHypno = HYPNO_SHADER.findUniform1f("STime");
		rainbowHypno = HYPNO_SHADER.findUniform1f("Rainbow");
	}

	private static void initEvents() {
		SetupEvent.EVENT.invoke(new SetupEvent(Runnable::run));

		RegisterReloadListenerEvent.EVENT.invoke(new RegisterReloadListenerEvent((id, listener) -> {
			ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new FabricReloadListener(id, listener));
		}));

		ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) ->
			DatapackSyncEvent.EVENT.invoke(new DatapackSyncEvent(player)));

		AddSpawnBiomeModificationsEvent.EVENT.invoke(new AddSpawnBiomeModificationsEvent((tag, spawnGroup, entityType, weight, minGroupSize, maxGroupSize) -> {
			BiomeModifications.addSpawn(biomeSelector -> biomeSelector.hasTag(tag) && biomeSelector.hasTag(BiomeTags.IS_OVERWORLD), spawnGroup, entityType, weight, minGroupSize, maxGroupSize);
		}));

		ServerWorldEvents.LOAD.register(((server, world) -> {
			if (world.isClient() || world.getDimensionEntry() != DimensionTypes.OVERWORLD) {
				return;
			}

			ServerWorldSpawnersUtil.register(world, new IceologerSpawner());
			ServerWorldSpawnersUtil.register(world, new IllusionerSpawner());
		}));
	}

	private static void addCustomStructurePoolElements() {
		ServerLifecycleEvents.SERVER_STARTING.register(LostLegendsStructurePoolElements::init);
	}
}
