package sabledream.studios.lostlegends.api.wind;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.PersistentState;
import org.jetbrains.annotations.NotNull;
import sabledream.studios.lostlegends.LostLegends;

public class WindStorage extends PersistentState
{
	public static final String WIND_FILE_ID = "frozenlib_wind";
	private final WindManager windManager;

	public WindStorage(WindManager windManager) {
		this.windManager = windManager;
		this.setDirty(true);
	}

	@Override
	public @NotNull NbtCompound writeNbt(@NotNull NbtCompound compoundTag, RegistryWrapper.WrapperLookup provider) {
		compoundTag.putLong("time", this.windManager.time);
		compoundTag.putBoolean("overrideWind", this.windManager.overrideWind);
		compoundTag.putDouble("commandWindX", this.windManager.commandWind.getX());
		compoundTag.putDouble("commandWindY", this.windManager.commandWind.getY());
		compoundTag.putDouble("commandWindZ", this.windManager.commandWind.getZ());
		compoundTag.putDouble("windX", this.windManager.windX);
		compoundTag.putDouble("windY", this.windManager.windY);
		compoundTag.putDouble("windZ", this.windManager.windZ);
		compoundTag.putDouble("laggedWindX", this.windManager.laggedWindX);
		compoundTag.putDouble("laggedWindY", this.windManager.laggedWindY);
		compoundTag.putDouble("laggedWindZ", this.windManager.laggedWindZ);
		compoundTag.putLong("seed", this.windManager.seed);

		// EXTENSIONS
		for (WindManagerExtension extension : this.windManager.attachedExtensions) {
			NbtCompound extensionTag = new NbtCompound();
			extension.save(extensionTag);
			compoundTag.put(extension.extensionID().toString(), extensionTag);
		}
		return compoundTag;
	}

	public static @NotNull WindStorage load(@NotNull NbtCompound compoundTag, WindManager manager) {
		WindStorage windStorage = new WindStorage(manager);

		windStorage.windManager.time = compoundTag.getLong("time");
		windStorage.windManager.overrideWind = compoundTag.getBoolean("overrideWind");
		windStorage.windManager.commandWind = new Vec3d(compoundTag.getDouble("commandWindX"), compoundTag.getDouble("commandWindY"), compoundTag.getDouble("commandWindZ"));
		windStorage.windManager.windX = compoundTag.getDouble("windX");
		windStorage.windManager.windY = compoundTag.getDouble("windY");
		windStorage.windManager.windZ = compoundTag.getDouble("windZ");
		windStorage.windManager.laggedWindX = compoundTag.getDouble("laggedWindX");
		windStorage.windManager.laggedWindY = compoundTag.getDouble("laggedWindY");
		windStorage.windManager.laggedWindZ = compoundTag.getDouble("laggedWindZ");
		windStorage.windManager.seed = compoundTag.getLong("seed");

		// EXTENSIONS
		for (WindManagerExtension extension : windStorage.windManager.attachedExtensions) {
			NbtCompound extensionTag = compoundTag.getCompound(extension.extensionID().toString());
			extension.load(extensionTag);
		}
		return windStorage;
	}

}