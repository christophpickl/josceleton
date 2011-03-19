package net.sf.josceleton.core.api.entity.user;

// MINOR @JAVADOC check if sample is displayed correctly
/**
 * Lets you avoid if-else cascades via dynamic dispatching.
 * 
 * E.g.:
 * <pre><code>
 * UserState state = ...;
 * String stateLabel = state.callback(new UserStateCallback<String>() {
 *     public String onStateWaiting() { return "Waiting"; }
 *     public String onStateProcessing() { return "Processing"; }
 *     public String onStateDead() { return "Dead"; }
 *   }) {
 * }
 * </code></pre>
 * 
 * @since 0.3
 */
public interface UserStateCallback<T> {

	T onStateWaiting();
	
	T onStateProcessing();
	
	T onStateDead();
	
}
