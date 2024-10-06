package sabledream.studios.lostlegends.screen;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.api.recipie.FilterableRecipe;
import sabledream.studios.lostlegends.init.LostLegendsBlocks;
import sabledream.studios.lostlegends.init.LostLegendsMenus;
import sabledream.studios.lostlegends.init.LostLegendsRecipes;
import sabledream.studios.lostlegends.init.LostLegendsSoundEvents;

import java.util.List;

public class SawmillMenu extends ScreenHandler {
	private final ScreenHandlerContext access;
	private final Property selectedRecipeIndex;
	private final World level;
	private final Inventory container;
	private final Slot inputSlot;
	private final Slot resultSlot;


	private List<FilterableRecipe> recipes;
	private ItemStack input;
	private long lastSoundTime;
	private final CraftingResultInventory resultContainer;
	private Runnable slotUpdateListener;
	private FilterableRecipe lastSelectedRecipe = null;


	public boolean isWide = LostLegends.getConfig().widegui;


	public SawmillMenu(int i, PlayerInventory inventory, PacketByteBuf buf) {
		this(i, inventory, ScreenHandlerContext.EMPTY);
	}

	public SawmillMenu(int i, PlayerInventory inventory, final ScreenHandlerContext containerLevelAccess) {
		super(LostLegendsMenus.SAWMILL_MENU.get(), i);
		this.selectedRecipeIndex = Property.create();
		this.recipes = Lists.newArrayList();
		this.input = ItemStack.EMPTY;
		this.slotUpdateListener = () -> {
		};
		this.container = new SimpleInventory(1){
			@Override
			public void markDirty() {
				super.markDirty();
				slotsChanged(this);
				slotUpdateListener.run();
			}
		};
		this.resultContainer = new CraftingResultInventory();
		this.access = containerLevelAccess;
		this.level = inventory.player.getWorld();
		this.inputSlot = this.addSlot(new Slot(this.container, 0, isWide ? 17 : 21, 33));
		this.resultSlot = this.addSlot(new Slot(this.resultContainer, 1, isWide ? 146 : 143, 33) {
			@Override
			public boolean canInsert(ItemStack stack) {
				return false;
			}

			@Override
			public void onTakeItem(PlayerEntity player, ItemStack stack) {
				stack.onCraftByPlayer(player.getWorld(), player, stack.getCount());
				resultContainer.unlockLastRecipe(player, this.getRelevantItems());
				ItemStack itemStack = inputSlot.takeStack(recipes.get(selectedRecipeIndex.get())
					.recipe().value().getInputCount());
				if (!itemStack.isEmpty()) {
					setupResultSlot();
				}

				containerLevelAccess.run((level, blockPos) -> {
					long l = level.getTime();
					if (lastSoundTime != l) {
						level.playSound(null, blockPos, LostLegendsSoundEvents.SAWMILL_TAKE.get(), SoundCategory.BLOCKS, 1.0F, 1.0F);
						lastSoundTime = l;
					}

				});
				super.onTakeItem(player, stack);
			}

			private List<ItemStack> getRelevantItems() {
				return List.of(inputSlot.getStack());
			}
		});

		int j;
		for (j = 0; j < 3; ++j) {
			for (int k = 0; k < 9; ++k) {
				this.addSlot(new Slot(inventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
			}
		}

		for (j = 0; j < 9; ++j) {
			this.addSlot(new Slot(inventory, j, 8 + j * 18, 142));
		}

		this.addProperty(this.selectedRecipeIndex);
	}



	public int getSelectedRecipeIndex() {
		return this.selectedRecipeIndex.get();
	}

	public List<FilterableRecipe> getRecipes() {
		return this.recipes;
	}

	public boolean hasInputItem() {
		return this.inputSlot.hasStack() && !this.recipes.isEmpty();
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return canUse(this.access, player, LostLegendsBlocks.SAWMILL_BLOCK.get());
	}

	@Override
	public boolean onButtonClick(PlayerEntity player, int id) {
		id = Byte.toUnsignedInt((byte)id);
		if (this.isValidRecipeIndex(id) || id == 255) {
			this.selectedRecipeIndex.set(id);
			this.setupResultSlot();
		}
		return true;
	}

	private boolean isValidRecipeIndex(int recipeIndex) {
		return recipeIndex >= 0 && recipeIndex < this.recipes.size();
	}

	public void slotsChanged(Inventory container) {
		ItemStack itemStack = this.inputSlot.getStack();
		ItemStack old = this.input;
		boolean sameStack = itemStack.isOf(old.getItem());
		int maxItemsThatCanBeConsumed = 5; //I made it the f up
		if (!sameStack || itemStack.getCount() < maxItemsThatCanBeConsumed || old.getCount() < maxItemsThatCanBeConsumed) {
			this.input = itemStack.copy();
//			this.setupRecipeList(container, itemStack);
		}

	}

	private static SingleStackRecipeInput createRecipeInput(Inventory container) {
		return new SingleStackRecipeInput(container.getStack(0));
	}


//	private void setupRecipeList(Inventory container, ItemStack stack) {
//		this.selectedRecipeIndex.set(-1);
//
//		this.resultSlot.setStack(ItemStack.EMPTY);
//		if (!stack.isEmpty()) {
//			var matching = this.level.getRecipeManager()
//				.getAllMatches(LostLegendsRecipes.WOODCUTTING_RECIPE.get(), createRecipeInput(container), this.level);
//
//			//remove blacklisted
//			matching.removeIf(r -> r.value().getResult(DynamicRegistryManager.EMPTY).isIn(LostLegendsTags.BLACKLIST));
//
//			RecipeSorter.sort(matching, this);
//
//			recipes = matching.stream().map(FilterableRecipe::of).toList();
//			// at most 256 recipes
//			recipes = recipes.subList(0, Math.min(recipes.size(), 255));
//
//			//preserve last clicked recipe on recipe change
//			if (lastSelectedRecipe != null) {
//				int newInd = this.recipes.indexOf(lastSelectedRecipe);
//				if (newInd != -1) {
//					this.selectedRecipeIndex.set(newInd);
//				}
//			}
//		}
//
//		lastSelectedRecipe = null;
//	}

	void setupResultSlot() {
		if (!this.recipes.isEmpty() && this.isValidRecipeIndex(this.selectedRecipeIndex.get())) {
			FilterableRecipe selected = this.recipes.get(this.selectedRecipeIndex.get());
			this.lastSelectedRecipe = selected;
			ItemStack itemStack = selected.recipe().value().craft(createRecipeInput(container), this.level.getRegistryManager());
			if (itemStack.isItemEnabled(this.level.getEnabledFeatures())) {
				this.resultContainer.setLastRecipe(selected.recipe());
				this.resultSlot.setStack(itemStack);
			} else {
				this.resultSlot.setStack(ItemStack.EMPTY);
			}
		} else {
			this.resultSlot.setStack(ItemStack.EMPTY);
		}

		this.sendContentUpdates();
	}
	@Override
	public ScreenHandlerType<?> getType() {
		return LostLegendsMenus.SAWMILL_MENU.get();
	}

	public void registerUpdateListener(Runnable listener) {
		this.slotUpdateListener = listener;
	}

	@Override
	public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
		return slot.inventory != this.resultContainer && super.canInsertIntoSlot(stack, slot);
	}

	@Override
	public ItemStack quickMove(PlayerEntity player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot.hasStack()) {
			ItemStack itemStack2 = slot.getStack();
			Item item = itemStack2.getItem();
			itemStack = itemStack2.copy();
			if (index == 1) {
				item.onCraftByPlayer(itemStack2, player.getWorld(), player);
				if (!this.insertItem(itemStack2, 2, 38, true)) {
					return ItemStack.EMPTY;
				}

				slot.onQuickTransfer(itemStack2, itemStack);
			} else if (index == 0) {
				if (!this.insertItem(itemStack2, 2, 38, false)) {
					return ItemStack.EMPTY;
				}
			} else if (this.level.getRecipeManager().getFirstMatch(LostLegendsRecipes.WOODCUTTING_RECIPE.get(),
				new SingleStackRecipeInput(itemStack2), this.level).isPresent()) {
				if (!this.insertItem(itemStack2, 0, 1, false)) {
					return ItemStack.EMPTY;
				}
			} else if (index >= 2 && index < 29) {
				if (!this.insertItem(itemStack2, 29, 38, false)) {
					return ItemStack.EMPTY;
				}
			} else if (index >= 29 && index < 38 && !this.insertItem(itemStack2, 2, 29, false)) {
				return ItemStack.EMPTY;
			}

			if (itemStack2.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			}

			slot.markDirty();
			if (itemStack2.getCount() == itemStack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTakeItem(player, itemStack2);
			this.sendContentUpdates();
		}

		return itemStack;
	}

	@Override
	public void onClosed(PlayerEntity player) {
		super.onClosed(player);
		this.resultContainer.removeStack(1);
		this.access.run((level, blockPos) -> {
			this.dropInventory(player, this.container);
		});
	}


}