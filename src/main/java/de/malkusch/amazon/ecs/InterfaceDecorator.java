package de.malkusch.amazon.ecs;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class InterfaceDecorator implements InvocationHandler {
	
	private Object object;
	
	public InterfaceDecorator(Object proxiedObject) {
		this.object = proxiedObject;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		
		Method proxiedMethod = object.getClass().getMethod(method.getName(), method.getParameterTypes());
		return proxiedMethod.invoke(object, args);
	}
	
	@SuppressWarnings("unchecked")
	static public <T> T getProxy(Object object, Class<?>... interfaces) {
		return (T) Proxy.newProxyInstance(
				InterfaceDecorator.class.getClassLoader(),
				interfaces,
				new InterfaceDecorator(object)
			);
	}

}
