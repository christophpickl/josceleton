package net.sf.josceleton.prototype.midi.logic.bindable;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.management.RuntimeErrorException;

import org.aspectj.lang.reflect.MethodSignature;

public aspect BindingAspect {
	

	pointcut bindingSetterMethod() :
		call(@BindingSetter * *.*(**)); // at least one
	

	Object around() : bindingSetterMethod() {
		final MethodSignature signature = (MethodSignature) thisJoinPoint.getSignature();
		
		final Object _target = thisJoinPoint.getTarget();
		if(BindingProvider.class.isAssignableFrom(_target.getClass()) == false) {
			throw new RuntimeException("Anything with @BindingSetter must implement BindingProvider!");
		}
		final BindingProvider bindingProvider = BindingProvider.class.cast(_target);
		
		final Method getter = this.lookupGetterForSetter(bindingProvider, signature);
		final Object oldValue = safeInvokeMethod(bindingProvider, getter);
		
		Object proceedResult = proceed();
		// TODO use thisJoinPoint.getArgs instead!
		final Object newValue = safeInvokeMethod(bindingProvider, getter);
		
		System.out.println("old/new: [" + oldValue + "] / [" + newValue + "]");

		if(oldValue == null && newValue == null) {
			return proceedResult;
		}
		if(oldValue != null && newValue != null && oldValue.equals(newValue)) {
			return proceedResult;
		}
		
		final String key = getKey(signature.getMethod());
		for(BindingListener listener : bindingProvider.getBindingListenersFor(key)) {
			listener.onValueChanged(newValue);
		}
		return proceedResult;
	}
	
	private String getKey(final Method m) {
		for(final Annotation a : m.getAnnotations()) {
			if(a.annotationType() == BindingSetter.class) {
				final BindingSetter bs = BindingSetter.class.cast(a);
				return bs.Key();
			}
		}
		throw new RuntimeException("Could not find @BindingSetter for method: " + m.getName());
	}
	
	private Object safeInvokeMethod(final Object target, final Method method) {
		try {
			return method.invoke(target);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("Could not invoke method!", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Could not invoke method!", e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Could not invoke method!", e);
		}
	}
	
	private Method lookupGetterForSetter(final Object target, final MethodSignature signature) {
		final String getterName = "g" + signature.getName().substring(1);
		for (final Method m : target.getClass().getMethods()) {
			if(m.getName().equals(getterName)) {
				return m;
			}
		}
		throw new RuntimeException("Could not find method [" + getterName + "] for type [" + target.getClass().getName() + "]!");
	}
	
}
