package sabledream.studios.lostlegends.api.recipie;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.DynamicRegistryManager;

import java.util.Locale;

public record FilterableRecipe(RecipeEntry<WoodcuttingRecipe> recipe, ItemStack output)
{
	public static FilterableRecipe of(RecipeEntry<WoodcuttingRecipe> recipe) {
		return new FilterableRecipe(recipe, recipe.value().getResult(DynamicRegistryManager.EMPTY));
	}

	public boolean matchFilter(String filter) {
		return output.getName().getString().toLowerCase(Locale.ROOT).contains(filter);
	}
}
