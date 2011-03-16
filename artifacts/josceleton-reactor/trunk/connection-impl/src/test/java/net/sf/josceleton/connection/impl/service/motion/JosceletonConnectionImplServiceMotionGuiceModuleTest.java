package net.sf.josceleton.connection.impl.service.motion;

import com.google.inject.Module;

import net.sf.josceleton.commons.test.AbstractGuiceModuleTest;

public class JosceletonConnectionImplServiceMotionGuiceModuleTest extends AbstractGuiceModuleTest {

	@Override protected final Module createTestee() {
		return new ConnectionImplServiceMotionGuiceModule();
	}

}
