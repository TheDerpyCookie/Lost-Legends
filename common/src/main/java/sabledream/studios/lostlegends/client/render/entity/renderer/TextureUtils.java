package sabledream.studios.lostlegends.client.render.entity.renderer;

import com.mojang.blaze3d.platform.TextureUtil;
import net.minecraft.util.Identifier;
import sabledream.studios.lostlegends.util.Utils;

import java.text.MessageFormat;

public class TextureUtils
{
	private TextureUtils(){
	}

	public static Identifier getTextureIdentifier(String entityType, String registryName) {
		String resourceTexture = MessageFormat.format("textures/entity/{0}/{1}/{1}.png", entityType, registryName);

		return Utils.modResourceLocationOf(resourceTexture);
	}

	public static Identifier getTextureIdentifier(String entityType, String registryName, String modifier) {
		String resourceTexture = MessageFormat.format("textures/entity/{0}/{1}/{1}_{2}.png", entityType, registryName, modifier);

		return Utils.modResourceLocationOf(resourceTexture);
	}

}
