package net.sf.josceleton.prototype.console.view;

import javax.swing.JFrame;

import net.sf.josceleton.connection.api.service.user.AvailableUsersCollection;

public class ConsoleWindowImpl extends JFrame implements ConsoleWindow {

	private static final long serialVersionUID = 1L;

	private final MainPanel mainPanel = new MainPanel();
	
	public ConsoleWindowImpl() {
		super("Josceleton Console Prototype");
		
//		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
//		this.addWindowListener(new WindowAdapter() {
//			@Override @SuppressWarnings("synthetic-access")
//			public void windowClosing(final WindowEvent e) {
//				MainWindow.this.onQuit();
//			}
//		});

		this.getContentPane().add(this.mainPanel);
		this.pack();
	}


	@Override
	public final void onAddUserPanel(final UserPanel userPanel) {
		this.mainPanel.onAddUserPanel(userPanel);
		
		this.pack();
	}

	@Override
	public final void onRemoveUserPanel(final UserPanel userPanel) {
		this.mainPanel.onRemoveUserPanel(userPanel);
		
		this.pack();
	}

	@Override public final void onUserCountChanged(final AvailableUsersCollection users) {
		this.mainPanel.onUserCountChanged(users);
	}
	
}
