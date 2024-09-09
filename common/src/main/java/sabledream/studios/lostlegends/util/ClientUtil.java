package sabledream.studios.lostlegends.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.world.World;
import sabledream.studios.lostlegends.client.render.block.RainbowBedBlockEntityRenderer;

public class ClientUtil
{
	public static void doClientStuff(){
		renderers();
	}

	@ExpectPlatform
	public static void renderers(){
		throw new AssertionError();
	}

	public static void tickClient() {
		destroyTextures();
	}


	public static void destroyTextures() {
		MinecraftClient minecraft = MinecraftClient.getInstance();
		World level = minecraft.world;
		if (level == null) {
			TextureManager textureManager = minecraft.getTextureManager();


			if (!RainbowBedBlockEntityRenderer.TEXTURES.isEmpty()) {
				// Release the textures in the jar tile render cache and clear the cache
				RainbowBedBlockEntityRenderer.TEXTURES.forEach((uuid, texture) -> textureManager.destroyTexture(texture));
				RainbowBedBlockEntityRenderer.TEXTURES.clear();
			}


		}

	}
}
