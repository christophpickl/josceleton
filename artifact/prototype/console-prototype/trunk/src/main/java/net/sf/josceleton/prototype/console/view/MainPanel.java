package net.sf.josceleton.prototype.console.view;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.sf.josceleton.commons.util.GuiUtil;
import net.sf.josceleton.connection.api.service.user.AvailableUsersCollection;
import net.sf.josceleton.prototype.console.glue.GlueCodeListener;

public class MainPanel extends JPanel implements GlueCodeListener {

	private static final long serialVersionUID = 5833077387459470873L;

	private final UserPanelsWrapper userPanelsWrapper = new UserPanelsWrapper();

	private final JLabel lblUserCount = new JLabel();
	

	public MainPanel() {
		this.setOpaque(false);
		this.updatedUserCountLabel(0, 0); // init
		this.initComponents();
	}
	public interface MainPanelListener {
		void onOpenScenariosClicked();
	}
	
	private void initComponents() {
		final JPanel infoPanel = new JPanel();
		infoPanel.setOpaque(false);
		infoPanel.add(GuiUtil.newJLabel("Tracked Users: ", StyleConstantsPool.FONT_MAIN_INFO_TEXT));
		this.lblUserCount.setFont(StyleConstantsPool.FONT_MAIN_INFO_TEXT);
		infoPanel.add(this.lblUserCount);
		
		final JScrollPane scrollableUserPanelsWrapper = new JScrollPane(this.userPanelsWrapper);
		
		this.setLayout(new BorderLayout());
//		this.add(this.createControlPanel(), BorderLayout.NORTH);
		this.add(scrollableUserPanelsWrapper, BorderLayout.CENTER);
		this.add(infoPanel, BorderLayout.SOUTH);
	}
	
//	private JPanel createControlPanel() {
//		final JPanel panel = new JPanel();
//		
//		final JButton btn = new JButton("Open Scenarios ...");
//		btn.addActionListener(new ActionListener() {
//			@Override public void actionPerformed(ActionEvent e) {
//				listener.onOpenScenariosClicked();
//		}});
//		panel.add(btn);
//		
//		return panel;
//	}

	@Override public final void onAddUserPanel(final UserPanel userPanel) {
		this.userPanelsWrapper.onAddUserPanel(userPanel);
	}

	@Override public final void onRemoveUserPanel(final UserPanel userPanel) {
		this.userPanelsWrapper.onRemoveUserPanel(userPanel);
	}

	@Override public final void onUserCountChanged(final AvailableUsersCollection users) {
		this.userPanelsWrapper.onUserCountChanged(users);
		this.updatedUserCountLabel(users.getProcessing().size(), users.getWaiting().size());
	}
	
	private void updatedUserCountLabel(final int processing, final int waiting) {
		this.lblUserCount.setText(processing + " (" + waiting + " waiting)");
	}
	
}
