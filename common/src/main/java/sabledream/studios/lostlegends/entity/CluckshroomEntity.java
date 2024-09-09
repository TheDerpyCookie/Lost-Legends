package sabledream.studios.lostlegends.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.AvoidSunlightGoal;
import net.minecraft.entity.ai.goal.EscapeSunlightGoal;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.world.World;
import sabledream.studios.lostlegends.entity.ai.goal.cluckshroom.CluckshroomPlaceBlockGoal;

public class CluckshroomEntity extends LostLegendsBaseChickenEntity
{
	public CluckshroomEntity(EntityType<CluckshroomEntity> type, World worldIn) {
		super(type, worldIn);
	}


	@Override
	protected void initGoals(){
		super.initGoals();
		goalSelector.add(2, new AvoidSunlightGoal(this));
		goalSelector.add(3, new EscapeSunlightGoal(this, 1.0D));
		goalSelector.add(3, new CluckshroomPlaceBlockGoal(this));
	}

}
