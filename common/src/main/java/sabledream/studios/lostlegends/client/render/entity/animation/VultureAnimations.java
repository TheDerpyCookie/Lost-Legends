package sabledream.studios.lostlegends.client.render.entity.animation;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class VultureAnimations {

	public static final Animation FLYING = Animation.Builder.create(0f)
		.addBoneAnimation("vulture",
			new Transformation(Transformation.Targets.TRANSLATE,
				new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 10f),
					Transformation.Interpolations.LINEAR)))
		.addBoneAnimation("vulture",
			new Transformation(Transformation.Targets.ROTATE,
				new Keyframe(0f, AnimationHelper.createRotationalVector(-52.5f, 0f, 0f),
					Transformation.Interpolations.LINEAR)))
		.addBoneAnimation("neck",
			new Transformation(Transformation.Targets.ROTATE,
				new Keyframe(0f, AnimationHelper.createRotationalVector(20f, 0f, 0f),
					Transformation.Interpolations.LINEAR)))
		.addBoneAnimation("left_wing",
			new Transformation(Transformation.Targets.ROTATE,
				new Keyframe(0f, AnimationHelper.createRotationalVector(-37.5f, 0f, 0f),
					Transformation.Interpolations.LINEAR)))
		.addBoneAnimation("right_wing",
			new Transformation(Transformation.Targets.ROTATE,
				new Keyframe(0f, AnimationHelper.createRotationalVector(-37.5f, 0f, 0f),
					Transformation.Interpolations.LINEAR)))
		.addBoneAnimation("right_leg",
			new Transformation(Transformation.Targets.ROTATE,
				new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
					Transformation.Interpolations.LINEAR))).build();
	public static final Animation FLYING_AGRESSIVE = Animation.Builder.create(0f)
		.addBoneAnimation("vulture",
			new Transformation(Transformation.Targets.TRANSLATE,
				new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 10f),
					Transformation.Interpolations.LINEAR)))
		.addBoneAnimation("vulture",
			new Transformation(Transformation.Targets.ROTATE,
				new Keyframe(0f, AnimationHelper.createRotationalVector(-52.5f, 0f, 0f),
					Transformation.Interpolations.LINEAR)))
		.addBoneAnimation("neck",
			new Transformation(Transformation.Targets.ROTATE,
				new Keyframe(0f, AnimationHelper.createRotationalVector(20f, 0f, 0f),
					Transformation.Interpolations.LINEAR)))
		.addBoneAnimation("right_wing",
			new Transformation(Transformation.Targets.ROTATE,
				new Keyframe(0f, AnimationHelper.createRotationalVector(-37.5f, 0f, 0f),
					Transformation.Interpolations.LINEAR)))
		.addBoneAnimation("right_leg",
			new Transformation(Transformation.Targets.ROTATE,
				new Keyframe(0f, AnimationHelper.createRotationalVector(85f, 0f, 0f),
					Transformation.Interpolations.LINEAR)))
		.addBoneAnimation("left_wing",
			new Transformation(Transformation.Targets.ROTATE,
				new Keyframe(0f, AnimationHelper.createRotationalVector(-37.5f, 0f, 0f),
					Transformation.Interpolations.LINEAR)))
		.addBoneAnimation("left_leg",
			new Transformation(Transformation.Targets.ROTATE,
				new Keyframe(0f, AnimationHelper.createRotationalVector(85f, 0f, 0f),
					Transformation.Interpolations.LINEAR))).build();
}