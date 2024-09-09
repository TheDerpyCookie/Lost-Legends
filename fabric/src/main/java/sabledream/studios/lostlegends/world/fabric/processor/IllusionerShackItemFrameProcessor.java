package sabledream.studios.lostlegends.world.fabric.processor;

import sabledream.studios.lostlegends.platform.fabric.StructureEntityProcessorTypesImpl;
import sabledream.studios.lostlegends.util.world.processor.IllusionerShackItemFrameProcessorHelper;
import sabledream.studios.lostlegends.world.processor.StructureEntityProcessor;
import com.mojang.serialization.MapCodec;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplate.StructureEntityInfo;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

/**
 * Inspired by use in Better Strongholds mod
 *
 * @author YUNGNICKYOUNG
 * <a href="https://github.com/YUNG-GANG/YUNGs-Better-Strongholds">https://github.com/YUNG-GANG/YUNGs-Better-Strongholds</a>
 */
public final class IllusionerShackItemFrameProcessor extends StructureEntityProcessor
{
	public static final MapCodec<IllusionerShackItemFrameProcessor> CODEC = MapCodec.unit(IllusionerShackItemFrameProcessor::new);

	private IllusionerShackItemFrameProcessor() {
	}

	@Override
	public StructureEntityInfo processEntity(
		ServerWorldAccess serverWorldAccess,
		BlockPos structurePiecePos,
		BlockPos structurePieceBottomCenterPos,
		StructureEntityInfo localEntityInfo,
		StructureEntityInfo globalEntityInfo,
		StructurePlacementData structurePlacementData
	) {
		return IllusionerShackItemFrameProcessorHelper.processEntity(
			globalEntityInfo,
			structurePlacementData
		);
	}

	@Nullable
	@Override
	public StructureTemplate.StructureBlockInfo process(
		WorldView world,
		BlockPos pos,
		BlockPos pivot,
		StructureTemplate.StructureBlockInfo localEntityInfo,
		StructureTemplate.StructureBlockInfo globalEntityInfo,
		StructurePlacementData data
	) {
		return globalEntityInfo;
	}

	@Override
	protected StructureProcessorType<?> getType() {
		return StructureEntityProcessorTypesImpl.ILLUSIONER_SHACK_ITEM_FRAME_PROCESSOR;
	}
}
