package sabledream.studios.lostlegends.modcompat.fabric;

import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.config.ConfigScreenBuilder;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.loader.api.FabricLoader;

public final class ModMenuCompat implements ModMenuApi
{
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return (parent) -> {
			if (FabricLoader.getInstance().isModLoaded("cloth-config")) {
				return ConfigScreenBuilder.createConfigScreen(LostLegends.getConfig(), parent);
			}

			return null;
		};
	}
}