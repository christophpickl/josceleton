package foo.app;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

public class AppTest {
	
	@Test public void testitTestit() {
		assertThat(new App().testit(), equalTo("a"));
	}
	
}