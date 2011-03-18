package net.sf.josceleton.commons.exception;

/**
 * @since 0.1
 */
public abstract class JosceletonException extends RuntimeException {

	private static final long serialVersionUID = 322428616175111188L;

    protected JosceletonException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
