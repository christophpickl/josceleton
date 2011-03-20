package net.sf.josceleton.prototype.midi.view;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.location.Direction;
import net.sf.josceleton.prototype.midi.logic.InvalidInputException;
import net.sf.josceleton.prototype.midi.logic.MidiMapping;
import net.sf.josceleton.prototype.midi.logic.ProtoUtil;
import net.sf.josceleton.prototype.midi.logic.PrototypeLogic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ViewMediator implements MainWindowListener {
	
	private static final Log LOG = LogFactory.getLog(ViewMediator.class);
	
	private PrototypeLogic recentLogic;
	
	private MainWindow window;
	
	@SuppressWarnings("synthetic-access")
	public void onStart(final String rawMappings, final String port) {
		new Thread(new Runnable() {
			public void run() {
				try {
					ProtoUtil.clearLog();
					ProtoUtil.log("Opening connection ...");
					final MidiMapping[] mappings = parseMappings(rawMappings);
					ViewMediator.this.recentLogic = new PrototypeLogic(port, mappings);
					ViewMediator.this.recentLogic.open();

					ProtoUtil.log("Successfully parsed MIDI mappings:");
					ProtoUtil.log("");
					int i = 1;
					for (final MidiMapping map : mappings) {
						ProtoUtil.log("  "+(i++)+". " + map);
					}
					ProtoUtil.log("");
					
					ProtoUtil.log("Connection established (displaying every " + ProtoUtil.LOG_JOINT_EVERY +"th messages) ...");
					ProtoUtil.log("");
					ViewMediator.this.window.setBtnStartStop(false);
					
				} catch (InvalidInputException e) {
//					ProtoUtil.log("WARNING: Invalid input: " + e.getMessage());
					JOptionPane.showMessageDialog(null, e.getMessage(), "Configuration Error", JOptionPane.WARNING_MESSAGE);
					
				} catch (Exception e) {
					ProtoUtil.handleException(e);
				}
			}
		}).start();
	}

	public void onStop() {
		this.window.setBtnStartStop(true);
		try {
			LOG.info("doStop() ... recentLogic=" + this.recentLogic);
			if(this.recentLogic != null) {
				this.recentLogic.close();
				this.recentLogic = null;
			}
			ProtoUtil.log("Connections closed.");
		} catch (Exception e) {
			ProtoUtil.handleException(e);
		}
	}

	public void onQuit() {
		try {
			this.onStop();
		} catch (Exception e) {
			ProtoUtil.handleException(e);
		}
	}
	
	
	private static MidiMapping[] parseMappings(String raw) throws InvalidInputException {
		List<MidiMapping> mappings = new LinkedList<MidiMapping>();
		
		String[] lines = raw.split("\n");
		for (String line : lines) {
			line = line.trim();
			if(line.startsWith("#") || line.isEmpty()) {
				continue;
			}
			
			String[] tokens = line.split(",");
			Joint part = ProtoUtil.bodyPartByOsceletonId(tokens[0].trim());
			Direction direction = Direction.valueOf(tokens[1].trim());
			int midiChannel = parseInt(tokens[2]);
			int controllerNumber = parseInt(tokens[3]);
			mappings.add(new MidiMapping(part, direction, midiChannel, controllerNumber));
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
	
	public void setCyclicDependency(MainWindow window) {
		this.window = window;
	}
	
}
