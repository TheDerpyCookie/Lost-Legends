 package sabledream.studios.lostlegends.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.api.recipie.FilterableRecipe;
import org.jetbrains.annotations.NotNull;
import sabledream.studios.lostlegends.init.LostLegendsSoundEvents;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;


public final class SawmillScreen extends HandledScreen<SawmillMenu>
{
	private static final Identifier BACKGROUND = LostLegends.makeID("textures/gui/container/sawmill.png");
	private static final Identifier BACKGROUND_SEARCH = LostLegends.makeID("textures/gui/container/sawmill_search.png");
	private static final Identifier BACKGROUND_WIDE = LostLegends.makeID("textures/gui/container/sawmill_wide.png");
	private static final Identifier BACKGROUND_WIDE_SEARCH = LostLegends.makeID("textures/gui/container/sawmill_search_wide.png");

	private static final Identifier SCROLLER_SPRITE = Identifier.ofVanilla("container/stonecutter/scroller");
	private static final Identifier SCROLLER_DISABLED_SPRITE = Identifier.ofVanilla("container/stonecutter/scroller_disabled");
	private static final Identifier RECIPE_SELECTED_SPRITE = Identifier.ofVanilla("container/stonecutter/recipe_selected");
	private static final Identifier RECIPE_HIGHLIGHTED_SPRITE = Identifier.ofVanilla("container/stonecutter/recipe_highlighted");
	private static final Identifier RECIPE_SPRITE = Identifier.ofVanilla("container/stonecutter/recipe");


	private float scrollOffs;
	private boolean scrolling;
	private int startIndex;
	private boolean displayRecipes;

	private TextFieldWidget searchBox;

	private final List<FilterableRecipe> filteredRecipes = new ArrayList<>();
	private int filteredIndex = -1;

	public SawmillScreen(SawmillMenu sawmillMenu, PlayerInventory inventory, Text component) {
		super(sawmillMenu, inventory, component);
		sawmillMenu.registerUpdateListener(this::containerChanged);
		--this.titleY;
	}

	@Override
	protected void init() {
		super.init();

		int boxX = this.x + (handler.isWide ? 41 : 53);
		int boxY = this.y + 15;
		this.searchBox = new TextFieldWidget(this.textRenderer, boxX, boxY, 69, 9, Text.translatable("itemGroup.search"));
		this.searchBox.setMaxLength(50);
		this.searchBox.setDrawsBackground(false);
		this.searchBox.setFocused(false);
		this.searchBox.setEditable(false);
		this.searchBox.setEditableColor(16777215);
		this.searchBox.setChangedListener(s -> this.refreshSearchResults());
		this.addDrawableChild(this.searchBox);

		updateSearchBarVisibility();
	}

	private void updateSearchBarVisibility() {
		boolean hasSearch = LostLegends.getConfig().hasSearchBar(handler.getRecipes().size());
		this.searchBox.visible = hasSearch;
		this.searchBox.active = hasSearch;
	}

	@Override
	protected void handledScreenTick() {
		super.handledScreenTick();
		//if (searchBox.visible) this.searchBox.tick();
	}

	private void refreshSearchResults() {
		int oldSize = filteredRecipes.size();
		this.filteredRecipes.clear();
		String filter = searchBox.getText().toLowerCase(Locale.ROOT);
		boolean isFiltered = searchBox.visible && !filter.equals("");
		for (var r : this.handler.getRecipes()) {
			if (!isFiltered || r.matchFilter(filter)) {
				this.filteredRecipes.add(r);
			}
		}
		if (oldSize != filteredRecipes.size()) {
			//only reset if the filtered list changed
			this.scrollOffs = 0;
			this.startIndex = 0;
		}

		updateSelectedIndex();
		// this makes it so after we typed something, the current result is reset as we are unselecting all clicked stuff
		// only clear if we cant keep selecting the old one
		if (filteredIndex == -1 && this.handler.getSelectedRecipeIndex() != -1
			&& this.handler.onButtonClick(client.player, -1)) {
			//also send a packet to servers to unselect
			this.client.interactionManager.clickButton(this.handler.syncId, -1);
		}

		updateSearchBarVisibility();
	}

	private void updateSelectedIndex() {
		filteredIndex = -1;
		int selectedInd = this.handler.getSelectedRecipeIndex();
		var recipes = this.handler.getRecipes();
		if (selectedInd > -1 && selectedInd < recipes.size()) {
			filteredIndex = filteredRecipes.indexOf(recipes.get(selectedInd));
		}
	}

	@Override
	public void resize(MinecraftClient minecraft, int width, int height) {
		// same as creative tab one
		String string = this.searchBox.getText();
		this.init(minecraft, width, height);
		this.searchBox.setText(string);
		this.containerChanged();
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		String string = this.searchBox.getText();
		if (this.searchBox.visible && this.searchBox.keyPressed(keyCode, scanCode, modifiers)) {
			if (!Objects.equals(string, this.searchBox.getText())) {
				this.refreshSearchResults();
			}

			return true;
		} else {
			return this.searchBox.isFocused() && this.searchBox.isVisible() && keyCode != 256 ? true : super.keyPressed(keyCode, scanCode, modifiers);
		}
	}

	@Override
	public void render(DrawContext guiGraphics, int mouseX, int mouseY, float partialTick) {
		super.render(guiGraphics, mouseX, mouseY, partialTick);
		this.drawMouseoverTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void drawBackground(DrawContext guiGraphics, float partialTick, int mouseX, int mouseY) {
		Identifier bgLocation = getBgLocation();
		guiGraphics.drawTexture(bgLocation, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);

		// scrollbar
		int barH = scrollBarHeight();
		int scrollY = minScrollY();
		float barSpan = maxScrollY() - scrollY - barH;
		int barPos = (int) (barSpan * this.scrollOffs);

		//guiGraphics.blit(bgLocation, minScrollX(), scrollY + barPos, 176 + (this.isScrollBarActive() ? 0 : 12), 0, 12, barH);

		int k = (int)(41.0F * this.scrollOffs);
		Identifier resourceLocation = this.isScrollBarActive() ? SCROLLER_SPRITE : SCROLLER_DISABLED_SPRITE;
		guiGraphics.drawGuiTexture(resourceLocation, this.x + 119, this.y + 15 + k, 12, 15);


		if (!displayRecipes) return;

		// buttons
		forEachButton((index, buttonX, buttonY) -> {
			Identifier buttonTexture;
			if (index == filteredIndex) {
				buttonTexture = RECIPE_SELECTED_SPRITE;
			} else if (mouseX >= buttonX && mouseY >= buttonY && mouseX < buttonX + 16 && mouseY < buttonY + 18) {
				buttonTexture = RECIPE_HIGHLIGHTED_SPRITE;
			}else {
				buttonTexture = RECIPE_SPRITE;
			}
			guiGraphics.drawGuiTexture(buttonTexture, buttonX, buttonY, 16, 18);
		});

		// items
		forEachButton((index, buttonX, buttonY) -> {
			ItemStack item = filteredRecipes.get(index).recipe().value().getResult(this.client.world.getRegistryManager());
			guiGraphics.drawItemWithoutEntity(item, buttonX, buttonY + 1);
			guiGraphics.drawItemInSlot(textRenderer, item, buttonX, buttonY + 1);
		});
	}

	@NotNull
	private Identifier getBgLocation() {
		if (handler.isWide) {
			return searchBox.visible ? BACKGROUND_WIDE_SEARCH : BACKGROUND_WIDE;
		}
		return searchBox.visible ? BACKGROUND_SEARCH : BACKGROUND;
	}

	@Override
	protected void drawMouseoverTooltip(DrawContext guiGraphics, int mouseX, int mouseY) {
		super.drawMouseoverTooltip(guiGraphics, mouseX, mouseY);
		if (this.displayRecipes) {
			forEachButton((index, buttonX, buttonY) -> {
				if (mouseX >= buttonX && mouseX < buttonX + 16 && mouseY >= buttonY && mouseY < buttonY + 18) {
					guiGraphics.drawItemTooltip(this.textRenderer, (filteredRecipes.get(index)).recipe().value()
						.getResult(this.client.world.getRegistryManager()), mouseX, mouseY);
				}
			});
		}
	}

	@Override
	protected void drawForeground(DrawContext guiGraphics, int mouseX, int mouseY) {
		super.drawForeground(guiGraphics, mouseX, mouseY);
		if (filteredIndex >= 0 && filteredIndex < filteredRecipes.size()) {
			int input = filteredRecipes.get(filteredIndex).recipe().value().getInputCount();
			if (input != 1) {
				String multiplier = input + "x";
				int labelX = this.titleX + (handler.isWide ? -4 : 0);
				guiGraphics.drawText(this.textRenderer, multiplier, labelX, this.titleY + 37, 4210752, false);
			}
		}
	}

	private int buttonCount() {
		return getRows() * buttonsPerRow();
	}

	private int buttonsPerRow() {
		return handler.isWide ? 5 : 4;
	}

	private int getRows() {
		return searchBox.visible ? 2 : 3;
	}

	private int minScrollX() {
		return this.x + (handler.isWide ? 123 : 119);
	}

	private int maxScrollX() {
		return minScrollX() + 12;
	}

	private int minScrollY() {
		return this.y + (searchBox.visible ? 29 : 15);
	}

	private int maxScrollY() {
		return this.y + (searchBox.visible ? 29 + 36 : 15 + 55);
	}

	private int scrollBarHeight() {
		return searchBox.visible ? 11 : 15;
	}

	private void forEachButton(ButtonConsumer buttonConsumer) {
		int buttonBoxX = this.x + (handler.isWide ? 40 : 52);
		int buttonBoxY = this.y + (searchBox.visible ? 27 : 13);
		int lastVisibleElementIndex = this.startIndex + buttonCount();
		int buttonsPerRow = buttonsPerRow();
		for (int index = this.startIndex; index < lastVisibleElementIndex && index < filteredRecipes.size(); ++index) {
			int visualIndex = index - this.startIndex;
			int buttonX = buttonBoxX + (visualIndex % buttonsPerRow) * 16;
			int buttonY = buttonBoxY + (visualIndex / buttonsPerRow) * 18 + 2;
			buttonConsumer.accept(index, buttonX, buttonY);
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		this.scrolling = false;
		if (this.displayRecipes) {
			AtomicReference<Boolean> success = new AtomicReference<>(false);
			forEachButton((index, buttonX, buttonY) -> {
				if (success.get()) return;
				int actualIndex = handler.getRecipes().indexOf(filteredRecipes.get(index));
				if (mouseX >= buttonX && mouseX < buttonX + 16 && mouseY >= buttonY && mouseY < buttonY + 18) {
					if (this.handler.onButtonClick(this.client.player, actualIndex)) {
						MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(LostLegendsSoundEvents.SAWMILL_SELECT.get(), 1.0F));
						this.client.interactionManager.clickButton(this.handler.syncId, actualIndex);
						updateSelectedIndex();
					}
					success.set(true);
				}
			});

			if (success.get()) return true;

			if (mouseX >= minScrollX() && mouseX < maxScrollX() && mouseY >= minScrollY() && mouseY < maxScrollY()) {
				this.scrolling = true;
			}
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
		if (this.scrolling && this.isScrollBarActive()) {
			int min = minScrollY();
			int max = maxScrollY();
			this.scrollOffs = ((float) mouseY - min - 7.5F) / ((max - min) - 15.0F);
			this.scrollOffs = MathHelper.clamp(this.scrollOffs, 0.0F, 1.0F);
			this.startIndex = (int) ((this.scrollOffs * this.getOffscreenRows()) + 0.5) * 4;
			return true;
		} else {
			return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
		}
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
		if (this.isScrollBarActive()) {
			int i = this.getOffscreenRows();
			float f = (float)scrollY / (float)i;
			this.scrollOffs = MathHelper.clamp(this.scrollOffs - f, 0.0F, 1.0F);
			this.startIndex = (int)((double)(this.scrollOffs * (float)i) + 0.5) * 4;
		}

		return true;
	}

	private boolean isScrollBarActive() {
		return this.displayRecipes && filteredRecipes.size() > buttonCount();
	}

	protected int getOffscreenRows() {
		return (filteredRecipes.size() + 4 - 1) / 4 - getRows();
	}

	private void containerChanged() {
		this.displayRecipes = this.handler.hasInputItem();
		if (!this.displayRecipes) {
			this.scrollOffs = 0.0F;
			this.startIndex = 0;
			this.searchBox.setText("");
		} else this.setFocused(searchBox);
		this.searchBox.setEditable(displayRecipes);
		this.searchBox.setFocused(displayRecipes);

		//recipes could have changed here so we need to refresh
		this.refreshSearchResults();
	}

	private interface ButtonConsumer {
		void accept(int index, int buttonX, int buttonY);
	}

}

