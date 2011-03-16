package net.sf.josceleton.core.impl.entity.body;

import com.google.inject.Module;

import net.sf.josceleton.commons.test.AbstractGuiceModuleTest;

public class JosceletonCoreImplEntityBodyGuiceModuleTest extends AbstractGuiceModuleTest {
	
	@Override protected final Module createTestee() {
		return new CoreImplEntityBodyGuiceModule();
	}

}
