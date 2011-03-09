package net.sf.josceleton.core.api.entity.body;


/**
 * Provides a set of interfaces representing subtypes of <code>BodyPart</code>.
 * 
 * These so-called <i>enum interfaces</i> represent some kind of <i>namespace</i>.
 * 
 * @since 0.1
 */
public final class BodyParts {
	
	private BodyParts() {
		// not instantiable
	}
	
    /* MAIN BODY PARTS                                                     */
    /* ******************************************************************* */

	/** @since 0.1 */
	public interface Head extends BodyPart { /* marker interface */ }
	
	/** @since 0.1 */
	public interface Neck extends BodyPart { /* marker interface */ }
	
	/** @since 0.1 */
	public interface Torso extends BodyPart { /* marker interface */ }

    /* SHOULDERS                                                           */
    /* ******************************************************************* */
	
	/** @since 0.1 */
	public interface Shoulder extends BodyPart { /* marker interface */ }
	
	/** @since 0.1 */
	public interface LeftShoulder extends LeftBodyPart<Shoulder>, Shoulder { /* marker interface */ }
	
	/** @since 0.1 */
	public interface RightShoulder extends RightBodyPart<Shoulder>, Shoulder { /* marker interface */ }
	
	/** @since 0.1 */
	public interface Shoulders extends SymetricBodyPart<Shoulder, LeftShoulder, RightShoulder> { /* marker interface*/ }

    /* ELBOW                                                               */
    /* ******************************************************************* */
	
	/** @since 0.1 */
	public interface Elbow extends BodyPart { /* marker interface */ }
	
	/** @since 0.1 */
	public interface LeftElbow extends LeftBodyPart<Elbow>, Elbow { /* marker interface */ }
	
	/** @since 0.1 */
	public interface RightElbow extends RightBodyPart<Elbow>, Elbow { /* marker interface */ }
	
	/** @since 0.1 */
	public interface Elbows extends SymetricBodyPart<Elbow, LeftElbow, RightElbow> { /* marker interface */ }

    /* HANDS                                                               */
    /* ******************************************************************* */
	
	/** @since 0.1 */
	public interface Hand extends BodyPart { /* marker interface */ }
	
	/** @since 0.1 */
	public interface LeftHand extends LeftBodyPart<Hand>, Hand { /* marker interface */ }
	
	/** @since 0.1 */
	public interface RightHand extends RightBodyPart<Hand>, Hand { /* marker interface */ }
	
	/** @since 0.1 */
	public interface Hands extends SymetricBodyPart<Hand, LeftHand, RightHand> { /* marker interface */ }

    /* HIPS                                                                */
    /* ******************************************************************* */
	
	/** @since 0.1 */
	public interface Hip extends BodyPart { /* marker interface */ }
	
	/** @since 0.1 */
	public interface LeftHip extends LeftBodyPart<Hip>, Hip { /* marker interface */ }
	
	/** @since 0.1 */
	public interface RightHip extends RightBodyPart<Hip>, Hip { /* marker interface */ }
	
	/** @since 0.1 */
	public interface Hips extends SymetricBodyPart<Hip, LeftHip, RightHip> { /* marker interface */ }

    /* KNEES                                                               */
    /* ******************************************************************* */
	
	/** @since 0.1 */
	public interface Knee extends BodyPart { /* marker interface */ }
	
	/** @since 0.1 */
	public interface LeftKnee extends LeftBodyPart<Knee>, Knee { /* marker interface */ }
	
	/** @since 0.1 */
	public interface RightKnee extends RightBodyPart<Knee>, Knee { /* marker interface */ }
	
	/** @since 0.1 */
	public interface Knees extends SymetricBodyPart<Knee, LeftKnee, RightKnee> { /* marker interface */ }

    /* ANKLES                                                              */
    /* ******************************************************************* */
	
	/** @since 0.1 */
	public interface Ankle extends BodyPart { /* marker interface */ }
	
	/** @since 0.1 */
	public interface LeftAnkle extends LeftBodyPart<Ankle>, Ankle { /* marker interface */ }
	
	/** @since 0.1 */
	
	public interface RightAnkle extends RightBodyPart<Ankle>, Ankle { /* marker interface */ }
	
	/** @since 0.1 */
	public interface Ankles extends SymetricBodyPart<Ankle, LeftAnkle, RightAnkle> { /* marker interface */ }

    /* FEET                                                                */
    /* ******************************************************************* */
	
	/** @since 0.1 */
	public interface Foot extends BodyPart { /* marker interface */ }
	
	/** @since 0.1 */
	public interface LeftFoot extends LeftBodyPart<Foot>, Foot { /* marker interface */ }
	
	/** @since 0.1 */
	public interface RightFoot extends RightBodyPart<Foot>, Foot { /* marker interface */ }
	
	/** @since 0.1 */
	public interface Feet extends SymetricBodyPart<Foot, LeftFoot, RightFoot> { /* marker interface */ }
	
}
