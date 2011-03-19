package net.sf.josceleton.motion.impl.gesture;

import net.sf.josceleton.commons.test.AbstractModuleTest;

import com.google.inject.Module;

public class MotionImplGestureModuleTest extends AbstractModuleTest {

	@Override protected final Module createTestee() {
		return new MotionImplGestureModule();
	}

}
