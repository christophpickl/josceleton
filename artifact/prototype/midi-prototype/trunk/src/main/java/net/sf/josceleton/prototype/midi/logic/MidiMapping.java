package net.sf.josceleton.prototype.midi.logic;

import net.pulseproject.commons.midi.entity.ControllerMessage;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.api.entity.location.Direction;
import net.sf.josceleton.prototype.midi.util.LogUtil;
import net.sf.josceleton.prototype.midi.util.SomeUtil;

public class MidiMapping {

	private final Joint joint;
	private final Direction direction;
	private final int midiChannel;
	private final int controllerNumber;
	
	private int currentLogCount = 0;
	
	public MidiMapping(Joint joint, Direction direction, int midiChannel, int controllerNumber) {
		this.joint = joint;
		this.direction = direction;
		this.midiChannel = midiChannel;
		this.controllerNumber = controllerNumber;
	}

	public boolean appliesPart(final Joint comparingPart) {
		return this.joint == comparingPart;
	}
	
	public ControllerMessage buildMidiMessage(Coordinate coord) {
		final int controllerValue = SomeUtil.transformControllerValue(coord, this.direction);
		
		this.currentLogCount++;
		if(this.currentLogCount == LogUtil.LOG_JOINT_EVERY) {
			this.currentLogCount = 0;
			LogUtil.log("Captured " +
					SomeUtil.fillString(this.joint.getLabel(), 12) + " -> " +
					"MIDI ch |ctl|val: " +
						this.midiChannel + " | " +
						this.controllerNumber + " | " +
						controllerValue);
		}
		
		return new ControllerMessage(this.midiChannel, this.controllerNumber, controllerValue);
	}
	
	
	@Override public String toString() {
		return "Mapping: " + this.joint.getLabel() +", "+this.direction+"-direction, MIDI ch/ctrl="+this.midiChannel+"/"+this.controllerNumber+"]";
	}
}
