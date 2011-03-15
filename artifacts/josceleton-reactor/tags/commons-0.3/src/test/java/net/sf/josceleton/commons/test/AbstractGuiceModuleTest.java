package net.sf.josceleton.commons.test;

import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;

import org.jmock.Expectations;
import org.testng.annotations.Test;

import com.google.inject.Binder;
import com.google.inject.Module;

public abstract class AbstractGuiceModuleTest extends AbstractMockeryTest {
	
	protected abstract Module createTestee();
	
	@Test public final void testGuiceModuleConfigureMethod() {
		final Binder builder = this.mock(Binder.class);
		this.checking(new Expectations() { { ignoring(builder); } });
		
		final Module module = this.createTestee();
		module.configure(builder);
	}
}
