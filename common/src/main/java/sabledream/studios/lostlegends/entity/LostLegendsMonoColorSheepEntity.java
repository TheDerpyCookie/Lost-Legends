package sabledream.studios.lostlegends.entity;

import net.minecraft.command.EntityDataObject;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class LostLegendsMonoColorSheepEntity extends LostLegendsBaseSheepEntity implements Shearable
{

	private static final TrackedData<Byte> isSheared = DataTracker.registerData(LostLegendsMonoColorSheepEntity.class, TrackedDataHandlerRegistry.BYTE);
	private final ItemStack wool;

	public LostLegendsMonoColorSheepEntity(EntityType<? extends LostLegendsBaseSheepEntity> type, World world, ItemStack wool) {
		super(type, world);
		this.wool = wool;
	}



	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(isSheared, (byte) 0);
	}

	@Override
	public RegistryKey<LootTable> getLootTableId() {
		if (isSheared()) {
			return RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("entities/sheep"));
		}
		return getType().getLootTableId();
	}

	public boolean isSheared() {
		return (dataTracker.get(isSheared) & 16) != 0;
	}

	public void setSheared(boolean sheared) {
		byte b0 = dataTracker.get(isSheared);
		if (sheared) {
			dataTracker.set(isSheared, (byte) (b0 | 16));
		} else {
			dataTracker.set(isSheared, (byte) (b0 & -17));
		}
	}

	public boolean isShearable() {
		return isAlive() && !isSheared() && !isBaby();
	}



	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);

		if (itemStack.isOf(Items.SHEARS)) {
			if (!getWorld().isClient && isShearable()) {
				sheared(SoundCategory.PLAYERS);
				itemStack.damage(1, player, getSlotForHand(hand));
				return ActionResult.SUCCESS;
			}
			return ActionResult.CONSUME;
		}
		return super.interactMob(player, hand);
	}

	public void sheared(SoundCategory soundSource) {
		getWorld().playSound(this, getBlockPos(), SoundEvents.ENTITY_SHEEP_SHEAR, soundSource, 1.0F, 1.0F);
		setSheared(true);
		int i = 1 + random.nextInt(3);
		for (int j = 0; j < i; ++j) {
			ItemEntity itemEntity = dropItem(wool.getItem(), 1);
			if (itemEntity != null) {
				itemEntity.setVelocity(itemEntity.getVelocity().add((random.nextFloat() - random.nextFloat()) * 0.1F, random.nextFloat() * 0.05F, (random.nextFloat() - random.nextFloat()) * 0.1F));
			}
		}
	}

}
