package net.sf.josceleton.prototype.console;

import javax.swing.SwingUtilities;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.Connector;
import net.sf.josceleton.connection.api.service.user.AvailableUsersCollection;
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
import com.google.inject.Inject;
import com.google.inject.Injector;

public class ConsolePrototypeApp implements MainWindowListener {
	
	private static final Log LOG = LogFactory.getLog(ConsolePrototypeApp.class);
	
	private final UserPanelFactory userPanelFactory;
	private final Connector connector;
	private Connection connection;
	
	public static void main(final String[] args) {
		final Injector injector = Guice.createInjector(new ConsolePrototypeModule());
		final ConsolePrototypeApp app = injector.getInstance(ConsolePrototypeApp.class);
		app.startUp();
	}
	
	@Inject ConsolePrototypeApp(final UserPanelFactory userPanelFactory, final Connector connector) {
		this.userPanelFactory = userPanelFactory;
		this.connector = connector;
	}

	public final void startUp() {
		LOG.info("startUp()");
		
		this.connection = this.connector.openConnection();
		final MainWindow window = new MainWindow(this);
		final AvailableUsersCollection users = this.connection.getUserService();
		
		// MINOR rewrite to interface and get instance injected by guice
		final OscConnectionWindowGlue windowGlue = new OscConnectionWindowGlue(this.userPanelFactory, window, users);
		
		synchronized(windowGlue) {
			// we dont want to have a context switch between the first two lines
			// could be a user change ... but... this is not really very likely!
			// ====> in here we are dealing with milli seconds; osceleton detects users within several seconds :)
			windowGlue.initAvailableUsers();
			this.connection.getUserService().addListener(windowGlue);
			
			this.connection.addListener(windowGlue);
		}
		
		final int noteChannel = 0;
		final SimpleMidiSender midiSender = new PlainSimpleMidiSenderImpl(noteChannel);
		final Scenario1 scenario = new Scenario1(this.connection);
		scenario.registerGestures(midiSender);
		
		
		SwingUtilities.invokeLater(new Runnable() { @Override public void run() {
			window.setVisible(true);	
		}});
	}
	
	/** {@inheritDoc} from {@link MainWindowListener} */
	@Override public final void onQuit() {
		LOG.debug("onQuit()");
		
		this.connection.close();
		this.connection = null;
	}

}
