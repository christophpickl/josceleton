/**
 * 
 */
package net.sf.josceleton.prototype.midi.view;

import javax.swing.text.JTextComponent;

import net.sf.josceleton.prototype.midi.logic.bindable.BindingListener;

public class BoundTextFieldListener implements BindingListener {
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