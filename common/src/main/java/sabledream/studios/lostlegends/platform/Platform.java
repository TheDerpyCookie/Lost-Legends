package sabledream.studios.lostlegends.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;

public final class Platform
{
	@ExpectPlatform
	public static String getProjectSlug() {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static boolean isModLoaded(String modId) {
		throw new AssertionError();
	}
}

