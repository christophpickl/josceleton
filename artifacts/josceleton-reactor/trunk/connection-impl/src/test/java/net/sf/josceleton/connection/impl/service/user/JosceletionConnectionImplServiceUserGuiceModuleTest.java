package net.sf.josceleton.connection.impl.service.user;

import com.google.inject.Module;

import net.sf.josceleton.commons.test.AbstractGuiceModuleTest;

public class JosceletionConnectionImplServiceUserGuiceModuleTest extends AbstractGuiceModuleTest {

	@Override protected final Module createTestee() {
		return new JosceletionConnectionImplServiceUserGuiceModule();
	}

}
