package sabledream.studios.lostlegends.modcompat.fabric;

import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.config.ConfigScreenBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.gui.screen.Screen;

public final class CatalogueCompat
{
	public static Screen createConfigScreen(Screen currentScreen, ModContainer container) {
		if (FabricLoader.getInstance().isModLoaded("catalogue") == false) {
			return null;
		}

		return ConfigScreenBuilder.createConfigScreen(LostLegends.getConfig(), currentScreen);
	}
}
