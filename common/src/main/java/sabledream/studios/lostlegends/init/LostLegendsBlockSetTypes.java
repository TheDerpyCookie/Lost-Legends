package sabledream.studios.lostlegends.init;

import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.platform.RegistryHelper;
import net.minecraft.block.BlockSetType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

import java.util.function.Supplier;

/**
 * @see net.minecraft.block.BlockSetType
 */
public final class LostLegendsBlockSetTypes
{
	public static Supplier<BlockSetType> COPPER = () -> new BlockSetType(
		LostLegends.makeStringID("copper"),
		false,
		false,
		false,
		BlockSetType.ActivationRule.EVERYTHING,
		BlockSoundGroup.COPPER,
		SoundEvents.BLOCK_COPPER_PLACE,
		SoundEvents.BLOCK_COPPER_PLACE,
		SoundEvents.BLOCK_COPPER_PLACE,
		SoundEvents.BLOCK_COPPER_PLACE,
		SoundEvents.BLOCK_COPPER_PLACE,
		SoundEvents.BLOCK_COPPER_PLACE,
		SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF,
		SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON
	);

	public static void init() {
		RegistryHelper.registerBlockSetType(COPPER);
	}

	private LostLegendsBlockSetTypes() {
	}
}
