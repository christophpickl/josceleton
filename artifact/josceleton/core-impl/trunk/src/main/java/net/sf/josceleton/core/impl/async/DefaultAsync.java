package net.sf.josceleton.core.impl.async;

import java.util.HashSet;
import java.util.Set;

import net.sf.josceleton.core.api.async.Async;
import net.sf.josceleton.core.api.async.Listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Can either be used as an super class (and extending from it) or by delegating to a private member instance. 
 */
public class DefaultAsync<L extends Listener> implements Async<L> {
	
	private static final Log LOG = LogFactory.getLog(DefaultAsync.class);
	
	private final Set<L> registeredListeners = new HashSet<L>();
	
	
	public final Iterable<L> getListeners() {
		return this.registeredListeners;
	}

	/** {@inheritDoc} from {@link Async} */
	@Override public final void addListener(final L listener) {
		this.beforeAddListener(listener);
		
		final boolean wasChanged = this.registeredListeners.add(listener);
		if(wasChanged == false) {
			LOG.warn("Not added listener [" + listener + "] as it was yet added!");
		}
	}

	/** {@inheritDoc} from {@link Async} */
	@Override public final void removeListener(final L listener) {
		this.beforeRemoveListener(listener);
		
		final boolean wasRemoved = this.registeredListeners.remove(listener);
		if(wasRemoved == false) {
			LOG.warn("Not removed listener [" + listener + "] as it was not yet added!");
		}
	}

	/**
	 * Will be used to check if <code>Closeable</code> was not yet closed.
	 */
	protected void beforeAddListener(@SuppressWarnings("unused") final L listener) {
		// can be overridden
	}

	/**
	 * Will be used to check if <code>Closeable</code> was not yet closed.
	 */
	protected void beforeRemoveListener(@SuppressWarnings("unused") final L listener) {
		// can be overridden
	}
}
