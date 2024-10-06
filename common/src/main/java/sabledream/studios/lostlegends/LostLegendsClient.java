package sabledream.studios.lostlegends;

import sabledream.studios.lostlegends.init.LostLegendsEntityRenderer;
import sabledream.studios.lostlegends.init.LostLegendsEntityModelLayer;
import sabledream.studios.lostlegends.init.LostLegendsEntityRendererInit;
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
		LostLegendsEntityRendererInit.init();
	}

	private static boolean hasManyRecipes = false;


	public static boolean hasManyRecipes() {
		return hasManyRecipes;
	}



}