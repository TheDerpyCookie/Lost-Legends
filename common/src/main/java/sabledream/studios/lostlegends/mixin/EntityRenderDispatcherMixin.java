package sabledream.studios.lostlegends.mixin;

import sabledream.studios.lostlegends.entity.PlayerIllusionEntity;
import sabledream.studios.lostlegends.init.LostLegendsEntityRenderer;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin
{
	@Unique
	private Map<SkinTextures.Model, EntityRenderer<? extends PlayerIllusionEntity>> illusionModelRenderers = ImmutableMap.of();

	@Inject(
		method = "getRenderer",
		at = @At("HEAD"),
		cancellable = true
	)
	public <T extends Entity> void LostLegends_getRenderer(
		T entity,
		CallbackInfoReturnable<EntityRenderer<? super T>> cir
	) {
		if (entity instanceof PlayerIllusionEntity) {
			SkinTextures.Model model = ((PlayerIllusionEntity) entity).getSkinTextures().model();
			EntityRenderer<? extends PlayerIllusionEntity> entityRenderer = this.illusionModelRenderers.get(model);
			entityRenderer = entityRenderer != null ? entityRenderer:this.illusionModelRenderers.get(SkinTextures.Model.WIDE);
			cir.setReturnValue((EntityRenderer<? super T>) entityRenderer);
		}
	}

	@ModifyVariable(
		method = "reload",
		ordinal = 0,
		at = @At(
			value = "LOAD"
		)
	)
	public EntityRendererFactory.Context LostLegends_reload(
		EntityRendererFactory.Context context
	) {
		this.illusionModelRenderers = LostLegendsEntityRenderer.reloadPlayerIllusionRenderers(context);
		return context;
	}
}
