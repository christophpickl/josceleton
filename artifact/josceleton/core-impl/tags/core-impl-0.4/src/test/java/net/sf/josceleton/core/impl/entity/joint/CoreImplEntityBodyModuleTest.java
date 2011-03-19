package net.sf.josceleton.core.impl.entity.joint;

import com.google.inject.Module;

import net.sf.josceleton.commons.test.AbstractModuleTest;

public class CoreImplEntityBodyModuleTest extends AbstractModuleTest {
	
	@Override protected final Module createTestee() {
		return new CoreImplEntityJointModule();
	}

}
