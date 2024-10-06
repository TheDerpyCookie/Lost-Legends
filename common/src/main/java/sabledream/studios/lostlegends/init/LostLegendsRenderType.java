package sabledream.studios.lostlegends.init;

import sabledream.studios.lostlegends.platform.RegistryHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public final class LostLegendsRenderType
{
	public static void postInit() {
		RegistryHelper.registerRenderType(RenderLayer.getCutout(), LostLegendsBlocks.BUTTERCUP.get());
		RegistryHelper.registerRenderType(RenderLayer.getCutout(), LostLegendsBlocks.POTTED_BUTTERCUP.get());
		RegistryHelper.registerRenderType(RenderLayer.getCutout(), LostLegendsBlocks.PINKDAISY.get());
		RegistryHelper.registerRenderType(RenderLayer.getCutout(), LostLegendsBlocks.POTTED_PINKDAISY.get());
		RegistryHelper.registerRenderType(RenderLayer.getCutout(), LostLegendsBlocks.TINYCACTUS.get());
		RegistryHelper.registerRenderType(RenderLayer.getCutout(), LostLegendsBlocks.POTTED_TINYCACTUS.get());
		RegistryHelper.registerRenderType(RenderLayer.getCutout(), LostLegendsBlocks.TUMBLEWEED_PLANT.get());
		RegistryHelper.registerRenderType(RenderLayer.getCutout(), LostLegendsBlocks.POTTED_TUMBLEWEED_PLANT.get());
		RegistryHelper.registerRenderType(RenderLayer.getCutout(), LostLegendsBlocks.TUMBLEWEED.get());
	}

	private LostLegendsRenderType() {
	}
}
