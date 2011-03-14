package net.sf.josceleton.connection.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

import java.util.Date;

import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.commons.test.jmock.ExpectationsProvider;
import net.sf.josceleton.commons.test.util.TestUtil;
import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.UserService;
import net.sf.josceleton.connection.impl.osc.OscMessageTransformer;
import net.sf.josceleton.connection.impl.osc.OscPort;
import net.sf.josceleton.connection.impl.service.UserServiceInternal;
import net.sf.josceleton.connection.impl.test.TestableConnectionListener;
import net.sf.josceleton.connection.impl.test.TestableOSCMessage;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;

import org.jmock.Expectations;
import org.testng.annotations.Test;

import com.illposed.osc.OSCMessage;

@SuppressWarnings("boxing")
public class ConnectionImplTest extends AbstractMockeryTest {
	
	@Test public final void getUserServiceTest() {
		final OscPort oscPort = this.mock(OscPort.class);
		final OscMessageAddressRouter addressRouter = this.mock(OscMessageAddressRouter.class);
		final OscMessageTransformer transformer = this.mock(OscMessageTransformer.class);
		final UserServiceInternal userService = this.mock(UserServiceInternal.class);
		
		
		final Connection connection = new ConnectionImpl(oscPort, addressRouter, transformer, userService);
		final UserService actualService = connection.getUserService();
		assertThat(actualService == userService, equalTo(true)); // old way, as of hamcrest type checks error
	}
	
	@Test public final void onAcceptedJointMessageForOscMessageAddressRouterCallback() {
		final OSCMessage oscMessage = new TestableOSCMessage("/address");
		final JointMessage jointMessage = this.mock(JointMessage.class);
		final ConnectionImpl connection = this.foo(oscMessage, jointMessage, null);
		
		final TestableConnectionListener collector = new TestableConnectionListener();
		connection.addListener(collector);
		final Date date = null; // will be ignored!
		connection.onAcceptedJointMessage(date, oscMessage);
		
		assertThat(collector.getReceivedUserMessages().size(), equalTo(0));
		assertThat(collector.getReceivedJointMessages().size(), equalTo(1));
		assertThat(collector.getReceivedJointMessages(), hasItem(jointMessage));
	}
	// MINOR @TEST copy'n'paste test code :(
	@Test public final void onAcceptedUserMessageForOscMessageAddressRouterCallback() {
		final OSCMessage oscMessage = new TestableOSCMessage("/address");
		final UserMessage userMessage = this.mock(UserMessage.class);
		final ConnectionImpl connection = this.foo(oscMessage, null, userMessage);
		
		final TestableConnectionListener collector = new TestableConnectionListener();
		connection.addListener(collector);
		final Date date = null; // will be ignored!
		connection.onAcceptedUserMessage(date, oscMessage);
		
		assertThat(collector.getReceivedJointMessages().size(), equalTo(0));
		assertThat(collector.getReceivedUserMessages().size(), equalTo(1));
		assertThat(collector.getReceivedUserMessages(), hasItem(userMessage));
	}
	
	private ConnectionImpl foo(final OSCMessage oscMessage, final JointMessage jointMessage, final UserMessage userMessage) {
		final UserServiceInternal userService = this.mock(UserServiceInternal.class);
		
		final OscMessageTransformer transformer = this.mock(OscMessageTransformer.class);
		this.checking(new Expectations() { {
			if(jointMessage != null) {
				oneOf(transformer).transformJointMessage(oscMessage, userService);
				will(returnValue(jointMessage));
			} else {
				oneOf(transformer).transformUserMessage(oscMessage, userService);
				will(returnValue(userMessage));
			}
		}});
		
		final OscPort oscPort = this.mock(OscPort.class);
		final OscMessageAddressRouter addressRouter = this.mock(OscMessageAddressRouter.class);
		
		final ConnectionImpl connection = new ConnectionImpl(oscPort, addressRouter, transformer, userService);
		return connection;
	}
	
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
		
		final UserServiceInternal mockedUserService = this.mock(UserServiceInternal.class);
		
		return new ConnectionImpl(mockedOscPort, mockedRouter, mockedTransformer,  mockedUserService);
	}
	
	@Test
	public final void testToString() {
		// MINOR @TEST ENHANCE implicitly provide custom mock names to check if included in tString result
		final String oscPortToString = "MockedOscPort[asdf]";
		final Connection connection = new ConnectionImpl(
			this.mock(OscPort.class, oscPortToString),
			this.mock(OscMessageAddressRouter.class),
			this.mock(OscMessageTransformer.class),
			this.mock(UserServiceInternal.class)
		);
		TestUtil.assertObjectToString(connection, "yetClosed", "false", oscPortToString);
	}
}
