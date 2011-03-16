package foo.model;

/**
 * This is class Model.
 * 
 * @since 0.1
 */
public class Model {

	public void doit() {
		System.out.println("Model doit() ... VERSION 0.1");
	}
	
	@Deprecated
	public String testit() {
		return "a";
	}
	
}