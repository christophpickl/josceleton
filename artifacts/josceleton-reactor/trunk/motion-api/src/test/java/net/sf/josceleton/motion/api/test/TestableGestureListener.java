package net.sf.josceleton.motion.api.test;

import java.util.LinkedList;
import java.util.List;

import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.motion.api.gesture.GestureListener;
import net.sf.josceleton.motion.api.gesture.hitwall.HitWallListener;

public class TestableGestureListener implements GestureListener {
	
	private List<Skeleton> gesturesDetected = new LinkedList<Skeleton>();
	
	@Override public final void onGestureDetected(final Skeleton skeleton) {
		this.gesturesDetected.add(skeleton);
	}

	public final List<Skeleton> getGesturesDetected() {
		return this.gesturesDetected;
	}
	public static class TestableHitWallListener extends TestableGestureListener implements HitWallListener {
		
	}
}
