package net.sf.josceleton.playground.motion.app2.framework.motionx;

import java.awt.Toolkit;
import java.util.Timer;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.api.entity.location.Direction;
import net.sf.josceleton.playground.motion.common.TimerTaskRunner;

public class PsiPosition extends AbstractPosition implements Runnable {
	
	private static final int TRIGGER_TIME = 3000;

	private static boolean SYSOUT_VALIDATE = false;
//	private static boolean SYSOUT_VALIDATE = true;
	
	private static float SIMILARITY_HANDS_WITH_HEAD = 0.09F;
	private static float SIMILARITY_ELBOWS_WITH_SHOULDERS = 0.11F;
	private static float SIMILARITY_HEAD_NECK_TORSO = 0.08F;
	
	private Timer timer;
	
	@Override public void onMoved(Joint movedJoint, Coordinate updatedCoordinate, Skeleton skeleton) {
		final boolean valid = this.validate(skeleton);
		// FIXME wenn ganze zeit hin&her wackelt zwischen valid und invalid ==> timewindow darueber bauen, um das zu begrenzen
		if(valid == true) {
			if(this.timer == null) {
				System.out.println("PsiPosition: Starting trigger time ...");
				this.timer = new Timer();
				this.timer.schedule(new TimerTaskRunner(this), TRIGGER_TIME);
			}
		} else {
			if(this.timer != null) {
				System.out.println("PsiPosition: close; you just missed staying in Psi");
				this.timer.cancel();
				this.timer = null;
			}
		}
	}
	
	private boolean validate(Skeleton skeleton) {
//		if(skeleton.areAvailableFor(Joints.HAND()) == false) { // NO! assume everything is available in here
		// TODO use gesture base class relevantJoints technique in here instead!
		if(skeleton.isAvailableFor(Joints.valuesMiddle()) == false ||
				skeleton.areAvailableFor(Joints.HAND()) == false ||
				skeleton.areAvailableFor(Joints.SHOULDER()) == false ||
				skeleton.areAvailableFor(Joints.ELBOW()) == false) {
			if(SYSOUT_VALIDATE) System.out.println("PSI: insufficient data");
			return false;
		}
		// 100cm == 0.4
		//  10cm == 0.04
		//   5cm == 0.02
		//   1cm == 0.004
		
		// step 1: hands are Y-similar with head
		if(areInOneLine(skeleton, Direction.Y, SIMILARITY_HANDS_WITH_HEAD, Joints.HEAD(), Joints.HAND().LEFT(), Joints.HAND().RIGHT() ) == false) {
			if(SYSOUT_VALIDATE) System.out.println("PSI #1: hands are NOT Y-similar with head");
			return false;
		}
		
		// step 2: elbows-shoulders are Y-similar
		if(areInOneLine(skeleton, Direction.Y, SIMILARITY_ELBOWS_WITH_SHOULDERS,
				Joints.ELBOW().LEFT(), Joints.ELBOW().RIGHT(), Joints.SHOULDER().LEFT(), Joints.SHOULDER().RIGHT()) == false) {
			if(SYSOUT_VALIDATE) System.out.println("PSI #2: elbows-shoulders are NOT Y-similar");
			return false;
		}
		
		// step 3: head-neck-torso X+Z-similar; "staying straight"
		if(areInOneLine(skeleton, Direction.X, SIMILARITY_HEAD_NECK_TORSO, Joints.valuesMiddle()) == false ||
			areInOneLine(skeleton, Direction.Z, SIMILARITY_HEAD_NECK_TORSO, Joints.valuesMiddle()) == false) {
			if(SYSOUT_VALIDATE) System.out.println("PSI #3: head-neck-torso X+Z-similar; NOT staying straight");
			return false;
		}
		
		// step 4: all joints are _roughly_ Z-similar
		// while debugging, no hands are available
//		if(areInOneLine(skeleton, Direction.Z, 0.06F, Joints.toArray()) == false) {
//			System.out.println("PSI #4/4: all joints are NOT _roughly_ Z-similar");
//			return false;
//		}
		
		return true;
	}
	private static boolean areInOneLine(Skeleton skeleton, Direction direction, float diffTolerance, Joint... joints) {
		float minValue = Float.MAX_VALUE;
		float maxValue = Float.MIN_VALUE;
		for (Joint joint : joints) {
			final float jointCoord = direction.extractValue(skeleton.get(joint));
			if(jointCoord > maxValue) maxValue = jointCoord;
			if(jointCoord < minValue) minValue = jointCoord;
		}
		return Math.abs(minValue - maxValue) < diffTolerance;
	}
	
	@Override public void run() {

		if(SYSOUT_VALIDATE) {
			System.out.println(" P S I     POSITION DETECTED");
			System.out.println(" P S I     POSITION DETECTED");
			System.out.println(" P S I     POSITION DETECTED");
			Toolkit.getDefaultToolkit().beep();
		}
		
		this.timer.cancel(); // just to get sure
		this.timer = null;
		
		this.dispatchPositionDetected();
		// TODO danach soll er kurz aufhoeren nach position zu suchen, is naemlich unnoetig (und hoechst unwahrscheinlich das gewollt)
	}
}
