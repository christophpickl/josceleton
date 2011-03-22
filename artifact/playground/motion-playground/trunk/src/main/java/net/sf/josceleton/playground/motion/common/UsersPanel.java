package net.sf.josceleton.playground.motion.common;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import net.sf.josceleton.connection.api.service.user.UserServiceListener;
import net.sf.josceleton.connection.api.service.user.UsersCollection;
import net.sf.josceleton.core.api.entity.user.User;

public class UsersPanel extends JPanel implements UserServiceListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final UsersCollection users;
	private int waitingCount;
	private int processingCount;
	
	public UsersPanel(UsersCollection users) {
		this.users = users;
		this.updateUsers();
	}

	@Override public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		
		int i = 0;
		g.setColor(Color.GREEN);
		for (; i < this.processingCount; i++) {
			g.drawLine((i * 3), 0, (i * 3), 13);
		}
		
		g.setColor(Color.ORANGE);
		final int n = this.waitingCount + this.processingCount;
		for (; i < n; i++) {
			g.drawLine((i * 3), 0, (i * 3), 13);
		}
	}
	
	private void updateUsers() {
		this.waitingCount = this.users.getWaiting().size();
		this.processingCount = this.users.getProcessing().size();
		this.repaint();
	}

	@Override
	public void onUserDead(User user) {
		this.updateUsers();
	}

	@Override
	public void onUserProcessing(User user) {
		this.updateUsers();
	}

	@Override
	public void onUserWaiting(User user) {
		this.updateUsers();
	}
}
