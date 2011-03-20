package net.sf.josceleton.prototype.midi;

import java.util.Properties;

import javax.swing.SwingUtilities;

import net.sf.josceleton.prototype.midi.logic.ProtoUtil;
import net.sf.josceleton.prototype.midi.view.MainWindow;
import net.sf.josceleton.prototype.midi.view.ViewMediator;

public class MidiPrototypeApp {

	public static void main(String[] args) throws Exception {

		final Properties p = ProtoUtil.loadPropertiesFromClassPath(MidiPrototypeApp.class.getClassLoader(), "app.properties");
		final String applicationVersion = p.get("app_version").toString();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				final ViewMediator mediator = new ViewMediator();
				final MainWindow window = new MainWindow(mediator, applicationVersion);
				mediator.setCyclicDependency(window);
				window.setVisible(true);
				window.logWelcomeMessage();
			}
		});
		
	}
}
