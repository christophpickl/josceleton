package net.sf.josceleton.connection.impl.osc;

import net.sf.josceleton.commons.exception.InvalidArgumentException;
import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.commons.test.util.TestUtil;

import org.jmock.Expectations;
import org.testng.annotations.Test;

import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCPortIn;

public class OscPortImplTest extends AbstractMockeryTest {
	
	// MINOR @TEST DRY outsource part of this into common CloseableTest

	@Test(expectedExceptions = InvalidArgumentException.class,
			expectedExceptionsMessageRegExp = ".*listener.*null.*")
	public final void addListenerForNullifiedListerFails() {
		final OscPort port = this.newIgnoringOscPort();
		port.establish();
		port.addListenerFor(OscAddress.JOINT, null);
	}
	
	@Test(expectedExceptions = InvalidArgumentException.class,
			expectedExceptionsMessageRegExp = ".*address.*null.*")
	public final void addListenerForNullifiedAddressFails() {
		final OscPort port = this.newIgnoringOscPort();
		port.establish();
		port.addListenerFor(null, this.mock(OSCListener.class));
	}
	
	@Test(expectedExceptions = IllegalStateException.class,
			expectedExceptionsMessageRegExp = "Connection already closed!")
	public final void afterCloseAddListenerForFails() {
		final OscPort port = this.newIgnoringOscPort();
		port.establish();
		port.close();
		port.addListenerFor(OscAddress.JOINT, this.mock(OSCListener.class));
	}
	
	@Test
	public final void testAddListener() {
		final OscAddress oscAddress = OscAddress.JOINT;
		final OSCListener listener = this.mock(OSCListener.class);
		final OSCPortIn mockedOSCPortIn = this.mock(OSCPortIn.class);
		
		this.checking(new Expectations() { {
			oneOf(mockedOSCPortIn).startListening();
			oneOf(mockedOSCPortIn).addListener(oscAddress.getAddress(), listener);
			oneOf(mockedOSCPortIn).stopListening();
		} });
		
		final OscPort port = new OscPortImpl(mockedOSCPortIn);
		port.establish();
		port.addListenerFor(oscAddress, listener);
		port.close();
	}
	
	@Test
	public final void testToString() {
		final OscPort port = this.newIgnoringOscPort();
		port.establish();
		TestUtil.assertObjectToString(port, "yetClosed=false");
		port.close();
		TestUtil.assertObjectToString(port, "yetClosed=true");
	}
	
	@Test(expectedExceptions = IllegalStateException.class,
			expectedExceptionsMessageRegExp = "Port not yet established!")
	public final void invokingMethodBeforeEstablishFails() {
		final OscPort port = this.newIgnoringOscPort();
		final OSCListener listener = this.mock(OSCListener.class);
		port.addListenerFor(OscAddress.JOINT, listener);
	}
	
	@Test(expectedExceptions = IllegalStateException.class,
			expectedExceptionsMessageRegExp = "Port was already established!")
	public final void establishSeveralTimesFails() {
		final OscPort port = this.newIgnoringOscPort();
		port.establish();
		port.establish();
	}
	
	@Test
	public final void closingSeveralTimesDoesNothing() {
		final OSCPortIn mockedOSCPortIn = this.mock(OSCPortIn.class);
		this.checking(new Expectations() { {
			oneOf(mockedOSCPortIn).startListening();
			oneOf(mockedOSCPortIn).stopListening();
		} });
		final OscPort port = new OscPortImpl(mockedOSCPortIn);
		port.establish();
		port.close();
		port.close();
		port.close();
	}
	
	private OscPort newIgnoringOscPort() {
		final OSCPortIn mockedOSCPortIn = this.mock(OSCPortIn.class);
		this.checking(new Expectations() { {
			ignoring(mockedOSCPortIn);
		} });
		return new OscPortImpl(mockedOSCPortIn);
	}
	
}
