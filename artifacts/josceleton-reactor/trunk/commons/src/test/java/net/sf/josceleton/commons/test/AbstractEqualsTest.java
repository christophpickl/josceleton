package net.sf.josceleton.commons.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public abstract class AbstractEqualsTest<E> {
	
	/** @return everytime a new, but a same (equal) instance. */
	protected abstract Object createSameTesteeForEquals();
	
	@Test
	public final void testSimpleEquals() {
		final Object testee = this.createSameTesteeForEquals();
		assertThat(testee.hashCode(), equalTo(testee.hashCode()));
		assertThat(testee.equals(testee), equalTo(true));
		
		assertThat(testee.equals(null), equalTo(false));
//		NO! assertThat(testee, not(equalTo(null)));
		
		assertThat(testee.equals("foobar"), equalTo(false));
//		NO! assertThat(testee, not(equalTo((Object) "foobar")));
		
		final Object testee2 = this.createSameTesteeForEquals();
		assertThat("Testees for createSameTesteeForEquals must be equals!\ntestee: " + testee + "\ntestee2: " + testee2,
				testee.equals(testee2), equalTo(true));
	}
	

	protected abstract EqualsDescriptor<E> createEqualsDescriptor();
	
	@Test
	public final void testObjectHashcodeAndEquals() {
		final EqualsDescriptor<E> descriptor = this.createEqualsDescriptor();
		final E sameA = descriptor.getSameA();
		final E sameB = descriptor.getSameB();
		this.assertSameObjectByEqualsAndHashcode(sameA, sameB);
		
		for (final E currentDifferent : descriptor.getDifferents()) {
			assertThat("Both should not be equal!", sameA.equals(currentDifferent), equalTo(false));
			assertThat("Both should not be equal!", sameB.equals(currentDifferent), equalTo(false));
			
			for (final E currentOtherDifferent : descriptor.getDifferents()) {
				if(currentDifferent == currentOtherDifferent) {
					assertThat(currentDifferent.equals(currentOtherDifferent), equalTo(true));
				} else {
					assertThat(currentDifferent.equals(currentOtherDifferent), equalTo(false));
				}
			}
		}
		
		// sameA/B.hashCode and different.hashCode can be same, still equals is false; you know the story ;)
	}
	
	private void assertSameObjectByEqualsAndHashcode(final E sameA, final E sameB) {
		assertThat("Both should be equal!", sameA, equalTo(sameB));
		assertThat("Both should have same hashcodes!", sameA.hashCode(), equalTo(sameB.hashCode()));
		
		assertThat(sameA, not(equalTo(null)));
	}
}
