package net.sf.josceleton.prototype.midi.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import net.sf.josceleton.prototype.midi.Model;
import net.sf.josceleton.prototype.midi.logic.bindable.BindingListener;

public class ConfigurationPanel extends JPanel {

	private static final long serialVersionUID = 7313613687940417715L;

	public ConfigurationPanel(final Model model) {
		final JTextField inpPort = new JTextField(50);
		final JTextArea inpMappings = new JTextArea(14, 45);

		model.addListenerFor(Model.MIDI_PORT, new BindingListener() { @Override public final void onValueChanged(Object newValue) {
			inpPort.setText((String) newValue); }});
		inpPort.addKeyListener(new KeyAdapter() { @Override public void keyReleased(KeyEvent e) {
				model.setMidiPort(inpPort.getText()); }});
		
		model.addListenerFor(Model.MIDI_MAPPINGS, new BindingListener() { @Override public final void onValueChanged(Object newValue) {
			inpMappings.setText((String) newValue); }});
		inpMappings.addKeyListener(new KeyAdapter() { @Override public void keyReleased(KeyEvent e) {
			model.setMidiMappings(inpMappings.getText()); }});
		
		this.initComponents(inpPort, inpMappings);
	}

	private void initComponents(final JTextField inpPort, final JTextArea inpMappings) {
		inpPort.setToolTipText("Enter a (receivable) MIDI Port Name");
		inpMappings.setToolTipText("Define MIDI Mappings, e.g.: 'l_hand, X, 1, 1'");
		inpPort.setFont(StyleConstants.FONT);
		inpMappings.setFont(StyleConstants.FONT);
		
		
		final JScrollPane mappingsScrollable = new JScrollPane(inpMappings);
		mappingsScrollable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		this.setLayout(new GridBagLayout());
		final GridBagConstraints c = new GridBagConstraints();
		
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		this.add(new JLabel("MIDI Port:"), c);
		
		c.gridy = 1;
		this.add(inpPort, c);
		
		c.gridy = 2;
		this.add(new JLabel("MIDI Mappings:"), c);
		
		c.gridy = 3; c.insets = new Insets(2, 2, 2, 2);
		this.add(mappingsScrollable, c);
	}
}
