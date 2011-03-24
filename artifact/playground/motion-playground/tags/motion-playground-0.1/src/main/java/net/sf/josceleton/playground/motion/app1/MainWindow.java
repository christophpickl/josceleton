package net.sf.josceleton.playground.motion.app1;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.entity.location.Coordinate;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	static interface MainWindowListener {
		void onQuit(MainWindow window);
	}
	
	private final JLabel lblCoordinate = new JLabel("N/A");
	
	private final ContentPanel contentPanel;
	
	public MainWindow(final Dimension monitorSize, final MainWindowListener listener) {
		this.contentPanel = new ContentPanel(monitorSize.width - 100, monitorSize.height - 100);
		JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				listener.onQuit(MainWindow.this);
		}});
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel commandPanel = new JPanel();
		
		commandPanel.add(btnQuit);
		commandPanel.add(this.lblCoordinate);
		
		panel.add(commandPanel, BorderLayout.NORTH);
		panel.add(this.contentPanel, BorderLayout.CENTER);
		
		this.getContentPane().add(panel);
		
//		this.setUndecorated(true);
	}
	
	public void updateSkeleton(final Skeleton skeleton) {
		if(skeleton.isCoordinateAvailable(Joints.HAND().LEFT())) {
			final Coordinate coordinate = skeleton.getNullSafe(Joints.HAND().LEFT());
			this.lblCoordinate.setText(coordinate.x() + " x " + coordinate.y());
		}
		
		this.contentPanel.update(skeleton);
	}
	
}
