package net.sf.josceleton.prototype.midiroute.view;

import java.util.LinkedList;
import java.util.List;

import net.sf.josceleton.core.api.entity.XyzDirection;
import net.sf.josceleton.core.api.entity.body.BodyPart;
import net.sf.josceleton.prototype.midiroute.MidiMapping;
import net.sf.josceleton.prototype.midiroute.ProtoUtil;
import net.sf.josceleton.prototype.midiroute.PrototypeLogic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ViewMediator implements MainWindowListener {
	
	private static final Log LOG = LogFactory.getLog(ViewMediator.class);
	
	private PrototypeLogic recentLogic;

	@SuppressWarnings("synthetic-access")
	public void onStart(final String rawMappings, final String port) {
		new Thread(new Runnable() {
			public void run() {
				try {
					ProtoUtil.clearLog();
					ProtoUtil.log("Opening connection ...");
					ViewMediator.this.recentLogic = new PrototypeLogic(port, parseMappings(rawMappings));
					ViewMediator.this.recentLogic.open();
					ProtoUtil.log("Connection established (will display every " + ProtoUtil.LOG_JOINT_EVERY +"th joint messages)");
					ProtoUtil.log("");
				} catch (Exception e) {
					ProtoUtil.handleException(e);
				}
			}
		}).start();
	}

	public void onStop() {
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
	
	
	private static MidiMapping[] parseMappings(String raw) {
		List<MidiMapping> mappings = new LinkedList<MidiMapping>();
		
		String[] lines = raw.split("\n");
		for (String line : lines) {
			line = line.trim();
			if(line.startsWith("#") || line.isEmpty()) {
				continue;
			}
			
			String[] tokens = line.split(",");
			BodyPart part = ProtoUtil.bodyPartByOsceletonId(tokens[0].trim());
			XyzDirection direction = XyzDirection.valueOf(tokens[1].trim());
			int midiChannel = Integer.parseInt(tokens[2].trim());
			int controllerNumber = Integer.parseInt(tokens[3].trim());
			mappings.add(new MidiMapping(part, direction, midiChannel, controllerNumber));
		}
		
		return mappings.toArray(new MidiMapping[mappings.size()]);
	}
	
}
