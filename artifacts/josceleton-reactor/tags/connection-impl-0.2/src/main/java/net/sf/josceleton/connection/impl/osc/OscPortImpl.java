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
	
	// LUXURY @REFACTOR DRY closed/established state already in CloseableAndAsyncSkeleton (see ConnectionInternalImpl)
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
		// TODO @DESIGN HEAVY maybe OSCListener listener should not be directly listening to openedPort, but rather...
		//        ... have own dispatching pool, and act as a mediator.
		//        => side effect: if close() invoked, we really have message dispatching under full control!
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
	}

	private void checkYetClosed() {
		if(this.yetClosed == true) {
			// REALLY LUXURY @EXCEPTION SIMPLE could have stored invoker of close() method by analyzing stacktrace ;)
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
