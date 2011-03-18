package net.sf.josceleton.motion.impl.gesture.hitwall;

import net.sf.josceleton.commons.test.AbstractModuleTest;

import com.google.inject.Module;

/**
 * @since 0.4
 */
public class MotionImplGestureHitwallModuleTest extends AbstractModuleTest {

	@Override protected final Module createTestee() {
		return new MotionImplGestureHitwallModule();
	}

}
