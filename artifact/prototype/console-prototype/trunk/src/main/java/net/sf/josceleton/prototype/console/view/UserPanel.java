package net.sf.josceleton.prototype.console.view;

import java.awt.Component;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.location.Coordinate;

public interface UserPanel {

	Component asComponent();

	void updateCoordinate(final Joint movedJoint, final Coordinate coordinate);
	
	void updateSkeletonAvailable();

}
