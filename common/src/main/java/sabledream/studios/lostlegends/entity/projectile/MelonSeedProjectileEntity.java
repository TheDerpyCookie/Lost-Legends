package sabledream.studios.lostlegends.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import sabledream.studios.lostlegends.init.LostLegendsEntityTypes;
import sabledream.studios.lostlegends.init.LostLegendsSoundEvents;

public class MelonSeedProjectileEntity extends E2JThrowableItemProjectile {


	public MelonSeedProjectileEntity(World world, LivingEntity owner) {
		super(LostLegendsEntityTypes.MELON_SEED_PROJECTILE.get(), owner, world);
	}

	public MelonSeedProjectileEntity(World world, double x, double y, double z) {
		super(LostLegendsEntityTypes.MELON_SEED_PROJECTILE.get(), x, y, z, world);
	}

	public MelonSeedProjectileEntity(EntityType<MelonSeedProjectileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected Item getDefaultItem() {
		return Items.MELON_SEEDS;
	}

	@Override
	protected SoundEvent getHitSound() {
		return LostLegendsSoundEvents.MELON_GOLEM_SEED_HIT.get();
	}
}
