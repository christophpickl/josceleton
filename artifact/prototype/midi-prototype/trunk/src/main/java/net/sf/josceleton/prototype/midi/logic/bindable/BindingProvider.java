package net.sf.josceleton.prototype.midi.logic.bindable;

import net.sf.josceleton.core.api.async.AsyncFor;


public interface BindingProvider extends AsyncFor<String, BindingListener> {
	
	Iterable<BindingListener> getBindingListenersFor(String attributeKey);
	
}
