package net.sf.josceleton.core.api.entity.joint;

import java.util.Arrays;
import java.util.Collections;

import net.sf.josceleton.core.api.entity.joint.JointImplProvider.AnklesImpl;
import net.sf.josceleton.core.api.entity.joint.JointImplProvider.ElbowsImpl;
import net.sf.josceleton.core.api.entity.joint.JointImplProvider.FeetImpl;
import net.sf.josceleton.core.api.entity.joint.JointImplProvider.HandsImpl;
import net.sf.josceleton.core.api.entity.joint.JointImplProvider.HeadImpl;
import net.sf.josceleton.core.api.entity.joint.JointImplProvider.HipsImpl;
import net.sf.josceleton.core.api.entity.joint.JointImplProvider.KneesImpl;
import net.sf.josceleton.core.api.entity.joint.JointImplProvider.NeckImpl;
import net.sf.josceleton.core.api.entity.joint.JointImplProvider.ShouldersImpl;
import net.sf.josceleton.core.api.entity.joint.JointImplProvider.TorsoImpl;
import net.sf.josceleton.core.api.entity.joint.JointParts.Ankles;
import net.sf.josceleton.core.api.entity.joint.JointParts.Elbows;
import net.sf.josceleton.core.api.entity.joint.JointParts.Feet;
import net.sf.josceleton.core.api.entity.joint.JointParts.Hands;
import net.sf.josceleton.core.api.entity.joint.JointParts.Head;
import net.sf.josceleton.core.api.entity.joint.JointParts.Hips;
import net.sf.josceleton.core.api.entity.joint.JointParts.Knees;
import net.sf.josceleton.core.api.entity.joint.JointParts.Neck;
import net.sf.josceleton.core.api.entity.joint.JointParts.Shoulders;
import net.sf.josceleton.core.api.entity.joint.JointParts.Torso;

/**
 * Implements an hierarchical enum.
 * 
 * @since 0.1
 */
public final class Joints {
	
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

	private static final Iterable<Joint> ALL_JOINTS;
	static {
		ALL_JOINTS = Collections.unmodifiableList(Arrays.asList(
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

	
	private Joints() { /* as its a pseudo-enum => not instantiable */ }
	
	
	/** @since 0.1 */
	public static Iterable<Joint> values() {
		return ALL_JOINTS;
	}
	
	// FIXME erkenntnis!!! es gibt keinen typ Head, sondern dieses HEAD() ding liefert nur Joint zurueck!
	// (dann ist auch dieses komische probleme von wegen cast zu (Joint) xy nicht mehr notwendig, aha!!!)
	/** @since 0.1 */
	public static Head HEAD() { return Joints.HEAD; }

	/** @since 0.1 */
	public static Neck NECK() { return Joints.NECK; }

	/** @since 0.1 */
	public static Torso TORSO() { return Joints.TORSO; }
	
	
	/** @since 0.1 */
	public static Shoulders SHOULDER() { return Joints.SHOULDERS; }

	/** @since 0.1 */
	public static Elbows ELBOW() { return Joints.ELBOWS; }

	/** @since 0.1 */
	public static Hands HAND() { return Joints.HANDS; }

	/** @since 0.1 */
	public static Hips HIP() { return Joints.HIPS; }

	/** @since 0.1 */
	public static Knees KNEE() { return Joints.KNEES; }

	/** @since 0.1 */
	public static Ankles ANKLE() { return Joints.ANKLES; }

	/** @since 0.1 */
	public static Feet FOOT() { return Joints.FEET; }

}
