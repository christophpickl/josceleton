package net.sf.josceleton.connection.impl.service;

import com.google.inject.Module;

import net.sf.josceleton.commons.test.AbstractGuiceModuleTest;

public class JosceletionConnectionImplServiceGuiceModuleTest extends AbstractGuiceModuleTest {

	@Override protected final Module createTestee() {
		return new JosceletionConnectionImplServiceGuiceModule();
	}

}
