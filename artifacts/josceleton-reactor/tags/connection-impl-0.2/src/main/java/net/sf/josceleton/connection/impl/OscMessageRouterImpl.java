package net.sf.josceleton.connection.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.josceleton.connection.impl.osc.OscAddress;
import net.sf.josceleton.connection.impl.osc.OscPort;

import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;

class OscMessageRouterImpl implements OscMessageRouter {

	/** {@inheritDoc} from {@link OscMessageRouter} */
	@Override public final void reroute(final OscPort oscPort, final OscMessageRouterCallback callback) {
		final Map<OscAddress, OSCListener> map = this.createListenerMap(callback);
		
		for(final OscAddress address : OscAddress.values()) {
			oscPort.addListenerFor(address, map.get(address));
		}
	}

	private Map<OscAddress, OSCListener> createListenerMap(final OscMessageRouterCallback callback) {
		final Map<OscAddress, OSCListener> map = new HashMap<OscAddress, OSCListener>();
		
		map.put(OscAddress.JOINT, new OSCListener() {
			@Override public void acceptMessage(final Date date, final OSCMessage oscMessage) {
				callback.onAcceptedJointMessage(date, oscMessage);
		}});
		final OSCListener userListener = new OSCListener() {
			@Override public void acceptMessage(final Date date, final OSCMessage oscMessage) {
				callback.onAcceptedUserMessage(date, oscMessage);
		}};
		map.put(OscAddress.NEW_USER, userListener);
		map.put(OscAddress.NEW_SKEL, userListener);
		map.put(OscAddress.LOST_USER, userListener);
		
		return map;
	}
}
