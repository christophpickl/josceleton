package net.sf.josceleton.playground.motion.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public abstract class WindowX extends JFrame {

	private static final long serialVersionUID = -4156677040212891948L;
	
	public WindowX(final DrawSurface drawSurface, final WindowXListener listener) {
		this.setBackground(Color.BLACK);
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override public void windowClosing(WindowEvent windowevent) {
				listener.onQuit(WindowX.this);
		}});
		
		final GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		final GraphicsDevice device = env.getDefaultScreenDevice();
		final Dimension monitorSize = device.getDefaultConfiguration().getBounds().getSize();
		
		drawSurface.overrideSize(monitorSize.width - 100, monitorSize.height - 100);
		
		final JButton btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				listener.onQuit(WindowX.this);
		}});
		
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel commandPanel = new JPanel();
		commandPanel.add(btnQuit);
		commandPanel.setBorder(BorderFactory.createLineBorder(new Color(0x222222)));
		commandPanel.setBackground(new Color(0x111111));
		panel.add(commandPanel, BorderLayout.SOUTH);
		
		panel.add(drawSurface, BorderLayout.CENTER);
		
		this.getContentPane().add(panel);
		this.pack();
		GuiUtil.setCenterLocation(this);
	}
	
	public int getCachedHalfWidth() {
		// TODO cache value
		return this.getWidth() / 2;
	}
	
	public void displayLater() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override public void run() {
				WindowX.this.setVisible(true);
		}});
	}
}
