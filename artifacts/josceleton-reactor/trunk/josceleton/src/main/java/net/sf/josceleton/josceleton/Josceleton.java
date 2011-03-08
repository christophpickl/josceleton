package net.sf.josceleton.josceleton;

import net.sf.josceleton.connection.api.Connection;

import com.google.inject.Guice;


public final class Josceleton /*statically implements JosceletonFacade */ {
	
	private static final JosceletonFacade FACADE =
		new JosceletonFacadeImpl(
			Guice.createInjector(
				new JosceletonGuiceModule()));
	
	private Josceleton() {
		// non instantiable, as just static methods available
	}
	
	public static Connection openConnection() {
		return FACADE.openConnection();
	}
	
	public static Connection openConnectionOnPort(final int port) {
		return FACADE.openConnectionOnPort(port);
	}
}
