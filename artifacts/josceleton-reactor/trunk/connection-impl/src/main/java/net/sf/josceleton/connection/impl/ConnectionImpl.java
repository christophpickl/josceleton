package net.sf.josceleton.connection.impl;

import java.util.Date;

import net.sf.josceleton.connection.api.ConnectionListener;
import net.sf.josceleton.connection.api.service.user.UserService;
import net.sf.josceleton.connection.impl.osc.OscMessageTransformer;
import net.sf.josceleton.connection.impl.osc.OscPort;
import net.sf.josceleton.connection.impl.service.user.UserServiceInternal;
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
	
	private final UserServiceInternal userService;
	
	/** state-full property */
	private boolean yetEstablished = false;
	
	
	@Inject ConnectionImpl(
			@Assisted final OscPort oscPort,
			final OscMessageAddressRouter addressRouter,
			final OscMessageTransformer transformer,
			final UserServiceInternal userService
			) {
		this.oscPort = oscPort;
		this.addressRouter = addressRouter;
		this.transformer = transformer;
		this.userService = userService;
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
		
		// if(userService != null) removeListener(userService) ?
		// or maybe just removeAllListeners()... no, would have no effect,
		// as OscPort will not dispatch anything anymore (at, least ... should not ;)
		
		// TODO ??? remove all listeners when connection closed? yes, i think so...
		// 		then we have to loosen conditions for removeListener(x) whereas x not added is listener;
		// 		actually nothing bad here, as postconditions is on method begin already fullfilled => perfectly fine
		
		this.oscPort.close();
	}

	/** {@inheritDoc} from {@link OscMessageAddressRouterCallback} */
	@Override public final void onAcceptedJointMessage(final Date date, final OSCMessage oscMessage) {
		// assert(yetClosed == false); && assert(yetEstablished == true);
		
		// TODO @EXCEPTION CODE if we are receiving an malformed oscMessage => catch it!
		//  and CHAIN with proper exception (message: "Received malformed bla")
		// try {
		final JointMessage message = this.transformer.transformJointMessage(oscMessage, this.userService);
		// } catch(...Exception ex) {
		//   throw new WrappingException("Could not transform malformed joint message " + oscMessage, ex);
		// }
		for (final ConnectionListener currentListener : this.getListeners()) {
			currentListener.onJointMessage(message);
		}
	}

	/** {@inheritDoc} from {@link OscMessageAddressRouterCallback} */
	@Override public final void onAcceptedUserMessage(final Date date, final OSCMessage oscMessage) {
		// assert(yetClosed == false); && assert(yetEstablished == true);
		final UserMessage message = this.transformer.transformUserMessage(oscMessage, this.userService);
		
		for (final ConnectionListener currentListener : this.getListeners()) {
			currentListener.onUserMessage(message);
		}
	}
	
	/** {@inheritDoc} from {@link Connection} */
	@Override public final UserService getUserService() {
		return this.userService;
	}
	
	@Override public final String toString() {
		return "ConnectionImpl[yetClosed=" + this.isYetClosed() + ", oscPort=" + this.oscPort + "]";
	}
	
}
