package net.sf.josceleton.josceleton;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.Connector;
import net.sf.josceleton.connection.impl.service.motion.ContinuousMotionSupplierFactory;
import net.sf.josceleton.core.api.entity.location.Direction;
import net.sf.josceleton.core.api.entity.location.Range;
import net.sf.josceleton.core.api.entity.location.RangeFactory;
import net.sf.josceleton.core.api.entity.location.RangeScaler;

import com.google.inject.Injector;

/**
 * @since 0.2
 */
public class JosceletonFacadeImpl implements JosceletonFacade {

	private final Injector injector;
	
	private final Connector connector;
	
	private final RangeFactory rangeFactory;
	

	public JosceletonFacadeImpl(final Injector injector) {
		this.injector = injector;
		this.connector = this.injector.getInstance(Connector.class);
		this.rangeFactory = this.injector.getInstance(RangeFactory.class);
	}

	/** {@inheritDoc} from {@link JosceletonFacade} */
	@Override public final Connection openConnection() {
		return this.connector.openConnection();
	}

	/** {@inheritDoc} from {@link JosceletonFacade} */
	@Override public final Connection openConnectionOnPort(final int port) {
		return this.connector.openConnectionOnPort(port);
	}

	/** {@inheritDoc} from {@link JosceletonFacade} */
	@Override public final Connector getConnector() {
		return this.connector;
	}

	/** {@inheritDoc} from {@link JosceletonFacade} */
	@Override public final ContinuousMotionSupplierFactory getContinuousMotionSupplierFactory() {
		return this.injector.getInstance(ContinuousMotionSupplierFactory.class); // MINOR maybe cache instance as well
	}

	/** {@inheritDoc} from {@link JosceletonFacade} */
	@Override public final RangeScaler getRangeScaler() {
		return this.injector.getInstance(RangeScaler.class);
	}

	/** {@inheritDoc} from {@link JosceletonFacade} */
	@Override public final Range newRange(
			final float fromStart, final float fromEnd, final int toStart, final int toEnd) {
		return this.rangeFactory.createSpecific(fromStart, fromEnd, toStart, toEnd);
	}

}
