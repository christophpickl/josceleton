package net.sf.josceleton.prototype.midi.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import net.sf.josceleton.prototype.midi.Model;
import net.sf.josceleton.prototype.midi.logic.bindable.BindingListener;
import net.sf.josceleton.prototype.midi.util.LogUtil;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = -4470277649663655733L;
	
	private final JTextArea logField = new JTextArea(6, 85);
	
	private final MainPanelListener listener;

	private static final String LBL_START = "Start";
	private static final String LBL_STOP = "Stop";
	
	private final JButton btnStartStop = new JButton();
	
	private final Model model;
	public MainPanel(final Model model, final MainPanelListener listener) {
		this.model = model;
		this.listener = listener;
		
		LogUtil.setLogField(this.logField);
		

		this.logField.setFont(StyleConstants.FONT);

		model.addListenerFor(Model.RUNNING, new BindingListener() { @Override public final void onValueChanged(Object newValue) {
			boolean isRunning = ((Boolean) newValue).booleanValue();
			if(isRunning) {
				btnStartStop.setText(LBL_STOP);
				btnStartStop.setToolTipText("Click to close Connection");
			} else {
				btnStartStop.setText(LBL_START);
				btnStartStop.setToolTipText("Click to open Connection");
			}
		}});
		
		this.initComponents(model);
	}

	private void initComponents(final Model model) {
		this.btnStartStop.setPreferredSize(new Dimension(200, 40));
		this.btnStartStop.addActionListener(new ActionListener() {
			@SuppressWarnings("synthetic-access")
			public void actionPerformed(ActionEvent e) {
				onBtnStartStopClicked();
			}
		});
		
		final JPanel westPanel = new JPanel(new BorderLayout());
		westPanel.add(new ConfigurationPanel(model), BorderLayout.CENTER);
		final JPanel cmdPanel = new JPanel();
		cmdPanel.add(this.btnStartStop);
		westPanel.add(cmdPanel, BorderLayout.SOUTH);
		
		final JScrollPane logScrolled = new JScrollPane(this.logField);
		logScrolled.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		
		this.split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, westPanel, logScrolled);
		this.split.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
		this.split.setResizeWeight(0.0);
		this.split.setDividerLocation(model.recentDividerLocation);
		this.setLayout(new BorderLayout());
		this.add(this.split, BorderLayout.CENTER);
	}
	private JSplitPane split;
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

	public void tearDown() {
		this.model.recentDividerLocation = this.split.getDividerLocation();
	}
}
