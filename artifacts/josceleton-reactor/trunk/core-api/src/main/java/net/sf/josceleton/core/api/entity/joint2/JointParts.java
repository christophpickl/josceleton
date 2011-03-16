package net.sf.josceleton.core.api.entity.joint2;


/**
 * Provides a set of interfaces representing subtypes of {@link Joint}.
 * 
 * These so-called <i>enum interfaces</i> represent some kind of <i>namespace</i>.
 * 
 * @since 0.1
 */
public final class JointParts {
	
	private JointParts() {
		// not instantiable
	}
	
    /* MAIN JOINTS                                                         */
    /* ******************************************************************* */

	/** @since 0.1 */
	public interface Head extends Joint { /* marker interface */ }
	
	/** @since 0.1 */
	public interface Neck extends Joint { /* marker interface */ }
	
	/** @since 0.1 */
	public interface Torso extends Joint { /* marker interface */ }

    /* SHOULDERS                                                           */
    /* ******************************************************************* */
	
	/** @since 0.1 */
	public interface Shoulder extends Joint { /* marker interface */ }
	
	/** @since 0.1 */
	public interface LeftShoulder extends LeftJoint<Shoulder>, Shoulder { /* marker interface */ }
	
	/** @since 0.1 */
	public interface RightShoulder extends RightJoint<Shoulder>, Shoulder { /* marker interface */ }
	
	/** @since 0.1 */
	public interface Shoulders extends SymetricJoint<Shoulder, LeftShoulder, RightShoulder> { /* marker interface*/ }

    /* ELBOW                                                               */
    /* ******************************************************************* */
	
	/** @since 0.1 */
	public interface Elbow extends Joint { /* marker interface */ }
	
	/** @since 0.1 */
	public interface LeftElbow extends LeftJoint<Elbow>, Elbow { /* marker interface */ }
	
	/** @since 0.1 */
	public interface RightElbow extends RightJoint<Elbow>, Elbow { /* marker interface */ }
	
	/** @since 0.1 */
	public interface Elbows extends SymetricJoint<Elbow, LeftElbow, RightElbow> { /* marker interface */ }

    /* HANDS                                                               */
    /* ******************************************************************* */
	
	/** @since 0.1 */
	public interface Hand extends Joint { /* marker interface */ }
	
	/** @since 0.1 */
	public interface LeftHand extends LeftJoint<Hand>, Hand { /* marker interface */ }
	
	/** @since 0.1 */
	public interface RightHand extends RightJoint<Hand>, Hand { /* marker interface */ }
	
	/** @since 0.1 */
	public interface Hands extends SymetricJoint<Hand, LeftHand, RightHand> { /* marker interface */ }

    /* HIPS                                                                */
    /* ******************************************************************* */
	
	/** @since 0.1 */
	public interface Hip extends Joint { /* marker interface */ }
	
	/** @since 0.1 */
	public interface LeftHip extends LeftJoint<Hip>, Hip { /* marker interface */ }
	
	/** @since 0.1 */
	public interface RightHip extends RightJoint<Hip>, Hip { /* marker interface */ }
	
	/** @since 0.1 */
	public interface Hips extends SymetricJoint<Hip, LeftHip, RightHip> { /* marker interface */ }

    /* KNEES                                                               */
    /* ******************************************************************* */
	
	/** @since 0.1 */
	public interface Knee extends Joint { /* marker interface */ }
	
	/** @since 0.1 */
	public interface LeftKnee extends LeftJoint<Knee>, Knee { /* marker interface */ }
	
	/** @since 0.1 */
	public interface RightKnee extends RightJoint<Knee>, Knee { /* marker interface */ }
	
	/** @since 0.1 */
	public interface Knees extends SymetricJoint<Knee, LeftKnee, RightKnee> { /* marker interface */ }

    /* ANKLES                                                              */
    /* ******************************************************************* */
	
	/** @since 0.1 */
	public interface Ankle extends Joint { /* marker interface */ }
	
	/** @since 0.1 */
	public interface LeftAnkle extends LeftJoint<Ankle>, Ankle { /* marker interface */ }
	
	/** @since 0.1 */
	
	public interface RightAnkle extends RightJoint<Ankle>, Ankle { /* marker interface */ }
	
	/** @since 0.1 */
	public interface Ankles extends SymetricJoint<Ankle, LeftAnkle, RightAnkle> { /* marker interface */ }

    /* FEET                                                                */
    /* ******************************************************************* */
	
	/** @since 0.1 */
	public interface Foot extends Joint { /* marker interface */ }
	
	/** @since 0.1 */
	public interface LeftFoot extends LeftJoint<Foot>, Foot { /* marker interface */ }
	
	/** @since 0.1 */
	public interface RightFoot extends RightJoint<Foot>, Foot { /* marker interface */ }
	
	/** @since 0.1 */
	public interface Feet extends SymetricJoint<Foot, LeftFoot, RightFoot> { /* marker interface */ }


	interface LeftJoint<J> {
		// marker interface only
	}
	interface RightJoint<J> {
		// marker interface only
	}

	interface SymetricJoint<J, LJ extends LeftJoint<J>, RJ extends RightJoint<J>> {

		/** This is part of the API as it will be visibile to user. */
		LJ LEFT();
		
		/** This is part of the API as it will be visibile to user. */
		RJ RIGHT();
		
	}
	
}
