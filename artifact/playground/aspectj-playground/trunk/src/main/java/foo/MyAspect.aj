package foo;

import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.SourceLocation;

public aspect MyAspect {

//	pointcut callSayMessage() : call(public static void foo.Something.say*(..));
//    before() : callSayMessage() {
//        System.out.println("Good day!");
//    }
//    after() : callSayMessage() {
//        System.out.println("Thank you!");
//    }

	pointcut logMethod() : call(public void foo.Something.foo());
	
	before() : logMethod() {
		final Signature signature = thisJoinPoint.getSignature();
		final String methodName = signature.getName();
		
		final SourceLocation location = thisJoinPoint.getSourceLocation();
		final Class<?> withinType = location.getWithinType();
		final String withTypeName = withinType.getName();
		System.out.println("[LOGGG] " + withTypeName + "#" + methodName + "()");
	}

	after() : logMethod() {
		System.out.println("after");
	}
	
//	pointcut logMethod() : call(@foo.AnnotateLog * *.*(**));
//
//	
//	Object around() : logMethod() {
//		System.out.println("around log POINTCUT 1");
//		Object res = proceed();
//		System.out.println("around log POINTCUT 2");
//		return res;
//	}
}
