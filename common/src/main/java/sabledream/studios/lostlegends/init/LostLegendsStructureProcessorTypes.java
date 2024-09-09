package sabledream.studios.lostlegends.init;

import sabledream.studios.lostlegends.platform.RegistryHelper;
import sabledream.studios.lostlegends.platform.StructureEntityProcessorTypes;
import sabledream.studios.lostlegends.world.processor.CitadelBottomProcessor;
import sabledream.studios.lostlegends.world.processor.IllusionerShackBrewingStandProcessor;
import net.minecraft.structure.processor.StructureProcessorType;

/**
 * @see StructureProcessorType
 */
public final class LostLegendsStructureProcessorTypes
{
	public static StructureProcessorType<IllusionerShackBrewingStandProcessor> ILLUSIONER_SHACK_BREWING_STAND_PROCESSOR = () -> IllusionerShackBrewingStandProcessor.CODEC;
	public static StructureProcessorType<CitadelBottomProcessor> CITADEL_BOTTOM_PROCESSOR = () -> CitadelBottomProcessor.CODEC;

	public static void init() {
		RegistryHelper.registerStructureProcessorType("illusioner_shack_brewing_stand_processor", ILLUSIONER_SHACK_BREWING_STAND_PROCESSOR);
		RegistryHelper.registerStructureProcessorType("citadel_bottom_processor", CITADEL_BOTTOM_PROCESSOR);
		StructureEntityProcessorTypes.init();
	}

	private LostLegendsStructureProcessorTypes() {
	}
}
