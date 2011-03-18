package net.sf.josceleton.connection.impl;

import net.sf.josceleton.commons.test.AbstractModuleTest;

import com.google.inject.Module;

public class ConnectionImplModuleTest extends AbstractModuleTest {

	@Override protected final Module createTestee() {
		return new ConnectionImplModule();
	}
	

}
