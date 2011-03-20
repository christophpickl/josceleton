package net.sf.josceleton.prototype.midi.logic;

import java.util.LinkedList;
import java.util.List;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.location.Direction;
import net.sf.josceleton.prototype.midi.util.SomeUtil;

public class MappingsParser {

	public static MidiMapping[] parseMappings(String raw) throws InvalidInputException {
		List<MidiMapping> mappings = new LinkedList<MidiMapping>();
		
		String[] lines = raw.split("\n");
		for (String line : lines) {
			line = line.trim();
			if(line.startsWith("#") || line.isEmpty()) {
				continue;
			}
			
			String[] tokens = line.split(",");
			
			if(tokens.length != 4) {
				throw new RuntimeException("Expected 4 arguments, but given: " + tokens.length);
			}
			
			Joint joint = SomeUtil.jointByOsceletonId(tokens[0].trim());
			Direction direction = Direction.valueOf(tokens[1].trim());
			int midiChannel = parseInt(tokens[2]);
			int controllerNumber = parseInt(tokens[3]);
			mappings.add(new MidiMapping(joint, direction, midiChannel, controllerNumber));
		}
		
		return mappings.toArray(new MidiMapping[mappings.size()]);
	}
	
	private static int parseInt(String s) throws InvalidInputException {
		try {
			return Integer.parseInt(s.trim());
		} catch(NumberFormatException e) {
			throw InvalidInputException.newInvalidNumber(s);
		}
	}
}
