package sabledream.studios.lostlegends.modcompat.fabric;

import sabledream.studios.lostlegends.LostLegends;

import static sabledream.studios.lostlegends.modcompat.ModChecker.loadModCompat;

public final class ModCheckerImpl
{
	public static void setupPlatformModCompat() {
		String modId = "";

		try {
			modId = "trinkets";
			loadModCompat(modId, () -> new TrinketsCompat());
		} catch (Throwable e) {
			LostLegends.getLogger().error("Failed to setup compat with " + modId);
			e.printStackTrace();
		}
	}
}
