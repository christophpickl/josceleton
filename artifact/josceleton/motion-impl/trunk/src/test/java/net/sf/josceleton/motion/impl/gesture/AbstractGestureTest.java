package net.sf.josceleton.motion.impl.gesture;

import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.motion.api.gesture.GestureConfig;
import net.sf.josceleton.motion.api.gesture.GestureListener;

import org.jmock.Expectations;
import org.testng.annotations.Test;

public class AbstractGestureTest<
		C extends GestureConfig,
		L extends GestureListener,
		G extends AbstractGesture<C, L>>
	extends AbstractMockeryTest {
	
	@Test public final void dispatchGestureDetected() {
		final Skeleton skeleton = this.mock(Skeleton.class);
		
		final GestureListener listener = this.mock(GestureListener.class);
		this.checking(new Expectations() { {
			oneOf(listener).onGestureDetected(skeleton);
		}});

		final AbstractGesture<GestureConfig, GestureListener> gesture = new AbstractGesture<GestureConfig, GestureListener>() {
			@Override
			public void onMoved(final Joint movedJoint, final Coordinate coordinate, final Skeleton passedSkeleton) {
				throw new AssertionError("Unexpected onMoved() invocation!");
		}};
		gesture.addListener(listener);
		gesture.dispatchGestureDetected(skeleton);
	}
	
}
