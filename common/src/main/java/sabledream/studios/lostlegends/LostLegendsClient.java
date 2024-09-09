package sabledream.studios.lostlegends;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.passive.GlowSquidEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import sabledream.studios.lostlegends.init.EntityRendererInit;
import sabledream.studios.lostlegends.init.LostLegendsEntityRenderer;
import sabledream.studios.lostlegends.init.LostLegendsEntityModelLayer;
import sabledream.studios.lostlegends.init.LostLegendsRenderType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;


public final class LostLegendsClient
{
	@Environment(EnvType.CLIENT)
	public static void init() {
		LostLegendsEntityModelLayer.init();
	}

	@Environment(EnvType.CLIENT)
	public static void postInit() {
		LostLegendsEntityRenderer.postInit();
		LostLegendsRenderType.postInit();
		EntityRendererInit.init();
	}
}