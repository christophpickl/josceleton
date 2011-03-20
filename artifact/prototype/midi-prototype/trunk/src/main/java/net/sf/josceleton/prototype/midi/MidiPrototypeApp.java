package net.sf.josceleton.prototype.midi;

import java.util.Properties;

import javax.swing.SwingUtilities;

import net.sf.josceleton.prototype.midi.logic.bindable.BindingListener;
import net.sf.josceleton.prototype.midi.util.LogUtil;
import net.sf.josceleton.prototype.midi.util.SomeUtil;
import net.sf.josceleton.prototype.midi.view.MainWindow;
import net.sf.josceleton.prototype.midi.view.ViewMediator;

public class MidiPrototypeApp {

	public static void main(String[] args) throws Exception {
//		startUp();
		
		Model m = new Model();
		m.addListenerFor(Model.BIND_MIDI_MAPPINGS, new BindingListener() {
			@Override
			public void onValueChanged(Object newValue) {
				System.out.println("MAIN value changed: ["+newValue+"]");
			}
		});
		
		System.out.println("MAIN: setting midi mappings to BAZ ...");
		m.setMidiMappings("BAZ");
		System.out.println("MAIN: setting midi mappings to BAZ ... END");
	}
	
	public static void startUp() {
		final Properties p = SomeUtil.loadPropertiesFromClassPath(MidiPrototypeApp.class.getClassLoader(), "app.properties");
		final String applicationVersion = p.get("app_version").toString();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				final Model model = new Model();
				final ViewMediator mediator = new ViewMediator(model);
				final MainWindow window = new MainWindow(model, mediator, applicationVersion);
				window.setVisible(true);
				MidiPrototypeApp.logWelcomeMessage();
			}
		});
	}
	
	static void logWelcomeMessage() {
		LogUtil.log("====================================================================");
		LogUtil.log("                               WELCOME");
		LogUtil.log("====================================================================");
		LogUtil.log("");
		LogUtil.log("Enter port, define mappings and hit start ...");
		LogUtil.log("");
		LogUtil.log("");
	}
}
