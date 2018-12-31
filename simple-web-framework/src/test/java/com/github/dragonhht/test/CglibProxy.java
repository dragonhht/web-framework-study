package com.github.dragonhht.test;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 描述.
 * User: huang
 * Date: 18-12-31
 */
public class CglibProxy implements MethodInterceptor {

    public <T> T getProxy(Class<T> tClass) {
        return (T) Enhancer.create(tClass, this);
    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib before");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("cglib after");
        return result;
    }
}
