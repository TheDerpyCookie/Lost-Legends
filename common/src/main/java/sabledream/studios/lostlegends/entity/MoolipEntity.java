package sabledream.studios.lostlegends.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sabledream.studios.lostlegends.entity.ai.goal.Moolip.MoolipPlaceBlockGoal;
import sabledream.studios.lostlegends.init.LostLegendsBlocks;

public class MoolipEntity extends FlowerPlacingCowEntity
{
	public MoolipEntity(EntityType<MoolipEntity> type, World world) {
		super(type, world);
	}


	@Override
	protected void initGoals() {
		super.initGoals();
		goalSelector.add(8, new MoolipPlaceBlockGoal(this));

	}

	@Override
	public void dropCustomItems() {
		for (int i = 0; i < 5; ++i) {
			getWorld().spawnEntity(new ItemEntity(getWorld(), getX(), getY(), getZ(), new ItemStack(LostLegendsBlocks.PINKDAISY.get())));
		}
	}
}
