package sabledream.studios.lostlegends.entity.ai.brain;

import net.minecraft.recipe.Ingredient;
import sabledream.studios.lostlegends.tag.LostLegendsTags;

public class OstrichBrain
{

	public static Ingredient getTemptItems() {
		return Ingredient.fromTag(LostLegendsTags.OSTRICH_TEMPT_ITEMS);
	}
}
