package net.sf.josceleton.core.impl.entity;

import com.google.inject.Module;

import net.sf.josceleton.commons.test.AbstractGuiceModuleTest;

public class CoreImplEntityGuiceModuleTest extends AbstractGuiceModuleTest {

	@Override protected final Module createTestee() {
		return new CoreImplEntityGuiceModule();
	}
	
}
