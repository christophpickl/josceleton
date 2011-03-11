package net.sf.josceleton.connection.impl.osc;

import com.illposed.osc.OSCPortIn;

public interface OscPortFactory {
	
	OscPort create(OSCPortIn oscPortIn);
	
}
