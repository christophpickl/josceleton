package net.sf.josceleton.prototype.midiroute;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.ConnectionListener;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;
import net.sf.josceleton.josceleton.Josceleton;
import net.sf.josceleton.prototype.midiroute.view.ProtoUtil;

public class PrototypeLogic implements ConnectionListener {
	private static final Log LOG = LogFactory.getLog(PrototypeLogic.class);
	
	private Connection oscConnection;
	private final MidiConnection midiConnection;
	private final CoordinatesTransformer transformer = new CoordinatesTransformer();
	private final JointMidiMappings mappings;

//	new PrototypeLogic("IAC Driver - Chrisi A",
//			//           bodyPart              direction        midiChannel  controllerNumber
//		new JointMidiMap(Body.HAND().LEFT(),   XyzDirection.X,  1,           1               ),
//		new JointMidiMap(Body.HAND().LEFT(),   XyzDirection.Y,  1,           2               )
//	).startUp();
	
	public PrototypeLogic(String midiPort, JointMidiMap... maps) {
		this.midiConnection = new MidiConnection(midiPort);
		this.mappings = new JointMidiMappings(Arrays.asList(maps));
	}
	
	public void open() throws Exception {
		LOG.info("open()");
		
		ProtoUtil.log("Successfully parsed configurations:");
		int i = 1;
		for (final JointMidiMap map : this.mappings.getMaps()) {
			ProtoUtil.log("  "+(i++)+". " + map);
		}
		ProtoUtil.log("");
		
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
	
	// overrides ConnectionListener
	public void onJointMessage(JointMessage message) {
		this.mappings.execute(message, this.transformer, this.midiConnection);
	}

	// overrides ConnectionListener
	public void onUserMessage(UserMessage message) {
		ProtoUtil.log("Received " + message.getUserState() + " state for user " + message.getUser().getOsceletonId());
	}
}
