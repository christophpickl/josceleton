package foo.logic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

public class LogicTest {
	
	@Test public void testitTestit() {
		assertThat(new Logic().testit(), equalTo("a"));
	}
	
}