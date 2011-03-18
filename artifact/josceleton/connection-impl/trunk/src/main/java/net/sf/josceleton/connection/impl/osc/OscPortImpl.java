package net.sf.josceleton.connection.impl.osc;

import net.sf.josceleton.commons.exception.InvalidArgumentException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCPortIn;

class OscPortImpl implements OscPort {
	
	private static final Log LOG = LogFactory.getLog(OscPortImpl.class);
	
	private final OSCPortIn openedPort;
	
	private boolean yetClosed = false;

	private boolean yetEstablished = false;
	
	/**
	 * @param openedPort startListening() already invoked by {@link OscPortOpener}.
	 */
	@Inject OscPortImpl(@Assisted final OSCPortIn openedPort) {
		this.openedPort = openedPort;
	}

	/** {@inheritDoc} from {@link OscPort} */
	@Override public final void establish() {
		if(this.yetEstablished == true) {
			throw new IllegalStateException("Port was already established!");
		}
		this.checkYetClosed();
		this.yetEstablished = true;
		
		this.openedPort.startListening();
	}

	/** {@inheritDoc} from {@link OscPort} */
	@Override public final void addListenerFor(final OscAddress address, final OSCListener listener) {
		LOG.debug("addListenerForAddress(address=" + address + ", listener=" + listener + ")");
		
		if (address == null) { throw InvalidArgumentException.newNotNull("address"); }
		if (listener == null) { throw InvalidArgumentException.newNotNull("listener"); }
		
		this.checkYetEstablished();
		this.checkYetClosed();
		// !!! maybe this should be refactored
		this.openedPort.addListener(address.getAddress(), listener);
	}

	/** {@inheritDoc} from {@link Closeable} */
	@Override public final void close() {
		this.checkYetEstablished();
		if(this.yetClosed == true) {
			LOG.warn("already closed; aborting");
			return;
		}
		this.yetClosed = true;
		
		this.openedPort.stopListening();
		// internally, javaosc uses a catch{ e.printStackTrace} statement within a while(isListening) loop... :-/
		System.err.println("The following SocketException can be ignored ;)");
		this.openedPort.close();
	}

	private void checkYetClosed() {
		if(this.yetClosed == true) {
			throw new IllegalStateException("Connection already closed!");
		}
	}
	private void checkYetEstablished() {
		if(this.yetEstablished == false) {
			throw new IllegalStateException("Port not yet established!");
		}
	}
	
	@Override public final String toString() {
		return "OscPortImpl[yetClosed=" + this.yetClosed + "]";
	}
	
}
