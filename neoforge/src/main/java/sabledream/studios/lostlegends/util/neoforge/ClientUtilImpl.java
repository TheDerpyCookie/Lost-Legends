package sabledream.studios.lostlegends.util.neoforge;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import sabledream.studios.lostlegends.client.render.block.RainbowBedBlockEntityRenderer;
import sabledream.studios.lostlegends.init.LostLegendsBlocks;
import sabledream.studios.lostlegends.init.LostLegendsBlockEntity;

public class ClientUtilImpl {

	public static void renderers() {
		BlockEntityRendererFactories.register(LostLegendsBlockEntity.RAINBOW_BED.get(), RainbowBedBlockEntityRenderer::new);
		RenderLayers.setRenderLayer(LostLegendsBlocks.RAINBOW_BED.get(), RenderLayer.getCutoutMipped());
	}
}
