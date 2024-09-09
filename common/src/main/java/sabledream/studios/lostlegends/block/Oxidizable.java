package sabledream.studios.lostlegends.block;

import sabledream.studios.lostlegends.init.LostLegendsBlocks;
import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings({"rawtypes", "unchecked"})
public interface Oxidizable extends net.minecraft.block.Oxidizable
{
	Supplier<BiMap<Block, Block>> OXIDATION_LEVEL_INCREASES = Suppliers.memoize(() -> (BiMap) ImmutableBiMap.builder()
		.put(LostLegendsBlocks.COPPER_BUTTON.get(), LostLegendsBlocks.EXPOSED_COPPER_BUTTON.get())
		.put(LostLegendsBlocks.EXPOSED_COPPER_BUTTON.get(), LostLegendsBlocks.WEATHERED_COPPER_BUTTON.get())
		.put(LostLegendsBlocks.WEATHERED_COPPER_BUTTON.get(), LostLegendsBlocks.OXIDIZED_COPPER_BUTTON.get())
		.put(Blocks.LIGHTNING_ROD, LostLegendsBlocks.EXPOSED_LIGHTNING_ROD.get())
		.put(LostLegendsBlocks.EXPOSED_LIGHTNING_ROD.get(), LostLegendsBlocks.WEATHERED_LIGHTNING_ROD.get())
		.put(LostLegendsBlocks.WEATHERED_LIGHTNING_ROD.get(), LostLegendsBlocks.OXIDIZED_LIGHTNING_ROD.get())
		.build());
	Supplier<BiMap<Block, Block>> OXIDATION_LEVEL_DECREASES = Suppliers.memoize(() -> ((BiMap) OXIDATION_LEVEL_INCREASES.get()).inverse());

	static Optional<Block> getDecreasedOxidationBlock(Block block) {
		return Optional.ofNullable((Block) ((BiMap) OXIDATION_LEVEL_DECREASES.get()).get(block));
	}

	static Block getUnaffectedOxidationBlock(Block block) {
		Block block2 = block;
		for (Block block3 = (Block) ((BiMap) OXIDATION_LEVEL_DECREASES.get()).get(block); block3 != null; block3 = (Block) ((BiMap) OXIDATION_LEVEL_DECREASES.get()).get(block3)) {
			block2 = block3;
		}

		return block2;
	}

	static Optional<BlockState> getDecreasedOxidationState(BlockState state) {
		return getDecreasedOxidationBlock(state.getBlock()).map((block) -> {
			return block.getStateWithProperties(state);
		});
	}

	static Optional<Block> getIncreasedOxidationBlock(Block block) {
		return Optional.ofNullable((Block) ((BiMap) OXIDATION_LEVEL_INCREASES.get()).get(block));
	}

	static BlockState getUnaffectedOxidationState(BlockState state) {
		return getUnaffectedOxidationBlock(state.getBlock()).getStateWithProperties(state);
	}

	@Override
	default Optional<BlockState> getDegradationResult(BlockState state) {
		return getIncreasedOxidationBlock(state.getBlock()).map((block) -> {
			return block.getStateWithProperties(state);
		});
	}

	@Override
	default float getDegradationChanceMultiplier() {
		return this.getDegradationLevel() == Oxidizable.OxidationLevel.UNAFFECTED ? 0.75F:1.0F;
	}
}