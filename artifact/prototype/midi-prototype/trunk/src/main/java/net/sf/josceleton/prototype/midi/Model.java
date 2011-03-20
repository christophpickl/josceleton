package net.sf.josceleton.prototype.midi;

import java.util.HashSet;
import java.util.Set;

import net.sf.josceleton.core.impl.async.DefaultAsyncFor;
import net.sf.josceleton.prototype.midi.logic.bindable.BindingListener;
import net.sf.josceleton.prototype.midi.logic.bindable.BindingProvider;
import net.sf.josceleton.prototype.midi.logic.bindable.BindingSetter;
import net.sf.josceleton.prototype.midi.logic.preference.PersistAsPreference;

public class Model extends DefaultAsyncFor<String, BindingListener> implements BindingProvider {

	private boolean running = false;

	@PersistAsPreference
	private String midiPort;

	
	public static final String BIND_MIDI_MAPPINGS = "BIND_MIDI_MAPPINGS";
	@PersistAsPreference
	private String midiMappings = "default init";
	
	public String getMidiMappings() {
		return this.midiMappings;
	}
	
	@Override public Iterable<BindingListener> getBindingListenersFor(final String attributeKey) {
		return this.getListenersFor(attributeKey);
	}
	
	@BindingSetter(Key = BIND_MIDI_MAPPINGS)
	public void setMidiMappings(String midiMappings) {
		System.out.println("MODEL setMappings");
		this.midiMappings = midiMappings;
		// TODO return this?
	}
	
	
	public static interface OnMidiPortChanged {
		void onChanged(String newMidiPort);
	}
	
	private Set<OnMidiPortChanged> onMidiPortChangeds = new HashSet<OnMidiPortChanged>();
	
	public void addOnMidiPortChanged(OnMidiPortChanged listener) {
		System.out.println("Model.addMidiChangeListener("+listener+")");
		this.onMidiPortChangeds.add(listener);
		this.dispatchMidiPortChanged();
	}
	
	public String getMidiPort() {
		return this.midiPort;
	}

	public void setMidiPort(String midiPort) {
		System.out.println("Model.setMidiPort("+midiPort+")");
		
		if(this.midiPort == null && midiPort == null) {
			return;
		}
		if(this.midiPort != null && midiPort != null &&
				this.midiPort.equals(midiPort)) {
			return;
		}
		
		this.midiPort = midiPort;
		this.dispatchMidiPortChanged();
	}
	private void dispatchMidiPortChanged() {
		for (OnMidiPortChanged changed : this.onMidiPortChangeds) {
			changed.onChanged(this.midiPort);
		}
	}

	public boolean isRunning() {
		return this.running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	
}
