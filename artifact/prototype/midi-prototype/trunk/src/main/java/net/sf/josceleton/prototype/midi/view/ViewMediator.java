package net.sf.josceleton.prototype.midi.view;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.location.Direction;
import net.sf.josceleton.prototype.midi.Model;
import net.sf.josceleton.prototype.midi.logic.InvalidInputException;
import net.sf.josceleton.prototype.midi.logic.MidiMapping;
import net.sf.josceleton.prototype.midi.logic.PrototypeLogic;
import net.sf.josceleton.prototype.midi.logic.preference.PreferencesPersister;
import net.sf.josceleton.prototype.midi.util.LogUtil;
import net.sf.josceleton.prototype.midi.util.SomeUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ViewMediator implements MainWindowListener {
	
	private static final Log LOG = LogFactory.getLog(ViewMediator.class);

	private final static String MODEL_PREF_ID = "MyMainWindow";
	
	private PrototypeLogic recentLogic;
	
	private MainWindow window;
	
	private final PreferencesPersister persister = new PreferencesPersister();
	
	private final Model model = new Model();

	@SuppressWarnings("synthetic-access")
	public void onStart() {

		final String port = this.window.getMidiPort();
		final String rawMappings = this.window.getMidiMappings();
		new Thread(new Runnable() {
			public void run() {
				try {
					LogUtil.clearLog();
					LogUtil.log("Opening connection ...");
					final MidiMapping[] mappings = parseMappings(rawMappings);
					ViewMediator.this.recentLogic = new PrototypeLogic(port, mappings);
					ViewMediator.this.recentLogic.open();

					LogUtil.log("Successfully parsed MIDI mappings:");
					LogUtil.log("");
					int i = 1;
					for (final MidiMapping map : mappings) {
						LogUtil.log("  "+(i++)+". " + map);
					}
					LogUtil.log("");
					
					LogUtil.log("Connection established (displaying every " + LogUtil.LOG_JOINT_EVERY +"th messages) ...");
					LogUtil.log("");
					ViewMediator.this.window.setBtnStartStop(false);
					
				} catch (InvalidInputException e) {
//					LogUtil.log("WARNING: Invalid input: " + e.getMessage());
					JOptionPane.showMessageDialog(null, e.getMessage(), "Configuration Error", JOptionPane.WARNING_MESSAGE);
					
				} catch (Exception e) {
					SomeUtil.handleException(e);
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
			LogUtil.log("Connections closed.");
		} catch (Exception e) {
			SomeUtil.handleException(e);
		}
	}
	
	public void initBySettingCyclicDependency(final MainWindow pWindow) {
		this.window = pWindow;
		
		this.persister.init(this.model, MODEL_PREF_ID);
		
		String midiPort = this.model.getMidiPort();
		if(midiPort == null) {
			midiPort = "IAC Driver - XXX";
		}
		this.window.setMidiPort(midiPort);
		
		String midiMappings = this.model.getMidiMappings();
		if(midiMappings == null) {
			midiMappings =	"# Format for each line:\n" +
							"#   <JOINT>, <XYZ>, <MIDI-CH>, <CTRL-NR>\n" +
							"\n" +
							"l_hand, X, 0, 1\n" +
							"r_hand, Y, 0, 2";
		}
		this.window.setMidiMappings(midiMappings);
	}

	public void onQuit() {
		try {
			this.onStop();
			
			this.model.setMidiPort(this.window.getMidiPort());
			this.model.setMidiMappings(this.window.getMidiMappings());
			
			this.persister.persist(this.model, MODEL_PREF_ID);
		} catch (Exception e) {
			SomeUtil.handleException(e);
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
			Joint part = SomeUtil.bodyPartByOsceletonId(tokens[0].trim());
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
	
}
