package sabledream.studios.lostlegends.entity;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.EquipmentTable;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;
import sabledream.studios.lostlegends.client.render.entity.feature.MoobloomCactusFeatureRenderer;
import sabledream.studios.lostlegends.init.LostLegendsBlocks;

import java.util.Map;

public class MoobloomCactusEntity extends FlowerPlacingCowEntity{
	public MoobloomCactusEntity(EntityType<MoobloomCactusEntity> type, World world) {
		super(type, world);
	}

	@Override
	protected void initGoals() {
		super.initGoals();
	}

	@Override
	public void dropCustomItems() {
		for (int i = 0; i < 5; ++i) {
			getWorld().spawnEntity(new ItemEntity(getWorld(), getX(), getY(), getZ(), new ItemStack(LostLegendsBlocks.TINYCACTUS.get())));
		}
	}
}
