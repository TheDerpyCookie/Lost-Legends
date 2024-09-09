package sabledream.studios.lostlegends.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import sabledream.studios.lostlegends.init.LostLegendsBlockEntity;

public class RainbowBedBlockEntity extends BlockEntity
{
	private DyeColor color;

	public RainbowBedBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(LostLegendsBlockEntity.RAINBOW_BED.get(), blockPos, blockState);
	}

	public RainbowBedBlockEntity(BlockPos blockPos, BlockState blockState, DyeColor dyeColor) {
		this(blockPos, blockState);
		setColor(dyeColor);
	}

	@Override
	public BlockEntityUpdateS2CPacket toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	@Environment(EnvType.CLIENT)
	public DyeColor getColor() {
		if (color == null) {
			color = ((BedBlock) getCachedState().getBlock()).getColor();
		}
		return color;
	}

	@Environment(EnvType.CLIENT)
	public void setColor(DyeColor color) {
		this.color = color;
	}
}