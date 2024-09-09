package sabledream.studios.lostlegends.init;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import sabledream.studios.lostlegends.client.render.block.RainbowBedBlockEntityRenderer;

public class EntityRendererInit
{
	public static void init(){
		registerBlockEntityRenderer();
	}


	private static void registerBlockEntityRenderer() {
		BlockEntityRendererFactories.register(LostLegendsBlockEntity.RAINBOW_BED.get(), RainbowBedBlockEntityRenderer::new);
	}
}
