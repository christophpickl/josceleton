package net.sf.josceleton.core.impl.entity.location;

import com.google.inject.assistedinject.Assisted;

import net.sf.josceleton.core.api.entity.location.Coordinate;

public interface CoordinateFactory {

	Coordinate create(
			@Assisted("x") float x,
			@Assisted("y") float y,
			@Assisted("z") float z);
	
}
