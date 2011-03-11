package net.sf.josceleton.connection.impl.osc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import net.sf.josceleton.commons.reflect.ClassAdapter;
import net.sf.josceleton.commons.reflect.ClassAdapterImpl;
import net.sf.josceleton.commons.reflect.DynamicInstantiationException;
import net.sf.josceleton.commons.reflect.DynamicInstantiator;
import net.sf.josceleton.commons.test.jmock.ClassMockery;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.Test;

import com.illposed.osc.OSCPortIn;

public class OscPortOpenerImplTest {
	
	@SuppressWarnings("boxing")
	@Test
	public final void simpleTest() {
		final int expectedPortNumber = 42;
		final Mockery mockery = new ClassMockery();
		
		final DynamicInstantiator mockedInstantiator = mockery.mock(DynamicInstantiator.class);
		final OSCPortIn mockedOSCPortIn = mockery.mock(OSCPortIn.class);
		
		final OscPortFactory mockedPortFactory = mockery.mock(OscPortFactory.class);
		final OscPort mockedOscPort = mockery.mock(OscPort.class);
		
		final ClassAdapter<OSCPortIn> oscPortInAdapter = ClassAdapterImpl.create(OSCPortIn.class);
		mockery.checking(new Expectations() { {
			oneOf(mockedInstantiator).create(oscPortInAdapter, expectedPortNumber);
			will(returnValue(mockedOSCPortIn));
			
			oneOf(mockedPortFactory).create(mockedOSCPortIn);
			will(returnValue(mockedOscPort));
		} });

		final OscPortOpener opener = new OscPortOpenerImpl(mockedInstantiator, mockedPortFactory, oscPortInAdapter);
		final OscPort actualOscPort = opener.connect(expectedPortNumber);

		assertThat(actualOscPort, is(mockedOscPort));
		mockery.assertIsSatisfied();
	}
	
	@SuppressWarnings("boxing")
	@Test(expectedExceptions = OscPortOpeningException.class, expectedExceptionsMessageRegExp = ".*42.*")
	public final void simulateSocketException() {
		final int expectedPortNumber = 42;
		final Mockery mockery = new ClassMockery();
		
		final DynamicInstantiator mockedInstantiator = mockery.mock(DynamicInstantiator.class);
		
		final OscPortFactory mockedPortFactory = mockery.mock(OscPortFactory.class);
		final ClassAdapter<OSCPortIn> oscPortInAdapter = ClassAdapterImpl.create(OSCPortIn.class);
		mockery.checking(new Expectations() { {
			oneOf(mockedInstantiator).create(oscPortInAdapter, expectedPortNumber);
			will(throwException(DynamicInstantiationException.
				newForInstantiation(oscPortInAdapter, new Object[] { expectedPortNumber}, null)));
//			will(throwException(new SocketException("Blah blah ... already bound to " + expectedPortNumber + "!")));
		} });

		final OscPortOpener opener = new OscPortOpenerImpl(mockedInstantiator, mockedPortFactory, oscPortInAdapter);
		opener.connect(expectedPortNumber);
	}
	
	
}
