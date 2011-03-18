package net.sf.josceleton.connection.impl.service.motion;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.motion.MotionSeparator;
import net.sf.josceleton.connection.api.service.motion.MotionSeparatorCache;

/**
 * @since 0.4
 */
class MotionSeparatorCacheImpl implements MotionSeparatorCache {
	
	private final Map<Connection, MotionSeparator> separatorByConnection = new HashMap<Connection, MotionSeparator>(); 
	
	private final MotionSeparatorFactory factory;
	
	@Inject MotionSeparatorCacheImpl(final MotionSeparatorFactory factory) {
		this.factory = factory;
	}

	/** {@inheritDoc} from {@link MotionSeparatorCache} */
	@Override public final MotionSeparator lookupMotionSeparator(final Connection openedConnection) {
		if(this.separatorByConnection.containsKey(openedConnection) == false) {
			
			final MotionSeparator newSeparator = this.factory.create(openedConnection);
			// separator will add itself as an connection listener if necessary (and remove itself as well)
			this.separatorByConnection.put(openedConnection, newSeparator);
		}
		
		return this.separatorByConnection.get(openedConnection);
	}

}
