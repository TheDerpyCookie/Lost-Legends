package sabledream.studios.lostlegends.util;

import sabledream.studios.lostlegends.init.LostLegendsBlocks;
import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.function.Supplier;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class WaxableBlocksMap
{
	public static final Supplier<BiMap<Block, Block>> WAXED_TO_UNWAXED_BLOCKS = Suppliers.memoize(() -> (BiMap) ImmutableBiMap.builder()
		.put(LostLegendsBlocks.WAXED_COPPER_BUTTON.get(), LostLegendsBlocks.COPPER_BUTTON.get())
		.put(LostLegendsBlocks.WAXED_EXPOSED_COPPER_BUTTON.get(), LostLegendsBlocks.EXPOSED_COPPER_BUTTON.get())
		.put(LostLegendsBlocks.WAXED_WEATHERED_COPPER_BUTTON.get(), LostLegendsBlocks.WEATHERED_COPPER_BUTTON.get())
		.put(LostLegendsBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get(), LostLegendsBlocks.OXIDIZED_COPPER_BUTTON.get())
		.put(LostLegendsBlocks.WAXED_LIGHTNING_ROD.get(), Blocks.LIGHTNING_ROD)
		.put(LostLegendsBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get(), LostLegendsBlocks.EXPOSED_LIGHTNING_ROD.get())
		.put(LostLegendsBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get(), LostLegendsBlocks.WEATHERED_LIGHTNING_ROD.get())
		.put(LostLegendsBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get(), LostLegendsBlocks.OXIDIZED_LIGHTNING_ROD.get())
		.build());

	public static final Supplier<BiMap<Block, Block>> UNWAXED_TO_WAXED_BUTTON_BLOCKS = Suppliers.memoize(() -> (BiMap) ImmutableBiMap.builder()
		.put(LostLegendsBlocks.COPPER_BUTTON.get(), LostLegendsBlocks.WAXED_COPPER_BUTTON.get())
		.put(LostLegendsBlocks.EXPOSED_COPPER_BUTTON.get(), LostLegendsBlocks.WAXED_EXPOSED_COPPER_BUTTON.get())
		.put(LostLegendsBlocks.WEATHERED_COPPER_BUTTON.get(), LostLegendsBlocks.WAXED_WEATHERED_COPPER_BUTTON.get())
		.put(LostLegendsBlocks.OXIDIZED_COPPER_BUTTON.get(), LostLegendsBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get())
		.put(Blocks.LIGHTNING_ROD, LostLegendsBlocks.WAXED_LIGHTNING_ROD.get())
		.put(LostLegendsBlocks.EXPOSED_LIGHTNING_ROD.get(), LostLegendsBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get())
		.put(LostLegendsBlocks.WEATHERED_LIGHTNING_ROD.get(), LostLegendsBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get())
		.put(LostLegendsBlocks.OXIDIZED_LIGHTNING_ROD.get(), LostLegendsBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get())
		.build());
}