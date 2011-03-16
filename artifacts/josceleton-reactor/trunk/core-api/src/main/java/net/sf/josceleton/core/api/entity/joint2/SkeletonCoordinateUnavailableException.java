package net.sf.josceleton.core.api.entity.joint2;

import net.sf.josceleton.commons.exception.JosceletonException;

/**
 * @since 0.4
 */
public class SkeletonCoordinateUnavailableException extends JosceletonException {

	private static final long serialVersionUID = -8993750865068059293L;

	private final Joint joint;
	
	
	protected SkeletonCoordinateUnavailableException(final String message, final Throwable cause, final Joint joint) {
		super(message, cause);
		this.joint = joint;
	}

	/**
	 * @since 0.4
	 */
	public static SkeletonCoordinateUnavailableException newUnavailable(final Joint joint) {
		return SkeletonCoordinateUnavailableException.newUnavailable(joint, null);
	}

	/**
	 * @since 0.4
	 */
	public static SkeletonCoordinateUnavailableException newUnavailable(final Joint joint, final Throwable cause) {
		return new SkeletonCoordinateUnavailableException(
			"No coordinates available for joint [" + joint + "]!", cause, joint);
	}

	/**
	 * @since 0.4
	 */
	public final Joint getJoint() {
		return this.joint;
	}
	
}
