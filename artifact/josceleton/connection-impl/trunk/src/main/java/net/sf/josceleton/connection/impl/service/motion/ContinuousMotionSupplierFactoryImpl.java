package net.sf.josceleton.connection.impl.service.motion;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.motion.ContinuousMotionSupplier;
import net.sf.josceleton.connection.api.service.motion.MotionSupplier;
import net.sf.josceleton.connection.api.service.motion.MotionSupplierFactory;
import net.sf.josceleton.connection.api.service.user.UserService;

import com.google.inject.Inject;

class ContinuousMotionSupplierFactoryImpl implements
		ContinuousMotionSupplierFactory {
	
	private final ContinuousMotionSupplierInternalFactory internalSupplierFactory;
	
	private final MotionSupplierFactory supplierFactory;
	
	@Inject ContinuousMotionSupplierFactoryImpl(
			final ContinuousMotionSupplierInternalFactory internalSupplierFactory,
			final MotionSupplierFactory supplierFactory) {
		this.internalSupplierFactory = internalSupplierFactory;
		this.supplierFactory = supplierFactory;
	}
	
	@Override public final ContinuousMotionSupplier create(final Connection connection) {
		final MotionSupplier supplier = this.supplierFactory.create(connection);
		final UserService userService = connection.getUserService();
		final ContinuousMotionSupplierInternal continuousSupplier =
			this.internalSupplierFactory.create(supplier, userService);
		
		continuousSupplier.initAttachingToUser();
		userService.addListener(continuousSupplier); // FIXME somewhen, someone has to remove listener!!!
		return continuousSupplier;
	}

}
