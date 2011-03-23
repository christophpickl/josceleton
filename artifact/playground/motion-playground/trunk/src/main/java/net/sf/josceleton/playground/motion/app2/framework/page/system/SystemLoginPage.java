package net.sf.josceleton.playground.motion.app2.framework.page.system;

import net.sf.josceleton.playground.motion.app2.framework.page.Page;
import net.sf.josceleton.playground.motion.app2.framework.view.resources.Images;

public class SystemLoginPage extends Page {
	
	public static final String ID = "__system_login";

	public SystemLoginPage(String idOfNextPage) {
		super(ID, new SystemLoginView(idOfNextPage, Images.NEXT));
	}

}
