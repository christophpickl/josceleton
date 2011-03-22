package net.sf.josceleton.playground.motion.app2.framework.view.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;

import bsh.commands.dir;

import net.sf.josceleton.core.api.entity.joint.Joint;
import net.sf.josceleton.core.api.entity.joint.Joints;
import net.sf.josceleton.core.api.entity.joint.Skeleton;
import net.sf.josceleton.core.api.entity.joint.SymetricJoint;
import net.sf.josceleton.core.api.entity.location.Coordinate;
import net.sf.josceleton.core.api.entity.location.Direction;
import net.sf.josceleton.motion.impl.CoordinateUtil;
import net.sf.josceleton.playground.motion.app2.framework.view.Drawable;
import net.sf.josceleton.playground.motion.app2.framework.view.common.Styles;
import net.sf.josceleton.playground.motion.app2.framework.world.WorldSnapshot;

public class SkeletonDrawer implements Drawable {
	
	private final Dimension size;
	private final Direction direction1;
	private final Direction direction2;
	private final String headerText;
	
	public SkeletonDrawer(Dimension size, Direction direction1, Direction direction2) {
		this.size = size;
		this.direction1 = direction1;
		this.direction2 = direction2;
		this.headerText = "Skeleton " + this.direction1.name() + "/" + this.direction2.name();
	}

	@Override
	public void drawOnPosition(Graphics2D g, int x, int y, WorldSnapshot world) {
		g.setColor(Color.LIGHT_GRAY);
		g.drawRect(x, y, this.size.width, this.size.height);
		
		g.setColor(Styles.TEXT_PRIMARY);
		g.drawString(this.headerText, x + this.size.width / 2 - 30, y + 10);
		
		final Skeleton skeleton = world.getSkeleton();
		if(skeleton == null || skeleton.allCoordinatesAvailable() == false) {
			g.setColor(Color.RED);
			g.drawString("Skeleton Data N/A", x + 10, y + 20);
			return;
		}
		
		drawSimpleJointConnection(g, x, y, Joints.HEAD(), Joints.NECK(), skeleton);
		drawSimpleJointConnection(g, x, y, Joints.NECK(), Joints.TORSO(), skeleton);
		
		drawSingleToSymConnection(g, x, y, Joints.NECK(), Joints.SHOULDER(), skeleton);
		drawSymToSymConnection(g, x, y, Joints.SHOULDER(), Joints.ELBOW(), skeleton);
		drawSymToSymConnection(g, x, y, Joints.ELBOW(), Joints.HAND(), skeleton);
		
		drawSingleToSymConnection(g, x, y, Joints.TORSO(), Joints.HIP(), skeleton);
		drawSymToSymConnection(g, x, y, Joints.HIP(), Joints.KNEE(), skeleton);
		drawSymToSymConnection(g, x, y, Joints.KNEE(), Joints.FOOT(), skeleton);
	}

	private void drawSymToSymConnection(Graphics2D g, int x, int y, SymetricJoint<?, ?, ?> symJoint1,
			SymetricJoint<?, ?, ?> symJoint2, Skeleton skeleton) {
		drawSimpleJointConnection(g, x, y, symJoint1.RIGHT(), symJoint2.RIGHT(), skeleton);
		drawSimpleJointConnection(g, x, y, symJoint1.LEFT(), symJoint2.LEFT(), skeleton);
	}

	private void drawSingleToSymConnection(Graphics2D g, int x, int y, Joint sourceJoint,
			SymetricJoint<?, ?, ?> symmetricJoint, Skeleton skeleton) {
		drawSimpleJointConnection(g, x, y, sourceJoint, symmetricJoint.LEFT(), skeleton);
		drawSimpleJointConnection(g, x, y, sourceJoint, symmetricJoint.RIGHT(), skeleton);
	}

	private void drawSimpleJointConnection(Graphics2D g, int x, int y, Joint joint1, Joint joint2, Skeleton skeleton) {
		Point head = extract2DFrontPosition(joint1, skeleton);
		Point torso = extract2DFrontPosition(joint2, skeleton);
		g.drawLine(x + head.x, y + head.y, x + torso.x, y + torso.y);
	}
	
	private Point extract2DFrontPosition(Joint joint, Skeleton skeleton) {
		final Coordinate jointCoord = skeleton.getNullSafe(joint);
		return new Point(transformNiceJointCoordinate(jointCoord, this.direction1, this.size.width),
						 transformNiceJointCoordinate(jointCoord, this.direction2, this.size.height));
	}
	
	private int transformNiceJointCoordinate(Coordinate jointCoord, Direction direction, int targetMaxValue) {
		float coordValue = direction.extractValue(jointCoord);
		if(coordValue < 0.0F) coordValue = 0.0F;
		if(direction == Direction.Z) coordValue /= 3.0F; // slim it down a bit 
		else if(coordValue > 1.0F) coordValue = 1.0F;
//		if(coordValue < CoordinateUtil.getLowerLimit()) coordValue = CoordinateUtil.getLowerLimit();
//		if(coordValue > CoordinateUtil.getUpperLimit(direction)) coordValue = CoordinateUtil.getUpperLimit(direction);
		return Math.round(coordValue * targetMaxValue);
	}
}
