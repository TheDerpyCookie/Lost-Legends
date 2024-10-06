package sabledream.studios.lostlegends.item.api.axe;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class AxeBehaviors {
	private static final Map<Block, AxeBehavior> AXE_BEHAVIORS = new Object2ObjectOpenHashMap<>();

	public static void register(Block block, AxeBehavior axeBehavior) {
		AXE_BEHAVIORS.put(block, axeBehavior);
	}

	@Nullable
	public static AxeBehavior get(Block block) {
		return AXE_BEHAVIORS.getOrDefault(block, null);
	}

	public interface AxeBehavior {
		boolean meetsRequirements(WorldView level, BlockPos pos, Direction direction, BlockState state);

		BlockState getOutputBlockState(BlockState state);

		void onSuccess(World level, BlockPos pos, Direction direction, BlockState state, BlockState oldState);
	}
}