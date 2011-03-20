package net.sf.josceleton.prototype.midi.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import net.pulseproject.commons.util.GuiUtil;
import net.sf.josceleton.prototype.midi.util.LogUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MainWindow extends JFrame {
	
	private static final Log LOG = LogFactory.getLog(MainWindow.class);
	private static final long serialVersionUID = -2564259433681771255L;

	private static final String LBL_START = "Start";
	private static final String LBL_STOP = "Stop";
	
	private final JButton btnStartStop = new JButton();
	private static final Font FONT = new Font("Monaco", Font.PLAIN, 11);
	private final JTextField inpPort = new JTextField();
	private final JTextArea inpMappings = new JTextArea(14, 45);
	
	private final JTextArea logField = new JTextArea(6, 85);
	
	private final MainWindowListener listener;
	
	public MainWindow(MainWindowListener listener, final String applicationVersion) {
		super("MidiRoute Prototype v" + applicationVersion);
		
		this.listener = listener;
		
		LogUtil.setLogField(this.logField);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override public void windowClosing(WindowEvent e) {
				onWindowClosing();
			}
		});
		
		final JPanel panel = new JPanel(new BorderLayout(5, 0));
		panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		panel.add(this.initComponents(), BorderLayout.WEST);
		
		final JScrollPane scroll = new JScrollPane(this.logField);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(scroll, BorderLayout.CENTER);
		this.getContentPane().add(panel);
		
		this.inpPort.setFont(FONT);
		this.inpMappings.setFont(FONT);
		this.logField.setFont(FONT);
		
		this.inpPort.setToolTipText("Name of an receivable MIDI Port");
		this.inpMappings.setToolTipText("MIDI Mappings");
		
		this.pack();
		GuiUtil.setCenterLocation(this);
	}
	
	void onWindowClosing() {
		LOG.info("onWindowClosing()");
		this.listener.onQuit();
		this.dispose();
	}

	private JPanel initComponents() {
		JPanel panel = new JPanel(new BorderLayout());
		
		final JPanel portPanel = new JPanel(new BorderLayout());
		portPanel.add(new JLabel("MIDI Port: "), BorderLayout.WEST);
		portPanel.add(this.inpPort, BorderLayout.CENTER);
		panel.add(portPanel, BorderLayout.NORTH);
		
		final JScrollPane scroll = new JScrollPane(this.inpMappings);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(scroll, BorderLayout.CENTER);
		
		this.btnStartStop.setPreferredSize(new Dimension(200, 40));
		this.setBtnStartStop(true);
		this.btnStartStop.addActionListener(new ActionListener() {
			@SuppressWarnings("synthetic-access")
			public void actionPerformed(ActionEvent e) {
				onBtnStartStopClicked();
			}
		});
		
		this.getRootPane().setDefaultButton(this.btnStartStop);
		final JPanel cmdPanel = new JPanel();
		cmdPanel.add(this.btnStartStop);
		panel.add(cmdPanel, BorderLayout.SOUTH);
		
		return panel;
	}
	
	private void onBtnStartStopClicked() {
		if(this.btnStartStop.getText().equals(LBL_START)) {
			doStart();
		} else {
			doStop();
		}
	}
	
	public void setBtnStartStop(boolean toStart) {
		if(toStart) {
			this.btnStartStop.setText(LBL_START);
			this.btnStartStop.setToolTipText("Click to open Connection");
		} else {
			this.btnStartStop.setText(LBL_STOP);
			this.btnStartStop.setToolTipText("Click to close Connection");
		}
	}

	void doStop() {
		this.listener.onStop();
	}
	
	void doStart() {
		this.listener.onStart();
	}
	
	public void logWelcomeMessage() {
		LogUtil.log("====================================================================");
		LogUtil.log("                               WELCOME");
		LogUtil.log("====================================================================");
		LogUtil.log("");
		LogUtil.log("Enter port, define mappings and hit start ...");
		LogUtil.log("");
		LogUtil.log("");
	}
	
	public String getMidiPort() {
		return this.inpPort.getText();
	}
	
	public void setMidiPort(final String midiPort) {
		this.inpPort.setText(midiPort);
	}

	public String getMidiMappings() {
		return this.inpMappings.getText();
	}

	public void setMidiMappings(String midiMappings) {
		this.inpMappings.setText(midiMappings);
	}

}
