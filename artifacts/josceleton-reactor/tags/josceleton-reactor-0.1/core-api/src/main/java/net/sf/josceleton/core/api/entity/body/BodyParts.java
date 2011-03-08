package net.sf.josceleton.core.api.entity.body;

import net.sf.josceleton.core.api.entity.body.BodyPartImplProvider.LeftPart;
import net.sf.josceleton.core.api.entity.body.BodyPartImplProvider.RightPart;
import net.sf.josceleton.core.api.entity.body.BodyPartImplProvider.SymetricBodyPart;

/**
 * Just provides kind of a "namespace" for <code>BodyPart</code> interfaces.
 * 
 * @since 0.1
 */
public final class BodyParts {
	
	private BodyParts() {
		// not instantiable
	}

	/** @since 0.1 */
	public interface Head extends BodyPart { /* marker interface */ }
	/** @since 0.1 */
	public interface Neck extends BodyPart { /* marker interface */ }
	/** @since 0.1 */
	public interface Torso extends BodyPart { /* marker interface */ }
	/** @since 0.1 */
	
	/** @since 0.1 */
	public interface Shoulder extends BodyPart { /* marker interface */ }
	/** @since 0.1 */
	public interface LeftShoulder extends LeftPart<Shoulder>, Shoulder { /* marker interface */ }
	/** @since 0.1 */
	public interface RightShoulder extends RightPart<Shoulder>, Shoulder { /* marker interface */ }
	/** @since 0.1 */
	public interface Shoulders extends SymetricBodyPart<Shoulder, LeftShoulder, RightShoulder> { /* marker interface*/ }

	/** @since 0.1 */
	public interface Elbow extends BodyPart { /* marker interface */ }
	/** @since 0.1 */
	public interface LeftElbow extends LeftPart<Elbow>, Elbow { /* marker interface */ }
	/** @since 0.1 */
	public interface RightElbow extends RightPart<Elbow>, Elbow { /* marker interface */ }
	/** @since 0.1 */
	public interface Elbows extends SymetricBodyPart<Elbow, LeftElbow, RightElbow> { /* marker interface */ }
	/** @since 0.1 */

	/** @since 0.1 */
	public interface Hand extends BodyPart { /* marker interface */ }
	/** @since 0.1 */
	public interface LeftHand extends LeftPart<Hand>, Hand { /* marker interface */ }
	/** @since 0.1 */
	public interface RightHand extends RightPart<Hand>, Hand { /* marker interface */ }
	/** @since 0.1 */
	public interface Hands extends SymetricBodyPart<Hand, LeftHand, RightHand> { /* marker interface */ }

	/** @since 0.1 */
	public interface Hip extends BodyPart { /* marker interface */ }
	/** @since 0.1 */
	public interface LeftHip extends LeftPart<Hip>, Hip { /* marker interface */ }
	/** @since 0.1 */
	public interface RightHip extends RightPart<Hip>, Hip { /* marker interface */ }
	/** @since 0.1 */
	public interface Hips extends SymetricBodyPart<Hip, LeftHip, RightHip> { /* marker interface */ }

	/** @since 0.1 */
	public interface Knee extends BodyPart { /* marker interface */ }
	/** @since 0.1 */
	public interface LeftKnee extends LeftPart<Knee>, Knee { /* marker interface */ }
	/** @since 0.1 */
	public interface RightKnee extends RightPart<Knee>, Knee { /* marker interface */ }
	/** @since 0.1 */
	public interface Knees extends SymetricBodyPart<Knee, LeftKnee, RightKnee> { /* marker interface */ }

	/** @since 0.1 */
	public interface Ankle extends BodyPart { /* marker interface */ }
	/** @since 0.1 */
	public interface LeftAnkle extends LeftPart<Ankle>, Ankle { /* marker interface */ }
	/** @since 0.1 */
	public interface RightAnkle extends RightPart<Ankle>, Ankle { /* marker interface */ }
	/** @since 0.1 */
	public interface Ankles extends SymetricBodyPart<Ankle, LeftAnkle, RightAnkle> { /* marker interface */ }

	/** @since 0.1 */
	public interface Foot extends BodyPart { /* marker interface */ }
	/** @since 0.1 */
	public interface LeftFoot extends LeftPart<Foot>, Foot { /* marker interface */ }
	/** @since 0.1 */
	public interface RightFoot extends RightPart<Foot>, Foot { /* marker interface */ }
	/** @since 0.1 */
	public interface Feet extends SymetricBodyPart<Foot, LeftFoot, RightFoot> { /* marker interface */ }
	
}
