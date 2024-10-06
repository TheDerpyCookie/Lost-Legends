package sabledream.studios.lostlegends.init;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.block.entity.BurrowBlockEntity;
import sabledream.studios.lostlegends.block.entity.RainbowBedBlockEntity;
import sabledream.studios.lostlegends.block.entity.TermiteMoundBlockEntity;
import sabledream.studios.lostlegends.events.DeferredRegistry;
import sabledream.studios.lostlegends.events.RegistrySupplier;


public class LostLegendsBlockEntity
{
	public static final DeferredRegistry<BlockEntityType<?>> TILES = DeferredRegistry.create(LostLegends.MOD_ID, Registries.BLOCK_ENTITY_TYPE.getKey());

	private static <T extends BlockEntity> BlockEntityType<T> registerTiles(BlockEntityType.BlockEntityFactory<T> tile, Block validBlock) {
		return BlockEntityType.Builder.create(tile, validBlock).build(null);
	}
	public static final RegistrySupplier<BlockEntityType<BurrowBlockEntity>>  BURROW = TILES.register("burrow", () -> registerTiles(BurrowBlockEntity::new, LostLegendsBlocks.BURROW.get()));
	public static final RegistrySupplier<BlockEntityType<RainbowBedBlockEntity>> RAINBOW_BED = TILES.register("rainbow_bed", () -> registerTiles(RainbowBedBlockEntity::new, LostLegendsBlocks.RAINBOW_BED.get()));
	public static final RegistrySupplier<BlockEntityType<TermiteMoundBlockEntity>>  TERMITE_MOUND = TILES.register("termite_mound", () -> registerTiles(TermiteMoundBlockEntity::new, LostLegendsBlocks.TERMITE_MOUND.get()));

}
