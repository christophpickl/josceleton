package net.sf.josceleton.connection.api.service;

import net.sf.josceleton.commons.exception.JosceletonException;
import net.sf.josceleton.core.api.entity.User;
import net.sf.josceleton.core.api.entity.body.BodyPart;

/**
 * @since 0.3
 */
public class MotionCoordinateUnavailableException extends JosceletonException {

	private static final long serialVersionUID = -8993750865068059293L;

	private final User user;
	
	private final BodyPart part;
	
	
	protected MotionCoordinateUnavailableException(final String message, final Throwable cause,
			final User user, final BodyPart part) {
		super(message, cause);
		this.user = user;
		this.part = part;
	}

	/**
	 * @since 0.3
	 */
	public static MotionCoordinateUnavailableException newUnavailable(final User user, final BodyPart part) {
		return MotionCoordinateUnavailableException.newUnavailable(user, part, null);
	}

	/**
	 * @since 0.3
	 */
	public static MotionCoordinateUnavailableException newUnavailable(
			final User user, final BodyPart part, final Throwable cause) {
		return new MotionCoordinateUnavailableException(
			"No coordinates available for part [" + part + "]!", cause, user, part);
	}

	/**
	 * @since 0.3
	 */
	public final User getUser() {
		return this.user;
	}

	/**
	 * @since 0.3
	 */
	public final BodyPart getPart() {
		return this.part;
	}
	
}
