package sabledream.studios.lostlegends.world.fabric.processor;

import com.mojang.serialization.MapCodec;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import sabledream.studios.lostlegends.platform.fabric.StructureEntityProcessorTypesImpl;
import sabledream.studios.lostlegends.util.world.processor.BeekeeperHutArmorStandProcessorHelper;
import sabledream.studios.lostlegends.world.processor.StructureEntityProcessor;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.ServerWorldAccess;




public final class BeekeeperHutArmorStandProcessor extends StructureEntityProcessor
{
	public static final MapCodec<BeekeeperHutArmorStandProcessor> CODEC = MapCodec.unit(BeekeeperHutArmorStandProcessor::new);

	@Override
	public StructureTemplate.StructureEntityInfo processEntity(
		ServerWorldAccess serverWorldAccess,
		BlockPos structurePiecePos,
		BlockPos structurePieceBottomCenterPos,
		StructureTemplate.StructureEntityInfo localEntityInfo,
		StructureTemplate.StructureEntityInfo globalEntityInfo,
		StructurePlacementData structurePlacementData
	) {
		return BeekeeperHutArmorStandProcessorHelper.processEntity(
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
		return StructureEntityProcessorTypesImpl.BEEKEEPER_HUT_ARMOR_STAND_PROCESSOR;
	}
}