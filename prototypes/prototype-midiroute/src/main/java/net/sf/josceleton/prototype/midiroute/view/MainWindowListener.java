package net.sf.josceleton.prototype.midiroute.view;

public interface MainWindowListener {

	void onStop();

	void onStart(String rawMappings, String port);

	void onQuit();

}
