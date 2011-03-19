package net.sf.josceleton.prototype.console.notification;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

class GrowlNotifierImpl implements GrowlNotifier {
	
	// for java swing native translucent popups see: http://stackoverflow.com/questions/2163544/re-paint-problem-on-translucent-frame-panel-component
	
	private static String COMMON_NOTIFICATION_NAME = "Common Notification";
	
	private final String appName;
	
	private boolean yetRegistered = false;
	
	@Inject GrowlNotifierImpl(@Assisted final String appName) {
		this.appName = this.osascriptSafefyString(appName);
		
	}
	
	public GrowlNotifier registerApp() {
		try {
			final String notificationNames = "{ \"" + COMMON_NOTIFICATION_NAME + "\"}";
			final String iconAppName = "Script Editor";
			execute("register as application \"" + this.appName + "\" " +
						"all notifications " + notificationNames + " " +
						"default notifications " + notificationNames + " " +
						"icon of application \"" + iconAppName + "\"");
		} catch (IOException e) {
			throw new RuntimeException("Could not register growl notifier!", e);
		} catch (InterruptedException e) {
			throw new RuntimeException("Could not register growl notifier (while waiting for process to end)!", e);
		}
		this.yetRegistered = true;
		return this;
	}

		
	public void show(final String title, final String description) {
		this.showNotification(new DefaultGrowlNotification(title, description));
	}
	
	public void showNotification(GrowlNotification message) {
		if(this.yetRegistered == false) {
			System.err.println("GrowlNotifier application was not yet registered!");
			return;
		}
		final String script =
		"notify with " +
			"name \"" + COMMON_NOTIFICATION_NAME + "\" " +
			"title \"" + osascriptSafefyString(message.getTitle()) + "\" " +
			"description \"" + osascriptSafefyString(message.getDescription()) + "\" " +
			"application name \"" + this.appName + "\"";

		try {
			execute(script);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private String osascriptSafefyString(final String in) {
		return in.replace("\"", "'");
	}
	
	private void execute(final String script) throws IOException, InterruptedException {
		final Runtime runtime = Runtime.getRuntime();
		final String[] args = buildArgs(splitLines(script));
		
		final Process process = runtime.exec(args);
		final int retCode = process.waitFor();
		if(retCode != 0) {
			final StringBuilder completeScript = new StringBuilder();
			for (final String arg : args) {
				completeScript.append(arg);
				if(arg.equals("-e") == true) {
					completeScript.append(" ");
				} else {
					completeScript.append("\n");
				}
			}
			System.err.println("Growl returned error code [" + retCode + "] while processing script:\n" + completeScript);
		}
	}
	
	
	private String[] splitLines(final String script) {
		final List<String> lines = new LinkedList<String>();
		lines.add("tell application \"GrowlHelperApp\"");
		
		if(script.contains("\n") == false) {
			lines.add(script);
		} else {
			for(final String scriptLine : script.split("\n")) {
				lines.add(scriptLine);
			}
		}
		lines.add("end tell");
		return lines.toArray(new String[lines.size()]);
	}
	
	private String[] buildArgs(String... lines) {
		String[] args = new String[1 + (lines.length * 2)];
		
		args[0] = "osascript";
		for (int i = 1; i < args.length; i++) {
			args[i] = (i % 2 == 1) ? "-e" : lines[ i / 2 - 1];
		}
		
		return args;
	}
}

/*
http://growl.info/documentation/applescript-support.php

tell application "GrowlHelperApp"
	-- Make a list of all the notification types that this script will ever send:
	set the allNotificationsList to {"notification_name_1", "notification_name_2"}
	
	-- Make a list of the notifications that will be enabled by default.      
	-- Those not enabled by default can be enabled later in the 'Applications' tab of the growl prefpane.
	set the enabledNotificationsList to {"notification_name_1"}
	
	set the sendingApplicationName to "Josceleton Console Prototype"
	
	-- Register our script with growl.
	-- You can optionally (as here) set a default icon for this script's notifications.
	register as application sendingApplicationName all notifications allNotificationsList default notifications enabledNotificationsList icon of application "Script Editor"
	
	--	Send a Notification...
	notify with name "notification_name_1" title "notification1 title" description "notification1 description" application name sendingApplicationName
	notify with name "notification_name_2" title "notification2 title" description "notification2 description" application name sendingApplicationName
	
end tell
*/