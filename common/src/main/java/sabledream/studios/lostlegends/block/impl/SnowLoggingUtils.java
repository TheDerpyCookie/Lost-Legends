package sabledream.studios.lostlegends.block.impl;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import org.jetbrains.annotations.Nullable;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.init.LostLegendsProperties;

public class SnowLoggingUtils
{

	public static final IntProperty SNOW_LAYERS = LostLegendsProperties.SNOW_LAYERS;
	public static final int MAX_LAYERS = 8;

	public static boolean supportsSnowlogging(BlockState state) {
		return state != null && state.getEntries() != null && state.contains(SNOW_LAYERS);
	}

	public static BlockState getSnowPlacementState(BlockState state, ItemPlacementContext context) {
		BlockState blockState;
		BlockState placementState = state;
		if (placementState != null
			&& SnowLoggingUtils.supportsSnowlogging(placementState) &&
			(blockState = context.getWorld().getBlockState(context.getBlockPos())).isOf(Blocks.SNOW)
		) {
			int layers = blockState.get(Properties.LAYERS);
			if (layers < 8) {
				placementState = placementState.with(SNOW_LAYERS, layers);
			}
		}
		return placementState;
	}




}
