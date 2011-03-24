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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.sf.josceleton.core.api.async.Async;
import net.sf.josceleton.core.impl.async.DefaultAsync;
import net.sf.josceleton.playground.motion.app2.framework.view.DrawSurface;
import net.sf.josceleton.playground.motion.app2.framework.view.resources.Images;
import net.sf.josceleton.playground.motion.app2.framework.view.resources.Style;

public class WindowX extends JFrame implements Async<WindowXListener> {

	private static final long serialVersionUID = -4156677040212891948L;
	private static final float MAX_SIZE_PERCENT_OF_IT = 0.7F;

	private static final ImageIcon PSI_INDICATOR = Images.PSI_TRANSPARENT_LITTLE_ICON;
	private final JLabel psiIndicator = new JLabel(PSI_INDICATOR);
	private final JLabel psiIndicatorPlaceholder = new JLabel();
	
	private final DefaultAsync<WindowXListener> async = new DefaultAsync<WindowXListener>();
	
	final GraphicsDevice device;
	private final Dimension monitorSize;
	final boolean fullscreen;
	
	public WindowX(UsersPanel usersPanel, boolean fullscreen, DrawSurface drawSurface, String subtitle) {
		this.fullscreen = fullscreen;
		this.setBackground(Color.BLACK);
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override public void windowClosing(WindowEvent windowevent) {
				dispatchQuit();
		}});
		
		final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		this.device = env.getDefaultScreenDevice();
		this.monitorSize = this.device.getDefaultConfiguration().getBounds().getSize();
		
		if(fullscreen == false) {
			drawSurface.enforceSize(Math.round(this.monitorSize.width * MAX_SIZE_PERCENT_OF_IT),
					Math.round(this.monitorSize.height * MAX_SIZE_PERCENT_OF_IT));
		}
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(this.createCommandPanel(usersPanel, subtitle), BorderLayout.SOUTH);
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
	private final JPanel commandPanel = new JPanel(new GridBagLayout());
	
	private JComponent createCommandPanel(UsersPanel usersPanel, String subtitle) {
		final int gapLeftRight = 10;
		
		final JButton btnQuit = new JButton("Quit");
		// http://nadeausoftware.com/articles/2008/11/mac_java_tip_how_create_aqua_single_and_segmented_buttons
		btnQuit.putClientProperty("JButton.buttonType", "textured");
		btnQuit.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				dispatchQuit();
		}});
		
		final JLabel lblInfo = new JLabel(subtitle);
		lblInfo.setFont(Style.styleAsComment(lblInfo).deriveFont(12.0F));
		
		this.commandPanel.setBorder(BorderFactory.createLineBorder(Style.LINE_PRIMARY));
		this.commandPanel.setBackground(Style.BACKGROUND_SECONDARY);

		this.setPsiIndicatorEnabled(false); // by default not visible at startup
		
		final GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(2, gapLeftRight, 2, 0);
		this.commandPanel.add(lblInfo, c);
		
		c.gridx = 1;
		final Dimension d = new Dimension(Images.PSI_TRANSPARENT_LITTLE.getWidth(null), Images.PSI_TRANSPARENT_LITTLE.getHeight(null));
		this.psiIndicatorPlaceholder.setPreferredSize(d);
		this.psiIndicatorPlaceholder.setMinimumSize(d);
		this.psiIndicatorPlaceholder.setSize(d);
		c.insets = new Insets(0, gapLeftRight, 0, 0);
		this.commandPanel.add(this.psiIndicator, c);
		c.gridx = 2;
		this.commandPanel.add(this.psiIndicatorPlaceholder, c);
		
		c.gridx = 3;
		c.insets = new Insets(2, gapLeftRight, 2, 0);
		this.commandPanel.add(usersPanel, c);
		
		c.gridx = 4;
		c.weightx = 0.9;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(2, 10, 2, gapLeftRight);
		this.commandPanel.add(btnQuit, c);
		
		return this.commandPanel;
	}
	
	public void setPsiIndicatorEnabled(boolean psiIndicatorEnabled) {
		this.psiIndicator.setVisible(psiIndicatorEnabled);
		this.psiIndicatorPlaceholder.setVisible(!psiIndicatorEnabled);
		this.commandPanel.invalidate();
	}
	
	public void displayLater() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run() {
				if(WindowX.this.fullscreen == true) {
					WindowX.this.device.setFullScreenWindow(WindowX.this);
				} else {
					WindowX.this.setVisible(true);
				}
		}});
	}
	
	void dispatchQuit() {
		for(WindowXListener listener : this.async.getListeners()) {
			listener.onQuit();
		}
	}
	
	public final Dimension getMonitorSize() {
		return this.monitorSize;
	}

	@Override public final void addListener(WindowXListener listener) {
		this.async.addListener(listener);
	}
	@Override public final void removeListener(WindowXListener listener) {
		this.async.removeListener(listener);
	}
}
