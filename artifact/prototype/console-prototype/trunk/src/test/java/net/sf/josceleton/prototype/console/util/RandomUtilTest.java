package net.sf.josceleton.prototype.console.util;

import static net.sf.josceleton.commons.test.matcher.JosceletonMatchers.hasSinglePrivateNullifiedConstructorAndInvokeIt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import net.sf.josceleton.prototype.console.util.RandomUtil;

import org.testng.annotations.Test;

@SuppressWarnings({ "unchecked", "boxing" })
public class RandomUtilTest {
	
	// MINOR @TEST genereally all util test cases are bad; as Util concept itself is bad :(
	
	@Test public final void hasUtilityConstructor() {
		assertThat(RandomUtil.class, hasSinglePrivateNullifiedConstructorAndInvokeIt());
	}
	
	@Test public final void randFromZeroToExclusive() {
		final int exclusive = 5;
		for (int i = 0; i < 50; i++) {
			assertThat(RandomUtil.randFromZeroToExclusive(exclusive),
				allOf(greaterThanOrEqualTo(0), lessThan(exclusive))
			);
		}
	}
	
	@Test public final void generateWithinRange() {
		final int deviation = 10;
		final int middle = 50;
		for (int i = 0; i < deviation; i++) {
			assertThat(RandomUtil.generateWithinRange(middle, deviation),
				allOf(greaterThanOrEqualTo(middle - deviation), lessThanOrEqualTo(middle + deviation)));
		}
	}
	
}
