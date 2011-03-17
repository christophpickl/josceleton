package net.sf.josceleton.connection.impl;

import net.sf.josceleton.connection.api.ConnectionListener;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;

/**
 * Utility method, to not have to implement all interface methods declared by <code>ConnectListener</code>.
 * 
 * Simply implement (override) only the methods you are really interested in. 
 * 
 * @since 0.4
 */
public abstract class ConnectionAdapter implements ConnectionListener {

	/** {@inheritDoc} from {@link ConnectionListener} */
	@Override public void onJointMessage(final JointMessage message) {
		// do nothing, as only subclass maybe will do something :)
	}

	/** {@inheritDoc} from {@link ConnectionListener} */
	@Override public void onUserMessage(final UserMessage message) {
		// do nothing, as only subclass maybe will do something :)
	}

}
