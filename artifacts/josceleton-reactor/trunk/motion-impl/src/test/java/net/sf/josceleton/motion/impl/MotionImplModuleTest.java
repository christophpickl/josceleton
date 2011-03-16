package net.sf.josceleton.motion.impl;

import com.google.inject.Module;

import net.sf.josceleton.commons.test.AbstractModuleTest;

public class MotionImplModuleTest extends AbstractModuleTest {

	@Override protected final Module createTestee() {
		return new MotionImplModule();
	}

}
