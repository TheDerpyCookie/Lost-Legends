package sabledream.studios.lostlegends.entity.ai.brain.task.coppergolem;

import sabledream.studios.lostlegends.entity.CopperGolemEntity;
import sabledream.studios.lostlegends.entity.ai.brain.CopperGolemBrain;
import sabledream.studios.lostlegends.init.LostLegendsMemoryModuleTypes;
import sabledream.studios.lostlegends.tag.LostLegendsTags;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public final class CopperGolemLocateButtonTask extends MultiTickTask<CopperGolemEntity>
{
	public CopperGolemLocateButtonTask() {
		super(Map.of(
			LostLegendsMemoryModuleTypes.COPPER_GOLEM_PRESS_BUTTON_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT,
			LostLegendsMemoryModuleTypes.COPPER_GOLEM_BUTTON_POS.get(), MemoryModuleState.VALUE_ABSENT,
			LostLegendsMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get(), MemoryModuleState.VALUE_ABSENT
		));
	}

	@Override
	protected void run(ServerWorld world, CopperGolemEntity copperGolem, long time) {
		BlockPos buttonBlockPos = this.findNearestRandomButton(copperGolem);

		if (buttonBlockPos == null) {
			CopperGolemBrain.setPressButtonCooldown(copperGolem, TimeHelper.betweenSeconds(10, 10));
			return;
		}

		RegistryKey<World> registryKey = copperGolem.getWorld().getRegistryKey();
		copperGolem.getBrain().remember(LostLegendsMemoryModuleTypes.COPPER_GOLEM_BUTTON_POS.get(), GlobalPos.create(registryKey, buttonBlockPos));
	}

	private BlockPos findNearestRandomButton(CopperGolemEntity copperGolem) {
		int horizontalRange = 16;
		int verticalRange = 8;

		List<BlockPos> buttons = this.findAllButtonsInRange(copperGolem.getBlockPos(), horizontalRange, verticalRange, (blockPos) -> {
			return copperGolem.getWorld().getBlockState(blockPos).isIn(LostLegendsTags.COPPER_BUTTONS);
		});

		if (buttons.isEmpty()) {
			return null;
		}

		return buttons.get(copperGolem.getRandom().nextInt(buttons.size()));
	}

	private List<BlockPos> findAllButtonsInRange(
		BlockPos copperGolemPos,
		int horizontalRange,
		int verticalRange,
		Predicate<BlockPos> condition
	) {
		List<BlockPos> buttons = new ArrayList<>();
		for (BlockPos blockPos : BlockPos.iterateOutwards(copperGolemPos, horizontalRange, verticalRange, horizontalRange)) {
			BlockPos possibleButtonBlockPos = blockPos.mutableCopy();

			if (condition.test(possibleButtonBlockPos) == false) {
				continue;
			}

			buttons.add(possibleButtonBlockPos);
		}

		return buttons;
	}
}
