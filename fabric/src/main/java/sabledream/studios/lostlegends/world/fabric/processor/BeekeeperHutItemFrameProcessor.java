package sabledream.studios.lostlegends.world.fabric.processor;

import com.mojang.serialization.MapCodec;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import sabledream.studios.lostlegends.platform.fabric.StructureEntityProcessorTypesImpl;
import sabledream.studios.lostlegends.util.world.processor.BeekeeperHutItemFrameProcessorHelper;
import sabledream.studios.lostlegends.world.processor.StructureEntityProcessor;

public final class BeekeeperHutItemFrameProcessor extends StructureEntityProcessor
{
	public static final MapCodec<BeekeeperHutItemFrameProcessor> CODEC = MapCodec.unit(BeekeeperHutItemFrameProcessor::new);

	@Override
	public StructureTemplate.StructureEntityInfo processEntity(
		ServerWorldAccess serverWorldAccess,
		BlockPos structurePiecePos,
		BlockPos structurePieceBottomCenterPos,
		StructureTemplate.StructureEntityInfo localEntityInfo,
		StructureTemplate.StructureEntityInfo globalEntityInfo,
		StructurePlacementData structurePlacementData
	) {
		return BeekeeperHutItemFrameProcessorHelper.processEntity(
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
		return StructureEntityProcessorTypesImpl.BEEKEEPER_HUT_ITEM_FRAME_PROCESSOR;
	}
}