package net.sf.josceleton.core.api.entity.body;

import net.sf.josceleton.commons.exception.JosceletonException;

/**
 * @since 0.4
 */
public class SkeletonCoordinateUnavailableException extends JosceletonException {

	private static final long serialVersionUID = -8993750865068059293L;

	private final BodyPart part;
	
	
	protected SkeletonCoordinateUnavailableException(final String message, final Throwable cause, final BodyPart part) {
		super(message, cause);
		this.part = part;
	}

	/**
	 * @since 0.4
	 */
	public static SkeletonCoordinateUnavailableException newUnavailable(final BodyPart part) {
		return SkeletonCoordinateUnavailableException.newUnavailable(part, null);
	}

	/**
	 * @since 0.4
	 */
	public static SkeletonCoordinateUnavailableException newUnavailable(final BodyPart part, final Throwable cause) {
		return new SkeletonCoordinateUnavailableException(
			"No coordinates available for part [" + part + "]!", cause, part);
	}

	/**
	 * @since 0.4
	 */
	public final BodyPart getPart() {
		return this.part;
	}
	
}
