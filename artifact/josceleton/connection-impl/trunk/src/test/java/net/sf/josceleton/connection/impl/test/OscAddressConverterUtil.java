package net.sf.josceleton.connection.impl.test;

import net.sf.josceleton.connection.impl.osc.OscAddress;
import net.sf.josceleton.core.api.entity.user.UserState;

public final class OscAddressConverterUtil {
	
	private OscAddressConverterUtil() {
		// utility method
	}
	
	public static OscAddress convertUserStateToOscAddress(final UserState state) {
		if(state == UserState.WAITING) {
			return OscAddress.NEW_USER;
		}
		
		if(state == UserState.PROCESSING) {
			return OscAddress.NEW_SKEL;
		}
		
		if(state == UserState.DEAD) {
			return OscAddress.LOST_USER;
		}
		
		throw new IllegalArgumentException("Could not find  OscAddress for UserState: " + state);
	}
}
