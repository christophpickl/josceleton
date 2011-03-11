package net.sf.josceleton.connection.impl.test;

import org.jmock.Expectations;
import org.jmock.Mockery;

import com.illposed.osc.OSCMessage;

public class OSCMessageX extends OSCMessage {
	
	private final String address;
	
	private final Object[] arguments;
	
	
	public OSCMessageX(final String address, final Object... arguments) {
		this.address = address;
		this.arguments = arguments;
	}
	
	@Override public final String getAddress() {
		return this.address;
	}
	
	@Override public final Object[] getArguments() {
		return this.arguments;
	}
	
	

	public static OSCMessage newMockSafeOSCMessage(final Mockery mockery, final Object[] oscArguments) {
		return OSCMessageX.newMockSafeOSCMessage(mockery, null, oscArguments);
	}

	public static OSCMessage newMockSafeOSCMessage(final Mockery mockery, final String address) {
		return OSCMessageX.newMockSafeOSCMessage(mockery, address, null);
	}

	public static OSCMessage newMockSafeOSCMessage(
			final Mockery mockery,
			final String address,
			final Object[] oscArguments) {
		final OSCMessage oscMessage = mockery.mock(OSCMessage.class);
		
		mockery.checking(new Expectations() { {
			if(oscArguments != null) {
				this.oneOf(oscMessage).getArguments();
				this.will(Expectations.returnValue(oscArguments));
			}
			
			if(address != null) {
				this.oneOf(oscMessage).getAddress();
				this.will(Expectations.returnValue(address));
			}
		}});
		
		return oscMessage;
	}
}
