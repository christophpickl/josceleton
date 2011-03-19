package net.sf.josceleton.core.api.test;

import net.sf.josceleton.core.api.entity.joint.Joint;

/**
 * @since 0.3
 */
public class TestableJoint implements Joint {
	
	private final String label;
	
	private final String osceletonId;
	
	public TestableJoint(final String label, final String osceletonId) {
		this.label = label;
		this.osceletonId = osceletonId;
	}
	
	@Override public final String getLabel() {
		return this.label;
	}

	@Override public final String getOsceletonId() {
		return this.osceletonId;
	}
	
}
