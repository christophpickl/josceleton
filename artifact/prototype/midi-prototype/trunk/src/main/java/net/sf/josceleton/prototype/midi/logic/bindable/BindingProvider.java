package net.sf.josceleton.prototype.midi.logic.bindable;

public interface BindingProvider {
	
	Iterable<BindingListener> getBindingListenersFor(String attributeKey);
	
}
