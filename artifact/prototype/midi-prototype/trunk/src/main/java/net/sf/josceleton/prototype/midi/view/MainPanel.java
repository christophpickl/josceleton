package net.sf.josceleton.prototype.midi.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import net.sf.josceleton.prototype.midi.Model;
import net.sf.josceleton.prototype.midi.Model.OnMidiPortChanged;
import net.sf.josceleton.prototype.midi.util.LogUtil;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = -4470277649663655733L;

	private static final String LBL_START = "Start";
	private static final String LBL_STOP = "Stop";
	
	private final JButton btnStartStop = new JButton();
	private static final Font FONT = new Font("Monaco", Font.PLAIN, 11);
	private final JTextArea inpMappings = new JTextArea(14, 45);
	
	private final JTextArea logField = new JTextArea(6, 85);
	
	private final Model model;
	
	private final MainPanelListener listener;
	
	public MainPanel(final Model model, final MainPanelListener listener) {
		this.model = model;
		this.listener = listener;
		
		LogUtil.setLogField(this.logField);

		final JTextField inpPort = new JTextField();
		
		this.setLayout(new BorderLayout(5, 0));
		this.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		this.add(this.initComponents(inpPort), BorderLayout.WEST);
		
		final JScrollPane scroll = new JScrollPane(this.logField);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(scroll, BorderLayout.CENTER);
		
		model.addOnMidiPortChanged(new OnMidiPortChanged() {
			@Override public void onChanged(String newMidiPort) {
				System.out.println("MainPanel.onChangedMidiPort("+newMidiPort+")");
				inpPort.setText(newMidiPort);
			}
		});
		
		inpPort.addKeyListener(new KeyAdapter() {
			@Override public void keyReleased(KeyEvent e) {
				System.out.println("MainPanel.inpPort.onKeyReleased");
				model.setMidiPort(inpPort.getText());
			}
//			@Override public void keyTyped(KeyEvent e) { }
//			@Override public void keyPressed(KeyEvent arg0) { }
		});
		
		inpPort.setFont(FONT);
		this.inpMappings.setFont(FONT);
		this.logField.setFont(FONT);
		
		inpPort.setToolTipText("Name of an receivable MIDI Port");
		this.inpMappings.setToolTipText("MIDI Mappings");
	}


	private JPanel initComponents(final JTextField inpPort) {
		JPanel panel = new JPanel(new BorderLayout());
		
		final JPanel portPanel = new JPanel(new BorderLayout());
		portPanel.add(new JLabel("MIDI Port: "), BorderLayout.WEST);
		portPanel.add(inpPort, BorderLayout.CENTER);
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
		
		final JPanel cmdPanel = new JPanel();
		cmdPanel.add(this.btnStartStop);
		panel.add(cmdPanel, BorderLayout.SOUTH);
		
		return panel;
	}
	
	public JButton getDefaultButton() {
		return this.btnStartStop;
	}
	
	private void onBtnStartStopClicked() {
		if(this.btnStartStop.getText().equals(LBL_START)) {
			this.listener.onStart();
		} else {
			this.listener.onStop();
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
}
