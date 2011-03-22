package net.sf.josceleton.prototype.console.util;

import java.awt.Color;


/**
 * @since 0.4
 */
public final class ColorUtil {
	
	private ColorUtil() {
		// utility class is not instantiable
	}

	/**
	 * @since 0.4
	 */
	public static Color newRandomColor() {
        final int[] rgb = new int[3];
        final int rgbIndexSelected = RandomUtil.nextIntZeroToExclusive(rgb.length);
        
        for (int i = 0; i < rgb.length; i++) {
                final int middle;
                if(rgbIndexSelected == i) {
                	middle = 230;
                } else {
                	middle = 30;
                }
                rgb[i] = RandomUtil.nextIntWithinRange(middle, 20);
        }
        
        return new Color(rgb[0], rgb[1], rgb[2]);
	}
	
	// LUXURY changeBrightness(Color, volume... can be -100 to +100) => internally uses brighten/darken

	/**
	 * @param volume 0 to 100
	 * @since 0.4
	 */
	public static Color brighten(final Color original, final int volume) {
        return ColorUtil.changeBrightness(original, volume, true);
	}

	/**
	 * @param volume 0 to 100
	 * @since 0.4
	 */
	public static Color darken(final Color original, final int volume) {
		return ColorUtil.changeBrightness(original, volume, false);
	}
	
	private static Color changeBrightness(final Color original, final int volume, final boolean isBrighter) {
		final int volumeAdjusted;
		final int limit;
		if(isBrighter == true) {
			volumeAdjusted = volume;
			limit = 255;
		} else {
			volumeAdjusted = -1 * volume;
			limit = 0;
		}
		
		final int newR = original.getRed() + volumeAdjusted;
		final int newG = original.getGreen() + volumeAdjusted;
		final int newB = original.getBlue() + volumeAdjusted;
		
		return new Color(
			MathUtil.checkForMinOrMax(limit, newR, isBrighter),
			MathUtil.checkForMinOrMax(limit, newG, isBrighter),
			MathUtil.checkForMinOrMax(limit, newB, isBrighter)
		);
	}
}
