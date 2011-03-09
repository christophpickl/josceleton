package net.sf.josceleton.josceleton;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.ConnectionListener;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;

import com.google.inject.Guice;


/**
 * @since 0.2
 */
public final class Josceleton /*statically implements JosceletonFacade */ {
	
	public static void main(final String[] args) {
		final Connection connection = Josceleton.openConnection();
		connection.addListener(new ConnectionListener() {
			@Override public void onUserMessage(final UserMessage message) {
				System.out.println("onUserMessage(message=" + message + ")");
			}
			@Override public void onJointMessage(final JointMessage message) {
				System.out.println("onJointMessage(message=" + message + ")");
			}
		});
		System.out.println("Running ...");
//		connection.close();
	}
	
	private static final JosceletonFacade FACADE =
		new JosceletonFacadeImpl(
			Guice.createInjector(
				new JosceletonGuiceModule()));
	
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
}
