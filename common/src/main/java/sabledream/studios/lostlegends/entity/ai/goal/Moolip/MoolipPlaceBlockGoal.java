package sabledream.studios.lostlegends.entity.ai.goal.Moolip;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.sound.SoundEvent;
import sabledream.studios.lostlegends.entity.MoolipEntity;
import sabledream.studios.lostlegends.init.LostLegendsBlocks;
import sabledream.studios.lostlegends.init.LostLegendsSoundEvents;

public class MoolipPlaceBlockGoal extends PlaceBlockGoal {

	public MoolipPlaceBlockGoal(MoolipEntity entity) {
		super(entity);
	}

	@Override
	protected Block getRandomFlower() {
		double random = Math.random();
		if (random > 0.6) {
			return Blocks.LILAC;
		} else if (random > 0.2) {
			return LostLegendsBlocks.PINKDAISY.get();
		}else if (random > 0.2) {
			return Blocks.ALLIUM;
		} else {
			return Blocks.PEONY;
		}
	}

	@Override
	protected SoundEvent getPlantSound() {
		return LostLegendsSoundEvents.MOOLIP_PLANT.get();
	}
}