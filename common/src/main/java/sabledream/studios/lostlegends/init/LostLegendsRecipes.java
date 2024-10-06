package sabledream.studios.lostlegends.init;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.api.recipie.WoodcuttingRecipe;
import sabledream.studios.lostlegends.platform.RegistryHelper;

import java.util.function.Supplier;

public class LostLegendsRecipes
{

	public static final Supplier<RecipeSerializer<WoodcuttingRecipe>> WOODCUTTING_RECIPE_SERIALIZER = RegistryHelper.registerRecipeSerializer(
		LostLegends.makeID("woodcutting"), WoodcuttingRecipe.Serializer::new);

	public static final Supplier<RecipeType<WoodcuttingRecipe>> WOODCUTTING_RECIPE = RegistryHelper.registerRecipeType(
		LostLegends.makeID("woodcutting"));

}
