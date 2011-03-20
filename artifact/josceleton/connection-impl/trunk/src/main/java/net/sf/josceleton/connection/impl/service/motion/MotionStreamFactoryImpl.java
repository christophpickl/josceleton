package net.sf.josceleton.connection.impl.service.motion;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.motion.MotionStream;
import net.sf.josceleton.connection.api.service.motion.MotionStreamFactory;

/**
 * Caches / lazy instantiates a <code>MotionStream</code> for each <code>Connection</code>.
 * 
 * @since 0.4
 */
class MotionStreamFactoryImpl implements MotionStreamFactory {
	
	private final Map<Connection, MotionStream> streamsCache = new HashMap<Connection, MotionStream>(); 
	
	private final MotionStreamInternalFactory factory;
	
	@Inject MotionStreamFactoryImpl(final MotionStreamInternalFactory factory) {
		this.factory = factory;
	}

	/** {@inheritDoc} from {@link MotionStreamFactory} */
	@Override public final MotionStream create(final Connection openedConnection) {
		if(this.streamsCache.containsKey(openedConnection) == false) {
			
			final MotionStream newStream = this.factory.create(openedConnection);
			// stream will add itself as an connection listener if necessary (and remove itself as well)
			this.streamsCache.put(openedConnection, newStream);
		}
		
		return this.streamsCache.get(openedConnection);
	}

}
