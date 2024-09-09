package sabledream.studios.lostlegends.mixin;

import sabledream.studios.lostlegends.entity.BlazeEntityAccess;
import sabledream.studios.lostlegends.entity.WildfireEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.UUID;

@Mixin(BlazeEntity.class)
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class BlazeEntityMixin extends BlazeLivingEntityMixin implements BlazeEntityAccess
{
	private static final String WILDFIRE_UUID_NBT_NAME = "WildfireUuid";

	private Optional<UUID> LostLegends_wildfireUuid = Optional.empty();

	protected BlazeEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);

		this.LostLegends_wildfireUuid = Optional.empty();
	}

	@Override
	public void LostLegends_writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
		if (this.LostLegends_getWildfireUuid() != null) {
			nbt.putUuid(WILDFIRE_UUID_NBT_NAME, this.LostLegends_getWildfireUuid());
		}
	}

	@Override
	public void LostLegends_readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
		if (nbt.containsUuid(WILDFIRE_UUID_NBT_NAME)) {
			this.LostLegends_setWildfireUuid(nbt.getUuid(WILDFIRE_UUID_NBT_NAME));
		}
	}

	@Override
	public void LostLegends_onDeath(DamageSource damageSource, CallbackInfo ci) {
		if (this.getWorld() instanceof ServerWorld) {
			WildfireEntity wildfireEntity = this.LostLegends_getWildfire();

			if (wildfireEntity != null) {
				wildfireEntity.setSummonedBlazesCount(wildfireEntity.getSummonedBlazesCount() - 1);
			}
		}
	}

	@Nullable
	public UUID LostLegends_getWildfireUuid() {
		return this.LostLegends_wildfireUuid.orElse(null);
	}

	public void LostLegends_setWildfireUuid(@Nullable UUID uuid) {
		this.LostLegends_wildfireUuid = Optional.ofNullable(uuid);
	}

	public void LostLegends_setWildfire(WildfireEntity wildfire) {
		this.LostLegends_setWildfireUuid(wildfire.getUuid());
	}

	@Nullable
	public WildfireEntity LostLegends_getWildfire() {
		try {
			ServerWorld serverWorld = (ServerWorld) this.getWorld();
			UUID uUID = this.LostLegends_getWildfireUuid();
			return uUID == null ? null:(WildfireEntity) serverWorld.getEntity(uUID);
		} catch (IllegalArgumentException var2) {
			return null;
		}
	}
}
