package net.sf.josceleton.connection.impl.osc;

import net.sf.josceleton.commons.test.AbstractGuiceModuleTest;

import com.google.inject.Module;

public class JosceletonConnectionImplOscGuiceModuleTest extends AbstractGuiceModuleTest {

	@Override protected final Module createTestee() {
		return new JosceletonConnectionImplOscGuiceModule();
	}

}
