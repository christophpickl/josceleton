package net.sf.josceleton.prototype.console.view;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import net.sf.josceleton.connection.api.service.user.AvailableUsersCollection;
import net.sf.josceleton.prototype.console.glue.GlueCodeListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UserPanelsWrapper extends JPanel implements GlueCodeListener {

	private static final long serialVersionUID = -838314886028159693L;
	
	private static final Log LOG = LogFactory.getLog(UserPanelsWrapper.class);
	
	
	public UserPanelsWrapper() {
		this.setOpaque(false);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		final int gap = 10;
		this.setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));
	}
	
	
	@Override public final void onAddUserPanel(final UserPanel userPanel) {
		LOG.debug("onAddUserPanel(userPanel)");
		this.add(userPanel.asComponent());
	}

	@Override public final void onRemoveUserPanel(final UserPanel userPanel) {
		LOG.debug("onRemoveUserPanel(userPanel)");
		this.remove(userPanel.asComponent());
	}

	@Override public final void onUserCountChanged(final AvailableUsersCollection users) {
		// nothing to do
	}
}
