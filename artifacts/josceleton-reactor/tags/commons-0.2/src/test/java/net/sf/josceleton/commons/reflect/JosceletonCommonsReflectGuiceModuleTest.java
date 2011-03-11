package net.sf.josceleton.commons.reflect;

import net.sf.josceleton.commons.test.AbstractGuiceModuleTest;

import com.google.inject.Module;

public class JosceletonCommonsReflectGuiceModuleTest extends AbstractGuiceModuleTest {

	@Override protected final Module createTestee() {
		return new JosceletonCommonsReflectGuiceModule();
	}
	
}
