package foo;

public class Something {
	public static void main(String[] args) {
		new Something().foo();
//		Something.say("say 1");
//		Something.sayToPerson("to", "person");
	}
	
	public void foo() {
		System.out.println("within Something.foo()");
	}
	
	public static void say(String message) {
        System.out.println(message);
    }
    
    public static void sayToPerson(String message, String name) {
        System.out.println(name + ", " + message);
    }

	
}
