package sabledream.studios.lostlegends.modcompat.neoforge;

import sabledream.studios.lostlegends.modcompat.ModCompat;

import java.util.EnumSet;
/*
import sabledream.studios.lostlegends.events.lifecycle.ClientSetupEvent;
import sabledream.studios.lostlegends.init.lostlegendsItems;
import sabledream.studios.lostlegends.modcompat.neoforge.curios.CuriosTotemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.neoforged.fml.InterModComms;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.util.function.Predicate;
 */

@SuppressWarnings({"all", "removal"})
public final class CuriosCompat implements ModCompat
{
	public CuriosCompat() {
		//InterModComms.sendTo("curios", top.theillusivec4.curios.api.SlotTypeMessage.REGISTER_TYPE, () -> top.theillusivec4.curios.api.SlotTypePreset.CHARM.getMessageBuilder().build());
		//ClientSetupEvent.EVENT.addListener(CuriosCompat::registerRenderers);
	}

	@Override
	public EnumSet<Type> compatTypes() {
		return EnumSet.of(Type.CUSTOM_EQUIPMENT_SLOTS);
	}

	/*
	private static void registerRenderers(final ClientSetupEvent clientSetupEvent) {
		CuriosRendererRegistry.register(Items.TOTEM_OF_UNDYING, CuriosTotemRenderer::new);
		CuriosRendererRegistry.register(lostlegendsItems.TOTEM_OF_FREEZING.get(), CuriosTotemRenderer::new);
		CuriosRendererRegistry.register(lostlegendsItems.TOTEM_OF_ILLUSION.get(), CuriosTotemRenderer::new);
	}

	@Override
	@Nullable
	public ItemStack getEquippedItemFromCustomSlots(Entity entity, Predicate<ItemStack> itemStackPredicate) {
		if (entity instanceof PlayerEntity player) {
			return CuriosApi.getCuriosInventory(player).map(i -> i.findFirstCurio(itemStackPredicate).map(SlotResult::stack).orElse(null)).orElse(null);
		}

		return null;
	}*/
}
