package net.sf.josceleton.josceleton;

import net.sf.josceleton.connection.api.Connection;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Facade class with static methods, acting as a single point of entry.
 * 
 * It is highly recommended to NOT make use of this class. Only exception is quick hacky code...
 * Please see documentation on the website for further help about this topic.
 * 
 * @since 0.2
 */
public final class Josceleton /* statically implements JosceletonFacade */ {
	
	private static /*non-final for testability*/ JosceletonFacade facade =
		new JosceletonFacadeImpl(Josceleton.newGuiceInjector());
	
	private Josceleton() {
		// non instantiable, as just static methods available
	}

	/**
	 * @since 0.2
	 */
	public static Connection openConnection() {
		return Josceleton.facade.openConnection();
	}

	/**
	 * @since 0.2
	 */
	public static Connection openConnectionOnPort(final int port) {
		return Josceleton.facade.openConnectionOnPort(port);
	}
	
	/**
	 * Handy utility method only, creating a new "instance realm" and should only be invoked once.
	 * 
	 * @since 0.2
	 */
	public static Injector newGuiceInjector() {
		return Guice.createInjector(new JosceletonGuiceModule());
	}
	
	/**
	 * @since 0.4
	 */
	public static Module newGuiceModule() {
		return new JosceletonGuiceModule();
	}
	
	// LUXURY provide more static facade API (remember: make it as simple as possible,... and as complex as necessary)
//	public static GestureFactory getGestureFactory() {
//	public static HitWallBuilder newHitWallBuilder() {
//	public static HitWallGesture newHitWall(HitWallConfig) {
	
}
