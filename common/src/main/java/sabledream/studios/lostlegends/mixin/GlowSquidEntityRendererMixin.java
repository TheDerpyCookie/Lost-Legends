package sabledream.studios.lostlegends.mixin;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.SquidEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.GlowSquidEntity;
import net.minecraft.entity.passive.SquidEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sabledream.studios.lostlegends.client.RenderedHypnoEntities;


@Mixin({SquidEntityRenderer.class})
public class GlowSquidEntityRendererMixin {
	public GlowSquidEntityRendererMixin() {
	}

	@Inject(method = "setupTransforms(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/util/math/MatrixStack;FFFF)V",
		at = {@At("TAIL")}
	)
	protected void setupTransforms(
		LivingEntity squidEntity,
		MatrixStack matrices,
		float animationProgress,
		float bodyYaw,
		float tickDelta,
		float scale,
		CallbackInfo ci
	) {
		if (squidEntity instanceof GlowSquidEntity glowSquid) {
			if ((float)glowSquid.getDarkTicksRemaining() <= 0.0F && Math.sqrt(MinecraftClient.getInstance().player.getPos().squaredDistanceTo(squidEntity.getPos())) < 20.0) {
				RenderedHypnoEntities.GLOWSQUIDS.add(glowSquid);
			}
		}

	}
}