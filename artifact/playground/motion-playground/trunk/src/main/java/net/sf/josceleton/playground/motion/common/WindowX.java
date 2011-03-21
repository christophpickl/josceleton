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

import net.sf.josceleton.playground.motion.app2.framework.view.DrawSurface;
import net.sf.josceleton.playground.motion.app2.framework.view.common.Styles;

public class WindowX extends JFrame {

	private static final long serialVersionUID = -4156677040212891948L;
	
	public WindowX(boolean fullscreen, DrawSurface drawSurface, final WindowXListener listener) {
		this.setBackground(Color.BLACK);
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override public void windowClosing(WindowEvent windowevent) {
				listener.onQuit(WindowX.this);
		}});
		
		final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		final GraphicsDevice device = env.getDefaultScreenDevice();
		final Dimension monitorSize = device.getDefaultConfiguration().getBounds().getSize();
		
		if(fullscreen == false) {
			drawSurface.enforceSize(monitorSize.width - 100, monitorSize.height - 100);
		}
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(this.createCommandPanel(listener), BorderLayout.SOUTH);
		panel.add(drawSurface.asComponent(), BorderLayout.CENTER);
		this.getContentPane().add(panel);
		
		if(fullscreen == false) {
			this.pack();
			GuiUtil.setCenterLocation(this);
		}
		// TODO lock minimum size
	}
	
	private JComponent createCommandPanel(final WindowXListener listener) {
		final int gapLeftRight = 10;
		
		final JButton btnQuit = new JButton("Quit");
		// http://nadeausoftware.com/articles/2008/11/mac_java_tip_how_create_aqua_single_and_segmented_buttons
		btnQuit.putClientProperty("JButton.buttonType", "textured");
		btnQuit.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				listener.onQuit(WindowX.this);
		}});
		
		final JLabel lblInfo = new JLabel("Josceleton Motion Playground");
		lblInfo.setFont(Styles.styleAsComment(lblInfo).deriveFont(12.0F));
		
		JPanel commandPanel = new JPanel(new GridBagLayout());
		commandPanel.setBorder(BorderFactory.createLineBorder(Styles.LINE_PRIMARY));
		commandPanel.setBackground(Styles.BACKGROUND_SECONDARY);
		
		final GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.weightx = 0.5;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(2, gapLeftRight, 2, 0);
		commandPanel.add(lblInfo, c);
		
		c.gridx = 1;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(2, 0, 2, gapLeftRight);
		commandPanel.add(btnQuit, c);
		
		return commandPanel;
	}
	
	public void displayLater() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run() {
				WindowX.this.setVisible(true);
		}});
	}
}
