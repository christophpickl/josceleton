package net.sf.josceleton.prototype.midiroute;

import java.util.Collection;

import net.pulseproject.commons.midi.entity.ControllerMessage;
import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.body.BodyPart;
import net.sf.josceleton.core.api.entity.message.JointMessage;

public class JointMidiMappings {
	
	private final Collection<JointMidiMap> maps;
	
	public JointMidiMappings(Collection<JointMidiMap> maps) {
		this.maps = maps;
	}

	public void execute(JointMessage message, CoordinatesTransformer transformer, MidiConnection midiConnection) {
		final BodyPart part = message.getJointPart();
		
		for (final JointMidiMap map : this.maps) {
			if(map.appliesPart(part) == false) {
				continue;
			}

			final Coordinate coord = message.getCoordinate();
			final ControllerMessage midiMsg = map.buildMidiMessage(coord, transformer);
			
			midiConnection.send(midiMsg);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("JointMidiMappings [\n");
		int i = 1;
		for (final JointMidiMap map : this.maps) {
			if(i != 1) sb.append("\n");
			sb.append("  ").append(i).append(". ").append(map);
			i++;
		}
		sb.append("\n]");
		
		return sb.toString();
	}

	public Collection<JointMidiMap> getMaps() {
		return this.maps;
	}
	
}
