package net.sf.josceleton.connection.impl.service.motion;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.motion.MotionSupplier;
import net.sf.josceleton.connection.api.service.motion.MotionSupplierFactory;

/**
 * @since 0.4
 */
class MotionSeparatorCacheImpl implements MotionSupplierFactory {
	
	private final Map<Connection, MotionSupplier> separatorByConnection = new HashMap<Connection, MotionSupplier>(); 
	
	private final MotionSeparatorFactory factory;
	
	@Inject MotionSeparatorCacheImpl(final MotionSeparatorFactory factory) {
		this.factory = factory;
	}

	/** {@inheritDoc} from {@link MotionSupplierFactory} */
	@Override public final MotionSupplier create(final Connection openedConnection) {
		if(this.separatorByConnection.containsKey(openedConnection) == false) {
			
			final MotionSupplier newSeparator = this.factory.create(openedConnection);
			// separator will add itself as an connection listener if necessary (and remove itself as well)
			this.separatorByConnection.put(openedConnection, newSeparator);
		}
		
		return this.separatorByConnection.get(openedConnection);
	}

}
