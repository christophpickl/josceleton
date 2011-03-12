package net.sf.josceleton.prototype.midiroute.view;

public interface MainWindowListener {

	void onStop();

	void onStart(String config, String port);

	void onQuit();

}
