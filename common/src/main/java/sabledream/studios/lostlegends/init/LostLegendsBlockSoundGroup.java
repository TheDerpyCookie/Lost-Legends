package sabledream.studios.lostlegends.init;

import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class LostLegendsBlockSoundGroup
{
	public static final BlockSoundGroup TUMBLEWEED_PLANT;
	public static final BlockSoundGroup TERMITEMOUND;
	public static final BlockSoundGroup HOLLOWED_STEM;
	public static final BlockSoundGroup HOLLOWED_LOG;




	public final float volume;
	public final float pitch;
	private final SoundEvent breakSound;
	private final SoundEvent stepSound;
	private final SoundEvent placeSound;
	private final SoundEvent hitSound;
	private final SoundEvent fallSound;


	public LostLegendsBlockSoundGroup(float volume, float pitch, SoundEvent breakSound, SoundEvent stepSound, SoundEvent placeSound, SoundEvent hitSound, SoundEvent fallSound) {
		this.volume = volume;
		this.pitch = pitch;
		this.breakSound = breakSound;
		this.stepSound = stepSound;
		this.placeSound = placeSound;
		this.hitSound = hitSound;
		this.fallSound = fallSound;
	}

	public float getVolume() {
		return this.volume;
	}

	public float getPitch() {
		return this.pitch;
	}

	public SoundEvent getBreakSound() {
		return this.breakSound;
	}

	public SoundEvent getStepSound() {
		return this.stepSound;
	}

	public SoundEvent getPlaceSound() {
		return this.placeSound;
	}

	public SoundEvent getHitSound() {
		return this.hitSound;
	}

	public SoundEvent getFallSound() {
		return this.fallSound;
	}

	static {
		TUMBLEWEED_PLANT = new BlockSoundGroup(1.0F, 1.0F,
			LostLegendsSoundEvents.BLOCK_TUMBLEWEED_PLANT_BREAK.get(),
			LostLegendsSoundEvents.BLOCK_TUMBLEWEED_PLANT_STEP.get(),
			LostLegendsSoundEvents.BLOCK_TUMBLEWEED_PLANT_PLACE.get(),
			LostLegendsSoundEvents.BLOCK_TUMBLEWEED_PLANT_HIT.get(),
			LostLegendsSoundEvents.BLOCK_TUMBLEWEED_PLANT_FALL.get()
		);

		HOLLOWED_STEM = new BlockSoundGroup(1.0F, 1.0F,
			LostLegendsSoundEvents.BLOCK_TUMBLEWEED_PLANT_BREAK.get(),
			LostLegendsSoundEvents.BLOCK_TUMBLEWEED_PLANT_STEP.get(),
			LostLegendsSoundEvents.BLOCK_TUMBLEWEED_PLANT_PLACE.get(),
			LostLegendsSoundEvents.BLOCK_TUMBLEWEED_PLANT_HIT.get(),
			LostLegendsSoundEvents.BLOCK_TUMBLEWEED_PLANT_FALL.get()
		);
		HOLLOWED_LOG = new BlockSoundGroup(1.0F, 1.0F,
			LostLegendsSoundEvents.BLOCK_TUMBLEWEED_PLANT_BREAK.get(),
			LostLegendsSoundEvents.BLOCK_TUMBLEWEED_PLANT_STEP.get(),
			LostLegendsSoundEvents.BLOCK_TUMBLEWEED_PLANT_PLACE.get(),
			LostLegendsSoundEvents.BLOCK_TUMBLEWEED_PLANT_HIT.get(),
			LostLegendsSoundEvents.BLOCK_TUMBLEWEED_PLANT_FALL.get()
		);

		TERMITEMOUND = new BlockSoundGroup(0.8F, 1.0F,
			LostLegendsSoundEvents.BLOCK_TERMITE_MOUND_BREAK.get(),
			LostLegendsSoundEvents.BLOCK_TERMITE_MOUND_STEP.get(),
			LostLegendsSoundEvents.BLOCK_TERMITE_MOUND_PLACE.get(),
			LostLegendsSoundEvents.BLOCK_TERMITE_MOUND_HIT.get(),
			LostLegendsSoundEvents.BLOCK_TERMITE_MOUND_FALL.get()
		);
	}



}
