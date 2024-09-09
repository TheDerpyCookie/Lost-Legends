package sabledream.studios.lostlegends.platform.neoforge;

import sabledream.studios.lostlegends.LostLegends;
import net.neoforged.fml.ModList;
import org.jetbrains.annotations.Nullable;

public final class ModVersionImpl
{
	@Nullable
	public static String getModVersion() {
		return ModList.get().getModContainerById(LostLegends.MOD_ID).map(modContainer -> modContainer.getModInfo().getVersion().toString()).orElse(null);
	}
}
