package com.github.dragonhht.framework.aop.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理管理器.
 * User: huang
 * Date: 18-12-31
 */
public class ProxyManager {

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(final Class<T> targetClass, final List<Proxy> proxyList) {
        return (T) Enhancer.create(targetClass,
                (MethodInterceptor) (o, method, objects, methodProxy) ->
                        new ProxyChain(targetClass, o, method, methodProxy, objects, proxyList).doProxyChain());
    }

}
