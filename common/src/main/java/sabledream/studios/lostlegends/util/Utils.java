package sabledream.studios.lostlegends.util;

import net.minecraft.util.Identifier;
import sabledream.studios.lostlegends.LostLegends;

public class Utils
{
	private Utils(){
		throw new IllegalStateException("Util Class");
	}


	public static Identifier modResourceLocationOf(String registryName) {
		return Identifier.of(LostLegends.MOD_ID, registryName);
	}
}
