package net.sf.josceleton.prototype.console;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.Connector;
import net.sf.josceleton.prototype.console.glue.ConsolePresenter;
import net.sf.josceleton.prototype.console.glue.ConsolePresenterFactory;
import net.sf.josceleton.prototype.console.view.ConsoleWindow;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ConsolePrototypeApp {

	public static void main(final String[] args) {
		// TODO this could all be hidden (in near future) by Josceleton static facade ;) (except of step 4 of course)
		
		// 1. setup guice
		final Injector injector = Guice.createInjector(new AbstractModule() {
			@Override protected void configure() {
				install(new ConsolePrototypeModule());
			}
		});
		
		// 2. open connection
		final Connector connector = injector.getInstance(Connector.class);
		final Connection connection = connector.openConnection();
	
		// 3. setup console window and presenter
		final ConsoleWindow consoleWindow = injector.getInstance(ConsoleWindow.class);
		final ConsolePresenter consolePresenter = injector.getInstance(ConsolePresenterFactory.class).create(consoleWindow, connection);
		consolePresenter.init();
		
		// 4. create own gui
		final JFrame frame = new JFrame("My Application");
		final JButton btn = new JButton("Display Josceleton Console");
		btn.addActionListener(new ActionListener() {
			@Override public void actionPerformed(final ActionEvent event) {
				consoleWindow.setVisible(true);
			}
		});
		
		frame.getContentPane().add(btn);
		frame.pack();
	
		SwingUtilities.invokeLater(new Runnable() { @Override public void run() {
			frame.setVisible(true);	
		}});
	}

}
