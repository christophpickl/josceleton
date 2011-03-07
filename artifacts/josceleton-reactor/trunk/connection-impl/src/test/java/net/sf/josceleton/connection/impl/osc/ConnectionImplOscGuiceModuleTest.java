package net.sf.josceleton.connection.impl.osc;

import net.sf.josceleton.commons.test.AbstractGuiceModuleTest;

import com.google.inject.Module;

public class ConnectionImplOscGuiceModuleTest extends AbstractGuiceModuleTest {

	@Override protected final Module createTestee() {
		return new ConnectionImplOscGuiceModule();
	}

}
