package foo.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

public class ModelTest {
	
	@Test public void testitTestit() {
		assertThat(new Model().testit(), equalTo("a"));
	}
	
}