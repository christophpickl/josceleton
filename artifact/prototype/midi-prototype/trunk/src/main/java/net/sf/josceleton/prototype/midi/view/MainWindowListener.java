package net.sf.josceleton.prototype.midi.view;

public interface MainWindowListener {

	void onStop();

	void onStart(String rawMappings, String port);

	void onQuit();

}
