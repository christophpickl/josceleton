package net.sf.josceleton.core.api.entity.joint2;

import static net.sf.josceleton.commons.test.matcher.JosceletonMatchers.hasSinglePrivateNullifiedConstructorAndInvokeIt;
import static org.hamcrest.MatcherAssert.assertThat;

import org.testng.annotations.Test;

public class JointPartsTest {
	
	@Test public final void hasSinglePrivateNullifiedConstructor() {
		assertThat(JointParts.class, hasSinglePrivateNullifiedConstructorAndInvokeIt());
	}
	
}
