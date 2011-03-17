package net.sf.josceleton.prototype.console.notification;

public interface GrowlNotifier {

	GrowlNotifier registerApp();

	/**
	 * Utility method, internally creating a default <code>GrowlNotification</code> object.
	 */
	void show(String title, String description);

	void showNotification(GrowlNotification notification);

}