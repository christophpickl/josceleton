package net.sf.josceleton.prototype.console.glue;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import net.sf.josceleton.connection.api.Connection;
import net.sf.josceleton.connection.api.service.user.UsersCollection;
import net.sf.josceleton.prototype.console.notification.GrowlNotifier;
import net.sf.josceleton.prototype.console.notification.GrowlNotifierFactory;
import net.sf.josceleton.prototype.console.view.ConsoleWindow;

class ConsolePresenterImpl implements ConsolePresenter {
	
	private final GlueCodeFactory glueFactory;

	private final GrowlNotifierFactory growlFactory;
	
	private final ConsoleWindow window;
	
	private final Connection connection;
	
	@Inject ConsolePresenterImpl(
			GlueCodeFactory glueFactory,
			GrowlNotifierFactory growlFactory,
			@Assisted ConsoleWindow window,
			@Assisted Connection connection
			) {
		this.glueFactory = glueFactory;
		this.growlFactory = growlFactory;
		this.window = window;
		this.connection = connection;
	}

	@Override
	public void init() {
		final UsersCollection users = this.connection.getUserService();
		
		final GrowlNotifier growl = this.growlFactory.create("Josceleton Console App").registerApp();
		final GlueCode windowGlue = this.glueFactory.create(this.window, users, growl);
		
		synchronized(windowGlue) {
			// we dont want to have a context switch between the first two lines
			// could be a user change ... but... this is not really very likely!
			// ====> in here we are dealing with milli seconds; osceleton detects users within several seconds :)
			windowGlue.initAvailableUsers();
			this.connection.getUserService().addListener(windowGlue);
			
			this.connection.addListener(windowGlue);
		}
	}
	
	
	
}
