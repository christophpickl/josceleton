package net.sf.josceleton.josceleton;

import net.sf.josceleton.connection.api.Connection;

public interface JosceletonFacade {

	Connection openConnection();

	Connection openConnectionOnPort(int port);
	
	// FIXME introduce FUTURE tasktag (get rid of either MINOR or LUXURY)
	
	// FUTURE HitWallGesture gesture = facade.create(HitWallGesture.class).propertyX(xValue).build();
	
}
