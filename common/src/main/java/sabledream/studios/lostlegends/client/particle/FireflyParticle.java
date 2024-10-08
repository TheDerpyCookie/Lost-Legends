package sabledream.studios.lostlegends.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.MathHelper;

import java.util.concurrent.ThreadLocalRandom;

public class FireflyParticle extends SpriteBillboardParticle
{
	private SpriteProvider spriteProvider;




	protected FireflyParticle(ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
		super(clientWorld, x, y, z, velocityX, velocityY, velocityZ);
		this.spriteProvider = spriteProvider;
		this.velocityMultiplier = 0.00f;
		this.maxAge = ThreadLocalRandom.current().nextInt(20, 40);
		this.scale *= 0.25f + random.nextFloat() * 0.50f;
		this.collidesWithWorld = false;
	}


	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	public float getSize(float tickDelta) {
		float f = ((float)this.age + tickDelta) / (float)this.maxAge;
		return this.scale * (1.0f - f * f * 0.5f);
	}

	@Override
	protected int getBrightness(float tint) {
		float f = ((float)this.age + tint) / (float)this.maxAge;
		f = MathHelper.clamp(f, 0.0f , 1.0f);
		int i = super.getBrightness(tint);
		int j = i & 0xFF;
		int k = i >> 16 & 0xFF;
		if ((j += (int)(f * 15.0f * 16.0f)) > 240) {
			j = 240;
		}
		return j | k << 16;
	}

	public static class Factory implements ParticleFactory<SimpleParticleType> {
		private final SpriteProvider spriteProvider;

		public Factory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		@Override
		public Particle createParticle(SimpleParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
			FireflyParticle fireflyParticle = new FireflyParticle(clientWorld, x, y, z, velocityX, velocityY, velocityZ, spriteProvider);
			fireflyParticle.setSprite(this.spriteProvider);
			return fireflyParticle;
		}
	}

}
