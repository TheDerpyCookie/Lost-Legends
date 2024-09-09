package sabledream.studios.lostlegends.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sabledream.studios.lostlegends.block.RainbowBedBlock;
import sabledream.studios.lostlegends.init.LostLegendsBlockEntity;

@Mixin(BuiltinModelItemRenderer.class)
public class BlockEntityWithoutLevelRendererMixin
{
	@Shadow
	@Final
	private BlockEntityRenderDispatcher blockEntityRenderDispatcher;

	@Inject(at = @At("HEAD"), method = "render", cancellable = true)
	private void Friendsandfoes_RenderRainbowBed(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
		Item item = stack.getItem();
		if (item instanceof BlockItem blockItem) {
			Block block = blockItem.getBlock();
			if (block instanceof RainbowBedBlock) {
				BlockState blockState = block.getDefaultState();
				blockEntityRenderDispatcher.renderEntity((BlockEntity) LostLegendsBlockEntity.RAINBOW_BED.get().instantiate(BlockPos.ORIGIN, blockState), matrices, vertexConsumers, light, overlay);
				ci.cancel();
			}
		}
	}

}
