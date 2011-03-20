package net.sf.josceleton.prototype.midi;

import net.sf.josceleton.prototype.midi.logic.preference.PersistAsPreference;

public class Model {

	@PersistAsPreference
	private String midiPort;

	@PersistAsPreference
	private String midiMappings;

	
	public String getMidiPort() {
		return this.midiPort;
	}

	public void setMidiPort(String midiPort) {
		this.midiPort = midiPort;
	}

	public String getMidiMappings() {
		return this.midiMappings;
	}

	public void setMidiMappings(String midiMappings) {
		this.midiMappings = midiMappings;
	}
	
}
