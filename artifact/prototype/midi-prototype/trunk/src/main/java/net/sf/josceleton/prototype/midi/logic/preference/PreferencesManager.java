package net.sf.josceleton.prototype.midi.logic.preference;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

class PreferencesManager {
	
	private final Preferences javaPreferences;
	
	PreferencesManager(final Class<?> typeToStorePreferencesFor) {
		this.javaPreferences = Preferences.userNodeForPackage(typeToStorePreferencesFor);
	}
	
	public void setString(final String key, final String value) {
		this.javaPreferences.put(key, value);
		safeFlush();
	}
	
	public void setInteger(final String key, final Integer value) {
		System.out.println("  prefs.put: " + key + " => [" + value+ "]");
		this.javaPreferences.putInt(key, value.intValue());
		safeFlush();
	}

	public String getString(final String key, String def) {
		return this.javaPreferences.get(key, def);
	}

	public Integer getInteger(final String key, int def) {
		return Integer.valueOf(this.javaPreferences.getInt(key, def));
	}

	private void safeFlush() {
		try {
			this.javaPreferences.flush();
		} catch (BackingStoreException e) {
			System.err.println("Could not flush preferences!");
			e.printStackTrace();
		}
	}
}
