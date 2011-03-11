package net.sf.josceleton.connection.impl;

import net.sf.josceleton.commons.test.AbstractGuiceModuleTest;

import com.google.inject.Module;

public class JosceletonConnectionImplGuiceModuleTest extends AbstractGuiceModuleTest {

	@Override protected final Module createTestee() {
		return new JosceletonConnectionImplGuiceModule();
	}
	

}
