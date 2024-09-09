package sabledream.studios.lostlegends.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import sabledream.studios.lostlegends.init.LostLegendsBlocks;
import sabledream.studios.lostlegends.init.LostLegendsSoundEvents;

public class RainbowSheepEntity extends LostLegendsMonoColorSheepEntity {

	public RainbowSheepEntity(EntityType<? extends RainbowSheepEntity> type, World world) {
		super(type, world, new ItemStack(LostLegendsBlocks.RAINBOW_WOOL.get()));
	}

	protected SoundEvent getAmbientSound() {
		return LostLegendsSoundEvents.RAINBOW_SHEEP_AMBIENT.get();
	}

	protected SoundEvent getDeathSound() {
		return LostLegendsSoundEvents.RAINBOW_SHEEP_DEATH.get();
	}

}