package net.sf.josceleton.connection.impl.test;

import java.util.HashMap;
import java.util.Map;

import net.sf.josceleton.connection.impl.osc.OscAddress;
import net.sf.josceleton.connection.impl.osc.OscPort;

import com.illposed.osc.OSCListener;

public class TestableOscPort implements OscPort {
	
	private final Map<OscAddress, OSCListener> listeners = new HashMap<OscAddress, OSCListener>();
	
	// TODO make use of (API limitted, as no remove/immutable) AsyncFor;
	//       should be: Map<OscAddress, Collection<OSCListener>>
	@Override public final void addListenerFor(final OscAddress addressType, final OSCListener listener) {
		this.listeners.put(addressType, listener);
	}
	
	@Override public void establish() { /* deliberately empty */ }
	
	@Override public void close() { /* deliberately empty */ }
	
	public final Map<OscAddress, OSCListener> getListeners() {
		return this.listeners;
	}
	
}
