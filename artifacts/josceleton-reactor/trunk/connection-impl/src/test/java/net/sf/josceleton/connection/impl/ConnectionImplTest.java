package net.sf.josceleton.connection.impl;

import java.util.LinkedList;
import java.util.List;

import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.commons.test.jmock.ExpectationsProvider;
import net.sf.josceleton.commons.test.util.TestUtil;
import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.ConnectionListener;
import net.sf.josceleton.connection.impl.osc.OscMessageTransformer;
import net.sf.josceleton.connection.impl.osc.OscPort;
import net.sf.josceleton.connection.impl.service.UserManagerInternal;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;

import org.jmock.Expectations;
import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public class ConnectionImplTest extends AbstractMockeryTest {
	
//	connection.close();
//	connection.addListener(new ConnectionListener() {
//		@Override public void onUserMessage(UserMessage message) {
//		}
//		@Override public void onJointMessage(JointMessage message) {
//		}
//	});
//	connection.removeListener(listener);
//	connection.onAcceptedJointMessage(date, oscMessage);
//	connection.onAcceptedUserMessage(date, oscMessage);
	
	@Test(expectedExceptions = IllegalStateException.class,
			expectedExceptionsMessageRegExp = "Connection was already established!")
	public final void establishTwiceFails() {
		final ConnectionInternal connection = this.newStandardConnectionInternal();
		
		connection.establish();
		connection.establish();
	}

	@Test(expectedExceptions = IllegalStateException.class,
			expectedExceptionsMessageRegExp = "Connection already closed!")
	public final void closeTwiceWillFail() {
		final ConnectionInternal connection = this.newStandardConnectionInternal(null, true);
		
		connection.establish();
		connection.close();
		connection.close();
	}
	// FIXME TEST
//	@Test public final void testEverything() {
//		final OSCMessage sentOscMessage1 = new OSCMessageX("/joint", 42, 0.1F, 0.2F, 0.3F);
//		final JointMessage sentJointMessage1 = this.mock(JointMessage.class);
//		final OSCMessage sentOscMessage2 = new OSCMessageX("/lost_user", 42);
//		final UserMessage sentUserMessage2 = this.mock(UserMessage.class);
//		
//		final ConnectionImpl connection = this.newStandardConnectionInternal(
//			new ExpectationsProvider<OscMessageTransformer>() {
//				@Override public Expectations provide(final OscMessageTransformer transformer) {
//					return new Expectations() { {
//						oneOf(transformer).transformJointMessage(sentOscMessage1);
//						will(returnValue(sentJointMessage1));
//						
//						oneOf(transformer).transformUserMessage(sentOscMessage2);
//						will(returnValue(sentUserMessage2));
//		}}; }});
//		
//		final ConnectionListenerCollector connectionListener = new ConnectionListenerCollector();
//		connection.addListener(connectionListener);
//		connection.establish();
//		connection.onAcceptedJointMessage(new Date(), sentOscMessage1);
//		connection.onAcceptedUserMessage(new Date(), sentOscMessage2);
//
//		assertThat(connectionListener.getReceivedJointMessages().size(), equalTo(1));
//		assertThat(connectionListener.getReceivedJointMessages().get(0), is(sentJointMessage1));
//		assertThat(connectionListener.getReceivedUserMessages().size(), equalTo(1));
//		assertThat(connectionListener.getReceivedUserMessages().get(0), is(sentUserMessage2));
//	}
	
	static class ConnectionListenerCollector implements ConnectionListener {
		private final List<JointMessage> receivedJointMessages = new LinkedList<JointMessage>();
		private final List<UserMessage> receivedUserMessages = new LinkedList<UserMessage>();
		@Override public void onJointMessage(final JointMessage message) {
			this.receivedJointMessages.add(message);
		}

		@Override public void onUserMessage(final UserMessage message) {
			this.receivedUserMessages.add(message);
		}
		public final List<JointMessage> getReceivedJointMessages() {
			return this.receivedJointMessages;
		}
		public final List<UserMessage> getReceivedUserMessages() {
			return this.receivedUserMessages;
		}
	}

	private ConnectionImpl newStandardConnectionInternal() {
		return this.newStandardConnectionInternal(null);
	}

	private ConnectionImpl newStandardConnectionInternal(
			final ExpectationsProvider<OscMessageTransformer> transformerProvider) {
		return this.newStandardConnectionInternal(transformerProvider, false);
	}
	private ConnectionImpl newStandardConnectionInternal(
			final ExpectationsProvider<OscMessageTransformer> transformerProvider, final boolean willBeClosed) {
		return this.newConnectionInternal(
			new ExpectationsProvider<OscPort>() {
				@Override public Expectations provide(final OscPort port) { return new Expectations() { {
					oneOf(port).establish();
					
					if(willBeClosed == true) {
						oneOf(port).close();
					}
			}}; }},
			new ExpectationsProvider<OscMessageAddressRouter>() {
				@Override public Expectations provide(final OscMessageAddressRouter router) { return new Expectations() { {
					oneOf(router).reroute(with(any(OscPort.class)), with(any(OscMessageAddressRouterCallback.class)));
			}}; }},
			transformerProvider
		);
	}
	
	private ConnectionImpl newConnectionInternal(
		final ExpectationsProvider<OscPort> portProvider,
		final ExpectationsProvider<OscMessageAddressRouter> routerProvider,
		final ExpectationsProvider<OscMessageTransformer> transformerProvider) {
		
		final OscPort mockedOscPort = this.mock(OscPort.class);
		if(portProvider != null) {
			this.checking(portProvider.provide(mockedOscPort));
		}
		
		final OscMessageAddressRouter mockedRouter = this.mock(OscMessageAddressRouter.class);
		if(routerProvider != null) {
			this.checking(routerProvider.provide(mockedRouter));
		}
		
		final OscMessageTransformer mockedTransformer = this.mock(OscMessageTransformer.class);
		if(transformerProvider != null) {
			this.checking(transformerProvider.provide(mockedTransformer));
		}
		
		final UserManagerInternal mockedUserManager = this.mock(UserManagerInternal.class);
		
		return new ConnectionImpl(mockedOscPort, mockedRouter, mockedTransformer,  mockedUserManager);
	}
	
	@Test
	public final void testToString() {
		// MINOR @TEST ENHANCE implicitly provide custom mock names to check if included in tString result
		final String oscPortToString = "MockedOscPort[asdf]";
		final Connection connection = new ConnectionImpl(
			this.mock(OscPort.class, oscPortToString),
			this.mock(OscMessageAddressRouter.class),
			this.mock(OscMessageTransformer.class),
			this.mock(UserManagerInternal.class)
		);
		TestUtil.assertObjectToString(connection, "yetClosed", "false", oscPortToString);
	}
}
