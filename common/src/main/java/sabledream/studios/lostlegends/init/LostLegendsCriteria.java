package sabledream.studios.lostlegends.init;

import sabledream.studios.lostlegends.advancements.criterion.CompleteHideAndSeekGameCriterion;
import sabledream.studios.lostlegends.platform.RegistryHelper;
import net.minecraft.advancement.criterion.LightningStrikeCriterion;
import net.minecraft.advancement.criterion.TameAnimalCriterion;

import java.util.function.Supplier;

public final class LostLegendsCriteria
{
	public static final Supplier<TameAnimalCriterion> TAME_GLARE;
	public static final Supplier<LightningStrikeCriterion> ACTIVATE_ZOMBIE_HORSE_TRAP;
	public static final Supplier<CompleteHideAndSeekGameCriterion> COMPLETE_HIDE_AND_SEEK_GAME;

	static {
		TAME_GLARE = RegistryHelper.registerCriterion("tame_glare", new TameAnimalCriterion());
		ACTIVATE_ZOMBIE_HORSE_TRAP = RegistryHelper.registerCriterion("activate_zombie_horse_trap", new LightningStrikeCriterion());
		COMPLETE_HIDE_AND_SEEK_GAME = RegistryHelper.registerCriterion("complete_hide_and_seek_game", new CompleteHideAndSeekGameCriterion());
	}

	public static void init() {
	}

	private LostLegendsCriteria() {
	}
}
