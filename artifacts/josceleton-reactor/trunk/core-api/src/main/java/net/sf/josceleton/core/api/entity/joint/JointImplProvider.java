package net.sf.josceleton.core.api.entity.joint;

import net.sf.josceleton.core.api.entity.joint.JointParts.Ankle;
import net.sf.josceleton.core.api.entity.joint.JointParts.Ankles;
import net.sf.josceleton.core.api.entity.joint.JointParts.Elbow;
import net.sf.josceleton.core.api.entity.joint.JointParts.Elbows;
import net.sf.josceleton.core.api.entity.joint.JointParts.Feet;
import net.sf.josceleton.core.api.entity.joint.JointParts.Foot;
import net.sf.josceleton.core.api.entity.joint.JointParts.Hand;
import net.sf.josceleton.core.api.entity.joint.JointParts.Hands;
import net.sf.josceleton.core.api.entity.joint.JointParts.Head;
import net.sf.josceleton.core.api.entity.joint.JointParts.Hip;
import net.sf.josceleton.core.api.entity.joint.JointParts.Hips;
import net.sf.josceleton.core.api.entity.joint.JointParts.Knee;
import net.sf.josceleton.core.api.entity.joint.JointParts.Knees;
import net.sf.josceleton.core.api.entity.joint.JointParts.LeftAnkle;
import net.sf.josceleton.core.api.entity.joint.JointParts.LeftElbow;
import net.sf.josceleton.core.api.entity.joint.JointParts.LeftFoot;
import net.sf.josceleton.core.api.entity.joint.JointParts.LeftHand;
import net.sf.josceleton.core.api.entity.joint.JointParts.LeftHip;
import net.sf.josceleton.core.api.entity.joint.JointParts.LeftJoint;
import net.sf.josceleton.core.api.entity.joint.JointParts.LeftKnee;
import net.sf.josceleton.core.api.entity.joint.JointParts.LeftShoulder;
import net.sf.josceleton.core.api.entity.joint.JointParts.Neck;
import net.sf.josceleton.core.api.entity.joint.JointParts.RightAnkle;
import net.sf.josceleton.core.api.entity.joint.JointParts.RightElbow;
import net.sf.josceleton.core.api.entity.joint.JointParts.RightFoot;
import net.sf.josceleton.core.api.entity.joint.JointParts.RightHand;
import net.sf.josceleton.core.api.entity.joint.JointParts.RightHip;
import net.sf.josceleton.core.api.entity.joint.JointParts.RightJoint;
import net.sf.josceleton.core.api.entity.joint.JointParts.RightKnee;
import net.sf.josceleton.core.api.entity.joint.JointParts.RightShoulder;
import net.sf.josceleton.core.api.entity.joint.JointParts.Shoulder;
import net.sf.josceleton.core.api.entity.joint.JointParts.Shoulders;
import net.sf.josceleton.core.api.entity.joint.JointParts.SymetricJoint;
import net.sf.josceleton.core.api.entity.joint.JointParts.Torso;

final class JointImplProvider {
	
	private JointImplProvider() {
		// not instantiable
	}
	
	static class HeadImpl extends DefaultJoint implements Head {
		HeadImpl() { super("Head", OsceletonJointConstants.HEAD); } }
	static class NeckImpl extends DefaultJoint implements Neck {
		NeckImpl() { super("Neck", OsceletonJointConstants.NECK); } }
	static class TorsoImpl extends DefaultJoint implements Torso {
		TorsoImpl() { super("Torso", OsceletonJointConstants.TORSO); } }

	private static class LeftShoulderImpl extends DefaultJoint implements LeftShoulder {
		LeftShoulderImpl() { super("Left Shoulder", OsceletonJointConstants.SHOULDER_LEFT); } }
	private static class RightShoulderImpl extends DefaultJoint implements RightShoulder {
		RightShoulderImpl() { super("Right Shoulder", OsceletonJointConstants.SHOULDER_RIGHT); } }
	static class ShouldersImpl extends DefaultSymetricJoint<Shoulder, LeftShoulder, RightShoulder>
		implements Shoulders { ShouldersImpl() { super(new LeftShoulderImpl(), new RightShoulderImpl()); } }
	
	private static class LeftElbowImpl extends DefaultJoint implements LeftElbow {
		LeftElbowImpl() { super("Left Elbow", OsceletonJointConstants.ELBOW_LEFT); } }
	private static class RightElbowImpl extends DefaultJoint implements RightElbow {
		RightElbowImpl() { super("Right Elbow", OsceletonJointConstants.ELBOW_RIGHT); } }
	static class ElbowsImpl extends DefaultSymetricJoint<Elbow, LeftElbow, RightElbow>
		implements Elbows { ElbowsImpl() { super(new LeftElbowImpl(), new RightElbowImpl()); } }

	private static class LeftHandImpl extends DefaultJoint implements LeftHand { LeftHandImpl() {
		super("Left Hand", OsceletonJointConstants.HAND_LEFT); } }
	private static class RightHandImpl extends DefaultJoint implements RightHand {
		RightHandImpl() { super("Right Hand", OsceletonJointConstants.HAND_RIGHT); } }
	static class HandsImpl extends DefaultSymetricJoint<Hand, LeftHand, RightHand>
		implements Hands { HandsImpl() { super(new LeftHandImpl(), new RightHandImpl()); } }
	
	private static class LeftHipImpl extends DefaultJoint implements LeftHip {
		LeftHipImpl() { super("Left Hip", OsceletonJointConstants.HIP_LEFT); } }
	private static class RightHipImpl extends DefaultJoint implements RightHip {
		RightHipImpl() { super("Right Hip", OsceletonJointConstants.HIP_RIGHT); } }
	static class HipsImpl extends DefaultSymetricJoint<Hip, LeftHip, RightHip>
		implements Hips { HipsImpl() { super(new LeftHipImpl(), new RightHipImpl()); } }
	
	private static class LeftKneeImpl extends DefaultJoint implements LeftKnee {
		LeftKneeImpl() { super("Left Knee", OsceletonJointConstants.KNEE_LEFT); } }
	private static class RightKneeImpl extends DefaultJoint implements RightKnee {
		RightKneeImpl() { super("Right Knee", OsceletonJointConstants.KNEE_RIGHT); } }
	static class KneesImpl extends DefaultSymetricJoint<Knee, LeftKnee, RightKnee>
		implements Knees { KneesImpl() { super(new LeftKneeImpl(), new RightKneeImpl()); } }
	
	private static class LeftAnkleImpl extends DefaultJoint implements LeftAnkle {
		LeftAnkleImpl() { super("Left Ankle", OsceletonJointConstants.ANKLE_LEFT); } }
	private static class RightAnkleImpl extends DefaultJoint implements RightAnkle {
		RightAnkleImpl() { super("Right Ankle", OsceletonJointConstants.ANKLE_RIGHT); } }
	static class AnklesImpl extends DefaultSymetricJoint<Ankle, LeftAnkle, RightAnkle>
		implements Ankles { AnklesImpl() { super(new LeftAnkleImpl(), new RightAnkleImpl()); } }
	
	private static class LeftFootImpl extends DefaultJoint implements LeftFoot {
		LeftFootImpl() { super("Left Foot", OsceletonJointConstants.FOOT_LEFT); } }
	private static class RightFootImpl extends DefaultJoint implements RightFoot {
		RightFootImpl() { super("Right Foot", OsceletonJointConstants.FOOT_RIGHT); } }
	static class FeetImpl extends DefaultSymetricJoint<Foot, LeftFoot, RightFoot>
		implements Feet { FeetImpl() { super(new LeftFootImpl(), new RightFootImpl()); } }
	
	
	private abstract static class DefaultJoint implements Joint {
		
		private static final String TO_STRING_PREFIX = "Body ";
		private final String cachedToString;
		private final String label;
		private final String osceletonId;
		
		DefaultJoint(final String label, final String osceletonId) {
			this.label = label;
			this.osceletonId = osceletonId;
			this.cachedToString = TO_STRING_PREFIX + this.label;
		}
		
		@Override public final String getLabel() { return this.label; }
		@Override public final String getOsceletonId() { return this.osceletonId; }
		
		@Override public final String toString() {
			return this.cachedToString;
		}
		
		@Override public final boolean equals(final Object other) {
			if(this == other) { return true; }
			if((other instanceof Joint) == false) { return false; }
			final Joint that = (Joint) other;
			return this.getOsceletonId().equals(that.getOsceletonId()) && 
				   this.getLabel().equals(that.getLabel());
		}
		
		@Override public final int hashCode() {
			return this.osceletonId.hashCode();
		}
	}
	
	private static class DefaultSymetricJoint<J, LJ extends LeftJoint<J>, RJ extends RightJoint<J>>
		implements SymetricJoint<J, LJ, RJ> {
		
		private final LJ leftJoint;
		private final RJ rightJoint;
		private final String cachedToString;
		
		DefaultSymetricJoint(final LJ leftJoint, final RJ rightJoint) {
			this.leftJoint = leftJoint;
			this.rightJoint = rightJoint;
			this.cachedToString = "Sym[" + leftJoint + "/" + rightJoint + "]";
		}
		
		@Override public final LJ LEFT() { return this.leftJoint; }
		@Override public final RJ RIGHT() { return this.rightJoint; }

		@Override public final String toString() {
			return this.cachedToString;
		}
		
		@Override public final boolean equals(final Object other) {
			if(this == other) { return true; }
			if((other instanceof SymetricJoint<?, ?, ?>) == false) { return false; }
			final SymetricJoint<?, ?, ?> that = (SymetricJoint<?, ?, ?>) other;
			return this.LEFT().equals(that.LEFT()) && 
				   this.RIGHT().equals(that.RIGHT());
		}
		
		@Override public final int hashCode() {
			return this.leftJoint.hashCode();
		}
	}
}
