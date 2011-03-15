package net.sf.josceleton.commons.test.jmock;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class AbstractMockeryTest {
	
	private Mockery mockery;
	
	@BeforeMethod public final void mockerySetUp() {
		this.mockery = new ClassMockery(); 
	}
	
	@AfterMethod public final void mockeryTearDown() {
		this.mockery.assertIsSatisfied();
		this.mockery = null;
	}
	
	protected final Mockery getMockery() {
		return this.mockery;
	}

	protected final <T> T mock(final Class<T> typeToMock) {
		return this.mockery.mock(typeToMock);
	}

	protected final <T> T mock(final Class<T> typeToMock, final String name) {
		return this.mockery.mock(typeToMock, name);
	}

	protected final void checking(final Expectations expectations) {
		this.mockery.checking(expectations);
	}
}
