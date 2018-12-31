package com.github.dragonhht.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 描述.
 * User: huang
 * Date: 18-12-31
 */
public class JdkProxy implements InvocationHandler {

    private Object target;

    public JdkProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Jdk before");
        Object result = method.invoke(target, args);
        System.out.println("Jdk after");
        return result;
    }
}
