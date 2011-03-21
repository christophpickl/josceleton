package net.sf.josceleton.playground.motion.app2.framework.page.system;

import net.sf.josceleton.playground.motion.app2.framework.page.Page;
import net.sf.josceleton.playground.motion.app2.framework.view.common.Resources;

public class LoginPage extends Page {

	public LoginPage(String idOfNextPage) {
		super("login", new LoginView(idOfNextPage, Resources.NEXT));
	}

}
