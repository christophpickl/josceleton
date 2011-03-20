package net.sf.josceleton.prototype.console.util;

import static net.sf.josceleton.commons.test.matcher.JosceletonMatchers.hasSinglePrivateNullifiedConstructorAndInvokeIt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.awt.Color;

import net.sf.josceleton.prototype.console.util.ColorUtil;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public class ColorUtilTest {

	@Test public final void hasUtilityConstructor() {
		assertThat(ColorUtil.class, hasSinglePrivateNullifiedConstructorAndInvokeIt());
	}
	
	@Test public final void newRandomColor() {
		assertThat(ColorUtil.newRandomColor(), Matchers.notNullValue()); // LUXURY @TEST ;)
	}
	
	@Test public final void brighten() {
		assertColorBrightened(new Color(50, 50, 50), 5, new Color(55, 55, 55));
		assertColorBrightened(new Color(250, 250, 250), 6, new Color(255, 255, 255));
	}
	
	@Test public final void darken() {
		assertColorBrightened(new Color(50, 50, 50), -5, new Color(45, 45, 45));
		assertColorBrightened(new Color(10, 10, 10), -11, new Color(0, 0, 0));
	}
	
	private void assertColorBrightened(final Color c, final int brightenOrDarken, final Color expected) {
		final Color c2;
		if(brightenOrDarken > 0) {
			c2 = ColorUtil.brighten(c, brightenOrDarken);
		} else {
			c2 = ColorUtil.darken(c, Math.abs(brightenOrDarken));
		}
		assertThat(c2.getRed(),   equalTo(expected.getRed()));
		assertThat(c2.getGreen(), equalTo(expected.getGreen()));
		assertThat(c2.getBlue(),  equalTo(expected.getBlue()));
	}
}
