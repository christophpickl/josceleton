package net.sf.josceleton.connection.impl.service.motion;

import com.google.inject.Module;

import net.sf.josceleton.commons.test.AbstractModuleTest;

public class ConnectionImplServiceMotionModuleTest extends AbstractModuleTest {

	@Override protected final Module createTestee() {
		return new ConnectionImplServiceMotionModule();
	}

}
