package net.sf.josceleton.prototype.midi.logic;

import net.pulseproject.commons.midi.entity.ControllerMessage;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.api.entity.location.Direction;

public class MidiMapping {

	private final Joint part;
	private final Direction direction;
	private final int midiChannel;
	private final int controllerNumber;
	
	private int currentLogCount = 0;
	
	public MidiMapping(Joint part, Direction direction, int midiChannel, int controllerNumber) {
		this.part = part;
		this.direction = direction;
		this.midiChannel = midiChannel;
		this.controllerNumber = controllerNumber;
	}

	public boolean appliesPart(final Joint comparingPart) {
		return this.part == comparingPart;
	}
	
	public ControllerMessage buildMidiMessage(Coordinate coord) {
		final int controllerValue = ProtoUtil.transformControllerValue(coord, this.direction);
		
		this.currentLogCount++;
		if(this.currentLogCount == ProtoUtil.LOG_JOINT_EVERY) {
			this.currentLogCount = 0;
			ProtoUtil.log("Captured " +
					ProtoUtil.fillString(this.part.getLabel(), 12) + " -> " +
					"MIDI ch |ctl|val: " +
						this.midiChannel + " | " +
						this.controllerNumber + " | " +
						controllerValue);
		}
		
		return new ControllerMessage(this.midiChannel, this.controllerNumber, controllerValue);
	}
	
	
	@Override public String toString() {
		return "Mapping: " + this.part.getLabel() +", "+this.direction+"-direction, MIDI ch/ctrl="+this.midiChannel+"/"+this.controllerNumber+"]";
	}
}
