package net.sf.josceleton.connection.impl.test;

import org.jmock.Expectations;
import org.jmock.Mockery;

import com.illposed.osc.OSCMessage;

public class TestableOSCMessage extends OSCMessage {
	
	private final String address;
	
	private final Object[] arguments;
	
	
	public TestableOSCMessage(final String address, final Object... arguments) {
		this.address = address;
		this.arguments = arguments;
	}
	
	@Override public final String getAddress() {
		return this.address;
	}
	
	@Override public final Object[] getArguments() {
		return this.arguments;
	}
	

	public static OSCMessage newMockSafeArguments(
			final Mockery mockery,
			final Object... oscArguments) {
		return TestableOSCMessage.newMockSafeAddressAndArgs(mockery, null, oscArguments);
	}

	public static OSCMessage newMockSafeAddressAndArgs(
			final Mockery mockery,
			final String address,
			final Object... oscArguments) {
		final OSCMessage oscMessage = mockery.mock(OSCMessage.class);
		
		mockery.checking(new Expectations() { {
			
			if(address != null) {
				this.oneOf(oscMessage).getAddress();
				this.will(Expectations.returnValue(address));
			}
			
			if(oscArguments != null) {
				this.oneOf(oscMessage).getArguments();
				this.will(Expectations.returnValue(oscArguments));
			}
			
		}});
		
		return oscMessage;
	}
}
