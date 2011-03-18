package net.sf.josceleton.commons;

import com.google.inject.Module;

import net.sf.josceleton.commons.test.AbstractModuleTest;

/**
 * @since 0.4
 */
public class CommonsModuleTest extends AbstractModuleTest {

	@Override protected final Module createTestee() {
		return new CommonsModule();
	}

}
