package net.sf.josceleton.core.api.async;

/**
 * @since 0.1
 */
public interface Async<L extends Listener> {

	/**
	 * @since 0.1
	 */
	void addListener(L listener);

	/**
	 * @since 0.1
	 */
	void removeListener(L listener);
	
//	void removeAllListeners(); // MINOR ??? is Async.removeAllListeners() needed?
	
}
