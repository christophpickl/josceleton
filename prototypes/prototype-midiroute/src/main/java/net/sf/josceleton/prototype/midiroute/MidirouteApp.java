package net.sf.josceleton.prototype.midiroute;

import javax.swing.SwingUtilities;

import net.sf.josceleton.prototype.midiroute.view.MainWindow;
import net.sf.josceleton.prototype.midiroute.view.ViewMediator;

public class MidirouteApp {
	
	public static void main(String[] args) throws Exception {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				final ViewMediator mediator = new ViewMediator();
				final MainWindow window = new MainWindow(mediator);
				mediator.setCyclicDependency(window);
				window.setVisible(true);
				window.logWelcomeMessage();
			}
		});
		
	}
	
}
