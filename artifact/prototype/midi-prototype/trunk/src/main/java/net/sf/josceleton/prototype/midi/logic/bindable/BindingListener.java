package net.sf.josceleton.prototype.midi.logic.bindable;

import net.sf.josceleton.core.api.async.Listener;

public interface BindingListener extends Listener {
	
	void onValueChanged(Object newValue);
	
}
