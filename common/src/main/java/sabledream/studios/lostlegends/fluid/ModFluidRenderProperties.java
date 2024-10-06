package sabledream.studios.lostlegends.fluid;

import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.FogShape;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;


public class ModFluidRenderProperties {

	private final Identifier flowing;
	private final Identifier still;
	private final int tint;

	public ModFluidRenderProperties(Identifier still, Identifier flowing, int tint) {
		this.still = still;
		this.flowing = flowing;
		this.tint = tint;
		this.afterInit();
	}

	private void afterInit() {
	}

	public ModFluidRenderProperties(Identifier still, Identifier flowing) {
		this(still, flowing, 0xFFFFFFFF);
	}


	public int getTintColor() {
		return tint;
	}

	@NotNull
	public Identifier getStillTexture() {
		return still;
	}

	@NotNull
	public Identifier getFlowingTexture() {
		return flowing;
	}

	/**
	 * the reference of the texture to apply to a fluid directly touching
	 * a non-opaque block. Null will call flowing or still textures
	 */
	@Nullable
	public Identifier getOverlayTexture() {
		return null;
	}

	/**
	 * Modifies how the fog is currently being rendered when the camera is
	 * within a fluid.
	 */
	public void modifyFogRender(Camera camera, BackgroundRenderer.FogType mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
	}

	@NotNull
	public Vector3f modifyFogColor(Camera camera, float partialTick, ClientTickEvent.ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
		return fluidFogColor;
	}

	/**
	 * location of the texture to apply to the camera when it is within the fluid
	 */
	@Nullable
	public Identifier getRenderOverlayTexture(MinecraftClient mc)
	{
		return null;
	}


	// Level accessors

	public Identifier getStillTexture(FluidState state, BlockRenderView getter, BlockPos pos) {
		return this.getStillTexture();
	}

	public Identifier getFlowingTexture(FluidState state, BlockRenderView getter, BlockPos pos) {
		return this.getFlowingTexture();
	}

	public Identifier getOverlayTexture(FluidState state, BlockRenderView getter, BlockPos pos) {
		return this.getOverlayTexture();
	}

	public int getTintColor(FluidState state, BlockRenderView getter, BlockPos pos) {
		return this.getTintColor();
	}

}