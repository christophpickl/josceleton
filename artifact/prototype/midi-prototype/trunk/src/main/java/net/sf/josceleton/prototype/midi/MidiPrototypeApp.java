package net.sf.josceleton.prototype.midi;

import java.util.Properties;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.sf.josceleton.prototype.midi.util.LogUtil;
import net.sf.josceleton.prototype.midi.util.SomeUtil;
import net.sf.josceleton.prototype.midi.view.MainWindow;
import net.sf.josceleton.prototype.midi.view.ViewMediator;

public class MidiPrototypeApp {

	public static void main(String[] args) throws Exception {
		startUp();
//		PrototypeLogic l = new PrototypeLogic("");
//		l.foo1("myS");
//		l.foo2(42, true, Arrays.asList(Direction.X, Direction.Y));
//		MappingsParser.fooAdd(3, 2);
	}
	
	public static void startUp() {
		final Properties p = SomeUtil.loadPropertiesFromClassPath(MidiPrototypeApp.class.getClassLoader(), "app.properties");
		final String applicationVersion = p.get("app_version").toString();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				final Model model = new Model();
				final ViewMediator mediator = new ViewMediator(applicationVersion, model);
				
				UIManager.put("swing.boldMetal", Boolean.FALSE);

				
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
