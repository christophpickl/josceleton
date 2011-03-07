package net.sf.josceleton.connection.impl;

import java.util.Date;

import net.sf.josceleton.connection.api.ConnectionListener;
import net.sf.josceleton.connection.impl.osc.OscMessageTransformer;
import net.sf.josceleton.connection.impl.osc.OscPort;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;
import net.sf.josceleton.core.impl.async.CloseableAndAsyncSkeleton;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.illposed.osc.OSCMessage;


// LUXURY @AOP CONTRACT? inject pre-method state check of yetEstablished (do same for closeable things)
// checks (especially for joint msgs) consumes lot of CPU cycles ==> therefore disable AOP in releases!

//LUXURY @AOP established state is similar to ("single"=inherited) from CloseableAsyncDelegator

class ConnectionInternalImpl
	extends CloseableAndAsyncSkeleton<ConnectionListener>
	implements ConnectionInternal, OscMessageRouterCallback {
	
	private final OscPort oscPort;
	
	private final OscMessageRouter router;
	
	private final OscMessageTransformer transformer;
	
	/** state-full property */
	private boolean yetEstablished = false;
	
	
	@Inject ConnectionInternalImpl(
			@Assisted final OscPort oscPort,
			final OscMessageRouter router,
			final OscMessageTransformer transformer
			) {
		this.oscPort = oscPort;
		this.router = router;
		this.transformer = transformer;
	}
	
	/** {@inheritDoc} from {@link ConnectionInternal} */
	@Override public final void establish() {
		// NO: this.checkYetClosed(); ... impossible invariant
		if(this.yetEstablished == true) {
			throw new IllegalStateException("Connection was already established!");
		}
		this.yetEstablished = true;
		
		this.oscPort.establish();
		this.router.reroute(this.oscPort, this);
	}
	
	/** {@inheritDoc} from {@link CloseableAndAsyncSkeleton} */
	@Override protected final void prepareToClose() {
		// NO: assert(yetClosed == false) ... already done by skeleton
		// assert(yetEstablished == true);
		this.oscPort.close();
	}

	/** {@inheritDoc} from {@link OscMessageRouterCallback} */
	@Override public final void onAcceptedJointMessage(final Date date, final OSCMessage oscMessage) {
		// assert(yetClosed == false); && assert(yetEstablished == true);
		final JointMessage message = this.transformer.transformJointMessage(oscMessage);
		this.dispatchJointMessage(message);
	}

	/** {@inheritDoc} from {@link OscMessageRouterCallback} */
	@Override public final void onAcceptedUserMessage(final Date date, final OSCMessage oscMessage) {
		// assert(yetClosed == false); && assert(yetEstablished == true);
		final UserMessage message = this.transformer.transformUserMessage(oscMessage);
		this.dispatchUserMessage(message);
	}
	
	private void dispatchJointMessage(final JointMessage message) {
		for (final ConnectionListener currentListener : this.getListeners()) {
			currentListener.onJointMessage(message);
		}
	}
	
	private void dispatchUserMessage(final UserMessage message) {
		for (final ConnectionListener currentListener : this.getListeners()) {
			currentListener.onUserMessage(message);
		}
	}
	
	@Override public final String toString() {
		return "ConnectionInternalImpl[yetClosed=" + this.isYetClosed() + ", oscPort=" + this.oscPort + "]";
	}
	
}
