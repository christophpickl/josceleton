package net.sf.josceleton.prototype.midi;

import java.util.Properties;

import javax.swing.SwingUtilities;

import net.sf.josceleton.prototype.midi.util.SomeUtil;
import net.sf.josceleton.prototype.midi.view.MainWindow;
import net.sf.josceleton.prototype.midi.view.ViewMediator;

public class MidiPrototypeApp {

	public static void main(String[] args) throws Exception {

		final Properties p = SomeUtil.loadPropertiesFromClassPath(MidiPrototypeApp.class.getClassLoader(), "app.properties");
		final String applicationVersion = p.get("app_version").toString();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				final ViewMediator mediator = new ViewMediator();
				final MainWindow window = new MainWindow(mediator, applicationVersion);
				
				mediator.initBySettingCyclicDependency(window);
				window.setVisible(true);
				window.logWelcomeMessage();
			}
		});
		
	}
}
