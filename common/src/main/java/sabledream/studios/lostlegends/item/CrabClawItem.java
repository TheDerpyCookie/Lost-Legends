package sabledream.studios.lostlegends.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CrabClawItem extends Item
{
	public CrabClawItem(Settings settings) {
		super(settings);
	}
	@Override
	public void inventoryTick(ItemStack pStack, World pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
		if(((PlayerEntity) pEntity).getOffHandStack().isOf(pStack.getItem())) {
			((PlayerEntity) pEntity).getAttributeInstance(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE).setBaseValue(4.5d + (pStack.getCount() / 2d));
		} else {
			((PlayerEntity) pEntity).getAttributeInstance(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE).setBaseValue(4.5d);
		}

		super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
	}
}
