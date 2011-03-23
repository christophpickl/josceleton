package net.sf.josceleton.playground.motion.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.sf.josceleton.core.api.async.Async;
import net.sf.josceleton.core.impl.async.DefaultAsync;
import net.sf.josceleton.playground.motion.app2.framework.view.DrawSurface;
import net.sf.josceleton.playground.motion.app2.framework.view.resources.Style;

public class WindowX extends JFrame implements Async<WindowXListener> {

	private static final long serialVersionUID = -4156677040212891948L;
	private static final float MAX_SIZE_PERCENT_OF_IT = 0.7F;
	
	private final DefaultAsync<WindowXListener> async = new DefaultAsync<WindowXListener>();
	
	private final GraphicsDevice device;
	
	private final boolean fullscreen;
	
	
	public WindowX(UsersPanel usersPanel, boolean fullscreen, DrawSurface drawSurface) {
		this.fullscreen = fullscreen;
		this.setBackground(Color.BLACK);
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override public void windowClosing(WindowEvent windowevent) {
				dispatchQuit();
		}});
		
		final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		this.device = env.getDefaultScreenDevice();
		final Dimension monitorSize = this.device.getDefaultConfiguration().getBounds().getSize();
		
		if(fullscreen == false) {
			drawSurface.enforceSize(Math.round(monitorSize.width * MAX_SIZE_PERCENT_OF_IT),
					Math.round(monitorSize.height * MAX_SIZE_PERCENT_OF_IT));
		}
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(this.createCommandPanel(usersPanel), BorderLayout.SOUTH);
		panel.add(drawSurface.asComponent(), BorderLayout.CENTER);
		this.getContentPane().add(panel);
		
		if(fullscreen == false) {
			this.pack();
			this.setLocation(10, 0); // TODO uncomment
//			GuiUtil.setCenterLocation(this);
			// TODO lock minimum size
		} else {
			this.setUndecorated(true);
		}
	}
	
	private JComponent createCommandPanel(UsersPanel usersPanel) {
		final int gapLeftRight = 10;
		
		final JButton btnQuit = new JButton("Quit");
		// http://nadeausoftware.com/articles/2008/11/mac_java_tip_how_create_aqua_single_and_segmented_buttons
		btnQuit.putClientProperty("JButton.buttonType", "textured");
		btnQuit.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				dispatchQuit();
		}});
		
		final JLabel lblInfo = new JLabel("Josceleton Motion Playground");
		lblInfo.setFont(Style.styleAsComment(lblInfo).deriveFont(12.0F));
		
		JPanel commandPanel = new JPanel(new GridBagLayout());
		commandPanel.setBorder(BorderFactory.createLineBorder(Style.LINE_PRIMARY));
		commandPanel.setBackground(Style.BACKGROUND_SECONDARY);
		
		final GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(2, gapLeftRight, 2, 0);
		commandPanel.add(lblInfo, c);
		
		c.gridx = 1;
		commandPanel.add(usersPanel, c);
		
		c.gridx = 2;
		c.weightx = 0.9;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(2, 10, 2, gapLeftRight);
		commandPanel.add(btnQuit, c);
		
		return commandPanel;
	}
	
	public void displayLater() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run() {
				if(fullscreen == true) {
					device.setFullScreenWindow(WindowX.this);
				} else {
					WindowX.this.setVisible(true);
				}
		}});
	}
	
	private void dispatchQuit() {
		for(WindowXListener listener : this.async.getListeners()) {
			listener.onQuit();
		}
	}

	@Override public final void addListener(WindowXListener listener) {
		this.async.addListener(listener);
	}
	@Override public final void removeListener(WindowXListener listener) {
		this.async.removeListener(listener);
	}
}
