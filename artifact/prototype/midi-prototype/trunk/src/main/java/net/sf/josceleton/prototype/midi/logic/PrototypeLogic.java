package net.sf.josceleton.prototype.midi.logic;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import net.pulseproject.commons.midi.entity.ControllerMessage;
import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.ConnectionListener;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;
import net.sf.josceleton.josceleton.Josceleton;
import net.sf.josceleton.prototype.midi.util.LogUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PrototypeLogic implements ConnectionListener {
	private static final Log LOG = LogFactory.getLog(PrototypeLogic.class);
	
	private Connection oscConnection;
	private final MidiConnection midiConnection;
	private final Collection<MidiMapping> mappings;

//	new PrototypeLogic("IAC Driver - Chrisi A",
//			//           bodyPart              direction        midiChannel  controllerNumber
//		new MidiMapping(Body.HAND().LEFT(),   XyzDirection.X,  1,           1               ),
//		new MidiMapping(Body.HAND().LEFT(),   XyzDirection.Y,  1,           2               )
//	).startUp();
	
	public PrototypeLogic(String midiPort, MidiMapping... maps) {
		this.midiConnection = new MidiConnection(midiPort);
		this.mappings = new LinkedList<MidiMapping>(Arrays.asList(maps));
	}
	
	public void open() throws InvalidInputException {
		LOG.info("open()");
		
		this.midiConnection.connect();
		this.oscConnection = Josceleton.openConnection();
		this.oscConnection.addListener(this);
		
		LOG.info("Running ...");
	}
	
	public void close() {
		LOG.info("close()");
		this.oscConnection.removeListener(this);
		this.oscConnection.close();
		this.midiConnection.close();
	}
	
	public void onJointMessage(JointMessage message) {
		final Joint part = message.getJoint();
		
		for (final MidiMapping map : this.mappings) {
			if(map.appliesPart(part) == false) {
				continue;
			}
			
			final Coordinate coord = message.getCoordinate();
			final ControllerMessage midiMsg = map.buildMidiMessage(coord);
			
			this.midiConnection.send(midiMsg);
		}
	}

	public void onUserMessage(UserMessage message) {
		LogUtil.log("Received " + message.getUserState() + " state for user " + message.getUser().getOsceletonId());
	}
}
