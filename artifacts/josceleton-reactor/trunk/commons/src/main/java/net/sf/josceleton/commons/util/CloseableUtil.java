package net.sf.josceleton.commons.util;

import java.io.Closeable;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @since 0.4
 */
public final class CloseableUtil {

	// TODO duplicate class of pulseproject/commons
	
	private static final Log LOG = LogFactory.getLog(CloseableUtil.class);
	
	private CloseableUtil() {
		// utility class is not instantiable
	}
	
	/**
	 * @since 0.4
	 */
	public static boolean close(final Closeable closeable) {
        if (closeable == null) {
            return false;
        }
        
        try {
	        closeable.close();
	        return true;
        } catch (final IOException e) {
            LOG.warn("Could not close closeable [" + closeable.getClass().getSimpleName() + "]!", e);
            return false;
        }
	}

}
