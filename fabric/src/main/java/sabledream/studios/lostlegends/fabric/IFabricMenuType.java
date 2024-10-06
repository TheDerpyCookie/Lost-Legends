package sabledream.studios.lostlegends.fabric;


import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public interface IFabricMenuType<T> {
	static <T extends ScreenHandler> ScreenHandlerType<T> create(Factory<T> factory) {
		return new ScreenHandlerType<>(factory,  FeatureFlags.DEFAULT_ENABLED_FEATURES);
	}


	T create(int i, PlayerInventory arg, PacketByteBuf arg2);


	interface Factory<T extends ScreenHandler> extends ScreenHandlerType.Factory<T> {
		T create(int i, PlayerInventory inventory, PacketByteBuf buffer);

		default T create(int i, PlayerInventory inventory) {
			return this.create(i, inventory, null);
		}
	}

}