package net.sf.josceleton.core.api.entity.body;

import java.util.Arrays;
import java.util.Collections;

import net.sf.josceleton.core.api.entity.body.BodyPartImplProvider.AnklesImpl;
import net.sf.josceleton.core.api.entity.body.BodyPartImplProvider.ElbowsImpl;
import net.sf.josceleton.core.api.entity.body.BodyPartImplProvider.FeetImpl;
import net.sf.josceleton.core.api.entity.body.BodyPartImplProvider.HandsImpl;
import net.sf.josceleton.core.api.entity.body.BodyPartImplProvider.HeadImpl;
import net.sf.josceleton.core.api.entity.body.BodyPartImplProvider.HipsImpl;
import net.sf.josceleton.core.api.entity.body.BodyPartImplProvider.KneesImpl;
import net.sf.josceleton.core.api.entity.body.BodyPartImplProvider.NeckImpl;
import net.sf.josceleton.core.api.entity.body.BodyPartImplProvider.ShouldersImpl;
import net.sf.josceleton.core.api.entity.body.BodyPartImplProvider.TorsoImpl;
import net.sf.josceleton.core.api.entity.body.BodyParts.Ankles;
import net.sf.josceleton.core.api.entity.body.BodyParts.Elbows;
import net.sf.josceleton.core.api.entity.body.BodyParts.Feet;
import net.sf.josceleton.core.api.entity.body.BodyParts.Hands;
import net.sf.josceleton.core.api.entity.body.BodyParts.Head;
import net.sf.josceleton.core.api.entity.body.BodyParts.Hips;
import net.sf.josceleton.core.api.entity.body.BodyParts.Knees;
import net.sf.josceleton.core.api.entity.body.BodyParts.Neck;
import net.sf.josceleton.core.api.entity.body.BodyParts.Shoulders;
import net.sf.josceleton.core.api.entity.body.BodyParts.Torso;

/**
 * Implements an hierarchical enum.
 * 
 * @since 0.1
 */
public final class Body {
	
	private static final Head HEAD           = new HeadImpl();
	private static final Neck NECK           = new NeckImpl();
	private static final Torso TORSO         = new TorsoImpl();
	private static final Shoulders SHOULDERS = new ShouldersImpl();
	private static final Elbows ELBOWS       = new ElbowsImpl();
	private static final Hands HANDS         = new HandsImpl();
	private static final Hips HIPS           = new HipsImpl();
	private static final Knees KNEES         = new KneesImpl();
	private static final Ankles ANKLES       = new AnklesImpl();
	private static final Feet FEET           = new FeetImpl();

	private static final Iterable<BodyPart> ALL_BODY_PARTS;
	static {
		ALL_BODY_PARTS = Collections.unmodifiableList(Arrays.asList(
			HEAD,
			NECK,
			TORSO,
			SHOULDERS.LEFT(), SHOULDERS.RIGHT(),
			ELBOWS.LEFT(),    ELBOWS.RIGHT(),
			HANDS.LEFT(),     HANDS.RIGHT(),
			HIPS.LEFT(),      HIPS.RIGHT(),
			KNEES.LEFT(),     KNEES.RIGHT(),
			ANKLES.LEFT(),    ANKLES.RIGHT(),
			FEET.LEFT(),      FEET.RIGHT()
		));
		
	}

	
	private Body() { /* as its a pseudo-enum => not instantiable */ }
	
	
	/** @since 0.1 */
	public static Iterable<BodyPart> values() {
		return ALL_BODY_PARTS;
	}
	
	
	/** @since 0.1 */
	public static Head HEAD() { return Body.HEAD; }

	/** @since 0.1 */
	public static Neck NECK() { return Body.NECK; }

	/** @since 0.1 */
	public static Torso TORSO() { return Body.TORSO; }
	
	
	/** @since 0.1 */
	public static Shoulders SHOULDER() { return Body.SHOULDERS; }

	/** @since 0.1 */
	public static Elbows ELBOW() { return Body.ELBOWS; }

	/** @since 0.1 */
	public static Hands HAND() { return Body.HANDS; }

	/** @since 0.1 */
	public static Hips HIP() { return Body.HIPS; }

	/** @since 0.1 */
	public static Knees KNEE() { return Body.KNEES; }

	/** @since 0.1 */
	public static Ankles ANKLE() { return Body.ANKLES; }

	/** @since 0.1 */
	public static Feet FOOT() { return Body.FEET; }

}
