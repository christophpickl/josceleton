package net.sf.josceleton.commons.collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public class SafeRemoveAddListTest {
	
	@Test(expectedExceptions = RuntimeException.class,
			expectedExceptionsMessageRegExp = "Could not remove .*")
	public final void removeEmptyListFails() {
		new SafeLinkedHashSet<String>().remove("a"); // should fails
	}

	@Test public final void removeOkay() {
		SafeLinkedHashSet<String> list = new SafeLinkedHashSet<String>();
		assertThat(list.add("a"), equalTo(true));
		assertThat(list.remove("a"), equalTo(true));
	}

	@Test(expectedExceptions = RuntimeException.class,
			expectedExceptionsMessageRegExp = "Already added .*")
	public final void addDuplicateFails() {
		SafeLinkedHashSet<String> list = new SafeLinkedHashSet<String>();
		assertThat(list.add("a"), equalTo(true));
		list.add("a"); // should fails
	}

	@Test public final void addSingleOkay() {
		SafeLinkedHashSet<String> list = new SafeLinkedHashSet<String>();
		assertThat(list.add("a"), equalTo(true));
	}
	
	
}
