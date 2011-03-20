package net.sf.josceleton.prototype.midi.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.JTextComponent;

import net.sf.josceleton.prototype.midi.Model;
import net.sf.josceleton.prototype.midi.logic.bindable.BindingListener;

public class ConfigurationPanel extends JPanel {

	private static final long serialVersionUID = 7313613687940417715L;

	public ConfigurationPanel(final Model model) {
		final JTextField inpPort = new JTextField(50);
		final JTextArea inpMappings = new JTextArea(14, 45);

		model.addListenerFor(Model.MIDI_PORT, new BoundTextFieldListener(inpPort));
		model.addListenerFor(Model.MIDI_MAPPINGS, new BoundTextFieldListener(inpMappings));
		inpPort.addKeyListener(new Foo(model, Model.MIDI_PORT));
		inpMappings.addKeyListener(new Foo(model, Model.MIDI_MAPPINGS));
		
		this.initComponents(inpPort, inpMappings);
	}
	public static class Foo extends KeyAdapter {
		private final Model model;
		private final String key;
		
		public Foo(Model model, String key) {
			this.model = model;
			this.key = key;
		}
		@Override public void keyReleased(KeyEvent e) {
			final JTextComponent text = (JTextComponent) e.getSource();
			
			if(this.model.get(this.key).equals(text.getText()) == true) {
				return;
			}
			
			this.model.set(this.key, text.getText());
		}
	}
	public static class BoundTextFieldListener implements BindingListener {
		private final JTextComponent text;
		
		public BoundTextFieldListener(final JTextComponent text) {
			this.text = text;
		}

		@Override
		public void onValueChanged(Object newValue) {
			final String newText = (String) newValue;
			if(this.text.getText().equals(newText) == false) {
				this.text.setText(newText);
			}
		}
		
	}

	private void initComponents(final JTextField inpPort, final JTextArea inpMappings) {
		inpPort.setToolTipText("Enter a (receivable) MIDI Port Name");
		inpMappings.setToolTipText("Define MIDI Mappings, e.g.: 'l_hand(#torso), X, [0.0 .. 1.0 => 0 .. 127], 0, 1'");
		inpPort.setFont(StyleConstants.FONT);
		inpMappings.setFont(StyleConstants.FONT);
		
		
		final JScrollPane mappingsScrollable = new JScrollPane(inpMappings);
		inpMappings.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		mappingsScrollable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		final JPanel northPanel = new JPanel(new GridBagLayout());
		final GridBagConstraints c = new GridBagConstraints();
		
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.gridy = 0;
		northPanel.add(new JLabel("MIDI Port:"), c);
		c.gridy = 1;
		northPanel.add(inpPort, c);
		c.gridy = 2;
		northPanel.add(new JLabel("MIDI Mappings:"), c);
		
		this.setLayout(new BorderLayout());
		this.add(northPanel, BorderLayout.NORTH);
		this.add(mappingsScrollable, BorderLayout.CENTER);
	}
}
