package net.sf.josceleton.core.api.entity;

import static net.sf.josceleton.commons.test.matcher.JosceletonMatchers.hasSinglePrivateNullifiedConstructorAndInvokeIt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import net.sf.josceleton.commons.test.util.TestUtil;
import net.sf.josceleton.core.api.entity.body.Body;
import net.sf.josceleton.core.api.entity.body.BodyPart;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@SuppressWarnings("boxing")
public class BodyTest {
	
	@Test
	public final void hasSinglePrivateNullifiedConstructor() {
		assertThat(Body.class, hasSinglePrivateNullifiedConstructorAndInvokeIt());
	}
	
	@Test
	public final void valuesCountShouldBeEqualsTo17() {
		final Iterable<BodyPart> parts = Body.values();
		final int partsCount = TestUtil.countIterable(parts);
		assertThat(partsCount, equalTo(17));
	}
	
	@DataProvider(name = "provideBodyPartsOsceletonId")
	public final Object[][] provideBodyPartsOsceletonId() {
		return new Object[][] {
			new Object[] { Body.HEAD(), "head" },
			new Object[] { Body.NECK(), "neck" },
			new Object[] { Body.TORSO(), "torso" },
			new Object[] { Body.SHOULDER().LEFT(), "l_shoulder" },
			new Object[] { Body.SHOULDER().RIGHT(), "r_shoulder" },
			new Object[] { Body.ELBOW().LEFT(), "l_elbow" },
			new Object[] { Body.ELBOW().RIGHT(), "r_elbow" },
			new Object[] { Body.HAND().LEFT(), "l_hand" },
			new Object[] { Body.HAND().RIGHT(), "r_hand" },
			new Object[] { Body.HIP().LEFT(), "l_hip" },
			new Object[] { Body.HIP().RIGHT(), "r_hip" },
			new Object[] { Body.KNEE().LEFT(), "l_knee" },
			new Object[] { Body.KNEE().RIGHT(), "r_knee" },
			new Object[] { Body.ANKLE().LEFT(), "l_ankle" },
			new Object[] { Body.ANKLE().RIGHT(), "r_ankle" },
			new Object[] { Body.FOOT().LEFT(), "l_foot" },
			new Object[] { Body.FOOT().RIGHT(), "r_foot" }
		};
	}
	@Test(dataProvider = "provideBodyPartsOsceletonId")
	public final void checkProperBodyPartsOsceletonId(final BodyPart part, final String osceletonId) {
		assertThat(part.getOsceletonId(), equalTo(osceletonId));
	}
	
	@Test
	public final void checkSomePartsLabels() {
		assertThat(Body.HEAD().getLabel(), equalTo("Head"));
		assertThat(Body.HAND().LEFT().getLabel(), equalTo("Left Hand"));
		// okay, we are done ;)
		
	}
	
	
}
