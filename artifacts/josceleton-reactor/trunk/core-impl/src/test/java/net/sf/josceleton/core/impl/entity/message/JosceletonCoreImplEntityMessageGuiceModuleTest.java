package net.sf.josceleton.core.impl.entity.message;

import com.google.inject.Module;

import net.sf.josceleton.commons.test.AbstractGuiceModuleTest;

public class JosceletonCoreImplEntityMessageGuiceModuleTest extends AbstractGuiceModuleTest {

	@Override protected final Module createTestee() {
		return new JosceletonCoreImplEntityMessageGuiceModule();
	}

}
