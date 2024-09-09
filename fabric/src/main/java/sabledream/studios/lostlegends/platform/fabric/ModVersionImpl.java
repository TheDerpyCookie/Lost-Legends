package sabledream.studios.lostlegends.platform.fabric;

import sabledream.studios.lostlegends.LostLegends;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.Nullable;

public final class ModVersionImpl
{
	@Nullable
	public static String getModVersion() {
		return FabricLoader.getInstance().getModContainer(LostLegends.MOD_ID).map(modContainer -> modContainer.getMetadata().getVersion().toString()).orElse(null);
	}
}
