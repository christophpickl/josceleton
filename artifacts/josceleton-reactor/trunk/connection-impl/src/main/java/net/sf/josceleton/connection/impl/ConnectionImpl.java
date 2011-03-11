package net.sf.josceleton.connection.impl;

import java.util.Date;

import net.sf.josceleton.connection.api.ConnectionListener;
import net.sf.josceleton.connection.api.service.UserManager;
import net.sf.josceleton.connection.impl.osc.OscMessageTransformer;
import net.sf.josceleton.connection.impl.osc.OscPort;
import net.sf.josceleton.connection.impl.service.UserManagerInternal;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;
import net.sf.josceleton.core.impl.async.CloseableAndAsyncSkeleton;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.illposed.osc.OSCMessage;

class ConnectionImpl
	extends CloseableAndAsyncSkeleton<ConnectionListener>
	implements ConnectionInternal, OscMessageAddressRouterCallback {
	
	private final OscPort oscPort;
	
	private final OscMessageAddressRouter addressRouter;
	
	private final OscMessageTransformer transformer;
	
	private final UserManagerInternal userManager;
	
	/** state-full property */
	private boolean yetEstablished = false;
	
	
	@Inject ConnectionImpl(
			@Assisted final OscPort oscPort,
			final OscMessageAddressRouter addressRouter,
			final OscMessageTransformer transformer,
			final UserManagerInternal userManager
			) {
		this.oscPort = oscPort;
		this.addressRouter = addressRouter;
		this.transformer = transformer;
		this.userManager = userManager;
	}
	
	/** {@inheritDoc} from {@link ConnectionInternal} */
	@Override public final void establish() {
		// NO: this.checkYetClosed(); ... impossible invariant
		if(this.yetEstablished == true) {
			throw new IllegalStateException("Connection was already established!");
		}
		this.yetEstablished = true;
		
		this.oscPort.establish();
		this.addressRouter.reroute(this.oscPort, this);
	}
	
	/** {@inheritDoc} from {@link CloseableAndAsyncSkeleton} */
	@Override protected final void prepareToClose() {
		// NO: assert(yetClosed == false) ... already done by skeleton
		// assert(yetEstablished == true);
		
		// if(userMgr != null) removeListener(userMgr) ?
		// or maybe just removeAllListeners()... no, would have no effect,
		// as OscPort will not dispatch anything anymore (at, least ... should not ;)
		
		this.oscPort.close();
	}

	/** {@inheritDoc} from {@link OscMessageAddressRouterCallback} */
	@Override public final void onAcceptedJointMessage(final Date date, final OSCMessage oscMessage) {
		// assert(yetClosed == false); && assert(yetEstablished == true);
		final JointMessage message = this.transformer.transformJointMessage(oscMessage, this.userManager);
		this.dispatchJointMessage(message);
	}

	/** {@inheritDoc} from {@link OscMessageAddressRouterCallback} */
	@Override public final void onAcceptedUserMessage(final Date date, final OSCMessage oscMessage) {
		// assert(yetClosed == false); && assert(yetEstablished == true);
		final UserMessage message = this.transformer.transformUserMessage(oscMessage, this.userManager);
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

	/** {@inheritDoc} from {@link Connection} */
	@Override public final UserManager getUserManager() {
		return this.userManager;
	}
	
	@Override public final String toString() {
		return "ConnectionImpl[yetClosed=" + this.isYetClosed() + ", oscPort=" + this.oscPort + "]";
	}
	
}
