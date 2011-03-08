package net.sf.josceleton.core.api.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import net.sf.josceleton.commons.exception.InvalidArgumentException;
import net.sf.josceleton.commons.test.AbstractEqualsTest;
import net.sf.josceleton.commons.test.EqualsDescriptor;
import net.sf.josceleton.commons.test.util.TestUtil;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public abstract class UserTest extends AbstractEqualsTest<User> {
	
	protected abstract User createTestee(int id, int osceletonId);

	@Override protected final Object createSameTesteeForEquals() {
		return this.createTestee(42, 21);
	}

	@Override protected final EqualsDescriptor<User> createEqualsDescriptor() {
		final User sameA = this.createTestee(1, 1);
		final User sameB = this.createTestee(1, 1);
		final User differentC = this.createTestee(2, 1);
		final User differentD = this.createTestee(4, 4);
		return new EqualsDescriptor<User>(sameA, sameB, differentC, differentD);
	}
	
	@DataProvider(name = "provideIllegalConstructorArguments")
	public final Object[][] provideIllegalConstructorArguments() {
		return new Object[][] {
			new Object[] {  1,  0 },
			new Object[] {  1, -1 },
			new Object[] {  0,  1 },
			new Object[] { -1,  1 },
			new Object[] {  2,  4 } // internal (unique) ID must always be higher then osceleton ID
		};
	}
	
	@Test(expectedExceptions = InvalidArgumentException.class, dataProvider = "provideIllegalConstructorArguments")
	public final void passingIllegalConstructorArgumentsFails(final int id, final int osceletonId) {
		this.createTestee(id, osceletonId);
	}

	@DataProvider(name = "provideValidConstructorArguments")
	public final Object[][] provideValidConstructorArguments() {
		return new Object[][] {
			new Object[] {  1,  1 },
			new Object[] {  2,  1 },
			new Object[] { 10, 10 },
			new Object[] { 42, 21 }
		};
	}
	@Test(dataProvider = "provideValidConstructorArguments")
	public final void passingValidConstructorArgumentsAndGettersReturnSame(final int id, final int osceletonId) {
		final User actualUser = this.createTestee(id, osceletonId);
		
		assertThat(actualUser.getId(), equalTo(id));
		assertThat(actualUser.getOsceletonId(), equalTo(osceletonId));
	}
	
	@Test
	public final void someEqualsTests() {
		assertThat(this.createTestee(1, 1), not(equalTo(null)));
		assertThat(this.createTestee(1, 1), equalTo(this.createTestee(1, 1)));
		assertThat(this.createTestee(1, 1), not(equalTo(this.createTestee(2, 2))));
		assertThat(this.createTestee(1, 1), not(equalTo(this.createTestee(2, 1))));
	}
	
	@Test
	public final void equalsTostring() {
		final User u1 = this.createTestee(2, 1);
		TestUtil.assertObjectToString(u1, "2", "1"); 
	}
	
}
