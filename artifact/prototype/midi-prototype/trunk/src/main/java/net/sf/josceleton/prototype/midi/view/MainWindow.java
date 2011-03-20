package net.sf.josceleton.prototype.midi.view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import net.pulseproject.commons.util.GuiUtil;
import net.sf.josceleton.prototype.midi.Model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MainWindow extends JFrame {
	
	private static final Log LOG = LogFactory.getLog(MainWindow.class);
	private static final long serialVersionUID = -2564259433681771255L;
	
	private final MainWindowListener listener;
	
	
	public MainWindow(Model model, MainWindowListener listener, final String applicationVersion) {
		super("MidiRoute Prototype v" + applicationVersion);
		
		this.listener = listener;
		
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override public void windowClosing(WindowEvent e) {
				onWindowClosing();
			}
		});

		final MainPanel mainPanel = new MainPanel(model, this.listener);
		this.getRootPane().setDefaultButton(mainPanel.getDefaultButton());
		this.getContentPane().add(mainPanel);
		
		this.pack();
		GuiUtil.setCenterLocation(this);
	}
	
	void onWindowClosing() {
		LOG.info("onWindowClosing()");
		this.listener.onQuit();
		this.dispose();
	}

}
