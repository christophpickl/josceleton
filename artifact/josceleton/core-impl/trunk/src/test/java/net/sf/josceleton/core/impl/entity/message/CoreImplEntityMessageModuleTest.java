package net.sf.josceleton.core.impl.entity.message;

import com.google.inject.Module;

import net.sf.josceleton.commons.test.AbstractModuleTest;

public class CoreImplEntityMessageModuleTest extends AbstractModuleTest {

	@Override protected final Module createTestee() {
		return new CoreImplEntityMessageModule();
	}

}
