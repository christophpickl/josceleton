package net.sf.josceleton.prototype.console.glue;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.prototype.console.view.ConsoleWindow;

public interface ConsolePresenterFactory {
	
	ConsolePresenter create(ConsoleWindow window, Connection connection);
	
}
