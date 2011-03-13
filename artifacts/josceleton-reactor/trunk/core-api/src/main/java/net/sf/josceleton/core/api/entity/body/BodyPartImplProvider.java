package net.sf.josceleton.core.api.entity.body;

import net.sf.josceleton.core.api.entity.body.BodyParts.Ankle;
import net.sf.josceleton.core.api.entity.body.BodyParts.Ankles;
import net.sf.josceleton.core.api.entity.body.BodyParts.Elbow;
import net.sf.josceleton.core.api.entity.body.BodyParts.Elbows;
import net.sf.josceleton.core.api.entity.body.BodyParts.Feet;
import net.sf.josceleton.core.api.entity.body.BodyParts.Foot;
import net.sf.josceleton.core.api.entity.body.BodyParts.Hand;
import net.sf.josceleton.core.api.entity.body.BodyParts.Hands;
import net.sf.josceleton.core.api.entity.body.BodyParts.Head;
import net.sf.josceleton.core.api.entity.body.BodyParts.Hip;
import net.sf.josceleton.core.api.entity.body.BodyParts.Hips;
import net.sf.josceleton.core.api.entity.body.BodyParts.Knee;
import net.sf.josceleton.core.api.entity.body.BodyParts.Knees;
import net.sf.josceleton.core.api.entity.body.BodyParts.LeftAnkle;
import net.sf.josceleton.core.api.entity.body.BodyParts.LeftElbow;
import net.sf.josceleton.core.api.entity.body.BodyParts.LeftFoot;
import net.sf.josceleton.core.api.entity.body.BodyParts.LeftHand;
import net.sf.josceleton.core.api.entity.body.BodyParts.LeftHip;
import net.sf.josceleton.core.api.entity.body.BodyParts.LeftKnee;
import net.sf.josceleton.core.api.entity.body.BodyParts.LeftShoulder;
import net.sf.josceleton.core.api.entity.body.BodyParts.Neck;
import net.sf.josceleton.core.api.entity.body.BodyParts.RightAnkle;
import net.sf.josceleton.core.api.entity.body.BodyParts.RightElbow;
import net.sf.josceleton.core.api.entity.body.BodyParts.RightFoot;
import net.sf.josceleton.core.api.entity.body.BodyParts.RightHand;
import net.sf.josceleton.core.api.entity.body.BodyParts.RightHip;
import net.sf.josceleton.core.api.entity.body.BodyParts.RightKnee;
import net.sf.josceleton.core.api.entity.body.BodyParts.RightShoulder;
import net.sf.josceleton.core.api.entity.body.BodyParts.Shoulder;
import net.sf.josceleton.core.api.entity.body.BodyParts.Shoulders;
import net.sf.josceleton.core.api.entity.body.BodyParts.Torso;

final class BodyPartImplProvider {
	
	private BodyPartImplProvider() {
		// not instantiable
	}
	
	static class HeadImpl extends DefaultBodyPart implements Head {
		HeadImpl() { super("Head", OsceletonJointConstants.HEAD); } }
	static class NeckImpl extends DefaultBodyPart implements Neck {
		NeckImpl() { super("Neck", OsceletonJointConstants.NECK); } }
	static class TorsoImpl extends DefaultBodyPart implements Torso {
		TorsoImpl() { super("Torso", OsceletonJointConstants.TORSO); } }

	private static class LeftShoulderImpl extends DefaultBodyPart implements LeftShoulder {
		LeftShoulderImpl() { super("Left Shoulder", OsceletonJointConstants.SHOULDER_LEFT); } }
	private static class RightShoulderImpl extends DefaultBodyPart implements RightShoulder {
		RightShoulderImpl() { super("Right Shoulder", OsceletonJointConstants.SHOULDER_RIGHT); } }
	static class ShouldersImpl extends DefaultSymetricBodyPart<Shoulder, LeftShoulder, RightShoulder>
		implements Shoulders { ShouldersImpl() { super(new LeftShoulderImpl(), new RightShoulderImpl()); } }
	
	private static class LeftElbowImpl extends DefaultBodyPart implements LeftElbow {
		LeftElbowImpl() { super("Left Elbow", OsceletonJointConstants.ELBOW_LEFT); } }
	private static class RightElbowImpl extends DefaultBodyPart implements RightElbow {
		RightElbowImpl() { super("Right Elbow", OsceletonJointConstants.ELBOW_RIGHT); } }
	static class ElbowsImpl extends DefaultSymetricBodyPart<Elbow, LeftElbow, RightElbow>
		implements Elbows { ElbowsImpl() { super(new LeftElbowImpl(), new RightElbowImpl()); } }

	private static class LeftHandImpl extends DefaultBodyPart implements LeftHand { LeftHandImpl() {
		super("Left Hand", OsceletonJointConstants.HAND_LEFT); } }
	private static class RightHandImpl extends DefaultBodyPart implements RightHand {
		RightHandImpl() { super("Right Hand", OsceletonJointConstants.HAND_RIGHT); } }
	static class HandsImpl extends DefaultSymetricBodyPart<Hand, LeftHand, RightHand>
		implements Hands { HandsImpl() { super(new LeftHandImpl(), new RightHandImpl()); } }
	
	private static class LeftHipImpl extends DefaultBodyPart implements LeftHip {
		LeftHipImpl() { super("Left Hip", OsceletonJointConstants.HIP_LEFT); } }
	private static class RightHipImpl extends DefaultBodyPart implements RightHip {
		RightHipImpl() { super("Right Hip", OsceletonJointConstants.HIP_RIGHT); } }
	static class HipsImpl extends DefaultSymetricBodyPart<Hip, LeftHip, RightHip>
		implements Hips { HipsImpl() { super(new LeftHipImpl(), new RightHipImpl()); } }
	
	private static class LeftKneeImpl extends DefaultBodyPart implements LeftKnee {
		LeftKneeImpl() { super("Left Knee", OsceletonJointConstants.KNEE_LEFT); } }
	private static class RightKneeImpl extends DefaultBodyPart implements RightKnee {
		RightKneeImpl() { super("Right Knee", OsceletonJointConstants.KNEE_RIGHT); } }
	static class KneesImpl extends DefaultSymetricBodyPart<Knee, LeftKnee, RightKnee>
		implements Knees { KneesImpl() { super(new LeftKneeImpl(), new RightKneeImpl()); } }
	
	private static class LeftAnkleImpl extends DefaultBodyPart implements LeftAnkle {
		LeftAnkleImpl() { super("Left Ankle", OsceletonJointConstants.ANKLE_LEFT); } }
	private static class RightAnkleImpl extends DefaultBodyPart implements RightAnkle {
		RightAnkleImpl() { super("Right Ankle", OsceletonJointConstants.ANKLE_RIGHT); } }
	static class AnklesImpl extends DefaultSymetricBodyPart<Ankle, LeftAnkle, RightAnkle>
		implements Ankles { AnklesImpl() { super(new LeftAnkleImpl(), new RightAnkleImpl()); } }
	
	private static class LeftFootImpl extends DefaultBodyPart implements LeftFoot {
		LeftFootImpl() { super("Left Foot", OsceletonJointConstants.FOOT_LEFT); } }
	private static class RightFootImpl extends DefaultBodyPart implements RightFoot {
		RightFootImpl() { super("Right Foot", OsceletonJointConstants.FOOT_RIGHT); } }
	static class FeetImpl extends DefaultSymetricBodyPart<Foot, LeftFoot, RightFoot>
		implements Feet { FeetImpl() { super(new LeftFootImpl(), new RightFootImpl()); } }
	
	
	private static abstract class DefaultBodyPart
		implements BodyPart {
		private static final String TO_STRING_PREFIX = "Body.";
		private final String cachedToString;
		private final String label;
		private final String osceletonId;
		
		DefaultBodyPart(final String label, final String osceletonId) {
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
			if((other instanceof BodyPart) == false) { return false; }
			final BodyPart that = (BodyPart) other;
			return	this.getOsceletonId().equals(that.getOsceletonId()) && 
					this.getLabel().equals(that.getLabel());
		}
		@Override public final int hashCode() {
			return this.osceletonId.hashCode();
		}
	}
	
	private static class DefaultSymetricBodyPart<BP, LBP extends LeftBodyPart<BP>, RBP extends RightBodyPart<BP>>
		implements SymetricBodyPart<BP, LBP, RBP> {
		
		private final LBP leftPart;
		private final RBP rightPart;
		
		DefaultSymetricBodyPart(final LBP leftPart, final RBP rightPart) {
			this.leftPart = leftPart;
			this.rightPart = rightPart;
		}
		
		@Override public final LBP LEFT() { return this.leftPart; }
		@Override public final RBP RIGHT() { return this.rightPart; }
	}
}
