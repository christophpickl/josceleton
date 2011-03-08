package net.sf.josceleton.core.api.entity.body;

import static net.sf.josceleton.commons.test.matcher.JosceletonMatchers.hasSinglePrivateNullifiedConstructorAndInvokeIt;
import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.annotations.Test;

public class BodyPartsTest {
	
	@Test public final void hasSinglePrivateNullifiedConstructor() {
		assertThat(BodyParts.class, hasSinglePrivateNullifiedConstructorAndInvokeIt());
	}
	
}
