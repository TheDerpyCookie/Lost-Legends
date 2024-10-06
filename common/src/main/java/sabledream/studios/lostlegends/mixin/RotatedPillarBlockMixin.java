package sabledream.studios.lostlegends.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.state.StateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sabledream.studios.lostlegends.init.LostLegendsRegisterProperties;

@Mixin(value = PillarBlock.class, priority = 990)
public class RotatedPillarBlockMixin
{
	@Inject(method = "appendProperties", at = @At("TAIL"))
	private void addTermiteEdibleState(StateManager.Builder<Block, BlockState> builder, CallbackInfo info) {
		builder.add(LostLegendsRegisterProperties.TERMITE_EDIBLE);
	}
	@Inject(method = "<init>", at = @At("TAIL"))
	private void lostlegends$appendFalseTermiteEdibleToState(AbstractBlock.Settings properties, CallbackInfo info) {
		PillarBlock rotatedPillarBlock = PillarBlock.class.cast(this);
		BlockState defaultBlockState = rotatedPillarBlock.getDefaultState();
		if (defaultBlockState.contains(LostLegendsRegisterProperties.TERMITE_EDIBLE)) {
			rotatedPillarBlock.setDefaultState(defaultBlockState.with(LostLegendsRegisterProperties.TERMITE_EDIBLE, false));
		}
	}
}
