package net.sf.josceleton.commons.util;

import static net.sf.josceleton.commons.test.matcher.JosceletonMatchers.hasSinglePrivateNullifiedConstructorAndInvokeIt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public class CollectionUtilTest {

	@Test public final void hasUtilityConstructor() {
		assertThat(CollectionUtil.class, hasSinglePrivateNullifiedConstructorAndInvokeIt());
	}

	@Test public final void toUnmodifiableSetProperly() {
		assertThat(CollectionUtil.toUnmodifiableSet("a", "b"), hasItems("a", "b"));
		assertThat(CollectionUtil.toUnmodifiableSet("a", "b", "a").size(), equalTo(2));
	}

	@Test(expectedExceptions = UnsupportedOperationException.class)
	public final void toUnmodifiableSetModificationFails() {
		CollectionUtil.toUnmodifiableSet("a", "b").add("FAILS");
	}
	
	@Test public final void mergeToUnmodifiableSet() {
		assertThat(CollectionUtil.mergeToUnmodifiableSet("a", new String[0]), hasItems("a"));
		assertThat(CollectionUtil.mergeToUnmodifiableSet("a", new String[]{ "b", "c" }), hasItems("a", "b", "c"));
	}
}
