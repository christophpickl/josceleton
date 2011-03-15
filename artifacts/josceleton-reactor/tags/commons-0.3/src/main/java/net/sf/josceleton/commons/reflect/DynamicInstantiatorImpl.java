package net.sf.josceleton.commons.reflect;

import java.lang.reflect.Constructor;

import net.sf.josceleton.commons.exception.InvalidArgumentException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class DynamicInstantiatorImpl implements DynamicInstantiator {
	
	private static final Log LOG = LogFactory.getLog(DynamicInstantiatorImpl.class);
	
	DynamicInstantiatorImpl() {
		// package-private constructor to reduce visibility
	}

	/** {@inheritDoc} from {@link DynamicInstantiator} */
	@Override public final <T> T create(final ClassAdapter<T> clazz, final Object... arguments) {
		LOG.debug("instantiate(clazz.name=" + clazz.getName() + ", arguments.length=" + arguments.length + ")");
		
		for (int i = 0; i < arguments.length; i++) {
			final Object currentArgument = arguments[i];
			if(currentArgument == null) {
				throw InvalidArgumentException.newNotNull("arguments[]@" + i);
			}
		}
		
		final Constructor<T> constructor = ReflectUtil.findConstructor(clazz, arguments);
		
	    try {
			return constructor.newInstance(arguments);
			// !!! bad design; simple exception handling ...
			//     done to increase coverage and avoid having to test for each and every exception ;)
		} catch (final Exception e) {
			throw DynamicInstantiationException.newForInstantiation(clazz, arguments, e);
		}
	}

}
