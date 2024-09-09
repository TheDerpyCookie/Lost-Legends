package sabledream.studios.lostlegends.world.neoforge.processor;

import sabledream.studios.lostlegends.platform.neoforge.StructureEntityProcessorTypesImpl;
import sabledream.studios.lostlegends.util.world.processor.BeekeeperHutItemFrameProcessorHelper;
import com.mojang.serialization.MapCodec;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public final class BeekeeperHutItemFrameProcessor extends StructureProcessor
{
	public static final MapCodec<BeekeeperHutItemFrameProcessor> CODEC = MapCodec.unit(BeekeeperHutItemFrameProcessor::new);

	@Override
	public StructureTemplate.StructureEntityInfo processEntity(
		WorldView world,
		BlockPos seedPos,
		StructureTemplate.StructureEntityInfo rawEntityInfo,
		StructureTemplate.StructureEntityInfo entityInfo,
		StructurePlacementData placementSettings,
		StructureTemplate template
	) {
		return BeekeeperHutItemFrameProcessorHelper.processEntity(
			entityInfo,
			placementSettings
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