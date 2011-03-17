package net.sf.josceleton.prototype.console.glue;

import java.util.HashMap;
import java.util.Map;

import net.sf.josceleton.connection.api.service.user.AvailableUsersCollection;
import net.sf.josceleton.connection.api.service.user.UserServiceListener;
import net.sf.josceleton.connection.impl.ConnectionAdapter;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.prototype.console.notification.GrowlNotifier;
import net.sf.josceleton.prototype.console.view.UserPanel;
import net.sf.josceleton.prototype.console.view.UserPanelFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

class GlueCodeImpl extends ConnectionAdapter implements GlueCode {

	private static final Log LOG = LogFactory.getLog(GlueCodeImpl.class);
	
	private final UserPanelFactory userPanelFactory;
	
	private final Map<User, UserPanel> userPanels = new HashMap<User, UserPanel>();

	private final GlueCodeListener listener;

	private final AvailableUsersCollection users;
	
	private final GrowlNotifier growlNotifier;
	
	@Inject GlueCodeImpl(
			final UserPanelFactory userPanelFactory,
			@Assisted final GrowlNotifier growlNotifier,
			@Assisted final GlueCodeListener listener,
			@Assisted final AvailableUsersCollection users) {
		this.userPanelFactory = userPanelFactory;
		this.listener = listener;
		this.users = users;
		this.growlNotifier = growlNotifier;
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
		
		this.growlNotifier.show("User Change", "New User with ID "  + user.getOsceletonId());
		
		this.doAddUser(user);
		this.listener.onUserCountChanged(this.users);
	}

	/** {@inheritDoc} from {@link UserServiceListener} */
	@Override public final void onUserProcessing(final User user) {
		LOG.info("onUserProcessing(user=" + user + ")");
		
		if(this.userPanels.containsKey(user) == false) { // TODO check if this is actually necessar; because i dont think so!! as already managed by UserService
			// artificial login
			this.doAddUser(user);
		}
		
		this.growlNotifier.show("User Change", "User " + user.getOsceletonId() +" is now processing");
		final UserPanel panel = this.userPanels.get(user);
		panel.updateSkeletonAvailable();
		
		this.listener.onUserCountChanged(this.users);
	}

	/** {@inheritDoc} from {@link UserServiceListener} */
	@Override public final void onUserDead(final User user) {
		if(this.userPanels.containsKey(user) == false) { // sanity check
			throw new IllegalArgumentException("No registered UserPanel for user [" + user + "]!");
		}

		this.growlNotifier.show("User Change", "Lost User "  + user.getOsceletonId());
		
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
