package sabledream.studios.lostlegends.client.render.entity.feature;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.VillagerHeldItemFeatureRenderer;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.RotationAxis;
import sabledream.studios.lostlegends.client.render.entity.model.VilerWitchModel;
import sabledream.studios.lostlegends.entity.VilerWitchEntity;

public class VilerWitchHeldItemFeatureRenderer<T extends VilerWitchEntity> extends VillagerHeldItemFeatureRenderer<T, VilerWitchModel<T>>
{
	public VilerWitchHeldItemFeatureRenderer(FeatureRendererContext<T, VilerWitchModel<T>> renderLayerParent, HeldItemRenderer itemInHandRenderer) {
		super(renderLayerParent, itemInHandRenderer);
	}

	@Override
	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
		ItemStack itemStack = livingEntity.getMainHandStack();
		matrixStack.push();
		if (itemStack.isOf(Items.POTION)) {
			getContextModel().getHead().rotate(matrixStack);
			getContextModel().getNose().rotate(matrixStack);
			matrixStack.translate(0.0625f, 0.25f, 0.0f);
			matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0f));
			matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(140.0f));
			matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(10.0f));
			matrixStack.translate(0.0f, -0.4f, 0.4f);
		}
		super.render(matrixStack, vertexConsumerProvider, i, livingEntity, f, g, h, j, k, l);
		matrixStack.pop();
	}
}