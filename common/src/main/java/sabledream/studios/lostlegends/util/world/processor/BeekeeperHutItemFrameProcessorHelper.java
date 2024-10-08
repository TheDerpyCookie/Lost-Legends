package sabledream.studios.lostlegends.util.world.processor;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate.StructureEntityInfo;
import net.minecraft.util.math.random.Random;

public final class BeekeeperHutItemFrameProcessorHelper
{
	public static StructureEntityInfo processEntity(
		StructureEntityInfo globalEntityInfo,
		StructurePlacementData structurePlacementData
	) {
		if (globalEntityInfo.nbt.getString("id").equals("minecraft:item_frame") == false) {
			return globalEntityInfo;
		}

		Random random = structurePlacementData.getRandom(globalEntityInfo.blockPos);

		NbtCompound newNbt = globalEntityInfo.nbt.copy();

		int randomRotation = random.nextInt(8);
		newNbt.putByte("ItemRotation", (byte) randomRotation);

		globalEntityInfo = new StructureEntityInfo(globalEntityInfo.pos, globalEntityInfo.blockPos, newNbt);

		return globalEntityInfo;
	}
}
