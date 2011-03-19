package net.sf.josceleton.commons.reflect;

import net.sf.josceleton.commons.test.AbstractModuleTest;

import com.google.inject.Module;

public class CommonsReflectModuleTest extends AbstractModuleTest {

	@Override protected final Module createTestee() {
		return new CommonsReflectModule();
	}
	
}
