package net.sf.josceleton.prototype.console.view;

import net.sf.josceleton.prototype.console.glue.GlueCodeListener;

public interface ConsoleWindow extends GlueCodeListener /* MINOR only for internal API*/ {
	
	void setVisible(boolean visible);
	
}
