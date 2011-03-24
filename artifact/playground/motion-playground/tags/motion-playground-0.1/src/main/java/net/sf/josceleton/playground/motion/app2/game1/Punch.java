package net.sf.josceleton.playground.motion.app2.game1;

import java.awt.Image;
import java.awt.Point;

import net.sf.josceleton.core.api.entity.joint.Joint;

public class Punch {
	private final Point location;
	private final Joint joint;
	private final Image image;
	public Punch(Point location, Joint joint, Image image) {
		this.location = location;
		this.joint = joint;
		this.image = image;
	}
	public Point getLocation() {
		return this.location;
	}
	public Joint getJoint() {
		return this.joint;
	}
	public Image getImage() {
		return this.image;
	}
}
