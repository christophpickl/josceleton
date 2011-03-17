package net.sf.josceleton.prototype.console.notification;

public interface GrowlNotifierFactory {
	
	GrowlNotifier create(final String applicationName);
	
}
