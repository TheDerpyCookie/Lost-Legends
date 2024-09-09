package sabledream.studios.lostlegends.platform.neoforge;

import sabledream.studios.lostlegends.platform.RegistryHelper;
import sabledream.studios.lostlegends.world.neoforge.processor.BeekeeperHutArmorStandProcessor;
import sabledream.studios.lostlegends.world.neoforge.processor.BeekeeperHutItemFrameProcessor;
import sabledream.studios.lostlegends.world.neoforge.processor.IceologerCabinArmorStandProcessor;
import sabledream.studios.lostlegends.world.neoforge.processor.IllusionerShackItemFrameProcessor;
import net.minecraft.structure.processor.StructureProcessorType;

public final class StructureEntityProcessorTypesImpl
{
	public static StructureProcessorType<IceologerCabinArmorStandProcessor> ICEOLOGER_CABIN_ARMOR_STAND_PROCESSOR = () -> IceologerCabinArmorStandProcessor.CODEC;
	public static StructureProcessorType<IllusionerShackItemFrameProcessor> ILLUSIONER_SHACK_ITEM_FRAME_PROCESSOR = () -> IllusionerShackItemFrameProcessor.CODEC;
	public static StructureProcessorType<BeekeeperHutArmorStandProcessor> BEEKEEPER_HUT_ARMOR_STAND_PROCESSOR = () -> BeekeeperHutArmorStandProcessor.CODEC;
	public static StructureProcessorType<BeekeeperHutItemFrameProcessor> BEEKEEPER_HUT_ITEM_FRAME_PROCESSOR = () -> BeekeeperHutItemFrameProcessor.CODEC;

	public static void init() {
		RegistryHelper.registerStructureProcessorType("iceologer_cabin_armor_stand_processor", ICEOLOGER_CABIN_ARMOR_STAND_PROCESSOR);
		RegistryHelper.registerStructureProcessorType("illusioner_shack_item_frame_processor", ILLUSIONER_SHACK_ITEM_FRAME_PROCESSOR);
		RegistryHelper.registerStructureProcessorType("beekeeper_hut_armor_stand_processor", BEEKEEPER_HUT_ARMOR_STAND_PROCESSOR);
		RegistryHelper.registerStructureProcessorType("beekeeper_hut_item_frame_processor", BEEKEEPER_HUT_ITEM_FRAME_PROCESSOR);

	}
}
