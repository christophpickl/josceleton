package net.sf.josceleton.connection.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.josceleton.commons.test.jmock.AbstractMockeryTest;
import net.sf.josceleton.connection.impl.osc.OscAddress;
import net.sf.josceleton.connection.impl.osc.OscPort;
import net.sf.josceleton.connection.impl.test.OSCMessageX;
import net.sf.josceleton.connection.impl.test.OscMessageRouterCallbackCollector;
import net.sf.josceleton.connection.impl.test.OscPortX;

import org.jmock.Expectations;
import org.testng.annotations.Test;

import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;

@SuppressWarnings("boxing")
public class OscMessageRouterImplTest extends AbstractMockeryTest {

	@Test
	public final void rerouteAddsOneListenerForEachAddress() {
		final OscMessageRouter router = new OscMessageRouterImpl();
		final OscPort oscPort = this.mock(OscPort.class);
		this.checking(new Expectations() { {
			exactly(1).of(oscPort).addListenerFor(with(equal(OscAddress.JOINT)), with(any(OSCListener.class)));
			exactly(1).of(oscPort).addListenerFor(with(equal(OscAddress.NEW_SKEL)), with(any(OSCListener.class)));
			exactly(1).of(oscPort).addListenerFor(with(equal(OscAddress.NEW_USER)), with(any(OSCListener.class)));
			exactly(1).of(oscPort).addListenerFor(with(equal(OscAddress.LOST_USER)), with(any(OSCListener.class)));
		}});
		
		router.reroute(oscPort, this.mock(OscMessageRouterCallback.class));
		// assert via mockery is satisfied
	}

	@Test
	public final void rerouteDispatchesCorrectly() {
		final OSCMessage oscMsg1 = new OSCMessageX("/new_user", 42);
		final OSCMessage oscMsg2 = new OSCMessageX("/new_skel", 42);
		final OSCMessage oscMsg3 = new OSCMessageX("/joint", 42, 0.1F, 0.2F, 0.3F);
		final OSCMessage oscMsg4 = new OSCMessageX("/lost_user", 42);
		
		final OscMessageRouter router = new OscMessageRouterImpl();
		final OscPortX oscPort = new OscPortX();
		
		final OscMessageRouterCallbackCollector callback = new OscMessageRouterCallbackCollector();
		router.reroute(oscPort, callback);
		
		final Date date = new Date();
		final Map<OscAddress, OSCListener> listeners = oscPort.getListeners();
		listeners.get(OscAddress.NEW_USER).acceptMessage(date, oscMsg1);
		listeners.get(OscAddress.NEW_SKEL).acceptMessage(date, oscMsg2);
		listeners.get(OscAddress.JOINT).acceptMessage(date, oscMsg3);
		listeners.get(OscAddress.LOST_USER).acceptMessage(date, oscMsg4);
		
		// MINOR @TEST REFACTOR test for different addresses/messages, and invalid address
		
		final List<OSCMessage> actualJoints = callback.getReceivedJointMessages();
		final List<OSCMessage> actualUsers = callback.getReceiveduserMessages();
		assertThat(actualJoints.size(), equalTo(1));
		assertThat(actualUsers.size(), equalTo(3));
	}
}
