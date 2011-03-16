package net.sf.josceleton.core.impl.entity;

import com.google.inject.Module;

import net.sf.josceleton.commons.test.AbstractModuleTest;

public class CoreImplEntityModuleTest extends AbstractModuleTest {

	@Override protected final Module createTestee() {
		return new CoreImplEntityModule();
	}
	
}
