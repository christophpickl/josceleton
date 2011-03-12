package net.sf.josceleton.prototype.midiroute;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.XyzDirection;

public class CoordinatesTransformer {

	public int transformControllerValue(Coordinate coordinate, XyzDirection direction) {
		final float f = direction.extractValue(coordinate);
		
		float roughly127 = f * 120;
		int rounded127 = Math.round(roughly127);
		
		if(roughly127 < 0) roughly127 = 0;
		if(roughly127 > 127) roughly127 = 127;
		
		return rounded127;
	}
}
