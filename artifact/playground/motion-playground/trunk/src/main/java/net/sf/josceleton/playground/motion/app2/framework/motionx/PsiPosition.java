package net.sf.josceleton.playground.motion.app2.framework.motionx;

import java.util.Timer;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.api.entity.location.Direction;
import net.sf.josceleton.playground.motion.common.TimerTaskRunner;

public class PsiPosition extends AbstractPosition implements Runnable {
	
	private static final int TRIGGER_TIME = 6000;
	
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
		// step 1: hands are Y-similar with head
		if(skeleton.areAvailableFor(Joints.HAND()) == false) {
			return false;
		}
		final Coordinate coordHead = skeleton.get(Joints.HEAD());
		final Coordinate coordLHand = skeleton.get(Joints.HAND().LEFT());
		final Coordinate coordRHand = skeleton.get(Joints.HAND().RIGHT());
		final float diffYLRHand = Math.abs(Direction.Y.extractValue(coordLHand) - Direction.Y.extractValue(coordRHand));
//		System.out.println("PsiPosition: diffYLRHand: " + diffYLRHand);
		if(diffYLRHand > 0.04F/* 10cm */) {
			return false;
		}
		final float diffYHeadRHand = Math.abs(Direction.Y.extractValue(coordHead) - Direction.Y.extractValue(coordRHand));
//		System.out.println("PsiPosition: diffYHeadRHand: " + diffYHeadRHand);
		
		
		// step 2: elbows-shoulders are Y-similar
		// step 3: head-neck-torso X+Y-similar; "staying straight"
		// step 4: all joints are _roughly_ Z-similar
		
		return true;
	}
	@Override public void run() {
		System.out.println("PageManager: XXXXXXXXXXXXXXXXX");
		this.timer.cancel(); // just to get sure
		this.timer = null;
		
	}
}
