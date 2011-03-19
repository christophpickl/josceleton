package net.sf.josceleton.josceleton;

import net.sf.josceleton.commons.test.AbstractModuleTest;

import com.google.inject.Module;

public class JosceletonGuiceModuleTest extends AbstractModuleTest {

	@Override protected final Module createTestee() {
		return new JosceletonGuiceModule();
	}

}
