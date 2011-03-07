package net.sf.josceleton.connection.impl;

import com.google.inject.Module;

import net.sf.josceleton.commons.test.AbstractGuiceModuleTest;

public class ConnectionImplGuiceModuleTest extends AbstractGuiceModuleTest {

	@Override protected final Module createTestee() {
		return new ConnectionImplGuiceModule();
	}

}
