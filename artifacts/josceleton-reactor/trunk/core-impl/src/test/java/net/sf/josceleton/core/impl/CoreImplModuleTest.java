package net.sf.josceleton.core.impl;

import com.google.inject.Module;

import net.sf.josceleton.commons.test.AbstractModuleTest;

/**
 * @since 0.4
 */
public class CoreImplModuleTest extends AbstractModuleTest {

	@Override protected final Module createTestee() {
		return new CoreImplModule();
	}

}
