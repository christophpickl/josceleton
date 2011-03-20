package net.sf.josceleton.josceleton.experimental;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.motion.ContinuousMotionSupplier;
import net.sf.josceleton.connection.impl.service.motion.ContinuousMotionSupplierFactory;
import net.sf.josceleton.josceleton.Josceleton;

/**
 * @since 0.5
 */
public class JosceletonContinuousMotionSupplierFactory implements ContinuousMotionSupplierFactory {

	private static final ContinuousMotionSupplierFactory DELEGATE = Josceleton.getContinuousMotionSupplierFactory();
	
	@Override public final ContinuousMotionSupplier create(final Connection connection) {
		return DELEGATE.create(connection);
	}

}
