package net.sf.josceleton.connection.impl.service.motion;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.motion.MotionSupplier;
import net.sf.josceleton.connection.api.service.motion.MotionSupplierFactory;

/**
 * Caches / lazy instantiates a <code>MotionSupplier</code> for each <code>Connection</code>.
 * 
 * @since 0.4
 */
class MotionSupplierFactoryImpl implements MotionSupplierFactory {
	
	private final Map<Connection, MotionSupplier> supplierByConnection = new HashMap<Connection, MotionSupplier>(); 
	
	private final MotionSupplierInternalFactory factory;
	
	@Inject MotionSupplierFactoryImpl(final MotionSupplierInternalFactory factory) {
		this.factory = factory;
	}

	/** {@inheritDoc} from {@link MotionSupplierFactory} */
	@Override public final MotionSupplier create(final Connection openedConnection) {
		if(this.supplierByConnection.containsKey(openedConnection) == false) {
			
			final MotionSupplier newSupplier = this.factory.create(openedConnection);
			// supplier will add itself as an connection listener if necessary (and remove itself as well)
			this.supplierByConnection.put(openedConnection, newSupplier);
		}
		
		return this.supplierByConnection.get(openedConnection);
	}

}
