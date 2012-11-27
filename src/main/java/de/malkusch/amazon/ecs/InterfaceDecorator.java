package de.malkusch.amazon.ecs;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * InvocationHandler for decorating objects with an interface at runtime.
 * 
 * The generated classes for the Amazon WS have many identical signatures. But they
 * don't implement common interfaces. To avoid writing boiler plate code you can decorate
 * these objects with interfaces at runtime.
 * 
 * @author Markus Malkusch <markus@malkusch.de>
 */
public class InterfaceDecorator implements InvocationHandler {

	private Object object;

	public InterfaceDecorator(Object proxiedObject) {
		this.object = proxiedObject;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		Method proxiedMethod = object.getClass().getMethod(method.getName(),
				method.getParameterTypes());
		return proxiedMethod.invoke(object, args);
	}

	/**
	 * Returns a proxy with the specified interfaces.
	 * 
	 * <code>
	 * MyInterface proxy = InterfaceDecorator.getProxy(proxied, MyInterface.class);
	 * </code>
	 */
	@SuppressWarnings("unchecked")
	static public <T> T getProxy(Object object, Class<?>... interfaces) {
		return (T) Proxy.newProxyInstance(
				InterfaceDecorator.class.getClassLoader(), interfaces,
				new InterfaceDecorator(object));
	}

}
