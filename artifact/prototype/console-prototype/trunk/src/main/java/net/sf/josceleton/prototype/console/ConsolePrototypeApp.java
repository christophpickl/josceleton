package net.sf.josceleton.prototype.console;

import javax.swing.SwingUtilities;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.josceleton.Josceleton;
import net.sf.josceleton.prototype.console.misc.OscConnectionWindowGlue;
import net.sf.josceleton.prototype.console.misc.PlainSimpleMidiSenderImpl;
import net.sf.josceleton.prototype.console.scenario1.Scenario1;
import net.sf.josceleton.prototype.console.scenario1.SimpleMidiSender;
import net.sf.josceleton.prototype.console.view.MainWindow;
import net.sf.josceleton.prototype.console.view.MainWindowListener;
import net.sf.josceleton.prototype.console.view.UserPanelFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class ConsolePrototypeApp implements MainWindowListener {
	
	private static final Log LOG = LogFactory.getLog(ConsolePrototypeApp.class);
	
	private Connection connection;
	
	
	public static void main(final String[] args) {
		final ConsolePrototypeApp app = new ConsolePrototypeApp();
		app.startUp();
	}
	
	public final void startUp() {
		LOG.info("startUp()");
		
		final MainWindow window = new MainWindow(this);
		
		final Injector injector = Guice.createInjector(new ConsolePrototypeModule());
		final UserPanelFactory userPanelFactory = injector.getInstance(UserPanelFactory.class);
		final OscConnectionWindowGlue windowGlue = new OscConnectionWindowGlue(userPanelFactory, window);
		
		this.connection = Josceleton.openConnection();
		
		this.connection.addListener(windowGlue);
		this.connection.getUserService().addListener(windowGlue);
		
//		final UserCollection users = this.connection.getUserCollection();
//		windowGlue.initWithUsers(users);
//		DELME this.connection.addListener(windowGlue);
		
		
		final int noteChannel = 0;
		final SimpleMidiSender midiSender = new PlainSimpleMidiSenderImpl(noteChannel);
		final Scenario1 scenario = new Scenario1(this.connection);
		scenario.registerGestures(midiSender);
		
		
		SwingUtilities.invokeLater(new Runnable() { @Override public void run() {
			window.setVisible(true);	
		}});
	}
	
	@Override
	public final void onQuit() {
		LOG.debug("onQuit()");
		
		this.connection.close();
		this.connection = null;
	}

}
