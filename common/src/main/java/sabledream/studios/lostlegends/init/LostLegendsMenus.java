package sabledream.studios.lostlegends.init;

import com.google.common.base.Suppliers;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.platform.RegistryHelper;
import sabledream.studios.lostlegends.screen.SawmillMenu;

import java.util.function.Supplier;

import static sabledream.studios.lostlegends.LostLegends.MOD_ID;

public class LostLegendsMenus
{
	public static final Supplier<ScreenHandlerType<SawmillMenu>> SAWMILL_MENU = RegistryHelper.registerMenuType(
		LostLegends.makeID("sawmill"), SawmillMenu::new);

}
