package net.sf.josceleton.playground.motion.app2.framework.page.system;

import java.awt.Image;

import net.sf.josceleton.playground.motion.app2.framework.page.Page;

public class LoginPage extends Page {

	public LoginPage(String idOfNextPage, Image continueImage) {
		super("login", new LoginView(idOfNextPage, continueImage));
	}

}
