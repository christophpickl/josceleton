package net.sf.josceleton.core.api.entity;

/**
 * Lets you avoid if-else cascades via dynamic dispatching.
 * 
 * @since 0.3
 */
public interface UserStateFunction<T> {

	T onStateWaiting();
	
	T onStateProcessing();
	
	T onStateDead();
	
}
