package sabledream.studios.lostlegends.modcompat.neoforge;

import sabledream.studios.lostlegends.LostLegends;

import static sabledream.studios.lostlegends.modcompat.ModChecker.loadModCompat;

public class ModCheckerImpl
{
	public static void setupPlatformModCompat() {
		String modId = "";

		try {
			modId = "curios";
			loadModCompat(modId, () -> new CuriosCompat());
		} catch (Throwable e) {
			LostLegends.getLogger().error("Failed to setup compat with " + modId);
			e.printStackTrace();
		}
	}
}
