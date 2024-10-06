package sabledream.studios.lostlegends.api.recipie;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.CuttingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.init.LostLegendsBlocks;
import sabledream.studios.lostlegends.init.LostLegendsRecipes;

public class WoodcuttingRecipe extends CuttingRecipe
{
	private final int inputCount;

	public WoodcuttingRecipe(String group, Ingredient ingredient, ItemStack itemStack, int inputCount) {
		super(LostLegendsRecipes.WOODCUTTING_RECIPE.get(), LostLegendsRecipes.WOODCUTTING_RECIPE_SERIALIZER.get(),
			group, ingredient, itemStack);
		this.inputCount = inputCount;
	}

	public int getInputCount() {
		return inputCount;
	}

	@Override
	public boolean matches(SingleStackRecipeInput container, World level) {
		ItemStack item = container.getStackInSlot(0);
		return this.ingredient.test(item) && item.getCount() >= inputCount;
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(LostLegendsBlocks.SAWMILL_BLOCK.get());
	}

	@Override
	public boolean isIgnoredInRecipeBook() {
		return true; //for recipe book
	}

	public static class Serializer implements RecipeSerializer<WoodcuttingRecipe>
	{

		private final MapCodec<WoodcuttingRecipe> codec;
		private final PacketCodec<RegistryByteBuf, WoodcuttingRecipe> streamCodec;

		public Serializer() {

			this.codec = RecordCodecBuilder.mapCodec(
				instance -> instance.group(
						Codec.STRING.optionalFieldOf("group", "").forGetter(arg -> arg.group),
						Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(arg -> arg.ingredient),
						ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(arg -> arg.result),
						Codecs.POSITIVE_INT.optionalFieldOf("ingredient_count", 1).forGetter(arg -> arg.inputCount)
					)
					.apply(instance, WoodcuttingRecipe::new)
			);
			this.streamCodec = PacketCodec.tuple(
				PacketCodecs.STRING, arg -> arg.group,
				Ingredient.PACKET_CODEC, arg -> arg.ingredient,
				ItemStack.PACKET_CODEC, arg -> arg.result,
				PacketCodecs.VAR_INT, arg -> arg.inputCount,
				WoodcuttingRecipe::new
			);
		}
		@Override
		public MapCodec<WoodcuttingRecipe> codec() {
			return codec;
		}

		@Override
		public PacketCodec<RegistryByteBuf, WoodcuttingRecipe> packetCodec() {
			return streamCodec;
		}
	}
}

