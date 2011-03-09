package net.sf.josceleton.josceleton;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.ConnectionListener;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Facade class with static methods, acting as a single point of entry.
 * 
 * It is highly recommended to NOT make use of this class. Only exception is quick hacky code...
 * Please see documentation on the website for further help about this topic.
 * 
 * @since 0.2
 */
public final class Josceleton /* statically implements JosceletonFacade */ {
	
	/** Just some sample code. */
	public static void main(final String[] args) {
		final Connection connection = Josceleton.openConnection();
		connection.addListener(new ConnectionListener() {
			@Override public void onUserMessage(final UserMessage message) {
				System.out.println("Received a user message: " + message);
			}
			@Override public void onJointMessage(final JointMessage message) {
				// this will be invoked quiet often ;)
				System.out.println("Received a joint message: " + message);
			}
		});
		System.out.println("Running ...");
//		connection.close();
	}
	
	private static final JosceletonFacade FACADE =
		new JosceletonFacadeImpl(Josceleton.newGuiceInjector());
	
	private Josceleton() {
		// non instantiable, as just static methods available
	}

	/**
	 * @since 0.2
	 */
	public static Connection openConnection() {
		return FACADE.openConnection();
	}

	/**
	 * @since 0.2
	 */
	public static Connection openConnectionOnPort(final int port) {
		return FACADE.openConnectionOnPort(port);
	}
	
	/**
	 * Handy utility method only, creating a new "instance realm" and should only be invoked once.
	 * 
	 * @since 0.2
	 */
	public static Injector newGuiceInjector() {
		return Guice.createInjector(new JosceletonGuiceModule());
	}
}
