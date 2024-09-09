package sabledream.studios.lostlegends.client.render.entity.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ThrownItemRenderer<T extends Entity & FlyingItemEntity> extends EntityRenderer<T>
{
	private static final float MIN_CAMERA_DISTANCE_SQUARED = 12.25F;
	private final ItemRenderer itemRenderer;
	private final float scale;
	private final boolean fullBright;

	public ThrownItemRenderer(EntityRendererFactory.Context context, float scale, boolean fullBright) {
		super(context);
		this.itemRenderer = context.getItemRenderer();
		this.scale = scale;
		this.fullBright = fullBright;
	}

	public ThrownItemRenderer(EntityRendererFactory.Context context) {
		this(context, 1.0F, false);
	}

	protected int getBlockLight(T entity, BlockPos pos) {
		return this.fullBright ? 15 : super.getBlockLight(entity, pos);
	}

	public void render(T entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
		if (entity.age >= 2 || !(this.dispatcher.camera.getFocusedEntity().squaredDistanceTo(entity) < 12.25)) {
			poseStack.push();
			poseStack.scale(this.scale, this.scale, this.scale);
			poseStack.multiply(this.dispatcher.getRotation());
			this.itemRenderer.renderItem(((FlyingItemEntity)entity).getStack(), ModelTransformationMode.GROUND, packedLight, OverlayTexture.DEFAULT_UV, poseStack, bufferSource, entity.getWorld(), entity.getId());
			poseStack.pop();
			super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
		}
	}

	public Identifier getTexture(Entity entity) {
		return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
	}
}