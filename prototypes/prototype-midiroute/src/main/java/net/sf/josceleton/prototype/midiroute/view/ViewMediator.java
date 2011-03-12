package net.sf.josceleton.prototype.midiroute.view;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.pulseproject.commons.util.StringUtil;
import net.sf.josceleton.core.api.entity.XyzDirection;
import net.sf.josceleton.core.api.entity.body.Body;
import net.sf.josceleton.core.api.entity.body.BodyPart;
import net.sf.josceleton.prototype.midiroute.JointMidiMap;
import net.sf.josceleton.prototype.midiroute.PrototypeLogic;

public class ViewMediator implements MainWindowListener {
	
	private static final Log LOG = LogFactory.getLog(ViewMediator.class);
	
	private PrototypeLogic recentLogic;


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
	
	
	private JointMidiMap[] parseConfig(final String raw) {
		final List<JointMidiMap> maps = new LinkedList<JointMidiMap>();
		
		final String[] lines = raw.split("\n");
		for (String line : lines) {
			line = line.trim();
			if(line.startsWith("#") || line.isEmpty()) {
				continue;
			}
			
			final String[] tokens = line.split(",");
			// r_hand,Y,1,2
			final BodyPart part = bodyPartByOsceletonId(tokens[0].trim());
			final XyzDirection direction = XyzDirection.valueOf(tokens[1].trim());
			final int midiChannel = Integer.parseInt(tokens[2].trim());
			final int controllerNumber = Integer.parseInt(tokens[3].trim());
			maps.add(new JointMidiMap(part, direction, midiChannel, controllerNumber));
		}
		
		return maps.toArray(new JointMidiMap[maps.size()]);
	}

	private BodyPart bodyPartByOsceletonId(String rawPart) {
		for (BodyPart part : Body.values()) {
			if(part.getOsceletonId().equals(rawPart)) {
				return part;
			}
		}
		throw new RuntimeException("unkown body part: " + rawPart);
	}


	public void onQuit() {
		try {
			this.onStop();
		} catch (Exception e) {
			ProtoUtil.handleException(e);
		}
	}
	

	public void onStart(final String config, final String port) {
//		ProtoUtil.clearLog();
		new Thread(new Runnable() {
			public void run() {
				try {
					LOG.debug("config:\n=========================\n" + config + "\n=========================\n");
					ProtoUtil.log("Opening connection ...");
					recentLogic = new PrototypeLogic(port, parseConfig(config));
					recentLogic.open();
					ProtoUtil.log("Connection established (will display every " + ProtoUtil.LOG_JOINT_EVERY +"th joint messages)");
					ProtoUtil.log("");
				} catch (Exception e) {
					ProtoUtil.handleException(e);
				}
			}
		}).start();
	}
	
}
