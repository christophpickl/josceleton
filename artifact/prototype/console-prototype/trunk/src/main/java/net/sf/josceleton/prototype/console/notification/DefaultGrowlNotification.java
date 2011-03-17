package net.sf.josceleton.prototype.console.notification;

class DefaultGrowlNotification implements GrowlNotification {
	
	private final String title;
	
	private final String description;
	
	public DefaultGrowlNotification(final String title, final String description) {
		this.title = title;
		this.description = description;
	}
	
	public final String getTitle() {
		return this.title;
	}
	
	public final String getDescription() {
		return this.description;
	}
	
}
