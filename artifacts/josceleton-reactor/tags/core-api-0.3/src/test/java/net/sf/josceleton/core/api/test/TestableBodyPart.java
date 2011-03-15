package net.sf.josceleton.core.api.test;

import net.sf.josceleton.core.api.entity.body.BodyPart;

/**
 * @since 0.3
 */
public class TestableBodyPart implements BodyPart {
	
	private final String label;
	
	private final String osceletonId;
	
	public TestableBodyPart(final String label, final String osceletonId) {
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
