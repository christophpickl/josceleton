package net.sf.josceleton.prototype.midi;

import net.sf.josceleton.prototype.midi.logic.bindable.BindKey;

public class ModelKeys {
	
	public static interface ModelKey extends BindKey { }
	
	public static final ModelKey KEY_MIDI_MAPPINGS = new ModelKey() { };
	
}
