package sabledream.studios.lostlegends.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.block.BurrowBlock;
//import sabledream.studios.lostlegends.block.entity.BurrowBlockEntity;
import sabledream.studios.lostlegends.platform.RegistryHelper;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static net.minecraft.block.MapColor.PALE_YELLOW;

/**
 * @see BlockEntityType
 */
public final class LostLegendsBlockEntityTypes {
	private static final Set<Block> BEEHIVE_BLOCKS = ImmutableList.of(
		LostLegendsBlocks.ACACIA_BEEHIVE.get(),
		LostLegendsBlocks.BAMBOO_BEEHIVE.get(),
		LostLegendsBlocks.BIRCH_BEEHIVE.get(),
		LostLegendsBlocks.CHERRY_BEEHIVE.get(),
		LostLegendsBlocks.CRIMSON_BEEHIVE.get(),
		LostLegendsBlocks.DARK_OAK_BEEHIVE.get(),
		LostLegendsBlocks.JUNGLE_BEEHIVE.get(),
		LostLegendsBlocks.MANGROVE_BEEHIVE.get(),
		LostLegendsBlocks.SPRUCE_BEEHIVE.get(),
		LostLegendsBlocks.WARPED_BEEHIVE.get()
	).stream().collect(ImmutableSet.toImmutableSet());

	public static void postInit() {
		Set<Block> beehiveBlocks = new HashSet<>();
		beehiveBlocks.addAll(BlockEntityType.BEEHIVE.blocks);
		beehiveBlocks.addAll(BEEHIVE_BLOCKS);
		BlockEntityType.BEEHIVE.blocks = beehiveBlocks;
	}



	private LostLegendsBlockEntityTypes() {
	}
}