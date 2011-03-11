package net.sf.josceleton.connection.impl.osc;

import net.sf.josceleton.commons.reflect.ClassAdapter;
import net.sf.josceleton.commons.reflect.DynamicInstantiationException;
import net.sf.josceleton.commons.reflect.DynamicInstantiator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Inject;
import com.illposed.osc.OSCPortIn;

class OscPortOpenerImpl implements OscPortOpener {
	
	private static final Log LOG = LogFactory.getLog(OscPortOpenerImpl.class);
	
	private final DynamicInstantiator instantiator;
	
	private final OscPortFactory portFactory;
	
	private final ClassAdapter<OSCPortIn> oscPortInType;
	
	
	@Inject OscPortOpenerImpl(
			final DynamicInstantiator instantiator,
			final OscPortFactory portFactory,
			final ClassAdapter<OSCPortIn> oscPortInType
			) {
		this.instantiator = instantiator;
		this.portFactory = portFactory;
		this.oscPortInType = oscPortInType;
	}

	/** {@inheritDoc} from {@link OscPortOpener} */
	@Override public final OscPort connect(final int port) {
		
		final OSCPortIn oscPortIn;
		try {
			LOG.debug("Connecting to OSCPortIn with port number [" + port + "] ...");
			
			// a.k.a. new OSCPortIn(port);
			oscPortIn = this.instantiator.create(this.oscPortInType, Integer.valueOf(port));
			
			// LUXURY @AOP LOG statements around method could be injected
			LOG.debug("Connection to OSCPortIn established.");
			
		} catch (final DynamicInstantiationException e) {
			throw OscPortOpeningException.newByPort(port, e);
		}
		
		return this.portFactory.create(oscPortIn);
	}
	
}
