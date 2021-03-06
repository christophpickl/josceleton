package net.sf.josceleton.core.impl.entity;

import com.google.inject.assistedinject.Assisted;

import net.sf.josceleton.core.api.entity.Coordinate;

public interface CoordinateFactory {

	Coordinate create(
			@Assisted("x") float x,
			@Assisted("y") float y,
			@Assisted("z") float z);
	
}
