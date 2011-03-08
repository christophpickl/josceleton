package net.sf.josceleton.connection.impl.osc;

import net.sf.josceleton.core.api.async.Closeable;

import com.illposed.osc.OSCListener;

/**
 * There is no removeListenerForAddress() as javaosc.OSCPortIn does not support it; oscConnection.stopListening instead.
 */
public interface OscPort extends Closeable {
	
	void establish();
	
	void addListenerFor(OscAddress addressType, OSCListener listener);

}
