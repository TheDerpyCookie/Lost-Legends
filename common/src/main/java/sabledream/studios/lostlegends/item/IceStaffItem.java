package sabledream.studios.lostlegends.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import sabledream.studios.lostlegends.entity.IceologerIceChunkEntity;
import sabledream.studios.lostlegends.entity.projectile.CustomSnowballEntity;

public class IceStaffItem extends Item {

	public IceStaffItem(Settings settings) {
		super(settings.maxDamage(50));
	}
@Override
	public TypedActionResult<ItemStack> use (World world, PlayerEntity user, Hand hand){
		ItemStack itemStack = user.getStackInHand(hand);
	world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
	if (!world.isClient){
		CustomSnowballEntity snowballEntity = new CustomSnowballEntity(world, user);
		snowballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
		world.spawnEntity(snowballEntity);
		user.getItemCooldownManager().set(this, 200);
		if (hand == Hand.MAIN_HAND){
			itemStack.damage(1, user, EquipmentSlot.MAINHAND);
		}else if
		(hand == Hand.OFF_HAND){
			itemStack.damage(1, user, EquipmentSlot.OFFHAND);

		}
	}
	return TypedActionResult.success(itemStack, world.isClient);
	}
}