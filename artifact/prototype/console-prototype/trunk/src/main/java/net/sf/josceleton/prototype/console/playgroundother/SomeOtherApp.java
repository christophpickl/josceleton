package net.sf.josceleton.prototype.console.playgroundother;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.Connector;
import net.sf.josceleton.prototype.console.ConsolePrototypeModule;
import net.sf.josceleton.prototype.console.glue.ConsolePresenter;
import net.sf.josceleton.prototype.console.glue.ConsolePresenterFactory;
import net.sf.josceleton.prototype.console.view.ConsoleWindow;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class SomeOtherApp {
	
	public static void main(String[] args) {
		final Injector injector = Guice.createInjector(new AbstractModule() {
			@Override protected void configure() {
				install(new ConsolePrototypeModule());
			}
		});
		
		final Connector connector = injector.getInstance(Connector.class);
		final Connection connection = connector.openConnection();

		final ConsoleWindow window = injector.getInstance(ConsoleWindow.class);
		final ConsolePresenter presenter = injector.getInstance(ConsolePresenterFactory.class).create(window, connection);
		presenter.init();
		
		final JFrame frame = new JFrame("My own application");
		final JButton btn = new JButton("Display Console Window");
		btn.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent event) {
				window.setVisible(true);
			}
		});
		
		frame.getContentPane().add(btn);
		frame.pack();

		SwingUtilities.invokeLater(new Runnable() { @Override public void run() {
			frame.setVisible(true);	
		}});
	}
	
}
