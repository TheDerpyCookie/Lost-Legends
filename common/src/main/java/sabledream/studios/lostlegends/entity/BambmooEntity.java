package sabledream.studios.lostlegends.entity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BambmooEntity extends FlowerPlacingCowEntity{
	public BambmooEntity(EntityType<BambmooEntity> type, World world) {
		super(type, world);
	}


	@Override
	protected void initGoals() {
		super.initGoals();
	}

	@Override
	public void dropCustomItems() {
		for (int i = 0; i < 5; ++i) {
			getWorld().spawnEntity(new ItemEntity(getWorld(), getX(), getY(), getZ(), new ItemStack(Blocks.BAMBOO)));
		}
	}
}
