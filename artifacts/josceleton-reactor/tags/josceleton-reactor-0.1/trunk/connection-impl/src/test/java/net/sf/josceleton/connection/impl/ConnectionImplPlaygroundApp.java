package net.sf.josceleton.connection.impl;

import net.sf.josceleton.commons.reflect.CommonsReflectGuiceModule;
import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.ConnectionListener;
import net.sf.josceleton.connection.api.Connector;
import net.sf.josceleton.connection.impl.osc.ConnectionImplOscGuiceModule;
import net.sf.josceleton.core.api.entity.message.JointMessage;
import net.sf.josceleton.core.api.entity.message.UserMessage;
import net.sf.josceleton.core.impl.entity.CoreImplEntityGuiceModule;
import net.sf.josceleton.core.impl.entity.message.CoreImplMessageGuiceModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

class ConnectionImplPlaygroundApp {
	
	public static void main(final String[] args) {
		
		final Injector injector = Guice.createInjector(
			new CommonsReflectGuiceModule(),
			new CoreImplEntityGuiceModule(),
			new CoreImplMessageGuiceModule(),
			new ConnectionImplGuiceModule(),
			new ConnectionImplOscGuiceModule()
		);
		
		final Connector connector = injector.getInstance(Connector.class);
		final Connection connection = connector.openConnection();
		final ConnectionImplPlaygroundApp app = new ConnectionImplPlaygroundApp(connection);
		app.doFoo();
	}
	
	private final Connection connection;
	
	public ConnectionImplPlaygroundApp(final Connection connection) {
		this.connection = connection;
	}

	public void doFoo() {
		this.connection.addListener(new ConnectionListener() {
			@Override public void onUserMessage(final UserMessage message) {
				System.out.println("onUserMessage(message=" + message + ")");
			}
			@Override public void onJointMessage(final JointMessage message) {
				System.out.println("onJointMessage(message=" + message + ")");
			}
		});
		System.out.println("waiting ...");

//		connection.removeListener(listener)
//		connection.close()
	}
	
}
