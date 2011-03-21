package net.sf.josceleton.playground.motion.app2.framework;

import java.awt.Image;

public class LoginPage extends Page {

	public LoginPage(String idOfNextPage, Image continueImage) {
		super("login", new LoginView(idOfNextPage, continueImage));
	}

}
