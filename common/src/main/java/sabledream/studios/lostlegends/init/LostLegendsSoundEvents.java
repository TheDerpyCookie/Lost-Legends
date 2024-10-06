package sabledream.studios.lostlegends.init;

import net.minecraft.client.sound.Sound;
import sabledream.studios.lostlegends.LostLegends;
import sabledream.studios.lostlegends.platform.RegistryHelper;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import java.util.function.Supplier;

/**
 * @see SoundEvents
 */
public final class LostLegendsSoundEvents
{
	public static final Supplier<SoundEvent> BLOCK_TERMITE_MOUND_TERMITE_GNAW;
	public static final Supplier<SoundEvent> BLOCK_TERMITE_MOUND_TERMITE_IDLE;
	public static final Supplier<SoundEvent> BLOCK_TERMITE_MOUND_BREAK;
	public static final Supplier<SoundEvent> BLOCK_TERMITE_MOUND_STEP;
	public static final Supplier<SoundEvent> BLOCK_TERMITE_MOUND_PLACE;
	public static final Supplier<SoundEvent> BLOCK_TERMITE_MOUND_HIT;
	public static final Supplier<SoundEvent> BLOCK_TERMITE_MOUND_FALL;
	public static final Supplier<SoundEvent> BLOCK_TERMITE_MOUND_ENTER;
	public static final Supplier<SoundEvent> BLOCK_TERMITE_MOUND_EXIT;
	public static final Supplier<SoundEvent>  BLOCK_TERMITE_MOUND_TERMITE_GNAW_FINISH;

	public static final Supplier<SoundEvent>  LOG_HOLLOWED;
	public static final Supplier<SoundEvent>  STEM_HOLLOWED;

	public static final Supplier<SoundEvent> SAWMILL_TAKE;
	public static final Supplier<SoundEvent> SAWMILL_SELECT;






	public static final Supplier<SoundEvent> ENTITY_BARNACLE_AMBIENT;
	public static final Supplier<SoundEvent> ENTITY_BARNACLE_HURT;
	public static final Supplier<SoundEvent> ENTITY_COPPER_GOLEM_DEATH;
	public static final Supplier<SoundEvent> ENTITY_COPPER_GOLEM_HEAD_SPIN;
	public static final Supplier<SoundEvent> ENTITY_COPPER_GOLEM_HURT;
	public static final Supplier<SoundEvent> ENTITY_COPPER_GOLEM_REPAIR;
	public static final Supplier<SoundEvent> ENTITY_COPPER_GOLEM_STEP;
	public static final Supplier<SoundEvent> ENTITY_GLARE_AMBIENT;
	public static final Supplier<SoundEvent> ENTITY_GLARE_DEATH;
	public static final Supplier<SoundEvent> ENTITY_GLARE_EAT;
	public static final Supplier<SoundEvent> ENTITY_GLARE_GRUMPINESS;
	public static final Supplier<SoundEvent> ENTITY_GLARE_GRUMPINESS_SHORT;
	public static final Supplier<SoundEvent> ENTITY_GLARE_HURT;
	public static final Supplier<SoundEvent> ENTITY_GLARE_RUSTLE;
	public static final Supplier<SoundEvent> ENTITY_GLARE_SHAKE;
	public static final Supplier<SoundEvent> ENTITY_ICE_CHUNK_AMBIENT;
	public static final Supplier<SoundEvent> ENTITY_ICE_CHUNK_HIT;
	public static final Supplier<SoundEvent> ENTITY_ICE_CHUNK_SUMMON;
	public static final Supplier<SoundEvent> ENTITY_ICEOLOGER_AMBIENT;
	public static final Supplier<SoundEvent> ENTITY_ICEOLOGER_CAST_SPELL;
	public static final Supplier<SoundEvent> ENTITY_ICEOLOGER_DEATH;
	public static final Supplier<SoundEvent> ENTITY_ICEOLOGER_HURT;
	public static final Supplier<SoundEvent> ENTITY_ICEOLOGER_PREPARE_SLOWNESS;
	public static final Supplier<SoundEvent> ENTITY_ICEOLOGER_PREPARE_SUMMON;
	public static final Supplier<SoundEvent> ENTITY_MAULER_BITE;
	public static final Supplier<SoundEvent> ENTITY_MAULER_DEATH;
	public static final Supplier<SoundEvent> ENTITY_MAULER_GROWL;
	public static final Supplier<SoundEvent> ENTITY_MAULER_HURT;
	public static final Supplier<SoundEvent> ENTITY_MOOBLOOM_CONVERT;
	public static final Supplier<SoundEvent> ENTITY_PLAYER_MIRROR_MOVE;
	public static final Supplier<SoundEvent> ENTITY_RASCAL_AMBIENT;
	public static final Supplier<SoundEvent> ENTITY_RASCAL_DISAPPEAR;
	public static final Supplier<SoundEvent> ENTITY_RASCAL_HURT;
	public static final Supplier<SoundEvent> ENTITY_RASCAL_NOD;
	public static final Supplier<SoundEvent> ENTITY_RASCAL_REAPPEAR;
	public static final Supplier<SoundEvent> ENTITY_RASCAL_REWARD;
	public static final Supplier<SoundEvent> ENTITY_RASCAL_REWARD_BAD;
	public static final Supplier<SoundEvent> ENTITY_TUFF_GOLEM_GLUE_ON;
	public static final Supplier<SoundEvent> ENTITY_TUFF_GOLEM_GLUE_OFF;
	public static final Supplier<SoundEvent> ENTITY_TUFF_GOLEM_HURT;
	public static final Supplier<SoundEvent> ENTITY_TUFF_GOLEM_MOVE;
	public static final Supplier<SoundEvent> ENTITY_TUFF_GOLEM_REPAIR;
	public static final Supplier<SoundEvent> ENTITY_TUFF_GOLEM_SLEEP;
	public static final Supplier<SoundEvent> ENTITY_TUFF_GOLEM_STEP;
	public static final Supplier<SoundEvent> ENTITY_TUFF_GOLEM_WAKE;
	public static final Supplier<SoundEvent> ENTITY_WILDFIRE_SHIELD_DEBRIS_IMPACT;
	public static final Supplier<SoundEvent> ENTITY_WILDFIRE_AMBIENT;
	public static final Supplier<SoundEvent> ENTITY_WILDFIRE_DEATH;
	public static final Supplier<SoundEvent> ENTITY_WILDFIRE_HURT;
	public static final Supplier<SoundEvent> ENTITY_WILDFIRE_SHIELD_BREAK;
	public static final Supplier<SoundEvent> ENTITY_WILDFIRE_SHOCKWAVE;
	public static final Supplier<SoundEvent> ENTITY_WILDFIRE_SHOOT;
	public static final Supplier<SoundEvent> ENTITY_WILDFIRE_STEP;
	public static final Supplier<SoundEvent> ENTITY_WILDFIRE_SUMMON_BLAZE;
	public static final Supplier<SoundEvent> ENTITY_CRAB_STEP;
	public static final Supplier<SoundEvent> ENTITY_PENGUIN_AMBIENT;
	public static final Supplier<SoundEvent> ENTITY_PENGUIN_STEP;
	public static final Supplier<SoundEvent> CLUCHSHROOM_LAY_MUSHROOM;
	public static final Supplier<SoundEvent> FANCY_CHICKEN_FLEE;
	public static final Supplier<SoundEvent> FANCY_CHICKEN_HURT;
	public static final Supplier<SoundEvent> FANCY_CHICKEN_AMBIENT;
	public static final Supplier<SoundEvent> FANCY_CHICKEN_DEATH;
	public static final Supplier<SoundEvent> MELON_GOLEM_SEED_HIT;
	public static final Supplier<SoundEvent> MELON_GOLEM_ATTACK;
	public static final Supplier<SoundEvent> MELON_GOLEM_AGGRO;
	public static final Supplier<SoundEvent> MOOLIP_PLANT;
	public static final Supplier<SoundEvent> FURNACE_GOLEM_ATTACK;
	public static final Supplier<SoundEvent> FURNACE_GOLEM_AGGRO;
	public static final Supplier<SoundEvent> SKELETON_WOLF_GROWL;
	public static final Supplier<SoundEvent> SKELETON_WOLF_WHINE;
	public static final Supplier<SoundEvent> SKELETON_WOLF_HURT;
	public static final Supplier<SoundEvent> SKELETON_WOLF_DEATH;
	public static final Supplier<SoundEvent> SKELETON_WOLF_AMBIENT;
	public static final Supplier<SoundEvent> SKELETON_WOLF_HOWL;
	public static final Supplier<SoundEvent> VILER_WITCH_CASTING;

	public static final Supplier<SoundEvent> RAINBOW_SHEEP_AMBIENT;
	public static final Supplier<SoundEvent> RAINBOW_SHEEP_DEATH;

	public static final Supplier<SoundEvent> FIREFLY_DEATH;
	public static final Supplier<SoundEvent> FIREFLY_AMBIENT;

	public static final Supplier<SoundEvent> ENTITY_TUMBLEWEED_BOUNCE;
	public static final Supplier<SoundEvent> ENTITY_TUMBLEWEED_BREAK;
	public static final Supplier<SoundEvent> ENTITY_TUMBLEWEED_DAMAGE;
	public static final Supplier<SoundEvent> BLOCK_TUMBLEWEED_SHEAR;

	public static final Supplier<SoundEvent> BLOCK_TUMBLEWEED_PLANT_BREAK;
	public static final Supplier<SoundEvent> BLOCK_TUMBLEWEED_PLANT_STEP;
	public static final Supplier<SoundEvent> BLOCK_TUMBLEWEED_PLANT_PLACE;
	public static final Supplier<SoundEvent> BLOCK_TUMBLEWEED_PLANT_HIT;
	public static final Supplier<SoundEvent> BLOCK_TUMBLEWEED_PLANT_FALL;

	static {
		SAWMILL_TAKE = register("ui", "sawmill.take_result");
		SAWMILL_SELECT = register("ui", "sawmill.select_recipe");
		LOG_HOLLOWED = register("block", "log_hollowed");
		STEM_HOLLOWED = register("block", "stem_hollowed");
		BLOCK_TUMBLEWEED_PLANT_BREAK = register("block", "tumbleweed_plant.break");
		BLOCK_TUMBLEWEED_PLANT_STEP = register("block", "tumbleweed_plant.step");
		BLOCK_TUMBLEWEED_PLANT_PLACE = register("block", "tumbleweed_plant.place");
		BLOCK_TUMBLEWEED_PLANT_HIT = register("block", "tumbleweed_plant.hit");
		BLOCK_TUMBLEWEED_PLANT_FALL = register("block", "tumbleweed_plant.fall");

		ENTITY_TUMBLEWEED_BOUNCE = register("entity", "tumbleweed.bounce");
		ENTITY_TUMBLEWEED_BREAK = register("entity", "tumbleweed.break");
		ENTITY_TUMBLEWEED_DAMAGE = register("entity", "tumbleweed.damage");
		BLOCK_TUMBLEWEED_SHEAR = register("block", "tumbleweed.shear");
		FIREFLY_DEATH = register("entity", "firefly.death");
		FIREFLY_AMBIENT = register("entity", "firefly.ambient");
		ENTITY_COPPER_GOLEM_DEATH = register("entity", "copper_golem.death");
		ENTITY_COPPER_GOLEM_HEAD_SPIN = register("entity", "copper_golem.head_spin");
		ENTITY_COPPER_GOLEM_HURT = register("entity", "copper_golem.hurt");
		ENTITY_COPPER_GOLEM_REPAIR = register("entity", "copper_golem.repair");
		ENTITY_COPPER_GOLEM_STEP = register("entity", "copper_golem.step");
		ENTITY_GLARE_AMBIENT = register("entity", "glare.ambient");
		ENTITY_GLARE_DEATH = register("entity", "glare.death");
		ENTITY_GLARE_EAT = register("entity", "glare.eat");
		ENTITY_GLARE_GRUMPINESS = register("entity", "glare.grumpiness");
		ENTITY_GLARE_GRUMPINESS_SHORT = register("entity", "glare.grumpiness_short");
		ENTITY_GLARE_HURT = register("entity", "glare.hurt");
		ENTITY_GLARE_RUSTLE = register("entity", "glare.rustle");
		ENTITY_GLARE_SHAKE = register("entity", "glare.shake");
		ENTITY_ICE_CHUNK_AMBIENT = register("entity", "ice_chunk.ambient");
		ENTITY_ICE_CHUNK_HIT = register("entity", "ice_chunk.hit");
		ENTITY_ICE_CHUNK_SUMMON = register("entity", "ice_chunk.summon");
		ENTITY_ICEOLOGER_AMBIENT = register("entity", "iceologer.ambient");
		ENTITY_ICEOLOGER_CAST_SPELL = register("entity", "iceologer.cast_spell");
		ENTITY_ICEOLOGER_DEATH = register("entity", "iceologer.death");
		ENTITY_ICEOLOGER_HURT = register("entity", "iceologer.hurt");
		ENTITY_ICEOLOGER_PREPARE_SLOWNESS = register("entity", "iceologer.prepare_slowness");
		ENTITY_ICEOLOGER_PREPARE_SUMMON = register("entity", "iceologer.prepare_summon");
		ENTITY_MAULER_BITE = register("entity", "mauler.bite");
		ENTITY_MAULER_DEATH = register("entity", "mauler.death");
		ENTITY_MAULER_GROWL = register("entity", "mauler.growl");
		ENTITY_MAULER_HURT = register("entity", "mauler.hurt");
		ENTITY_MOOBLOOM_CONVERT = register("entity", "moobloom.convert");
		ENTITY_PLAYER_MIRROR_MOVE = register("entity", "player.mirror_move");
		ENTITY_RASCAL_AMBIENT = register("entity", "rascal.ambient");
		ENTITY_RASCAL_DISAPPEAR = register("entity", "rascal.disappear");
		ENTITY_RASCAL_HURT = register("entity", "rascal.hurt");
		ENTITY_RASCAL_NOD = register("entity", "rascal.nod");
		ENTITY_RASCAL_REAPPEAR = register("entity", "rascal.reappear");
		ENTITY_RASCAL_REWARD = register("entity", "rascal.reward");
		ENTITY_RASCAL_REWARD_BAD = register("entity", "rascal.reward_bad");
		ENTITY_TUFF_GOLEM_GLUE_ON = register("entity", "tuff_golem.glue_on");
		ENTITY_TUFF_GOLEM_GLUE_OFF = register("entity", "tuff_golem.glue_off");
		ENTITY_TUFF_GOLEM_HURT = register("entity", "tuff_golem.hurt");
		ENTITY_TUFF_GOLEM_MOVE = register("entity", "tuff_golem.move");
		ENTITY_TUFF_GOLEM_REPAIR = register("entity", "tuff_golem.repair");
		ENTITY_TUFF_GOLEM_SLEEP = register("entity", "tuff_golem.sleep");
		ENTITY_TUFF_GOLEM_STEP = register("entity", "tuff_golem.step");
		ENTITY_TUFF_GOLEM_WAKE = register("entity", "tuff_golem.wake");
		ENTITY_WILDFIRE_SHIELD_DEBRIS_IMPACT = register("entity", "shield_debris.impact");
		ENTITY_WILDFIRE_AMBIENT = register("entity", "wildfire.ambient");
		ENTITY_WILDFIRE_DEATH = register("entity", "wildfire.death");
		ENTITY_WILDFIRE_HURT = register("entity", "wildfire.hurt");
		ENTITY_WILDFIRE_SHIELD_BREAK = register("entity", "wildfire.shield_break");
		ENTITY_WILDFIRE_SHOCKWAVE = register("entity", "wildfire.shockwave");
		ENTITY_WILDFIRE_SHOOT = register("entity", "wildfire.shoot");
		ENTITY_WILDFIRE_STEP = register("entity", "wildfire.step");
		ENTITY_WILDFIRE_SUMMON_BLAZE = register("entity", "wildfire.summon_blaze");
		ENTITY_CRAB_STEP = register("entity", "crab.step");
		ENTITY_BARNACLE_AMBIENT = register("entity", "barnacle.ambient");
		ENTITY_BARNACLE_HURT = register("entity", "barnacle.hurt");
		ENTITY_PENGUIN_AMBIENT =register("entity","penguin.ambient");
		ENTITY_PENGUIN_STEP = register("entity", "penguin.step");
		CLUCHSHROOM_LAY_MUSHROOM = register("entity", "cluckshroom.lay_mushroom");
		FANCY_CHICKEN_FLEE = register("entity", "fancy_chicken.flee");
		FANCY_CHICKEN_HURT = register("entity", "fancy_chicken.hurt");
		FANCY_CHICKEN_DEATH = register("entity", "fancy_chicken.death");
		FANCY_CHICKEN_AMBIENT = register("entity", "fancy_chicken.ambient");
		MOOLIP_PLANT = register("entity", "moolip.plant");
		MELON_GOLEM_ATTACK = register("entity", "melon_golem.attack");
		MELON_GOLEM_SEED_HIT = register("entity", "melon_golem.seed.hit");
		MELON_GOLEM_AGGRO = register("entity", "melon_golem.aggro");
		FURNACE_GOLEM_AGGRO = register("entity", "furnace_golem.aggro");
		SKELETON_WOLF_GROWL = register("entity", "skeleton_wolf.growl");
		SKELETON_WOLF_WHINE = register("entity", "skeleton_wolf.whine");
		SKELETON_WOLF_HURT = register("entity", "skeleton_wolf.hurt");
		SKELETON_WOLF_DEATH = register("entity", "skeleton_wolf.death");
		FURNACE_GOLEM_ATTACK = register("entity", "skeleton_wolf.attack");
		SKELETON_WOLF_AMBIENT = register("entity", "skeleton_wolf.ambient");
		SKELETON_WOLF_HOWL =  register("entity", "skeleton_wolf.howl");
		VILER_WITCH_CASTING = register("entity", "viler_witch.casting");

		RAINBOW_SHEEP_AMBIENT = register("entity", "rainbow_sheep.ambient");
		RAINBOW_SHEEP_DEATH = register("entity", "rainbow_sheep.death");


		BLOCK_TERMITE_MOUND_TERMITE_IDLE = register("block", "termite_mound.termite_idle");
		BLOCK_TERMITE_MOUND_TERMITE_GNAW = register("block","termite_mound.termite_gnaw");
		BLOCK_TERMITE_MOUND_BREAK = register("block", "termite_mound.break");
		BLOCK_TERMITE_MOUND_STEP = register("block", "termite_mound.step");
		BLOCK_TERMITE_MOUND_PLACE = register("block", "termite_mound.place");
		BLOCK_TERMITE_MOUND_HIT = register("block", "termite_mound.hit");
		BLOCK_TERMITE_MOUND_FALL = register("block", "termite_mound.fall");
		BLOCK_TERMITE_MOUND_ENTER = register("block", "termite_mound.enter");
		BLOCK_TERMITE_MOUND_EXIT = register("block", "termite_mound.exit");
		BLOCK_TERMITE_MOUND_TERMITE_GNAW_FINISH = register("block", "termite_mound.termite_gnaw_finish");
	}


	private static Supplier<SoundEvent> register(String type, String name) {
		String id = type + "." + name;
		var soundEvent = SoundEvent.of(LostLegends.makeID(id));

		return RegistryHelper.registerSoundEvent(id, () -> soundEvent);
	}

	public static void init() {
	}

	private LostLegendsSoundEvents() {
	}
}
