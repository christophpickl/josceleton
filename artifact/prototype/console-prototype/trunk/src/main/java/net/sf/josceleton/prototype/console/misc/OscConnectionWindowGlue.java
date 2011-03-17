package net.sf.josceleton.prototype.console.misc;

import java.util.HashMap;
import java.util.Map;

import net.sf.josceleton.connection.api.service.user.AvailableUsersCollection;
import net.sf.josceleton.connection.api.service.user.UserServiceListener;
import net.sf.josceleton.connection.impl.ConnectionAdapter;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.prototype.console.view.UserPanel;
import net.sf.josceleton.prototype.console.view.UserPanelFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OscConnectionWindowGlue extends ConnectionAdapter implements UserServiceListener {

	private static final Log LOG = LogFactory.getLog(OscConnectionWindowGlue.class);
	
	private final UserPanelFactory userPanelFactory;
	
	private final Map<User, UserPanel> userPanels = new HashMap<User, UserPanel>();

	private final OscConnectionWindowGlueListener listener;

	private final AvailableUsersCollection users;
	
	
	public OscConnectionWindowGlue(
			final UserPanelFactory userPanelFactory,
			final OscConnectionWindowGlueListener listener,
			final AvailableUsersCollection users) {
		this.userPanelFactory = userPanelFactory;
		this.listener = listener;
		this.users = users;
	}

	public final void initAvailableUsers() {
		for(final User user : this.users.getWaiting()) {
			this.onUserWaiting(user);
		}
		
		for(final User user : this.users.getProcessing()) {
			this.onUserProcessing(user);
		}
	}
	
	/** {@inheritDoc} from {@link ConnectionAdapter} */
	@Override public final void onJointMessage(final JointMessage message) {
		final UserPanel panel = this.userPanels.get(message.getUser());
		panel.updateCoordinate(message.getJoint(), message.getCoordinate());
	}
	
	
	
	
	/** {@inheritDoc} from {@link UserServiceListener} */
	@Override public final void onUserWaiting(final User user) {
		LOG.info("onUserWaiting(user=" + user + ")");
		
		this.doAddUser(user);
		this.listener.onUserCountChanged(this.users);
	}

	/** {@inheritDoc} from {@link UserServiceListener} */
	@Override public final void onUserProcessing(final User user) {
		LOG.info("onUserProcessing(user=" + user + ")");
		
		if(this.userPanels.containsKey(user) == false) {
			// artificial login
			this.doAddUser(user);
		}
		
		final UserPanel userPanel = this.userPanels.get(user);
		userPanel.setSkeletonAvailableTrue();
		
		this.listener.onUserCountChanged(this.users);
	}

	/** {@inheritDoc} from {@link UserServiceListener} */
	@Override public final void onUserDead(final User user) {
		if(this.userPanels.containsKey(user) == false) { // sanity check
			throw new IllegalArgumentException("No registered UserPanel for user [" + user + "]!");
		}
		
		final UserPanel userPanel = this.userPanels.remove(user);
		this.listener.onRemoveUserPanel(userPanel);
		this.listener.onUserCountChanged(this.users);
	}
	
	
	private void doAddUser(final User user) {
		LOG.debug("doAddUser(user=" + user + ")");
		
		final UserPanel userPanel = this.userPanelFactory.create(user);
		this.userPanels.put(user, userPanel);
		this.listener.onAddUserPanel(userPanel);
	}
	
}
