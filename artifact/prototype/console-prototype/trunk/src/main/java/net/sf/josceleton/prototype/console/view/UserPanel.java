package net.sf.josceleton.prototype.console.view;

import java.awt.Component;

import net.sf.josceleton.core.api.entity.Coordinate;
import net.sf.josceleton.core.api.entity.joint.Joint;

public interface UserPanel {

	Component asComponent();

	boolean isSkeletonAvailable();

	void setSkeletonAvailableTrue();
	
	void updateCoordinate(final Joint bodyPart, final Coordinate coordinate);
	

}
