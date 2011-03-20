package net.sf.josceleton.prototype.midi.view;

import javax.swing.JOptionPane;

import net.sf.josceleton.prototype.midi.Model;
import net.sf.josceleton.prototype.midi.logic.InvalidInputException;
import net.sf.josceleton.prototype.midi.logic.MappingsParser;
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
	
	private final PreferencesPersister persister = new PreferencesPersister();
	
	private final Model model;
	
	
	public ViewMediator(final Model model) {
		this.model = model;
		
		this.persister.init(this.model, MODEL_PREF_ID);
		
		String midiPort = this.model.getMidiPort();
		if(midiPort == null) {
			midiPort = "IAC Driver - XXX";
		}
		this.model.setMidiPort(midiPort);
		
		String midiMappings = this.model.getMidiMappings();
		if(midiMappings == null) {
			midiMappings =	"# Format for each line:\n" +
							"#   <JOINT>, <XYZ>, <MIDI-CH>, <CTRL-NR>\n" +
							"\n" +
							"l_hand, X, 0, 1\n" +
							"r_hand, Y, 0, 2";
		}
		this.model.setMidiMappings(midiMappings);
	}
	
	@SuppressWarnings("synthetic-access")
	public void onStart() {

		final String port = this.model.getMidiPort();
		final String rawMappings = this.model.getMidiMappings();
		new Thread(new Runnable() {
			public void run() {
				try {
					ViewMediator.this.model.setRunning(true);
					LogUtil.clearLog();
					LogUtil.log("Opening connection ...");
					final MidiMapping[] mappings = MappingsParser.parseMappings(rawMappings);
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
					
				} catch (InvalidInputException e) {
//					LogUtil.log("WARNING: Invalid input: " + e.getMessage());
					ViewMediator.this.model.setRunning(false);
					JOptionPane.showMessageDialog(null, e.getMessage(), "Configuration Error", JOptionPane.WARNING_MESSAGE);
					
				} catch (Exception e) {
					ViewMediator.this.model.setRunning(false);
					SomeUtil.handleException(e);
				}
			}
		}).start();
	}

	public void onStop() {
		ViewMediator.this.model.setRunning(false);
		
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

	public void onQuit() {
		try {
			this.onStop();
			
			this.persister.persist(this.model, MODEL_PREF_ID);
		} catch (Exception e) {
			SomeUtil.handleException(e);
		}
	}
	
}
