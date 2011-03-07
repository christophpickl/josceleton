package net.sf.josceleton.commons.test;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.Test;

import com.google.inject.Binder;
import com.google.inject.Module;

public abstract class AbstractGuiceModuleTest {
	
	protected abstract Module createTestee();
	
	@Test public final void testGuiceModuleConfigureMethod() {
		final Mockery mockery = new Mockery();
		
		final Binder builder = mockery.mock(Binder.class);
		mockery.checking(new Expectations() { { ignoring(builder); } });
		
		final Module module = this.createTestee();
		module.configure(builder);
		
		mockery.assertIsSatisfied();
	}
}
