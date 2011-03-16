package net.sf.josceleton.josceleton;

import net.sf.josceleton.commons.test.AbstractGuiceModuleTest;

import com.google.inject.Module;

public class JosceletonGuiceModuleTest extends AbstractGuiceModuleTest {

	@Override protected final Module createTestee() {
		return new JosceletonGuiceModule();
	}

}
