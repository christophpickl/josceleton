package net.sf.josceleton.commons.util;

import java.awt.Color;

/**
 * @since 0.4
 */
public final class ColorUtil {
	
	// TODO duplicate class of pulseproject/commons
	
	private ColorUtil() {
		// utility class is not instantiable
	}

	/**
	 * @since 0.4
	 */
	public static Color newRandomColor() {
        final int[] rgb = new int[3];
        final int rgbIndexSelected = RandomUtil.randFromZeroToExclusive(rgb.length);
        
        for (int i = 0; i < rgb.length; i++) {
                final int middle;
                if(rgbIndexSelected == i) {
                	middle = 230;
                } else {
                	middle = 30;
                }
                rgb[i] = RandomUtil.generateWithinRange(middle, 20);
        }
        
        return new Color(rgb[0], rgb[1], rgb[2]);
}

	/**
	 * @param volume 0 to 100
	 * @since 0.4
	 */
	public static Color brighten(final Color original, final int volume) {
        return new Color(
                Math.min(255, original.getRed() + volume),
                Math.min(255, original.getGreen() + volume),
                Math.min(255, original.getBlue() + volume)
        );
	}

	/**
	 * @param volume 0 to 100
	 * @since 0.4
	 */
	public static Color darken(final Color original, final int volume) {
        return new Color(
                Math.max(0, original.getRed() - volume),
                Math.max(0, original.getGreen() - volume),
                Math.max(0, original.getBlue() - volume)
        );
	}
	
}
