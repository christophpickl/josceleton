package net.sf.josceleton.prototype.midiroute;

import net.pulseproject.commons.midi.entity.ControllerMessage;
import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.XyzDirection;
import net.sf.josceleton.core.api.entity.body.BodyPart;
import net.sf.josceleton.prototype.midiroute.view.ProtoUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JointMidiMap {
	private static final Log LOG = LogFactory.getLog(JointMidiMap.class);

	private int currentLogCount = 0;
	
	private final BodyPart part;
	private final XyzDirection direction;
	private final int midiChannel;
	private final int controllerNumber;
	
	public JointMidiMap(BodyPart part, XyzDirection direction, int midiChannel, int controllerNumber) {
		this.part = part;
		this.direction = direction;
		this.midiChannel = midiChannel;
		this.controllerNumber = controllerNumber;
	}

	public boolean appliesPart(final BodyPart comparingPart) {
		return this.part == comparingPart;
	}
	
	public ControllerMessage buildMidiMessage(Coordinate coord, CoordinatesTransformer transformer) {
		final int controllerValue = transformer.transformControllerValue(coord, this.direction);
		
		this.currentLogCount++;
		if(this.currentLogCount == ProtoUtil.LOG_JOINT_EVERY) {
			this.currentLogCount = 0;
			ProtoUtil.log("Captured " +
					ProtoUtil.fillString(this.part.getLabel(), 12) + " - " +
					"MIDI channel: " + this.midiChannel + ", " +
					"Ctrl Nr|Val: " + this.controllerNumber + "|" + controllerValue);
		}
		
		return new ControllerMessage(this.midiChannel, this.controllerNumber, controllerValue);
	}
	
	
	@Override public String toString() {
		return "Config[" + this.part.getLabel() +", "+this.direction+"-direction, MIDI ch/ctrl="+this.midiChannel+"/"+this.controllerNumber+"]";
	}
}
