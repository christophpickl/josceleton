package net.sf.josceleton;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.Connector;
import net.sf.josceleton.connection.api.service.motion.MotionStreamFactory;
import net.sf.josceleton.connection.impl.service.motion.ContinuousMotionStreamFactory;
import net.sf.josceleton.core.api.entity.location.Range;
import net.sf.josceleton.core.api.entity.location.RangeFactory;
import net.sf.josceleton.core.api.entity.location.RangeScaler;
import net.sf.josceleton.motion.api.gesture.GestureFactoryFacade;

import com.google.inject.Injector;

/**
 * Facade hiding anything which is anyhow related to the internal dependency injection framework.
 * 
 * <p>Sample usage:
 * <pre>
 * Injector injector = JosceletonGuice.newInjector();
 * JosceletonFacade josceleton = new JosceletonFacadeImpl(injector);
 * // josceleton.getConnector()
 * // josceleton.getContinuousMotionStreamFactory()
 * // josceleton.getGestureFactoryFacade()
 * // josceleton.getMotionStreamFactory()
 * // josceleton.get...
 * </pre>
 * 
 * @since 0.2
 */
public class JosceletonFacadeImpl implements JosceletonFacade {

	private final Injector injector;
	
	private final Connector connector;

	private final RangeFactory rangeFactory;

	private final MotionStreamFactory motionStreamFactory;

	private final GestureFactoryFacade gestureFactoryFacade;

	public JosceletonFacadeImpl(final Injector injector) {
		this.injector = injector;
		this.connector = this.injector.getInstance(Connector.class);
		this.rangeFactory = this.injector.getInstance(RangeFactory.class);
		this.motionStreamFactory = this.injector.getInstance(MotionStreamFactory.class);
		this.gestureFactoryFacade = this.injector.getInstance(GestureFactoryFacade.class);
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
	@Override public final ContinuousMotionStreamFactory getContinuousMotionStreamFactory() {
		return this.injector.getInstance(ContinuousMotionStreamFactory.class); // MINOR maybe cache instance as well
	}

	/** {@inheritDoc} from {@link JosceletonFacade} */
	@Override public final RangeScaler getRangeScaler() {
		return this.injector.getInstance(RangeScaler.class);
	}

	/** {@inheritDoc} from {@link JosceletonFacade} */
	@Override public final RangeFactory getRangeFactory() {
		return this.rangeFactory;
	}

	/** {@inheritDoc} from {@link JosceletonFacade} */
	@Override public final Range newRange(
			final float fromStart, final float fromEnd, final int toStart, final int toEnd) {
		return this.rangeFactory.createSpecific(fromStart, fromEnd, toStart, toEnd);
	}

	/** {@inheritDoc} from {@link JosceletonFacade} */
	@Override public final MotionStreamFactory getMotionStreamFactory() {
		return this.motionStreamFactory;
	}

	@Override public final GestureFactoryFacade getGestureFactoryFacade() {
		return this.gestureFactoryFacade;
	}

}
