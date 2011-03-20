package net.sf.josceleton.prototype.midi.logic;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import net.pulseproject.commons.midi.entity.ControllerMessage;
import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.motion.ContinuousMotionSupplier;
import net.sf.josceleton.connection.api.service.motion.MotionSupplierListener;
import net.sf.josceleton.connection.api.service.user.UserServiceListener;
import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.api.entity.user.User;
import net.sf.josceleton.josceleton.Josceleton;
import net.sf.josceleton.prototype.midi.util.LogUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PrototypeLogic implements MotionSupplierListener, UserServiceListener {
	private static final Log LOG = LogFactory.getLog(PrototypeLogic.class);
	
	private Connection joscConnection;
	private final MidiConnection midiConnection;
	private final Collection<MidiMapping> mappings;
	
	private ContinuousMotionSupplier cms;

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
		
		this.joscConnection = Josceleton.openConnection();
		this.cms = Josceleton.getContinuousMotionSupplierFactory().create(this.joscConnection);
		this.joscConnection.getUserService().addListener(this);
		this.cms.addListener(this);
		
		LOG.info("Running ...");
	}
	
	public void close() {
		LOG.info("close()");
		if(this.joscConnection != null) { // TODO check hack
			this.cms.removeListener(this);
			this.cms = null;
			
			this.joscConnection.getUserService().removeListener(this);
			this.joscConnection.close();
			this.joscConnection = null;
			
			this.midiConnection.close();
		}
	}

	@Override
	public void onMoved(Joint part, Coordinate updatedCoordinate, Skeleton skeleton) {
		for (final MidiMapping map : this.mappings) {
			if(map.appliesPart(part) == false) {
				continue;
			}
			
			final ControllerMessage midiMsg = map.buildMidiMessage(updatedCoordinate, skeleton);
			if(midiMsg != null) {
				this.midiConnection.send(midiMsg);
			}
		}
	}

	@Override public void onUserWaiting(User user) { LogUtil.log("New waiting User: " + user.getOsceletonId()); }
	@Override public void onUserProcessing(User user) { LogUtil.log("New processing User: " + user.getOsceletonId()); }
	@Override public void onUserDead(User user) { LogUtil.log("Lost User: " + user.getOsceletonId()); }
	
}
