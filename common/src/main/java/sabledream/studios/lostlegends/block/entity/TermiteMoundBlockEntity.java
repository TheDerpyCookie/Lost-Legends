package sabledream.studios.lostlegends.block.entity;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sabledream.studios.lostlegends.api.TermiteManager;
import sabledream.studios.lostlegends.init.LostLegendsBlockEntity;
import sabledream.studios.lostlegends.init.LostLegendsSoundEvents;

import java.util.Objects;

public class TermiteMoundBlockEntity extends BlockEntity
{
	public final TermiteManager termiteManager;
	public final IntArrayList clientTermiteIDs = new IntArrayList();
	public final IntArrayList prevClientTermiteIDs = new IntArrayList();



	public TermiteMoundBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		super(LostLegendsBlockEntity.TERMITE_MOUND.get(), pos, state);
		this.termiteManager = new TermiteManager();
	}
	public void tickServer(@NotNull World level, @NotNull BlockPos pos, boolean natural, boolean awake, boolean canSpawn) {
		this.termiteManager.tick(level, pos, natural, awake, canSpawn);
		this.updateSync();
	}
//
//	public void updateSync() {
//		for (ServerPlayerEntity player : LostLegendsPlayerLookup.tracking(this)) {
//			player.networkHandler.send(Objects.requireNonNull(this.getUpdatePacket()));
//		}
//	}

	public void updateSync() {
		for (ServerPlayerEntity player : LostLegendsPlayerLookup.tracking(this)) {
			var packet = this.getUpdatePacket();
			if (packet != null) {
				player.networkHandler.send(packet, null);
			}
		}
	}
	public void tickClient() {
		for (TermiteManager.Termite termite : this.termiteManager.termites()) {
			int termiteID = termite.getID();
			if (clientTermiteIDs.contains(termiteID) && !this.prevClientTermiteIDs.contains(termiteID)) {
				addTermiteSound(this, termiteID, termite.getEating());
			}
		}
		this.prevClientTermiteIDs.clear();
		this.prevClientTermiteIDs.addAll(this.clientTermiteIDs);
		this.clientTermiteIDs.clear();
		for (TermiteManager.Termite termite : this.termiteManager.termites()) {
			this.clientTermiteIDs.add(termite.getID());
		}
	}

	public BlockEntityUpdateS2CPacket getUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup provider) {
		return this.createNbt(provider);
	}

	@Override
	protected void writeNbt(@NotNull NbtCompound tag, RegistryWrapper.WrapperLookup provider) {
		super.writeNbt(tag, provider);
		this.termiteManager.saveAdditional(tag);
	}

	@Override
	public void readNbt(@NotNull NbtCompound tag, RegistryWrapper.WrapperLookup provider) {
		super.readNbt(tag, provider);
		this.termiteManager.load(tag);
	}
	public static void addTermiteSound(TermiteMoundBlockEntity mound, int termiteID, boolean eating) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.world != null) {
			client.getSoundManager().play(
				new TermiteSoundInstance<>(
					mound,
					termiteID,
					eating ? LostLegendsSoundEvents.BLOCK_TERMITE_MOUND_TERMITE_GNAW.get() : LostLegendsSoundEvents.BLOCK_TERMITE_MOUND_TERMITE_IDLE.get(),
					SoundCategory.BLOCKS,
					0.2F,
					1F,
					eating
				)
			);
		}
	}

	public static class TermiteSoundInstance<T extends TermiteMoundBlockEntity> extends MovingSoundInstance
	{
		private final T mound;
		private final int termiteID;
		private final boolean eating;
		private boolean initialTermiteCheck;

		public TermiteSoundInstance(T mound, int termiteID, SoundEvent sound, SoundCategory category, float volume, float pitch, boolean eating) {
			super(sound, category, SoundInstance.createRandom());
			this.mound = mound;
			this.repeat = true;
			this.repeatDelay = 0;
			this.volume = volume;
			this.pitch = pitch;
			this.termiteID = termiteID;
			this.eating = eating;
		}

		@Nullable
		public TermiteManager.Termite getTermite() {
			if (this.mound != null && !this.mound.isRemoved()) {
				for (TermiteManager.Termite termite : this.mound.termiteManager.termites()) {
					if (termite.getID() == this.termiteID) {
						return termite;
					}
				}
			}
			return null;
		}

		@Override
		public boolean shouldAlwaysPlay() {
			return true;
		}

		@Override
		public void tick() {
			TermiteManager.Termite termite = this.getTermite();
			if (termite != null) {
				BlockPos pos = termite.getPos();
				this.x = pos.getX();
				this.y = pos.getY();
				this.z = pos.getZ();
				if (termite.getEating() != this.eating) {
					this.mound.clientTermiteIDs.removeIf((i -> i == this.termiteID));
					this.setDone();
				}
			} else {
				if (!this.initialTermiteCheck) {
					this.initialTermiteCheck = true;
				} else {
					this.setDone();
				}
			}
		}

		@Override
		public String toString() {
			return "TermiteSoundInstance[" + this.id + "]";
		}
	}
}
