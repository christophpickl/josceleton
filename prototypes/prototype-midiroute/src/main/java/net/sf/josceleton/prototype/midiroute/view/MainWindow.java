package net.sf.josceleton.prototype.midiroute.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import net.pulseproject.commons.util.GuiUtil;
import net.sf.josceleton.core.api.entity.XyzDirection;
import net.sf.josceleton.core.api.entity.body.Body;
import net.sf.josceleton.core.api.entity.body.BodyPart;
import net.sf.josceleton.prototype.midiroute.JointMidiMap;
import net.sf.josceleton.prototype.midiroute.PrototypeLogic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MainWindow extends JFrame {
	
	private static final Log LOG = LogFactory.getLog(MainWindow.class);
	private static final long serialVersionUID = -2564259433681771255L;

	private static final String LBL_START = "Start";
	private static final String LBL_STOP = "Stop";
	
	private final JButton btnStartStop = new JButton(LBL_START);
	private static final Font FONT = new Font("Monaco", Font.PLAIN, 11);
	private final JTextField inpPort = new JTextField();
	private final JTextArea inpConfig = new JTextArea(14, 45);
	
	private final JTextArea logField = new JTextArea(6, 85);
	
	private final MainWindowListener listener;
	
	public MainWindow(MainWindowListener listener) {
		super("MidiRoute Prototype");
		
		this.listener = listener;
		
		ProtoUtil.setLogField(this.logField);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override public void windowClosing(WindowEvent e) {
				onWindowClosing();
			}
		});
		
		final JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		final JPanel westPanel = new JPanel(new BorderLayout());
		westPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		westPanel.add(this.initComponents(), BorderLayout.NORTH);
		westPanel.add(new JPanel());
		panel.add(westPanel, BorderLayout.WEST);
		
		final JScrollPane scroll = new JScrollPane(this.logField);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(scroll, BorderLayout.CENTER);
		this.getContentPane().add(panel);
		
		this.inpPort.setFont(FONT);
		this.inpConfig.setFont(FONT);
		this.logField.setFont(FONT);
		
		this.inpPort.setText("IAC Driver - Chrisi A");
		this.inpConfig.setText(
			"# Format for each line:\n" +
			"#   <JOINT>, <XYZ>, <MIDI-CH>, <CTRL-NR>\n" +
			"# J ... head, neck, torso\n" +
			"#       x_shoulder, x_elbow, x_hand\n" +
			"#       x_hip, x_knee, x_ankle, x_foot\n" +
			"\n" +
			"l_hand, X, 1, 1\n" +
			"r_hand, Y, 1, 2"
		);
		
		this.inpPort.setToolTipText("Enter the Name of an available MIDI Port");
		this.inpConfig.setToolTipText("Enter the Routing Configuration Text");
		this.logField.setToolTipText("Computer generated Log Output");
		
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
		
		final JScrollPane scroll = new JScrollPane(this.inpConfig);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(scroll, BorderLayout.CENTER);
		
		this.btnStartStop.setPreferredSize(new Dimension(200, 40));
		this.btnStartStop.setToolTipText("Click to open Connection");
		this.btnStartStop.addActionListener(new ActionListener() {
			@SuppressWarnings("synthetic-access")
			public void actionPerformed(ActionEvent e) {
				if(btnStartStop.getText().equals(LBL_START)) {
					doStart();
					btnStartStop.setText(LBL_STOP);
					btnStartStop.setToolTipText("Click to close Connection");
				} else {
					doStop();
					btnStartStop.setText(LBL_START);
					btnStartStop.setToolTipText("Click to open Connection");
				}
			}
		});
		this.getRootPane().setDefaultButton(this.btnStartStop);
		final JPanel cmdPanel = new JPanel();
		cmdPanel.add(this.btnStartStop);
		panel.add(cmdPanel, BorderLayout.SOUTH);
		
		return panel;
	}

	void doStop() {
		this.listener.onStop();
	}
	
	void doStart() {
		final String config = this.inpConfig.getText();
		final String port = this.inpPort.getText();
		this.listener.onStart(config, port);
	}
	
}
